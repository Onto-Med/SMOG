package test.model;

import java.text.ParseException;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import de.imise.excel_api.excel_reader.ExcelReader;
import de.imise.excel_api.excel_writer.ExcelWriter;

public class Sheet1Tab1Record {

  private Row row;
  private int firstColNum;
  private int lastColNum;

  protected Sheet1Tab1Record(Row row, int firstColNum, int lastColNum) {
    this.row = row;
    this.firstColNum = firstColNum;
    this.lastColNum = lastColNum;
  }

  public int getRowNum() {
    return row.getRowNum();
  }

  public Sheet1Tab1Record insertRecordAfter() {
    ExcelWriter.shiftTableRecordsDown(row.getSheet(), getRowNum() + 1, firstColNum, lastColNum);
    return new Sheet1Tab1Record(
        ExcelWriter.getRow(row.getSheet(), getRowNum() + 1), firstColNum, lastColNum);
  }

  public void deleteRecord() {
    ExcelWriter.shiftTableRecordsUp(row.getSheet(), getRowNum() + 1, firstColNum, lastColNum);
  }

  public Optional<String> getCol1() {
    return ExcelReader.getStringValue(row, 0);
  }

  public void setCol1(String value) {
    ExcelWriter.setValue(row, 0, value);
  }

  public void clearCol1() {
    ExcelWriter.clearCell(row, 0);
  }

  public boolean isEmptyCol1() {
    return ExcelReader.isEmpty(row, 0);
  }

  public Optional<String> getCol2() {
    return ExcelReader.getStringValue(row, 1);
  }

  public void setCol2(String value) {
    ExcelWriter.setValue(row, 1, value);
  }

  public void clearCol2() {
    ExcelWriter.clearCell(row, 1);
  }

  public boolean isEmptyCol2() {
    return ExcelReader.isEmpty(row, 1);
  }

  public Optional<String> getCol3() {
    return ExcelReader.getStringValue(row, 2);
  }

  public void setCol3(String value) {
    ExcelWriter.setValue(row, 2, value);
  }

  public void clearCol3() {
    ExcelWriter.clearCell(row, 2);
  }

  public boolean isEmptyCol3() {
    return ExcelReader.isEmpty(row, 2);
  }

  public Optional<String> getCol4() {
    return ExcelReader.getStringValue(row, 3);
  }

  public void setCol4(String value) {
    ExcelWriter.setValue(row, 3, value);
  }

  public void clearCol4() {
    ExcelWriter.clearCell(row, 3);
  }

  public boolean isEmptyCol4() {
    return ExcelReader.isEmpty(row, 3);
  }

  public Optional<String> getCol5() {
    return ExcelReader.getStringValue(row, 4);
  }

  public void setCol5(String value) {
    ExcelWriter.setValue(row, 4, value);
  }

  public void clearCol5() {
    ExcelWriter.clearCell(row, 4);
  }

  public boolean isEmptyCol5() {
    return ExcelReader.isEmpty(row, 4);
  }

  public Map<String, Object> getRecord() throws ParseException {
    Map<String, Object> record = new LinkedHashMap<>();
    record.put("Col1", getCol1());
    record.put("Col2", getCol2());
    record.put("Col3", getCol3());
    record.put("Col4", getCol4());
    record.put("Col5", getCol5());

    return record;
  }

}