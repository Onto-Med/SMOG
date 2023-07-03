package de.imise.excel_api.model_generator;

import de.imise.excel_api.excel_reader.Coordinates;
import de.imise.excel_api.excel_reader.ExcelReader;
import de.imise.excel_api.model_generator.entity_specification.DynamicTableSpec;
import de.imise.excel_api.model_generator.entity_specification.Entity;
import de.imise.excel_api.model_generator.entity_specification.FieldSpec;
import de.imise.excel_api.model_generator.entity_specification.ObjectSpec;
import de.imise.excel_api.model_generator.entity_specification.TableSpec;
import de.imise.excel_api.model_generator.entity_specification.TreeSpec;
import de.imise.excel_api.model_generator.entity_specification.TreeTableSpec;
import de.imise.excel_api.model_generator.template.Templates;
import de.imise.excel_api.util.StrUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * This class is initialized with a Microsoft Excel template file and is able to generate Java
 * classes according to the annotations in the template. The generated Java classes can be used to
 * read/write from/to Excel files with the same structure as the template file.
 */
public class ModelGenerator {
  private Map<String, ObjectSpec> classes;
  private List<ObjectSpec> objects;
  private File file;

  /**
   * Instantiate a Model Generator object to generate Java classes.
   *
   * @param file a Microsoft Excel template file with annotations
   */
  public ModelGenerator(File file) {
    this.file = file;
    this.classes = new HashMap<>();
    this.objects = new ArrayList<>();
  }

  /**
   * Created Java class files in the given directory by parsing the initial Microsoft Excel
   * template.
   *
   * @param dir the directory where the generated Java class files should be written to
   * @param packageName the package name to be used for the generated Java classes
   */
  public void generate(File dir, String packageName) throws IOException {
    for (Sheet sheet : ExcelReader.getWorkbook(file)) {
      createObjectSpec(sheet);
    }

    if (!dir.exists()) {
      Files.createDirectory(dir.toPath());
    }

    Templates temp = new Templates();
    temp.writeWorkbook(dir, StrUtil.getJavaName(file.getName()), packageName, classes, objects);
  }

  private void createObjectSpec(Sheet sheet) {
    ObjectSpec obj = new ObjectSpec(sheet.getSheetName());
    String clsName = null;

    for (Row row : sheet) {
      for (Cell cell : row) {
        if (ExcelReader.hasMark(cell, Entity.CLASS)) clsName = ExcelReader.getEntityName(cell);
        else if (ExcelReader.hasMark(cell, Entity.FIELD))
          obj.addField(getFieldSpec(cell, ExcelReader.getRightCell(cell), -1));
        else if (ExcelReader.hasMark(cell, Entity.TABLE)) obj.addTable(getTableSpec(cell));
        else if (ExcelReader.hasMark(cell, Entity.TREE))
          obj.addTree(
              new TreeSpec(
                  ExcelReader.getEntityName(cell), cell.getRowIndex(), cell.getColumnIndex()));
        else if (ExcelReader.hasMark(cell, Entity.DYNAMIC_TABLE))
          obj.addDynamicTable(
              new DynamicTableSpec(
                  ExcelReader.getEntityName(cell), cell.getRowIndex(), cell.getColumnIndex()));
        else if (ExcelReader.hasMark(cell, Entity.FREE_POSITION_TABLE))
          obj.addFreePositionTable(getTableSpec(cell));
        else if (ExcelReader.hasMark(cell, Entity.TREE_TABLE))
          obj.addTreeTable(getTreeTableSpec(cell));
      }
    }

    if (clsName == null) {
      if (!obj.isEmpty()) objects.add(obj);
    } else {
      if (classes.get(clsName) == null) {
        obj.setName(clsName);
        classes.put(clsName, obj);
      }
    }
  }

  private FieldSpec getFieldSpec(Cell nameCell, Cell valueCell, int tabColIndex) {
    String name = ExcelReader.getEntityName(nameCell);
    String dataType = ExcelReader.getDataType(nameCell, valueCell);
    String listSeparator = ExcelReader.getListSeparator(nameCell);
    Optional<String[]> colRef = ExcelReader.getColRef(nameCell);
    Coordinates valueCellCoordinates = ExcelReader.getValueCellCoordinatesForNameCell(nameCell);
    return new FieldSpec(
        name,
        dataType,
        colRef,
        listSeparator,
        valueCellCoordinates.getRow(),
        valueCellCoordinates.getCol(),
        tabColIndex);
  }

  private TableSpec getTableSpec(Cell nameCell) {
    return getTableSpec(
        ExcelReader.getEntityName(nameCell),
        ExcelReader.getNextRow(nameCell),
        nameCell.getColumnIndex());
  }

  private TableSpec getTableSpec(String name, Row fieldNamesRow, int firstCol) {
    TableSpec tab = new TableSpec(name, fieldNamesRow.getRowNum() + 1, firstCol);

    for (int i = firstCol, tabColIndex = 0; true; i++, tabColIndex++) {
      Cell fieldNameCell = fieldNamesRow.getCell(i);
      if (ExcelReader.isEmpty(fieldNameCell)) {
        tab.setLastCol(i - 1);
        break;
      }

      tab.addField(
          getFieldSpec(fieldNameCell, ExcelReader.getLowerCell(fieldNameCell), tabColIndex));
    }

    return tab;
  }

  private TreeTableSpec getTreeTableSpec(Cell nameCell) {
    String name = ExcelReader.getEntityName(nameCell);
    TreeSpec treeSpec = new TreeSpec(name, nameCell.getRowIndex(), nameCell.getColumnIndex());
    Row fieldNamesRow = nameCell.getRow();
    int firstCol =
        ExcelReader.getNextNotEmptyCellNumInRow(fieldNamesRow, nameCell.getColumnIndex() + 1);
    TableSpec tabSpec = getTableSpec(name, fieldNamesRow, firstCol);
    return new TreeTableSpec(treeSpec, tabSpec);
  }

  @Override
  public String toString() {
    return "ModelGenerator [classes=" + classes + ", objects=" + objects + "]";
  }
}
