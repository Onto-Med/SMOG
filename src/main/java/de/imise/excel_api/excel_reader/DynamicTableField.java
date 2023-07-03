package de.imise.excel_api.excel_reader;

import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;

public record DynamicTableField(Optional<String> value, int rowNum, int colNum) {
  public DynamicTableField(Cell cell, int rowNum, int colNum) {
    this(ExcelReader.getStringValue(cell), rowNum, colNum);
  }
}
