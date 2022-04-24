package de.imise.excel_api.excel_reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class DynamicTreeTable {

  private Cell markCell;
  private Map<Integer, Map<String, DynamicTableField>> table;

  public DynamicTreeTable(Cell markCell, DynamicTable table) {
    this.markCell = markCell;
    this.table = table.getNumberedRecordsAsMaps();
  }

  public List<DynamicTreeTableNode> getRootNodes() {
    List<DynamicTreeTableNode> rootNodes = new ArrayList<>();

    for (Row row : markCell.getSheet()) {
      if (row.getRowNum() > markCell.getRowIndex()) {
        Cell rootCell = row.getCell(markCell.getColumnIndex());
        if (!ExcelReader.isEmpty(rootCell))
          rootNodes.add(new DynamicTreeTableNode(rootCell, table));
      }
    }

    return rootNodes;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    for (DynamicTreeTableNode node : getRootNodes()) sb.append(node.toString(""));
    return sb.toString();
  }
}
