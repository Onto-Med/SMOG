package de.imise.excel_api.model_generator.entity_specification;

public class DynamicTableSpec extends Spec {

  private final int row;
  private final int col;

  public DynamicTableSpec(String name, int row, int col) {
    super(name);
    this.row = row;
    this.col = col;
  }

  @Override
  public String toString() {
    return "DynamicTableSpec [row=" + row + ", col=" + col + ", name=" + name + "]";
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }
}
