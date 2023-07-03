package test.model;

import java.io.File;
import org.apache.poi.ss.usermodel.*;
import de.imise.excel_api.excel_reader.ExcelReader;
import de.imise.excel_api.excel_writer.ExcelWriter;

public class TestWorkbook {

  private Workbook workbook;
  private File file;

  public TestWorkbook(File file) {
    this.workbook = ExcelReader.getWorkbook(file);
    this.file = file;
  }

  public void save() {
    ExcelWriter.writeWorkbook(workbook, file);
  }

  public Sheet1 getSheet1() {
    return new Sheet1(workbook.getSheet("Sheet1"));
  }

  public Sheet2 getSheet2() {
    return new Sheet2(workbook.getSheet("Sheet2"));
  }

  public Sheet3 getSheet3() {
    return new Sheet3(workbook.getSheet("Sheet3"));
  }

}