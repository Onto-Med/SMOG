package templates;

import de.imise.excel_api.util.StrUtil;

import java.text.ParseException;
//_START:_HEADER
import java.util.*;
import org.apache.poi.ss.usermodel.Row;
import de.imise.excel_api.excel_reader.ExcelReader;
import de.imise.excel_api.excel_reader.FreePositionTableRecord;
import de.imise.excel_api.excel_writer.ExcelWriter;

public class _FREE_POSITION_TABLE_RECORD_T extends FreePositionTableRecord {

	private Row row;
	private int firstColNum;
	private int lastColNum;

	public _FREE_POSITION_TABLE_RECORD_T(Row row, int firstColNum, int lastColNum) {
		this.row = row;
		this.firstColNum = firstColNum;
		this.lastColNum = lastColNum;
	}
	
	public int getRowNum() {
		return row.getRowNum();
	}

    public _FREE_POSITION_TABLE_RECORD_T insertRecordAfter() {
    	ExcelWriter.shiftTableRecordsDown(row.getSheet(), getRowNum() + 1, firstColNum, lastColNum);
    	return new _FREE_POSITION_TABLE_RECORD_T(ExcelWriter.getRow(row.getSheet(), getRowNum() + 1), firstColNum, lastColNum);
    }
    
    public void deleteRecord() {
    	ExcelWriter.shiftTableRecordsUp(row.getSheet(), getRowNum() + 1, firstColNum, lastColNum);
    }
//_END:_HEADER

	private int _COL_INDEX;

//_START:get_STRING_FIELD
	public Optional<String> get_STRING_FIELD() {
		return ExcelReader.getStringValue(row, firstColNum + _COL_INDEX);
	}
    
    public void set_STRING_FIELD(String value) {
    	ExcelWriter.setValue(row, firstColNum + _COL_INDEX, value);
    }

    public void clear_STRING_FIELD() {
    	ExcelWriter.clearCell(row, firstColNum + _COL_INDEX);
    }

    public boolean isEmpty_STRING_FIELD() {
    	return ExcelReader.isEmpty(row, firstColNum + _COL_INDEX);
    }
//_END:get_STRING_FIELD

//_START:get_INTEGER_FIELD
	public Optional<Integer> get_INTEGER_FIELD() {
		return ExcelReader.getIntegerValue(row, firstColNum + _COL_INDEX);
	}
    
    public void set_INTEGER_FIELD(int value) {
    	ExcelWriter.setValue(row, firstColNum + _COL_INDEX, value);
    }

    public void clear_INTEGER_FIELD() {
    	ExcelWriter.clearCell(row, firstColNum + _COL_INDEX);
    }

    public boolean isEmpty_INTEGER_FIELD() {
    	return ExcelReader.isEmpty(row, firstColNum + _COL_INDEX);
    }
//_END:get_INTEGER_FIELD

//_START:get_DOUBLE_FIELD
	public Optional<Double> get_DOUBLE_FIELD() {
		return ExcelReader.getDoubleValue(row, firstColNum + _COL_INDEX);
	}
    
    public void set_DOUBLE_FIELD(double value) {
    	ExcelWriter.setValue(row, firstColNum + _COL_INDEX, value);
    }

    public void clear_DOUBLE_FIELD() {
    	ExcelWriter.clearCell(row, firstColNum + _COL_INDEX);
    }

    public boolean isEmpty_DOUBLE_FIELD() {
    	return ExcelReader.isEmpty(row, firstColNum + _COL_INDEX);
    }
//_END:get_DOUBLE_FIELD

//_START:get_DATE_FIELD
	public Optional<Date> get_DATE_FIELD() {
		return ExcelReader.getDateValue(row, firstColNum + _COL_INDEX);
	}
    
    public void set_DATE_FIELD(Date value) {
    	ExcelWriter.setValue(row, firstColNum + _COL_INDEX, value);
    }

    public void set_DATE_FIELD(String value) throws ParseException {
    	ExcelWriter.setDateValue(row, firstColNum + _COL_INDEX, value);
    }

    public void clear_DATE_FIELD() {
    	ExcelWriter.clearCell(row, firstColNum + _COL_INDEX);
    }

    public boolean isEmpty_DATE_FIELD() {
    	return ExcelReader.isEmpty(row, firstColNum + _COL_INDEX);
    }
//_END:get_DATE_FIELD

//_START:get_BOOLEAN_FIELD
	public Optional<Boolean> get_BOOLEAN_FIELD() {
		return ExcelReader.getBooleanValue(row, firstColNum + _COL_INDEX);
	}
    
    public void set_BOOLEAN_FIELD(boolean value) {
    	ExcelWriter.setValue(row, firstColNum + _COL_INDEX, value);
    }

    public void clear_BOOLEAN_FIELD() {
    	ExcelWriter.clearCell(row, firstColNum + _COL_INDEX);
    }

    public boolean isEmpty_BOOLEAN_FIELD() {
    	return ExcelReader.isEmpty(row, firstColNum + _COL_INDEX);
    }
//_END:get_BOOLEAN_FIELD
    
//_START:get_STRING_LIST_FIELD
    public List<String> get_STRING_LIST_FIELD() {
    	return ExcelReader.getStringValues(row, firstColNum + _COL_INDEX, "_LIST_SEPARATOR");
    }

    public void set_STRING_LIST_FIELD(String... values) {
    	ExcelWriter.setValues(row, firstColNum + _COL_INDEX, "_LIST_SEPARATOR", values);
    }

    public void clear_STRING_LIST_FIELD() {
    	ExcelWriter.clearCell(row, firstColNum + _COL_INDEX);
    }

    public boolean isEmpty_STRING_LIST_FIELD() {
    	return ExcelReader.isEmpty(row, firstColNum + _COL_INDEX);
    }
//_END:get_STRING_LIST_FIELD
    
//_START:get_INTEGER_LIST_FIELD
    public List<Integer> get_INTEGER_LIST_FIELD() {
    	return ExcelReader.getIntegerValues(row, firstColNum + _COL_INDEX, "_LIST_SEPARATOR");
    }
    
    public void set_INTEGER_LIST_FIELD(int... values) {
    	ExcelWriter.setValues(row, firstColNum + _COL_INDEX, "_LIST_SEPARATOR", values);
    }

    public void clear_INTEGER_LIST_FIELD() {
    	ExcelWriter.clearCell(row, firstColNum + _COL_INDEX);
    }

    public boolean isEmpty_INTEGER_LIST_FIELD() {
    	return ExcelReader.isEmpty(row, firstColNum + _COL_INDEX);
    }
//_END:get_INTEGER_LIST_FIELD
    
//_START:get_DOUBLE_LIST_FIELD
    public List<Double> get_DOUBLE_LIST_FIELD() {
    	return ExcelReader.getDoubleValues(row, firstColNum + _COL_INDEX, "_LIST_SEPARATOR");
    }
    
    public void set_DOUBLE_LIST_FIELD(double... values) {
    	ExcelWriter.setValues(row, firstColNum + _COL_INDEX, "_LIST_SEPARATOR", values);
    }

    public void clear_DOUBLE_LIST_FIELD() {
    	ExcelWriter.clearCell(row, firstColNum + _COL_INDEX);
    }

    public boolean isEmpty_DOUBLE_LIST_FIELD() {
    	return ExcelReader.isEmpty(row, firstColNum + _COL_INDEX);
    }
//_END:get_DOUBLE_LIST_FIELD
    
//_START:get_DATE_LIST_FIELD:import java.text.ParseException;
    public List<Date> get_DATE_LIST_FIELD() throws ParseException {
    	return ExcelReader.getDateValues(row, firstColNum + _COL_INDEX, "_LIST_SEPARATOR");
    }
    
    public void set_DATE_LIST_FIELD(Date... values) {
    	ExcelWriter.setValues(row, firstColNum + _COL_INDEX, "_LIST_SEPARATOR", values);
    }

    public void set_DATE_LIST_FIELD(String... values) throws ParseException {
    	ExcelWriter.setDateValues(row, firstColNum + _COL_INDEX, "_LIST_SEPARATOR", values);
    }

    public void clear_DATE_LIST_FIELD() {
    	ExcelWriter.clearCell(row, firstColNum + _COL_INDEX);
    }

    public boolean isEmpty_DATE_LIST_FIELD() {
    	return ExcelReader.isEmpty(row, firstColNum + _COL_INDEX);
    }
//_END:get_DATE_LIST_FIELD
    
//_START:get_BOOLEAN_LIST_FIELD:import java.text.ParseException;
    public List<Boolean> get_BOOLEAN_LIST_FIELD() throws ParseException {
    	return ExcelReader.getBooleanValues(row, firstColNum + _COL_INDEX, "_LIST_SEPARATOR");
    }
    
    public void set_BOOLEAN_LIST_FIELD(boolean... values) {
    	ExcelWriter.setValues(row, firstColNum + _COL_INDEX, "_LIST_SEPARATOR", values);
    }

    public void clear_BOOLEAN_LIST_FIELD() {
    	ExcelWriter.clearCell(row, firstColNum + _COL_INDEX);
    }

    public boolean isEmpty_BOOLEAN_LIST_FIELD() {
    	return ExcelReader.isEmpty(row, firstColNum + _COL_INDEX);
    }
//_END:get_BOOLEAN_LIST_FIELD

//_START:getRecord:import java.text.ParseException;
    public Map<String, Object> getRecord() throws ParseException {
        Map<String, Object> record = new LinkedHashMap<>();
//_PUT_FIELDS
        return record;
    }
//_END:getRecord
}
