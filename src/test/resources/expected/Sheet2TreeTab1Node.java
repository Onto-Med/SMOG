package test.model;

import java.text.ParseException;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import de.imise.excel_api.excel_reader.ExcelReader;
import de.imise.excel_api.excel_writer.ExcelWriter;

public class Sheet2TreeTab1Node {

  private Row row;
  private int rowNum;
  private Sheet sheet;
  private int firstColNum;
  private int lastColNum;

  private String nodeName;
  private int nodeColNum;

  public Sheet2TreeTab1Node(Cell nodeCell, int firstColNum, int lastColNum) {
    this.row = nodeCell.getRow();
    this.rowNum = row.getRowNum();
    this.sheet = row.getSheet();
    this.firstColNum = firstColNum;
    this.lastColNum = lastColNum;

    this.nodeName = ExcelReader.getStringValue(nodeCell).get();
    this.nodeColNum = nodeCell.getColumnIndex();
  }

  public String getNodeName() {
    return nodeName;
  }

  public int getRowNum() {
    return rowNum;
  }

  public List<Sheet2TreeTab1Node> getChildren() {
    List<Sheet2TreeTab1Node> children = new ArrayList<>();

    for (Row row : sheet) {
      if (row.getRowNum() > rowNum) {
        Cell nextSiblingCell = row.getCell(nodeColNum);
        Cell childCell = row.getCell(nodeColNum + 1);

        if (ExcelReader.isEmpty(nextSiblingCell)) {
          if (!ExcelReader.isEmpty(childCell))
            children.add(new Sheet2TreeTab1Node(childCell, firstColNum, lastColNum));
        } else break;
      }
    }

    return children;
  }

  public Sheet2TreeTab1Node addChild(String childNodeName) {
    ExcelWriter.insertRow(sheet, rowNum + 1);
    ExcelWriter.setValue(sheet, rowNum + 1, nodeColNum + 1, childNodeName);
    ExcelWriter.copyRowStyles(sheet.getRow(rowNum), sheet.getRow(rowNum + 1));
    return new Sheet2TreeTab1Node(
        ExcelWriter.getCell(sheet, rowNum + 1, nodeColNum + 1), firstColNum, lastColNum);
  }

  public void deleteNode() {
    for (Row row : sheet) {
      if (row.getRowNum() > rowNum) {
        for (int i = 0; i <= nodeColNum; i++) {
          if (!ExcelReader.isEmpty(row, i)) {
            ExcelWriter.deleteRows(sheet, rowNum, row.getRowNum() - 1);
            return;
          }
        }
      }
    }

    ExcelWriter.deleteRows(sheet, rowNum, sheet.getLastRowNum());
  }

  public List<Integer> getC3() {
    return ExcelReader.getIntegerValues(row, firstColNum + 2, "|");
  }

  public void setC3(int... values) {
    ExcelWriter.setValues(row, firstColNum + 2, "|", values);
  }

  public void clearC3() {
    ExcelWriter.clearCell(row, firstColNum + 2);
  }

  public boolean isEmptyC3() {
    return ExcelReader.isEmpty(row, firstColNum + 2);
  }

  public Optional<String> getC1() {
    return ExcelReader.getStringValue(row, firstColNum + 0);
  }

  public void setC1(String value) {
    ExcelWriter.setValue(row, firstColNum + 0, value);
  }

  public void clearC1() {
    ExcelWriter.clearCell(row, firstColNum + 0);
  }

  public boolean isEmptyC1() {
    return ExcelReader.isEmpty(row, firstColNum + 0);
  }

  public Optional<Integer> getC2() {
    return ExcelReader.getIntegerValue(row, firstColNum + 1);
  }

  public void setC2(int value) {
    ExcelWriter.setValue(row, firstColNum + 1, value);
  }

  public void clearC2() {
    ExcelWriter.clearCell(row, firstColNum + 1);
  }

  public boolean isEmptyC2() {
    return ExcelReader.isEmpty(row, firstColNum + 1);
  }

  public Map<String, Object> getRecord() throws ParseException {
    Map<String, Object> record = new LinkedHashMap<>();
    record.put("c3", getC3());
    record.put("c1", getC1());
    record.put("c2", getC2());

    return record;
  }

}