package de.imise.excel_api.excel_reader;

import java.util.HashSet;
import java.util.Set;
import org.apache.poi.ss.usermodel.Sheet;

public class PositionsInSheet {

  private final Sheet sheet;
  private final Set<Coordinates> coordinates;

  public PositionsInSheet(Sheet sheet) {
    this.sheet = sheet;
    this.coordinates = new HashSet<Coordinates>();
  }

  public void rowAdded(int addedRow) {
    for (Coordinates c : coordinates) {
      c.rowAdded(addedRow);
    }
  }

  public void colAdded(int addedCol) {
    for (Coordinates c : coordinates) {
      c.colAdded(addedCol);
    }
  }

  public void addCoordinates(int row, int col) {
    this.coordinates.add(new Coordinates(row, col));
  }

  @Override
  public String toString() {
    return "PositionsInSheet [sheet=" + sheet.getSheetName() + ", coordinates=" + coordinates + "]";
  }

  public Sheet getSheet() {
    return sheet;
  }

  public Set<Coordinates> getCoordinates() {
    return coordinates;
  }
}
