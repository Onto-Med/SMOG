package de.imise.excel_api.model_generator.entity_specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectSpec extends Spec {
	
	private List<FieldSpec> fields;
	private Map<String, TableSpec> tables;
	private List<DynamicTableSpec> dynamicTables;
	private List<TableSpec> freePositionTables;
	private List<TreeSpec> trees;
	private List<TreeTableSpec> treeTables;
	
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

	public List<TableSpec> getFreePositionTables() {
		return freePositionTables;
	}

	public List<FieldSpec> getFields() {
		return fields;
	}

	public Collection<TableSpec> getTables() {
		return tables.values();
	}

	public TableSpec getTable(String name) {
		return tables.get(name);
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
		return fields.size() == 0 && tables.size() == 0 && dynamicTables.size() == 0 && freePositionTables.size() == 0 && trees.size() == 0 && treeTables.size() == 0;
	}

	@Override
	public String toString() {
		return "ObjectSpec [fields=" + fields + ", tables=" + tables + ", dynamicTables=" + dynamicTables
				+ ", freePositionTables=" + freePositionTables + ", trees=" + trees + ", treeTables=" + treeTables
				+ "]";
	}
}
