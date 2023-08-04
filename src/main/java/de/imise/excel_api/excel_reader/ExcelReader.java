package de.imise.excel_api.excel_reader;

import de.imise.excel_api.model_generator.entity_specification.DataType;
import de.imise.excel_api.model_generator.entity_specification.Entity;
import de.imise.excel_api.util.StrUtil;
import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

  public static Workbook getWorkbook(File file) {
    Workbook workbook = null;

    try {
      workbook = new XSSFWorkbook(new FileInputStream(file));
    } catch (Exception e) {
      System.out.println("Error by creating workbook from " + file.getName());
      e.printStackTrace();
    }

    return workbook;
  }

  public static Optional<String> getStringValue(Sheet sheet, int row, int col) {
    return getStringValue(sheet.getRow(row), col);
  }

  public static Optional<Integer> getIntegerValue(Sheet sheet, int row, int col) {
    return getIntegerValue(sheet.getRow(row), col);
  }

  public static Optional<Double> getDoubleValue(Sheet sheet, int row, int col) {
    return getDoubleValue(sheet.getRow(row), col);
  }

  public static Optional<Date> getDateValue(Sheet sheet, int row, int col) {
    return getDateValue(sheet.getRow(row), col);
  }

  public static Optional<Boolean> getBooleanValue(Sheet sheet, int row, int col) {
    return getBooleanValue(sheet.getRow(row), col);
  }

  public static Optional<String> getStringValue(Row row, int col) {
    return getStringValue(row.getCell(col));
  }

  public static Optional<Integer> getIntegerValue(Row row, int col) {
    return getIntegerValue(row.getCell(col));
  }

  public static Optional<Double> getDoubleValue(Row row, int col) {
    return getDoubleValue(row.getCell(col));
  }

  public static Optional<Date> getDateValue(Row row, int col) {
    return getDateValue(row.getCell(col));
  }

  public static Optional<Boolean> getBooleanValue(Row row, int col) {
    return getBooleanValue(row.getCell(col));
  }

  public static Optional<String> getStringValue(Cell cell) {
    if (isEmpty(cell)) return Optional.empty();
    try {
      if (isDate(cell)) return Optional.of(StrUtil.formatDate(cell.getDateCellValue()));
      if (isInteger(cell)) return Optional.of(Integer.toString((int) cell.getNumericCellValue()));
      if (isNumeric(cell)) {
        double dv = cell.getNumericCellValue();
        int iv = (int) dv;
        if (dv == iv) return Optional.of(Integer.toString(iv));
        else return Optional.of(Double.toString(dv));
      }
      if (isBoolean(cell)) return Optional.of(Boolean.toString(cell.getBooleanCellValue()));
      if (isString(cell)) return Optional.of(cell.getStringCellValue().trim());
      if (isFormula(cell)) return Optional.of(cell.getRichStringCellValue().getString());
    } catch (Exception e) {
      throw new RuntimeException(
          "Cannot get string value from cell '"
              + cell
              + "' at "
              + cell.getAddress()
              + " in sheet "
              + cell.getSheet().getSheetName(),
          e);
    }
    return Optional.empty();
  }

  public static Optional<Integer> getIntegerValue(Cell cell) {
    if (isEmpty(cell)) return Optional.empty();
    if (isNumeric(cell)) return Optional.of((int) cell.getNumericCellValue());
    if (isString(cell)) return StrUtil.parseInt(cell.getStringCellValue().trim());

    return Optional.empty();
  }

  public static Optional<Double> getDoubleValue(Cell cell) {
    if (isEmpty(cell)) return Optional.empty();
    if (isNumeric(cell)) return Optional.of(cell.getNumericCellValue());
    if (isString(cell)) return StrUtil.parseDouble(cell.getStringCellValue().trim());

    return Optional.empty();
  }

  public static Optional<Date> getDateValue(Cell cell) {
    if (isEmpty(cell)) return Optional.empty();
    if (isNumeric(cell)) return Optional.of(cell.getDateCellValue());
    if (isString(cell)) return StrUtil.parseDate(cell.getStringCellValue().trim());

    return Optional.empty();
  }

  public static Optional<Boolean> getBooleanValue(Cell cell) {
    if (isEmpty(cell)) return Optional.empty();
    if (isBoolean(cell)) return Optional.of(cell.getBooleanCellValue());
    if (isNumeric(cell)) return Optional.of(cell.getNumericCellValue() > 0);
    if (isString(cell)) return StrUtil.parseBoolean(cell.getStringCellValue().trim());

    return Optional.empty();
  }

  public static List<String> getStringValues(
      Sheet sheet, int rowNum, int colNum, String separator) {
    return getStringValues(sheet.getRow(rowNum), colNum, separator);
  }

  public static List<Integer> getIntegerValues(
      Sheet sheet, int rowNum, int colNum, String separator) {
    return getIntegerValues(sheet.getRow(rowNum), colNum, separator);
  }

  public static List<Double> getDoubleValues(
      Sheet sheet, int rowNum, int colNum, String separator) {
    return getDoubleValues(sheet.getRow(rowNum), colNum, separator);
  }

  public static List<Date> getDateValues(Sheet sheet, int rowNum, int colNum, String separator)
      throws ParseException {
    return getDateValues(sheet.getRow(rowNum), colNum, separator);
  }

  public static List<Boolean> getBooleanValues(
      Sheet sheet, int rowNum, int colNum, String separator) throws ParseException {
    return getBooleanValues(sheet.getRow(rowNum), colNum, separator);
  }

  public static List<String> getStringValues(Row row, int colNum, String separator) {
    List<String> values = new ArrayList<>();
    Collections.addAll(values, getValues(row, colNum, separator));

    return values;
  }

  public static List<Integer> getIntegerValues(Row row, int colNum, String separator) {
    List<Integer> values = new ArrayList<>();
    for (String val : getValues(row, colNum, separator)) values.add(Integer.parseInt(val));

    return values;
  }

  public static List<Double> getDoubleValues(Row row, int colNum, String separator) {
    List<Double> values = new ArrayList<>();
    for (String val : getValues(row, colNum, separator)) values.add(Double.parseDouble(val));

    return values;
  }

  public static List<Date> getDateValues(Row row, int colNum, String separator)
      throws ParseException {
    List<Date> values = new ArrayList<>();
    for (String val : getValues(row, colNum, separator)) {
      Optional<Date> dateOpt = StrUtil.parseDate(val);
      if (dateOpt.isPresent()) values.add(dateOpt.get());
      else throw new ParseException("Wrong date format: '" + val + "'!", 0);
    }

    return values;
  }

  public static List<Boolean> getBooleanValues(Row row, int colNum, String separator)
      throws ParseException {
    List<Boolean> values = new ArrayList<>();
    for (String val : getValues(row, colNum, separator)) {
      Optional<Boolean> boolOpt = StrUtil.parseBoolean(val);
      if (boolOpt.isPresent()) values.add(boolOpt.get());
      else throw new ParseException("Wrong boolean format: '" + val + "'!", 0);
    }

    return values;
  }

  private static String[] getValues(Row row, int colNum, String separator) {
    Optional<String> valuesStr = getStringValue(row, colNum);
    if (!valuesStr.isPresent()) return new String[] {};

    return valuesStr.get().trim().split(" *\\" + separator + " *");
  }

  public static boolean isString(Cell cell) {
    return cell != null && cell.getCellType() == CellType.STRING;
  }

  public static boolean isDate(Cell cell) {
    return (isNumeric(cell) || isBlank(cell)) && hasDateFormat(cell);
  }

  public static boolean isInteger(Cell cell) {
    return (isNumeric(cell) || isBlank(cell)) && hasIntegerFormat(cell);
  }

  public static boolean isDouble(Cell cell) {
    return (isNumeric(cell) || isBlank(cell)) && hasDoubleFormat(cell);
  }

  public static boolean isBoolean(Cell cell) {
    return cell != null && cell.getCellType() == CellType.BOOLEAN;
  }

  public static boolean isNumeric(Cell cell) {
    return cell != null && cell.getCellType() == CellType.NUMERIC;
  }

  public static boolean isBlank(Cell cell) {
    return cell != null && cell.getCellType() == CellType.BLANK;
  }

  public static boolean isFormula(Cell cell) {
    return cell != null && cell.getCellType() == CellType.FORMULA;
  }

  //	private static boolean hasGeneralFormat(Cell cell) {
  //		return cell.getCellStyle().getDataFormatString().equalsIgnoreCase("General");
  //	}
  //
  //	private static boolean hasTextFormat(Cell cell) {
  //		return cell.getCellStyle().getDataFormatString().equals("@");
  //	}

  private static boolean hasDoubleFormat(Cell cell) {
    String format = cell.getCellStyle().getDataFormatString();
    return (format.contains("0") || format.contains("#")) && format.contains(".");
  }

  private static boolean hasIntegerFormat(Cell cell) {
    String format = cell.getCellStyle().getDataFormatString();
    return (format.contains("0") || format.contains("#")) && !format.contains(".");
  }

  private static boolean hasDateFormat(Cell cell) {
    return cell.getCellStyle().getDataFormatString().contains("/");
  }

  public static boolean isEmpty(Cell cell) {
    return cell == null || cell.getCellType() == CellType.BLANK;
  }

  public static boolean isEmpty(Row row, int colNum) {
    if (row == null) return true;

    return isEmpty(row.getCell(colNum));
  }

  public static boolean isEmpty(Sheet sheet, int rowNum, int colNum) {
    return isEmpty(sheet.getRow(rowNum), colNum);
  }

  public static Row getNextRow(Cell cell) {
    return cell.getSheet().getRow(cell.getRowIndex() + 1);
  }

  public static Cell getLowerCell(Cell cell) {
    return getNextRow(cell).getCell(cell.getColumnIndex());
  }

  public static Cell getRightCell(Cell cell) {
    return cell.getRow().getCell(cell.getColumnIndex() + 1);
  }

  public static boolean isEmptyRowPart(Sheet sheet, int rowNum, int firstColNum, int lastColNum) {
    return isEmptyRowPart(sheet.getRow(rowNum), firstColNum, lastColNum);
  }

  public static boolean isEmptyRowPart(Row row, int firstCol, int lastCol) {
    if (row == null) return true;

    for (int i = firstCol; i <= lastCol; i++) {
      if (!isEmpty(row.getCell(i))) return false;
    }

    return true;
  }

  public static List<Sheet> getClassSheets(Workbook workbook, String clsName) {
    List<Sheet> clsSheets = new ArrayList<>();

    for (Sheet sheet : workbook) {
      for (Row row : sheet) {
        for (Cell cell : row) {
          if (hasMark(cell, Entity.CLASS) && clsName.equals(getEntityName(cell)))
            clsSheets.add(sheet);
        }
      }
    }

    return clsSheets;
  }

  public static boolean hasMark(Cell cell, String mark) {
    Optional<String> value = getStringValue(cell);
    return value.isPresent() && value.get().trim().startsWith(mark);
  }

  public static String getEntityName(Cell cell) {
    String mark = "";
    for (String ent : Entity.entities) {
      if (hasMark(cell, ent)) mark = ent;
    }

    return cell.getStringCellValue()
        .replace(mark, "")
        .replaceAll(Entity.DATA_TYPE_PATTERN, "")
        .replaceAll(Entity.LIST_SEPARATOR_PATTERN, "")
        .replaceAll(Entity.COL_REF_PATTERN, "")
        .trim();
  }

  public static Coordinates getValueCellCoordinatesForNameCell(Cell cell) {
    if (hasMark(cell, Entity.FIELD))
      return new Coordinates(cell.getRowIndex(), cell.getColumnIndex() + 1);
    else return new Coordinates(cell.getRowIndex() + 1, cell.getColumnIndex());
  }

  public static String getDataType(Cell nameCell, Cell valueCell) {
    Optional<String> optDt = getSpecifiedDataType(nameCell);
    if (optDt.isPresent()) return optDt.get();

    if (isDate(valueCell)) return DataType.DATE;
    else if (isInteger(valueCell)) return DataType.INTEGER;
    else if (isDouble(valueCell) || isNumeric(valueCell)) return DataType.DOUBLE;
    else if (isBoolean(valueCell)) return DataType.BOOLEAN;
    else return DataType.STRING;
  }

  private static Optional<String> getSpecifiedDataType(Cell cell) {
    Optional<String> value = getStringValue(cell);

    if (value.isPresent()) return StrUtil.findInString(Entity.DATA_TYPE_PATTERN, value.get());
    else return Optional.empty();
  }

  public static String getListSeparator(Cell cell) {
    Optional<String> value = getStringValue(cell);

    if (value.isPresent()) {
      Optional<String> sepOpt = StrUtil.findInString(Entity.LIST_SEPARATOR_PATTERN, value.get());
      if (sepOpt.isPresent()) return sepOpt.get();
    }

    return Entity.DEFAULT_LIST_SEPARATOR;
  }

  public static Optional<String[]> getColRef(Cell cell) {
    Optional<String> value = getStringValue(cell);

    if (value.isPresent()) {
      Optional<String> ref = StrUtil.findInString(Entity.COL_REF_PATTERN, value.get());
      if (ref.isPresent()) {
        String[] refAr = ref.get().split(Entity.COL_REF_SEPARATOR);
        if (refAr != null
            && refAr.length == 2
            && refAr[0] != null
            && !refAr[0].trim().isEmpty()
            && refAr[1] != null
            && !refAr[1].trim().isEmpty()) return Optional.of(refAr);
      }
    }

    return Optional.empty();
  }

  public static Tree getTree(Cell markCell) {
    return new Tree(markCell);
  }

  public static Tree getTree(Sheet sheet, int row, int col) {
    return new Tree(sheet.getRow(row).getCell(col));
  }

  public static Tree getTree(
      Sheet sheet, int row, int col, HashMap<Integer, FreePositionTableRecord> table) {
    return new Tree(sheet.getRow(row).getCell(col), table);
  }

  public static DynamicTable getDynamicTable(Cell markCell) {
    return new DynamicTable(markCell);
  }

  public static DynamicTable getDynamicTable(Sheet sheet, int row, int col) {
    return new DynamicTable(sheet.getRow(row).getCell(col));
  }

  public static DynamicTreeTable getDynamicTreeTable(
      Sheet sheet, int treeRow, int treeCol, int tabRow, int tabCol) {
    return new DynamicTreeTable(
        sheet.getRow(treeRow).getCell(treeCol), getDynamicTable(sheet, tabRow, tabCol));
  }

  public static DynamicTreeTable getDynamicTreeTable(
      String file, String sheet, int treeRow, int treeCol, int tabRow, int tabCol) {
    return getDynamicTreeTable(
        getWorkbook(new File(file)).getSheet(sheet), treeRow, treeCol, tabRow, tabCol);
  }

  public static List<DynamicTreeTable> getDynamicTreeTables(String file) {
    List<DynamicTreeTable> treeTabs = new ArrayList<>();
    for (Sheet sheet : getWorkbook(new File(file))) {
      DynamicTreeTable treeTab = getDynamicTreeTable(sheet);
      if (treeTab != null) treeTabs.add(treeTab);
    }
    return treeTabs;
  }

  public static DynamicTreeTable getDynamicTreeTable(String file, String sheetName) {
    return getDynamicTreeTable(getWorkbook(new File(file)), sheetName);
  }

  public static DynamicTreeTable getDynamicTreeTable(Workbook workbook, String sheetName) {
    return getDynamicTreeTable(workbook.getSheet(sheetName));
  }

  public static DynamicTreeTable getDynamicTreeTable(Sheet sheet) {
    Optional<Coordinates> treeCoord = find("Tree", sheet);
    Optional<Coordinates> tabCoord = find("Table", sheet);
    if (treeCoord.isEmpty() || tabCoord.isEmpty()) return null;
    return getDynamicTreeTable(
        sheet,
        treeCoord.get().getRow(),
        treeCoord.get().getCol(),
        tabCoord.get().getRow(),
        tabCoord.get().getCol());
  }

  public static Optional<Coordinates> find(String mark, String entityName, Sheet sheet) {
    for (Row row : sheet) {
      for (Cell cell : row) {
        if (hasMark(cell, mark) && getEntityName(cell).equals(entityName))
          return Optional.of(new Coordinates(cell.getRowIndex(), cell.getColumnIndex()));
      }
    }

    return Optional.empty();
  }

  public static Optional<Coordinates> find(String mark, Sheet sheet) {
    for (Row row : sheet) {
      for (Cell cell : row) {
        if (hasMark(cell, mark))
          return Optional.of(new Coordinates(cell.getRowIndex(), cell.getColumnIndex()));
      }
    }

    return Optional.empty();
  }

  public static int getNextNotEmptyCellNumInRow(Row row, int startIndex) {
    for (int i = startIndex; true; i++) {
      Cell cell = row.getCell(i);
      if (!isEmpty(cell)) return i;
    }
  }

  public static int getLastTableRowNum(Sheet sheet, int firstRow, int firstCol, int lastCol) {
    Row row = sheet.getRow(firstRow);
    if (isEmptyRowPart(row, firstCol, lastCol)) return firstRow - 1;

    for (int i = firstRow + 1; true; i++) {
      if (isEmptyRowPart(sheet.getRow(i), firstCol, lastCol)) return i - 1;
    }
  }
}
