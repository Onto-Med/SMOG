package de.imise.excel_api.excel_writer;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class CStyle {

  private CellStyle cellStyle;
  private Font font;

  public CStyle(Workbook wb) {
    this.cellStyle = wb.createCellStyle();
    this.font = wb.createFont();
  }

  public CStyle foregroundColor(IndexedColors foregroundColor) {
    cellStyle.setFillForegroundColor(foregroundColor.getIndex());
    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    return this;
  }

  public CStyle fontColor(IndexedColors fontColor) {
    font.setColor(fontColor.getIndex());
    return this;
  }

  public CStyle fontBold() {
    font.setBold(true);
    return this;
  }

  public CStyle fontItalic() {
    font.setItalic(true);
    return this;
  }

  public CStyle wrapText() {
    cellStyle.setWrapText(true);
    return this;
  }

  public CStyle border(BorderStyle borderStyle) {
    cellStyle.setBorderBottom(borderStyle);
    cellStyle.setBorderTop(borderStyle);
    cellStyle.setBorderLeft(borderStyle);
    cellStyle.setBorderRight(borderStyle);
    return this;
  }

  public void set(Cell... cells) {
    cellStyle.setFont(font);

    for (Cell cell : cells) cell.setCellStyle(cellStyle);
  }

  public void set(Sheet sheet, int row, int col) {
    set(sheet.getRow(row).getCell(col));
  }
}
