package de.imise.excel_api.owl_export;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyReader {

  private static final String VALUE_SPLIT = "\\s*\\|\\s*";
  private static final String PROPERTY_SPLIT = "\\s*\\\\\\s*";
  private static final String ATTRIBUTE_SPLIT = "\\s*:\\s*";
  private static final String OPEN_BRACKET = "{";
  private static final String CLOSED_BRACKET = "}";
  public static final List<String> REF_ANNOTATION = List.of("ref", "ref-a");
  public static final List<String> REF_RESTRICTION = List.of("ref-r");

  public static List<PropertySpec> getProperties(String propSpec, String valSpec) {
    List<PropertySpec> props = new ArrayList<>();
    String[] vals = valSpec.split(VALUE_SPLIT);
    for (String val : vals) props.add(getProperty(propSpec, val));
    return props;
  }

  private static PropertySpec getProperty(String propSpec, String valSpec) {
    String propMainPart = getMainPart(propSpec);
    String propAddPart = getAdditionalPart(propSpec);
    String valMainPart = getMainPart(valSpec);
    String valAddPart = getAdditionalPart(valSpec);

    Property mainProp = new Property(propMainPart.split(ATTRIBUTE_SPLIT), valMainPart);
    PropertySpec propertySpec = new PropertySpec(mainProp);
    if (propAddPart != null) propertySpec.setAdditionalProperties(getProperties(propAddPart));
    if (valAddPart != null) propertySpec.setValueProperties(getProperties(valAddPart));

    return propertySpec;
  }

  private static List<Property> getProperties(String spec) {
    return Stream.of(spec.split(PROPERTY_SPLIT))
        .map(p -> new Property(p.split(ATTRIBUTE_SPLIT)))
        .collect(Collectors.toList());
  }

  private static String getMainPart(String spec) {
    return (spec.contains(OPEN_BRACKET))
        ? spec.substring(0, spec.indexOf(OPEN_BRACKET)).trim()
        : spec;
  }

  private static String getAdditionalPart(String spec) {
    return (spec.contains(OPEN_BRACKET))
        ? spec.substring(spec.indexOf(OPEN_BRACKET) + 1, spec.indexOf(CLOSED_BRACKET)).trim()
        : null;
  }
}
