package test.model;

import java.text.ParseException;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import de.imise.excel_api.excel_reader.ExcelReader;
import de.imise.excel_api.excel_writer.ExcelWriter;

public class Sheet1Tab2Record {
	
	private Row row;
	private int firstColNum;
	private int lastColNum;

	protected Sheet1Tab2Record(Row row, int firstColNum, int lastColNum) {
		this.row = row;
		this.firstColNum = firstColNum;
		this.lastColNum = lastColNum;
	}
	
	public int getRowNum() {
		return row.getRowNum();
	}

    public Sheet1Tab2Record insertRecordAfter() {
    	ExcelWriter.shiftTableRecordsDown(row.getSheet(), getRowNum() + 1, firstColNum, lastColNum);
    	return new Sheet1Tab2Record(ExcelWriter.getRow(row.getSheet(), getRowNum() + 1), firstColNum, lastColNum);
    }
    
    public void deleteRecord() {
    	ExcelWriter.shiftTableRecordsUp(row.getSheet(), getRowNum() + 1, firstColNum, lastColNum);
    }

    public Optional<String> getCol1() {
        return ExcelReader.getStringValue(row, 6);
    }
    
    public void setCol1(String value) {
    	ExcelWriter.setValue(row, 6, value);
    }

    public void clearCol1() {
    	ExcelWriter.clearCell(row, 6);
    }

    public boolean isEmptyCol1() {
    	return ExcelReader.isEmpty(row, 6);
    }

    public Optional<Integer> getCol2() {
    	return ExcelReader.getIntegerValue(row, 7);
    }
    
    public void setCol2(int value) {
    	ExcelWriter.setValue(row, 7, value);
    }

    public void clearCol2() {
    	ExcelWriter.clearCell(row, 7);
    }

    public boolean isEmptyCol2() {
    	return ExcelReader.isEmpty(row, 7);
    }

    public Optional<Double> getCol3() {
    	return ExcelReader.getDoubleValue(row, 8);
    }
    
    public void setCol3(double value) {
    	ExcelWriter.setValue(row, 8, value);
    }

    public void clearCol3() {
    	ExcelWriter.clearCell(row, 8);
    }

    public boolean isEmptyCol3() {
    	return ExcelReader.isEmpty(row, 8);
    }

    public Optional<Date> getCol4() {
    	return ExcelReader.getDateValue(row, 9);
    }
    
    public void setCol4(Date value) {
    	ExcelWriter.setValue(row, 9, value);
    }

    public void setCol4(String value) throws ParseException {
    	ExcelWriter.setDateValue(row, 9, value);
    }

    public void clearCol4() {
    	ExcelWriter.clearCell(row, 9);
    }

    public boolean isEmptyCol4() {
    	return ExcelReader.isEmpty(row, 9);
    }

    public Map<String, Object> getRecord() throws ParseException {
        Map<String, Object> record = new LinkedHashMap<>();
        record.put("Col1", getCol1());
        record.put("Col2", getCol2());
        record.put("Col3", getCol3());
        record.put("Col4", getCol4());

        return record;
    }

}