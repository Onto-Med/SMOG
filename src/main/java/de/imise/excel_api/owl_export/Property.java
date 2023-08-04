package de.imise.excel_api.owl_export;

import java.util.Arrays;

public class Property {

  private final String value;
  private String property;
  private String language;
  private int reference = 0;

  public Property(String[] attrs, String value) {
    setAttributes(attrs);
    this.value = value;
  }

  public Property(String[] attrs) {
    setAttributes(Arrays.copyOf(attrs, attrs.length - 1));
    this.value = attrs[attrs.length - 1];
  }

  public boolean hasLanguage() {
    return language != null;
  }

  public boolean hasReference() {
    return reference > 0;
  }

  public boolean hasReferenceAnnotation() {
    return reference == 1;
  }

  public boolean hasReferenceRestriction() {
    return reference == 2;
  }

  public String getProperty() {
    return property;
  }

  public String getLanguage() {
    return language;
  }

  public String getValue() {
    return value;
  }

  private void setAttributes(String[] attrs) {
    this.property = attrs[0];
    if (attrs.length > 1) {
      String attr = attrs[1].toLowerCase();
      if (PropertyReader.REF_ANNOTATION.contains(attr)) this.reference = 1;
      else if (PropertyReader.REF_RESTRICTION.contains(attr)) this.reference = 2;
      else this.language = attr;
    }
  }
}
