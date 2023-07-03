package de.imise.excel_api.excel_reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class DynamicTable {

  private Cell markCell;
  private Map<Integer, String> header;
  private List<Integer> headerNumbers;

  public DynamicTable(Cell markCell) {
    this.markCell = markCell;
    setHeader();
  }

  public List<Map<String, DynamicTableField>> getRecordsAsMaps() {
    List<Map<String, DynamicTableField>> records = new ArrayList<Map<String, DynamicTableField>>();

    for (int i = markCell.getRowIndex() + 2; true; i++) {
      Row row = markCell.getSheet().getRow(i);
      if (row == null
          || ExcelReader.isEmptyRowPart(
              row, headerNumbers.get(0), headerNumbers.get(headerNumbers.size() - 1))) break;
      else {
        Map<String, DynamicTableField> record = new HashMap<String, DynamicTableField>();

        for (int headNum : headerNumbers)
          record.put(
              header.get(headNum),
              new DynamicTableField(row.getCell(headNum), row.getRowNum(), headNum));

        records.add(record);
      }
    }

    return records;
  }

  public Map<Integer, Map<String, DynamicTableField>> getNumberedRecordsAsMaps() {
    Map<Integer, Map<String, DynamicTableField>> records =
        new HashMap<Integer, Map<String, DynamicTableField>>();

    for (int i = markCell.getRowIndex() + 2; true; i++) {
      Row row = markCell.getSheet().getRow(i);
      if (row == null
          || ExcelReader.isEmptyRowPart(
              row, headerNumbers.get(0), headerNumbers.get(headerNumbers.size() - 1))) break;
      else {
        Map<String, DynamicTableField> record = new HashMap<String, DynamicTableField>();

        for (int headNum : headerNumbers)
          record.put(
              header.get(headNum),
              new DynamicTableField(row.getCell(headNum), row.getRowNum(), headNum));

        records.put(row.getRowNum(), record);
      }
    }

    return records;
  }

  public List<List<DynamicTableField>> getRecordsAsLists() {
    List<List<DynamicTableField>> records = new ArrayList<List<DynamicTableField>>();

    for (int i = markCell.getRowIndex() + 2; true; i++) {
      Row row = markCell.getSheet().getRow(i);
      if (row == null
          || ExcelReader.isEmptyRowPart(
              row, headerNumbers.get(0), headerNumbers.get(headerNumbers.size() - 1))) break;
      else {
        List<DynamicTableField> record = new ArrayList<DynamicTableField>();

        for (int headNum : headerNumbers)
          record.add(new DynamicTableField(row.getCell(headNum), row.getRowNum(), headNum));

        records.add(record);
      }
    }

    return records;
  }

  public Map<Integer, List<DynamicTableField>> getNumberedRecordsAsLists() {
    Map<Integer, List<DynamicTableField>> records = new HashMap<Integer, List<DynamicTableField>>();

    for (int i = markCell.getRowIndex() + 2; true; i++) {
      Row row = markCell.getSheet().getRow(i);
      if (row == null
          || ExcelReader.isEmptyRowPart(
              row, headerNumbers.get(0), headerNumbers.get(headerNumbers.size() - 1))) break;
      else {
        List<DynamicTableField> record = new ArrayList<DynamicTableField>();

        for (int headNum : headerNumbers)
          record.add(new DynamicTableField(row.getCell(headNum), row.getRowNum(), headNum));

        records.put(row.getRowNum(), record);
      }
    }

    return records;
  }

  public Map<Integer, List<DynamicTableField>> findRecords(String text, int colNum) {
    Map<Integer, List<DynamicTableField>> records = new HashMap<Integer, List<DynamicTableField>>();
    Map<Integer, List<DynamicTableField>> allRecords = getNumberedRecordsAsLists();

    for (Integer rowNum : allRecords.keySet()) {
      List<DynamicTableField> record = allRecords.get(rowNum);
      DynamicTableField field = record.get(colNum);
      Optional<String> fieldValOpt = field.value();
      if (fieldValOpt.isPresent()) {
        String fieldVal = fieldValOpt.get();
        if (fieldVal.trim().equalsIgnoreCase(text.trim())) records.put(rowNum, record);
      }
    }

    return records;
  }

  private void setHeader() {
    header = new HashMap<Integer, String>();
    headerNumbers = new ArrayList<Integer>();

    for (Cell head : ExcelReader.getNextRow(markCell)) {
      if (head.getColumnIndex() >= markCell.getColumnIndex()) {

        if (ExcelReader.isEmpty(head)) break;

        header.put(head.getColumnIndex(), ExcelReader.getStringValue(head).get());
        headerNumbers.add(head.getColumnIndex());
      }
    }
  }

  public int getHeadColumnIndex(int colNum) {
    return headerNumbers.get(colNum);
  }

  public void print() {
    for (Map<String, DynamicTableField> record : getRecordsAsMaps()) {
      System.out.println(record);
    }

    System.out.println();

    for (List<DynamicTableField> record : getRecordsAsLists()) {
      System.out.println(record);
    }

    System.out.println();

    for (Integer num : getNumberedRecordsAsMaps().keySet()) {
      System.out.println(num + " :: " + getNumberedRecordsAsMaps().get(num));
    }
  }
}
