package de.imise.excel_api.model_generator.entity_specification;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TableSpec extends Spec {
	
	private int firstRow;
	private int firstCol;
	private int lastCol;
	private Map<String, FieldSpec> fields;
	private FieldSpec colRefOwn;
	private String[] colRefForeign;
	
	public TableSpec(String name, int firstRow, int firstCol) {
		super(name);
		this.firstRow = firstRow;
		this.firstCol = firstCol;
		this.fields = new HashMap<>();
	}

	public void addField(FieldSpec field) {
		fields.put(field.getName(), field);
		if (field.hasColRef()) {
			colRefOwn = field;
			colRefForeign = field.getColRef();
		}
	}
	
	public boolean hasColRef() {
		return colRefOwn != null;
	}
	
	public FieldSpec getColRefOwn() {
		return colRefOwn;
	}
	
	public String getColRefForeignTable() {
		return colRefForeign[0].trim();
	}

	public String getColRefForeignColumn() {
		return colRefForeign[1].trim();
	}
	
	public int getFirstRow() {
		return firstRow;
	}

	public int getFirstCol() {
		return firstCol;
	}

	public int getLastCol() {
		return lastCol;
	}

	public void setLastCol(int lastCol) {
		this.lastCol = lastCol;
	}

	public Collection<FieldSpec> getFields() {
		return fields.values();
	}

	public FieldSpec getField(String name) {
		return fields.get(name);
	}
	
	public int getFieldsNumber() {
		return fields.size();
	}

	@Override
	public String toString() {
		return "TableSpec [name=" + name + ", fields=" + fields + "]";
	}
}
