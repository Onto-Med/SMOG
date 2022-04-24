package de.imise.excel_api.excel_reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class DynamicTreeTableNode {

  private String name;
  private int rowNum;
  private int colNum;
  private Sheet sheet;
  private Map<Integer, Map<String, DynamicTableField>> table;

  public DynamicTreeTableNode(Cell cell, Map<Integer, Map<String, DynamicTableField>> table) {
    this.name = ExcelReader.getStringValue(cell).get();
    this.rowNum = cell.getRowIndex();
    this.colNum = cell.getColumnIndex();
    this.sheet = cell.getSheet();
    this.table = table;
  }

  public String getName() {
    return name;
  }

  public int getRowNum() {
    return rowNum;
  }

  public int getColNum() {
    return colNum;
  }

  public Map<String, DynamicTableField> getProperties() {
    return table.get(rowNum);
  }

  public Optional<String> getProperty(String propertyName) {
    return getProperties().get(propertyName).getValue();
  }

  public List<DynamicTreeTableNode> getChildren() {
    List<DynamicTreeTableNode> children = new ArrayList<DynamicTreeTableNode>();

    for (Row row : sheet) {
      if (row.getRowNum() > rowNum) {
        Cell nextRootCell = row.getCell(colNum);
        Cell childCell = row.getCell(colNum + 1);

        if (ExcelReader.isEmpty(nextRootCell)) {
          if (!ExcelReader.isEmpty(childCell))
            children.add(new DynamicTreeTableNode(childCell, table));
        } else break;
      }
    }

    return children;
  }

  public String toString(String gap) {
    StringBuffer sb =
        new StringBuffer(gap + name + " :: " + getProperties()).append(System.lineSeparator());
    for (DynamicTreeTableNode childNode : getChildren()) sb.append(childNode.toString(gap + "  "));
    return sb.toString();
  }
}
