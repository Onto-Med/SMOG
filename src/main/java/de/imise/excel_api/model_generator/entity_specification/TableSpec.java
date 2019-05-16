package de.imise.excel_api.model_generator.entity_specification;

import java.util.ArrayList;
import java.util.List;

public class TableSpec extends Spec {
	
	private int firstRow;
	private int firstCol;
	private int lastCol;
	private List<FieldSpec> fields;
	
	public TableSpec(String name, int firstRow, int firstCol) {
		super(name);
		this.firstRow = firstRow;
		this.firstCol = firstCol;
		this.fields = new ArrayList<>();
	}

	public void addField(FieldSpec field) {
		fields.add(field);
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

	public List<FieldSpec> getFields() {
		return fields;
	}
	
	public int getFieldsNumber() {
		return fields.size();
	}

	@Override
	public String toString() {
		return "TableSpec [name=" + name + ", fields=" + fields + "]";
	}
}
