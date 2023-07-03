package templates;

import java.util.*;

// _START:_HEADER
import java.io.File;
import org.apache.poi.ss.usermodel.*;
import de.imise.excel_api.excel_reader.ExcelReader;
import de.imise.excel_api.excel_writer.ExcelWriter;

public class _WORKBOOK_T {

  private Workbook workbook;
  private File file;

  public _WORKBOOK_T(File file) {
    this.workbook = ExcelReader.getWorkbook(file);
    this.file = file;
  }

  public void save() {
    ExcelWriter.writeWorkbook(workbook, file);
  }
  // _END:_HEADER

  // _START:get_SHEET_T
  public _SHEET_T get_SHEET_T() {
    return new _SHEET_T(workbook.getSheet("_SHEET_NAME"));
  }
  // _END:get_SHEET_T

  // _START:get_SHEET_TList:import java.util.*;
  public List<_SHEET_T> get_SHEET_TList() {
    List<_SHEET_T> objects = new ArrayList<>();

    for (Sheet sheet : ExcelReader.getClassSheets(workbook, "_CLASS_NAME"))
      objects.add(new _SHEET_T(sheet));

    return objects;
  }
  // _END:get_SHEET_TList

}