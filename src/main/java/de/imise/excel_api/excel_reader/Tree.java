package de.imise.excel_api.excel_reader;

import de.imise.excel_api.excel_writer.ExcelWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Tree {

  private final Cell markCell;
  HashMap<Integer, FreePositionTableRecord> table;

  public Tree(Cell markCell) {
    this.markCell = markCell;
  }

  public Tree(Cell markCell, HashMap<Integer, FreePositionTableRecord> table) {
    this.markCell = markCell;
    this.table = table;
  }

  public void addRootNode(String node) {
    ExcelWriter.insertRow(markCell.getSheet(), markCell.getRowIndex() + 1);
    ExcelWriter.setValue(
        markCell.getSheet(), markCell.getRowIndex() + 1, markCell.getColumnIndex(), node);
  }

  public boolean hasTable() {
    return table != null && !table.isEmpty();
  }

  public PositionsInSheet find(String name) {
    PositionsInSheet coordinates = new PositionsInSheet(markCell.getSheet());

    for (TreeNode node : getRootNodes()) {
      if (node.getName().trim().equalsIgnoreCase(name.trim()))
        coordinates.addCoordinates(node.getRowNum(), node.getColNum());

      node.find(name, coordinates);
    }

    return coordinates;
  }

  public void print() {
    for (TreeNode node : getRootNodes()) {
      node.print("");
      System.out.println();
    }
  }

  public List<TreeNode> getRootNodes() {
    List<TreeNode> rootNodes = new ArrayList<>();

    for (Row row : markCell.getSheet()) {
      if (row.getRowNum() > markCell.getRowIndex()) {
        Cell rootCell = row.getCell(markCell.getColumnIndex());

        if (!ExcelReader.isEmpty(rootCell)) {
          if (hasTable()) rootNodes.add(new TreeNode(rootCell, table));
          else rootNodes.add(new TreeNode(rootCell));
        }
      }
    }

    return rootNodes;
  }
}
