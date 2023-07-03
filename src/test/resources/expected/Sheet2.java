package test.model;

import java.util.*;
import org.apache.poi.ss.usermodel.*;
import de.imise.excel_api.excel_reader.*;
import de.imise.excel_api.excel_writer.ExcelWriter;

public class Sheet2 {

  private Sheet sheet;

  public Sheet2(Sheet sheet) {
    this.sheet = sheet;
  }

  public List<Sheet2TreeTab1Node> getTreeTab1() {
    int headRowNum = 0;
    int markCellColNum = 0;
    int firstColNum =
        ExcelReader.getNextNotEmptyCellNumInRow(sheet.getRow(headRowNum), markCellColNum + 1);
    int lastColNum = firstColNum + 3 - 1;
    Cell markCell = sheet.getRow(headRowNum).getCell(markCellColNum);

    List<Sheet2TreeTab1Node> rootNodes = new ArrayList<>();

    for (Row row : markCell.getSheet()) {
      if (row.getRowNum() > headRowNum) {
        Cell rootCell = row.getCell(markCell.getColumnIndex());

        if (!ExcelReader.isEmpty(rootCell))
          rootNodes.add(new Sheet2TreeTab1Node(rootCell, firstColNum, lastColNum));
      }
    }

    return rootNodes;
  }

  public boolean isEmptyTreeTab1() {
    int headRowNum = 0;
    int markCellColNum = 0;
    int firstColNum =
        ExcelReader.getNextNotEmptyCellNumInRow(sheet.getRow(headRowNum), markCellColNum + 1);
    int lastColNum = firstColNum + 3 - 1;
    return ExcelReader.isEmptyRowPart(sheet, headRowNum + 1, markCellColNum, lastColNum);
  }

  public void clearTreeTab1() {
    for (Row row : sheet) {
      if (row.getRowNum() > 0) {
        for (Cell cell : row) ExcelWriter.clearCell(cell);
      }
    }
  }

  public Sheet2TreeTab1Node addRootNodeInTreeTab1(String nodeName) {
    int headRowNum = 0;
    int markCellColNum = 0;
    int firstColNum =
        ExcelReader.getNextNotEmptyCellNumInRow(sheet.getRow(headRowNum), markCellColNum + 1);
    int lastColNum = firstColNum + 3 - 1;
    ExcelWriter.insertRow(sheet, headRowNum + 1);
    ExcelWriter.setValue(sheet, headRowNum + 1, markCellColNum, nodeName);
    ExcelWriter.copyRowStyles(sheet.getRow(headRowNum + 2), sheet.getRow(headRowNum + 1));
    return new Sheet2TreeTab1Node(
        ExcelWriter.getCell(sheet, headRowNum + 1, markCellColNum), firstColNum, lastColNum);
  }

}