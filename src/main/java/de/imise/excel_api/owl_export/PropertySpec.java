package de.imise.excel_api.owl_export;

public class PropertySpec {

  private Property mainProperty;
  private Property additionalProperty;
  private Property valueProperty;

  public PropertySpec(Property mainProperty) {
    this.mainProperty = mainProperty;
  }

  public Property getMainProperty() {
    return mainProperty;
  }

  public Property getAdditionalProperty() {
    return additionalProperty;
  }

  public boolean hasAdditionalProperty() {
    return additionalProperty != null;
  }

  public void setAdditionalProperty(Property additionalProperty) {
    this.additionalProperty = additionalProperty;
  }

  public Property getValueProperty() {
    return valueProperty;
  }

  public boolean hasValueProperty() {
    return valueProperty != null;
  }

  public void setValueProperty(Property valueProperty) {
    this.valueProperty = valueProperty;
  }
}
