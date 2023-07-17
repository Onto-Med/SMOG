package de.imise.excel_api.excel_writer;

import de.imise.excel_api.excel_reader.ExcelReader;
import de.imise.excel_api.util.StrUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ExcelWriter {

  public static void writeWorkbook(Workbook wb, File file) {
    try {
      var fileOut = new FileOutputStream(file);
      wb.write(fileOut);
      fileOut.close();
    } catch (IOException e) {
      System.out.println("Error by writing workbook to " + file.getName());
      e.printStackTrace();
    }
  }

  public static void setValue(Row row, int colNum, String value) {
    getCell(row, colNum).setCellValue(value);
  }

  public static void setValue(Row row, int colNum, int value) {
    Cell c = getCell(row, colNum);
    c.setCellValue(value);
  }

  public static void setValue(Row row, int colNum, double value) {
    Cell c = getCell(row, colNum);
    c.setCellValue(value);
  }

  public static void setValue(Row row, int colNum, Date value) {
    Cell c = getCell(row, colNum);
    c.setCellValue(value);
  }

  public static void setDateValue(Row row, int colNum, String value) throws ParseException {
    Optional<Date> date = StrUtil.parseDate(value);
    if (date.isPresent()) setValue(row, colNum, date.get());
    else throw new ParseException("Wrong date format: '" + value + "'!", 0);
  }

  public static void setValue(Row row, int colNum, boolean value) {
    getCell(row, colNum).setCellValue(value);
  }

  public static void setValue(Sheet sheet, int rowNum, int colNum, String value) {
    setValue(getRow(sheet, rowNum), colNum, value);
  }

  public static void setValue(Sheet sheet, int rowNum, int colNum, int value) {
    setValue(getRow(sheet, rowNum), colNum, value);
  }

  public static void setValue(Sheet sheet, int rowNum, int colNum, double value) {
    setValue(getRow(sheet, rowNum), colNum, value);
  }

  public static void setValue(Sheet sheet, int rowNum, int colNum, Date value) {
    setValue(getRow(sheet, rowNum), colNum, value);
  }

  public static void setDateValue(Sheet sheet, int rowNum, int colNum, String value)
      throws ParseException {
    setDateValue(getRow(sheet, rowNum), colNum, value);
  }

  public static void setValue(Sheet sheet, int rowNum, int colNum, boolean value) {
    setValue(getRow(sheet, rowNum), colNum, value);
  }

  public static void setValues(
      Sheet sheet, int rowNum, int colNum, String separator, String... values) {
    setValues(sheet.getRow(rowNum), colNum, separator, values);
  }

  public static void setValues(
      Sheet sheet, int rowNum, int colNum, String separator, int... values) {
    setValues(sheet.getRow(rowNum), colNum, separator, values);
  }

  public static void setValues(
      Sheet sheet, int rowNum, int colNum, String separator, double... values) {
    setValues(sheet.getRow(rowNum), colNum, separator, values);
  }

  public static void setValues(
      Sheet sheet, int rowNum, int colNum, String separator, Date... values) {
    setValues(sheet.getRow(rowNum), colNum, separator, values);
  }

  public static void setDateValues(
      Sheet sheet, int rowNum, int colNum, String separator, String... values)
      throws ParseException {
    setDateValues(sheet.getRow(rowNum), colNum, separator, values);
  }

  public static void setValues(
      Sheet sheet, int rowNum, int colNum, String separator, boolean... values) {
    setValues(sheet.getRow(rowNum), colNum, separator, values);
  }

  public static void setValues(Row row, int colNum, String separator, String... values) {
    var vals = new StringBuilder();
    for (int i = 0; i < values.length; i++) {
      if (i > 0) vals.append(separator);
      vals.append(values[i]);
    }

    setValue(row, colNum, vals.toString());
  }

  public static void setValues(Row row, int colNum, String separator, int... values) {
    var vals = new StringBuilder();
    for (int i = 0; i < values.length; i++) {
      if (i > 0) vals.append(separator);
      vals.append(values[i]);
    }

    setValue(row, colNum, vals.toString());
  }

  public static void setValues(Row row, int colNum, String separator, double... values) {
    var vals = new StringBuilder();
    for (int i = 0; i < values.length; i++) {
      if (i > 0) vals.append(separator);
      vals.append(values[i]);
    }

    setValue(row, colNum, vals.toString());
  }

  public static void setValues(Row row, int colNum, String separator, Date... values) {
    var vals = new StringBuilder();
    for (int i = 0; i < values.length; i++) {
      if (i > 0) vals.append(separator);
      vals.append(StrUtil.formatDate(values[i]));
    }

    setValue(row, colNum, vals.toString());
  }

  public static void setDateValues(Row row, int colNum, String separator, String... values)
      throws ParseException {
    var vals = new StringBuilder();
    for (int i = 0; i < values.length; i++) {
      if (i > 0) vals.append(separator);

      Optional<Date> date = StrUtil.parseDate(values[i]);
      if (date.isPresent()) vals.append(values[i]);
      else throw new ParseException("Wrong date format: '" + values[i] + "'!", 0);
    }

    setValue(row, colNum, vals.toString());
  }

  public static void setValues(Row row, int colNum, String separator, boolean... values) {
    var vals = new StringBuilder();
    for (int i = 0; i < values.length; i++) {
      if (i > 0) vals.append(separator);
      vals.append(values[i]);
    }

    setValue(row, colNum, vals.toString());
  }

  public static Row getRow(Sheet sheet, int rowNum) {
    Row row = sheet.getRow(rowNum);
    if (row == null) row = sheet.createRow(rowNum);

    return row;
  }

  public static Cell getCell(Sheet sheet, int rowNum, int colNum) {
    return getCell(getRow(sheet, rowNum), colNum);
  }

  public static Cell getCell(Row row, int colNum) {
    Cell cell = row.getCell(colNum);
    if (cell == null) cell = row.createCell(colNum);

    return cell;
  }

  public static void clearCell(Cell cell) {
    if (cell != null) cell.setCellType(CellType.BLANK);
  }

  public static void clearCell(Row row, int colNum) {
    if (row != null) clearCell(row.getCell(colNum));
  }

  public static void clearCell(Sheet sheet, int rowNum, int colNum) {
    clearCell(sheet.getRow(rowNum), colNum);
  }

  public static void clearTable(Sheet sheet, int firstRow, int firstCol, int lastCol) {
    for (int i = firstRow; true; i++) {
      if (ExcelReader.isEmptyRowPart(sheet.getRow(i), firstCol, lastCol)) break;
      else {
        for (int j = firstCol; j <= lastCol; j++) clearCell(sheet, i, j);
      }
    }
  }

  private static void copyCell(Cell oldCell, Cell newCell) {
    switch (oldCell.getCellType()) {
      case BOOLEAN:
        {
          newCell.setCellValue(oldCell.getBooleanCellValue());
          break;
        }
      case NUMERIC:
        {
          newCell.setCellValue(oldCell.getNumericCellValue());
          break;
        }
      case STRING:
        {
          newCell.setCellValue(oldCell.getStringCellValue());
          break;
        }
      case ERROR:
        {
          newCell.setCellValue(oldCell.getErrorCellValue());
          break;
        }
      case FORMULA:
        {
          newCell.setCellFormula(oldCell.getCellFormula());
          break;
        }
      default:
        break;
    }

    copyCellStyles(oldCell, newCell);
  }

  private static void copyCellStyles(Cell oldCell, Cell newCell) {
    newCell.setCellStyle(oldCell.getCellStyle());
  }

  public static void copyRowStyles(Row oldRow, Row newRow) {
    if (oldRow != null)
      for (Cell oldCell : oldRow)
        copyCellStyles(oldCell, getCell(newRow, oldCell.getColumnIndex()));
  }

  public static void addEmptyTableRecord(
      Sheet sheet, int oldRowNum, int newRowNum, int firstColNum, int lastColNum) {
    Row oldRow = sheet.getRow(oldRowNum);
    if (oldRow == null) return;

    for (int i = firstColNum; i <= lastColNum; i++)
      copyCellStyles(getCell(oldRow, i), getCell(sheet, newRowNum, i));
  }

  public static void shiftTableRecordsDown(Sheet sheet, int firstRow, int firstCol, int lastCol) {
    for (int i = ExcelReader.getLastTableRowNum(sheet, firstRow, firstCol, lastCol);
        i >= firstRow;
        i--) copyRowPart(sheet, i, i + 1, firstCol, lastCol);
  }

  public static void shiftTableRecordsUp(Sheet sheet, int firstRow, int firstCol, int lastCol) {
    if (ExcelReader.isEmptyRowPart(sheet.getRow(firstRow), firstCol, lastCol))
      clearRowPart(sheet, firstRow - 1, firstCol, lastCol);

    int lastTabRowNum = ExcelReader.getLastTableRowNum(sheet, firstRow, firstCol, lastCol);

    for (int i = firstRow; i <= lastTabRowNum; i++) copyRowPart(sheet, i, i - 1, firstCol, lastCol);

    clearRowPart(sheet, lastTabRowNum, firstCol, lastCol);
  }

  private static void copyRowPart(
      Sheet sheet, int oldRowNum, int newRowNum, int firstColNum, int lastColNum) {
    for (int i = firstColNum; i <= lastColNum; i++)
      copyCell(getCell(sheet, oldRowNum, i), getCell(sheet, newRowNum, i));
  }

  private static void clearRowPart(Sheet sheet, int rowNum, int firstColNum, int lastColNum) {
    for (int i = firstColNum; i <= lastColNum; i++) clearCell(sheet, rowNum, i);
  }

  public static void insertRow(Sheet sheet, int rowNum) {
    if (rowNum <= sheet.getLastRowNum()) {
      sheet.shiftRows(rowNum, sheet.getLastRowNum(), 1);
      updateCellReferencesForShifting(sheet, rowNum);
    } else sheet.createRow(rowNum);
  }

  @SuppressWarnings("unused")
  public static void insertCol(Sheet sheet, int colNum, int lastColNum) {
    sheet.shiftColumns(colNum, lastColNum, 1);
  }

  private static void updateCellReferencesForShifting(Sheet sheet, int rowNum) {
    for (int nRow = rowNum; nRow <= sheet.getLastRowNum(); ++nRow) {
      final Row row = sheet.getRow(nRow);
      if (row != null) {
        for (Cell c : row) ((XSSFCell) c).updateCellReferencesForShifting("");
      }
    }
  }

  @SuppressWarnings("unused")
  public static void deleteRow(Sheet sheet, int rowNum) {
    sheet.removeRow(sheet.getRow(rowNum));
    if (rowNum < sheet.getLastRowNum()) {
      sheet.shiftRows(rowNum + 1, sheet.getLastRowNum(), -1);
      updateCellReferencesForShifting(sheet, rowNum + 1);
    }
  }

  public static void deleteRows(Sheet sheet, int startRowNum, int endRowNum) {
    for (int i = startRowNum; i <= endRowNum; i++) sheet.removeRow(sheet.getRow(i));

    if (endRowNum < sheet.getLastRowNum()) {
      sheet.shiftRows(endRowNum + 1, sheet.getLastRowNum(), -(endRowNum - startRowNum + 1));
      updateCellReferencesForShifting(sheet, startRowNum);
    }
  }
}
