package test.model;

import java.text.ParseException;
import java.util.*;
import org.apache.poi.ss.usermodel.Row;
import de.imise.excel_api.excel_reader.ExcelReader;
import de.imise.excel_api.excel_reader.FreePositionTableRecord;
import de.imise.excel_api.excel_writer.ExcelWriter;

public class Sheet1Tab5Record extends FreePositionTableRecord {

	private Row row;
	private int firstColNum;
	private int lastColNum;

	public Sheet1Tab5Record(Row row, int firstColNum, int lastColNum) {
		this.row = row;
		this.firstColNum = firstColNum;
		this.lastColNum = lastColNum;
	}
	
	public int getRowNum() {
		return row.getRowNum();
	}

    public Sheet1Tab5Record insertRecordAfter() {
    	ExcelWriter.shiftTableRecordsDown(row.getSheet(), getRowNum() + 1, firstColNum, lastColNum);
    	return new Sheet1Tab5Record(ExcelWriter.getRow(row.getSheet(), getRowNum() + 1), firstColNum, lastColNum);
    }
    
    public void deleteRecord() {
    	ExcelWriter.shiftTableRecordsUp(row.getSheet(), getRowNum() + 1, firstColNum, lastColNum);
    }

    public List<String> getCol6() {
    	return ExcelReader.getStringValues(row, firstColNum + 0, ";");
    }

    public void setCol6(String... values) {
    	ExcelWriter.setValues(row, firstColNum + 0, ";", values);
    }

    public void clearCol6() {
    	ExcelWriter.clearCell(row, firstColNum + 0);
    }

    public boolean isEmptyCol6() {
    	return ExcelReader.isEmpty(row, firstColNum + 0);
    }

    public List<Integer> getCol7() {
    	return ExcelReader.getIntegerValues(row, firstColNum + 1, "|");
    }
    
    public void setCol7(int... values) {
    	ExcelWriter.setValues(row, firstColNum + 1, "|", values);
    }

    public void clearCol7() {
    	ExcelWriter.clearCell(row, firstColNum + 1);
    }

    public boolean isEmptyCol7() {
    	return ExcelReader.isEmpty(row, firstColNum + 1);
    }

    public List<Double> getCol8() {
    	return ExcelReader.getDoubleValues(row, firstColNum + 2, "|");
    }
    
    public void setCol8(double... values) {
    	ExcelWriter.setValues(row, firstColNum + 2, "|", values);
    }

    public void clearCol8() {
    	ExcelWriter.clearCell(row, firstColNum + 2);
    }

    public boolean isEmptyCol8() {
    	return ExcelReader.isEmpty(row, firstColNum + 2);
    }

    public List<Date> getCol9() throws ParseException {
    	return ExcelReader.getDateValues(row, firstColNum + 3, ",");
    }
    
    public void setCol9(Date... values) {
    	ExcelWriter.setValues(row, firstColNum + 3, ",", values);
    }

    public void setCol9(String... values) throws ParseException {
    	ExcelWriter.setDateValues(row, firstColNum + 3, ",", values);
    }

    public void clearCol9() {
    	ExcelWriter.clearCell(row, firstColNum + 3);
    }

    public boolean isEmptyCol9() {
    	return ExcelReader.isEmpty(row, firstColNum + 3);
    }

    public List<Boolean> getCol10() throws ParseException {
    	return ExcelReader.getBooleanValues(row, firstColNum + 4, "|");
    }
    
    public void setCol10(boolean... values) {
    	ExcelWriter.setValues(row, firstColNum + 4, "|", values);
    }

    public void clearCol10() {
    	ExcelWriter.clearCell(row, firstColNum + 4);
    }

    public boolean isEmptyCol10() {
    	return ExcelReader.isEmpty(row, firstColNum + 4);
    }

    public Map<String, Object> getRecord() throws ParseException {
        Map<String, Object> record = new LinkedHashMap<>();
        record.put("Col6", getCol6());
        record.put("Col7", getCol7());
        record.put("Col8", getCol8());
        record.put("Col9", getCol9());
        record.put("Col10", getCol10());

        return record;
    }

}