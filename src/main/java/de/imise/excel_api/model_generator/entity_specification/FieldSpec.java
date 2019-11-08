package de.imise.excel_api.model_generator.entity_specification;

import java.util.Optional;

public class FieldSpec extends Spec {

	private String dataType;
	private String listSeparator;
	private Optional<String[]> colRef;
	private int row;
	private int col;
	private int tabColIndex;
	
	public FieldSpec(String name, String dataType, Optional<String[]> colRef, String listSeparator, int row, int col, int tabColIndex) {
		super(name);
		this.dataType = dataType;
		this.listSeparator = listSeparator;
		this.colRef = colRef;
		this.row = row;
		this.col = col;
		this.tabColIndex = tabColIndex;
	}

	public String getDataType() {
		if (hasListDataType())
			return DataType.getDataTypeOfList(dataType);
		else
			return dataType;
	}
	
	public boolean hasListDataType() {
		return DataType.isList(dataType);
	}

	public String getListSeparator() {
		return listSeparator;
	}
	
	public boolean hasColRef() {
		return colRef.isPresent();
	}
	
	public String[] getColRef() {
		return colRef.get();
	}
	
	public String getColRefTable() {
		return colRef.get()[0].trim();
	}

	public String getColRefColumn() {
		return colRef.get()[1].trim();
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getTabColIndex() {
		return tabColIndex;
	}

	@Override
	public String toString() {
		return "FieldSpec [name=" + name + ", dataType=" + dataType + ", row=" + row + ", col=" + col + "]";
	}
}
