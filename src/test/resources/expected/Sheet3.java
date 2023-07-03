package test.model;

import java.util.*;
import org.apache.poi.ss.usermodel.*;
import de.imise.excel_api.excel_reader.*;
import de.imise.excel_api.excel_writer.ExcelWriter;

public class Sheet3 {

  private Sheet sheet;

  public Sheet3(Sheet sheet) {
    this.sheet = sheet;
  }

  public List<Sheet3Tab2Record> getTab2() {
    List<Sheet3Tab2Record> records = new ArrayList<>();
    for (int i = 2; true; i++) {
      Row row = sheet.getRow(i);
      if (ExcelReader.isEmptyRowPart(row, 3, 4)) break;
      else records.add(new Sheet3Tab2Record(row, 3, 4));
    }
    return records;
  }

  public void clearTab2() {
    ExcelWriter.clearTable(sheet, 2, 3, 4);
  }

  public boolean isEmptyTab2() {
    return ExcelReader.isEmptyRowPart(sheet, 2, 3, 4);
  }

  public Sheet3Tab2Record addRecordInTab2() {
    int lastRowNum = ExcelReader.getLastTableRowNum(sheet, 2, 3, 4);

    if (!isEmptyTab2())
      ExcelWriter.addEmptyTableRecord(sheet, lastRowNum, lastRowNum + 1, 3, 4);

    return new Sheet3Tab2Record(ExcelWriter.getRow(sheet, lastRowNum + 1), 3, 4);
  }

  public void deleteLastRecordInTab2() {
    List<Sheet3Tab2Record> records = getTab2();
    records.get(records.size() - 1).deleteRecord();
  }

  public List<Sheet3Tab1Record> getTab1() {
    List<Sheet3Tab1Record> records = new ArrayList<>();
    for (int i = 2; true; i++) {
      Row row = sheet.getRow(i);
      if (ExcelReader.isEmptyRowPart(row, 0, 1)) break;
      else records.add(new Sheet3Tab1Record(row, 0, 1));
    }
    return records;
  }

  public void clearTab1() {
    ExcelWriter.clearTable(sheet, 2, 0, 1);
  }

  public boolean isEmptyTab1() {
    return ExcelReader.isEmptyRowPart(sheet, 2, 0, 1);
  }

  public Sheet3Tab1Record addRecordInTab1() {
    int lastRowNum = ExcelReader.getLastTableRowNum(sheet, 2, 0, 1);

    if (!isEmptyTab1())
      ExcelWriter.addEmptyTableRecord(sheet, lastRowNum, lastRowNum + 1, 0, 1);

    return new Sheet3Tab1Record(ExcelWriter.getRow(sheet, lastRowNum + 1), 0, 1);
  }

  public void deleteLastRecordInTab1() {
    List<Sheet3Tab1Record> records = getTab1();
    records.get(records.size() - 1).deleteRecord();
  }

}