package test.model;

import java.text.ParseException;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import de.imise.excel_api.excel_reader.ExcelReader;
import de.imise.excel_api.excel_writer.ExcelWriter;

public class Sheet1Tab4Record {
	
	private Row row;
	private int firstColNum;
	private int lastColNum;

	protected Sheet1Tab4Record(Row row, int firstColNum, int lastColNum) {
		this.row = row;
		this.firstColNum = firstColNum;
		this.lastColNum = lastColNum;
	}
	
	public int getRowNum() {
		return row.getRowNum();
	}

    public Sheet1Tab4Record insertRecordAfter() {
    	ExcelWriter.shiftTableRecordsDown(row.getSheet(), getRowNum() + 1, firstColNum, lastColNum);
    	return new Sheet1Tab4Record(ExcelWriter.getRow(row.getSheet(), getRowNum() + 1), firstColNum, lastColNum);
    }
    
    public void deleteRecord() {
    	ExcelWriter.shiftTableRecordsUp(row.getSheet(), getRowNum() + 1, firstColNum, lastColNum);
    }

    public List<String> getCol6() {
    	return ExcelReader.getStringValues(row, 6, "|");
    }

    public void setCol6(String... values) {
    	ExcelWriter.setValues(row, 6, "|", values);
    }

    public void clearCol6() {
    	ExcelWriter.clearCell(row, 6);
    }

    public boolean isEmptyCol6() {
    	return ExcelReader.isEmpty(row, 6);
    }

    public List<Integer> getCol7() {
    	return ExcelReader.getIntegerValues(row, 7, "|");
    }
    
    public void setCol7(int... values) {
    	ExcelWriter.setValues(row, 7, "|", values);
    }

    public void clearCol7() {
    	ExcelWriter.clearCell(row, 7);
    }

    public boolean isEmptyCol7() {
    	return ExcelReader.isEmpty(row, 7);
    }

    public List<Double> getCol8() {
    	return ExcelReader.getDoubleValues(row, 8, "|");
    }
    
    public void setCol8(double... values) {
    	ExcelWriter.setValues(row, 8, "|", values);
    }

    public void clearCol8() {
    	ExcelWriter.clearCell(row, 8);
    }

    public boolean isEmptyCol8() {
    	return ExcelReader.isEmpty(row, 8);
    }

    public List<Date> getCol9() throws ParseException {
    	return ExcelReader.getDateValues(row, 9, "|");
    }
    
    public void setCol9(Date... values) {
    	ExcelWriter.setValues(row, 9, "|", values);
    }

    public void setCol9(String... values) throws ParseException {
    	ExcelWriter.setDateValues(row, 9, "|", values);
    }

    public void clearCol9() {
    	ExcelWriter.clearCell(row, 9);
    }

    public boolean isEmptyCol9() {
    	return ExcelReader.isEmpty(row, 9);
    }

    public List<Boolean> getCol10() throws ParseException {
    	return ExcelReader.getBooleanValues(row, 10, "|");
    }
    
    public void setCol10(boolean... values) {
    	ExcelWriter.setValues(row, 10, "|", values);
    }

    public void clearCol10() {
    	ExcelWriter.clearCell(row, 10);
    }

    public boolean isEmptyCol10() {
    	return ExcelReader.isEmpty(row, 10);
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