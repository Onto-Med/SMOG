package de.imise.excel_api.model_generator.entity_specification;

public class FieldSpec extends Spec {

	private String dataType;
	private String listSeparator;
	private int row;
	private int col;
	private int tabColIndex;
	
	public FieldSpec(String name, String dataType, String listSeparator, int row, int col, int tabColIndex) {
		super(name);
		this.dataType = dataType;
		this.listSeparator = listSeparator;
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
