package de.imise.excel_api.owl_export;

import java.util.ArrayList;
import java.util.List;

public class PropertySpec {

  private final Property mainProperty;
  private List<Property> additionalProperties = new ArrayList<>();
  private List<Property> valueProperties = new ArrayList<>();

  public PropertySpec(Property mainProperty) {
    this.mainProperty = mainProperty;
  }

  public Property getMainProperty() {
    return mainProperty;
  }

  public List<Property> getAdditionalProperties() {
    return additionalProperties;
  }

  public void setAdditionalProperties(List<Property> additionalProperties) {
    this.additionalProperties = additionalProperties;
  }

  public List<Property> getValueProperties() {
    return valueProperties;
  }

  public void setValueProperties(List<Property> valueProperties) {
    this.valueProperties = valueProperties;
  }
}
