package de.imise.excel_api.model_generator.entity_specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectSpec extends Spec {

  private final List<FieldSpec> fields;
  private final Map<String, TableSpec> tables;
  private final List<DynamicTableSpec> dynamicTables;
  private final List<TableSpec> freePositionTables;
  private final List<TreeSpec> trees;
  private final List<TreeTableSpec> treeTables;

  public ObjectSpec(String name) {
    super(name);
    this.fields = new ArrayList<>();
    this.tables = new HashMap<>();
    this.dynamicTables = new ArrayList<>();
    this.freePositionTables = new ArrayList<>();
    this.trees = new ArrayList<>();
    this.treeTables = new ArrayList<>();
  }

  public void addField(FieldSpec field) {
    fields.add(field);
  }

  public void addTable(TableSpec table) {
    tables.put(table.getName(), table);
  }

  public void addDynamicTable(DynamicTableSpec dynamicTable) {
    dynamicTables.add(dynamicTable);
  }

  public void addTree(TreeSpec tree) {
    trees.add(tree);
  }

  public void addFreePositionTable(TableSpec freePositionTable) {
    this.freePositionTables.add(freePositionTable);
  }

  public void addTreeTable(TreeTableSpec treeTable) {
    this.treeTables.add(treeTable);
  }

  public TableSpec getTable(String name) {
    return tables.get(name);
  }

  @Override
  public String toString() {
    return "ObjectSpec [fields="
        + fields
        + ", tables="
        + tables
        + ", dynamicTables="
        + dynamicTables
        + ", freePositionTables="
        + freePositionTables
        + ", trees="
        + trees
        + ", treeTables="
        + treeTables
        + "]";
  }

  public List<TableSpec> getFreePositionTables() {
    return freePositionTables;
  }

  public List<FieldSpec> getFields() {
    return fields;
  }

  public Collection<TableSpec> getTables() {
    return tables.values();
  }

  public List<DynamicTableSpec> getDynamicTables() {
    return dynamicTables;
  }

  public List<TreeSpec> getTrees() {
    return trees;
  }

  public List<TreeTableSpec> getTreeTables() {
    return treeTables;
  }

  public boolean isEmpty() {
    return fields.isEmpty()
        && tables.isEmpty()
        && dynamicTables.isEmpty()
        && freePositionTables.isEmpty()
        && trees.isEmpty()
        && treeTables.isEmpty();
  }
}
