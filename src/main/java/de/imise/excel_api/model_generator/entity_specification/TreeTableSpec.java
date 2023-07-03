package de.imise.excel_api.model_generator.entity_specification;

public class TreeTableSpec {

  private TreeSpec treeSpec;
  private TableSpec tableSpec;

  public TreeTableSpec(TreeSpec treeSpec, TableSpec tableSpec) {
    this.treeSpec = treeSpec;
    this.tableSpec = tableSpec;
  }

  public TreeSpec getTreeSpec() {
    return treeSpec;
  }

  public TableSpec getTableSpec() {
    return tableSpec;
  }
}
