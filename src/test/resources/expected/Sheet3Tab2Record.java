package test.model;

import java.text.ParseException;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import de.imise.excel_api.excel_reader.ExcelReader;
import de.imise.excel_api.excel_writer.ExcelWriter;

public class Sheet3Tab2Record {
	
	private Row row;
	private int firstColNum;
	private int lastColNum;

	protected Sheet3Tab2Record(Row row, int firstColNum, int lastColNum) {
		this.row = row;
		this.firstColNum = firstColNum;
		this.lastColNum = lastColNum;
	}
	
	public int getRowNum() {
		return row.getRowNum();
	}

    public Sheet3Tab2Record insertRecordAfter() {
    	ExcelWriter.shiftTableRecordsDown(row.getSheet(), getRowNum() + 1, firstColNum, lastColNum);
    	return new Sheet3Tab2Record(ExcelWriter.getRow(row.getSheet(), getRowNum() + 1), firstColNum, lastColNum);
    }
    
    public void deleteRecord() {
    	ExcelWriter.shiftTableRecordsUp(row.getSheet(), getRowNum() + 1, firstColNum, lastColNum);
    }

    public Optional<String> getValue() {
        return ExcelReader.getStringValue(row, 4);
    }
    
    public void setValue(String value) {
    	ExcelWriter.setValue(row, 4, value);
    }

    public void clearValue() {
    	ExcelWriter.clearCell(row, 4);
    }

    public boolean isEmptyValue() {
    	return ExcelReader.isEmpty(row, 4);
    }

    public Optional<String> getId() {
        return ExcelReader.getStringValue(row, 3);
    }
    
    public void setId(String value) {
    	ExcelWriter.setValue(row, 3, value);
    }

    public void clearId() {
    	ExcelWriter.clearCell(row, 3);
    }

    public boolean isEmptyId() {
    	return ExcelReader.isEmpty(row, 3);
    }

    public Map<String, Object> getRecord() throws ParseException {
        Map<String, Object> record = new LinkedHashMap<>();
        record.put("Value", getValue());
        record.put("Id", getId());

        return record;
    }

}