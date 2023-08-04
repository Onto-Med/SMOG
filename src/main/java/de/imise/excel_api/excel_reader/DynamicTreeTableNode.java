package de.imise.excel_api.excel_reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public record DynamicTreeTableNode(
    String name,
    int rowNum,
    int colNum,
    Sheet sheet,
    Map<Integer, Map<String, DynamicTableField>> table) {
  public DynamicTreeTableNode(Cell cell, Map<Integer, Map<String, DynamicTableField>> table) {
    this(
        ExcelReader.getStringValue(cell).get(),
        cell.getRowIndex(),
        cell.getColumnIndex(),
        cell.getSheet(),
        table);
  }

  public Optional<String> getProperty(String propertyName) {
    return getProperties().get(propertyName).value();
  }

  public String toString(String gap) {
    var sb = new StringBuilder(gap + name + " :: " + getProperties()).append("\n");
    for (DynamicTreeTableNode childNode : getChildren()) sb.append(childNode.toString(gap + "  "));
    return sb.toString();
  }

  public Map<String, DynamicTableField> getProperties() {
    return table.get(rowNum);
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
}
