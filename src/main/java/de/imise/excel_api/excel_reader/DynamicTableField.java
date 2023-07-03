package de.imise.excel_api.excel_reader;

import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;

public class DynamicTableField {

  private Optional<String> value;
  private int rowNum;
  private int colNum;

  public DynamicTableField(Cell cell, int rowNum, int colNum) {
    this.value = ExcelReader.getStringValue(cell);
    this.rowNum = rowNum;
    this.colNum = colNum;
  }

  public Optional<String> getValue() {
    return value;
  }

  public int getRowNum() {
    return rowNum;
  }

  public int getColNum() {
    return colNum;
  }

  @Override
  public String toString() {
    return "DynamicTableField [" + value + "]";
  }
}
