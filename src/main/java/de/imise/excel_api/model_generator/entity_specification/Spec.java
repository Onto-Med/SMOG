package de.imise.excel_api.model_generator.entity_specification;

import de.imise.excel_api.util.StrUtil;

public class Spec {

  protected String name;
  protected String javaName;

  public Spec(String name) {
    setName(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
    this.javaName = StrUtil.getJavaName(name);
  }

  public String getJavaName() {
    return javaName;
  }
}
