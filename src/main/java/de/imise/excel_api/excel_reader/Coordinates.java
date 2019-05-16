package de.imise.excel_api.excel_reader;

public class Coordinates {

	private int row;
	private int col;
	
	public Coordinates(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void rowAdded(int addedRow) {
		if (addedRow <= row)
			row++;
	}

	public void colAdded(int addedCol) {
		if (addedCol <= col)
			col++;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setCol(int col) {
		this.col = col;
	}

	@Override
	public String toString() {
		return "Coordinates [row=" + row + ", col=" + col + "]";
	}
}
