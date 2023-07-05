package de.imise.excel_api.excel_reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class TreeNode {

  private final String name;
  private final int rowNum;
  private final int colNum;
  private final Sheet sheet;
  HashMap<Integer, FreePositionTableRecord> table;

  public TreeNode(Cell cell) {
    this.name = ExcelReader.getStringValue(cell).get();
    this.rowNum = cell.getRowIndex();
    this.colNum = cell.getColumnIndex();
    this.sheet = cell.getSheet();
  }

  public TreeNode(Cell cell, HashMap<Integer, FreePositionTableRecord> table) {
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

  public boolean hasTable() {
    return table != null && table.size() > 0;
  }

  public FreePositionTableRecord getRecord() {
    return table.get(rowNum);
  }

  public List<TreeNode> getChildren() {
    List<TreeNode> children = new ArrayList<TreeNode>();

    for (Row row : sheet) {
      if (row.getRowNum() > rowNum) {
        Cell nextRootCell = row.getCell(colNum);
        Cell childCell = row.getCell(colNum + 1);

        if (ExcelReader.isEmpty(nextRootCell)) {
          if (!ExcelReader.isEmpty(childCell)) {
            if (hasTable()) children.add(new TreeNode(childCell, table));
            else children.add(new TreeNode(childCell));
          }
        } else break;
      }
    }

    return children;
  }

  public void print(String gap) {
    if (hasTable()) System.out.println(gap + name + " :: " + getRecord());
    else System.out.println(gap + name);

    for (TreeNode childNode : getChildren()) childNode.print(gap + "  ");
  }

  public void find(String name, PositionsInSheet coordinates) {
    for (TreeNode node : getChildren()) {
      if (node.getName().trim().equalsIgnoreCase(name.trim()))
        coordinates.addCoordinates(node.getRowNum(), node.getColNum());

      node.find(name, coordinates);
    }
  }
}
