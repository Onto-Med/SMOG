package templates;

import de.imise.excel_api.model_generator.entity_specification.Entity;
import de.imise.excel_api.util.StrUtil;
import java.text.ParseException;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import de.imise.excel_api.excel_reader.*;
import de.imise.excel_api.excel_writer.ExcelWriter;

public class _SHEET_T {

	private Sheet sheet;
	
	public _SHEET_T(Sheet sheet) {
        this.sheet = sheet;
    }
//_END:_HEADER
	
	private int _ROW;
	private int _COL;
	private int _FIRST_ROW;
	private int _FIRST_COL;
	private int _LAST_COL;
	private int _FIELDS_NUMBER;

//_START:get_STRING_FIELD
    public Optional<String> get_STRING_FIELD() {
        return ExcelReader.getStringValue(sheet, _ROW, _COL);
    }

    public void set_STRING_FIELD(String value) {
    	ExcelWriter.setValue(sheet, _ROW, _COL, value);
    }

    public void clear_STRING_FIELD() {
    	ExcelWriter.clearCell(sheet, _ROW, _COL);
    }

    public boolean isEmpty_STRING_FIELD() {
    	return ExcelReader.isEmpty(sheet, _ROW, _COL);
    }
//_END:get_STRING_FIELD
    
//_START:get_INTEGER_FIELD
    public Optional<Integer> get_INTEGER_FIELD() {
    	return ExcelReader.getIntegerValue(sheet, _ROW, _COL);
    }

    public void set_INTEGER_FIELD(int value) {
    	ExcelWriter.setValue(sheet, _ROW, _COL, value);
    }

    public void clear_INTEGER_FIELD() {
    	ExcelWriter.clearCell(sheet, _ROW, _COL);
    }

    public boolean isEmpty_INTEGER_FIELD() {
    	return ExcelReader.isEmpty(sheet, _ROW, _COL);
    }
//_END:get_INTEGER_FIELD
    
//_START:get_DOUBLE_FIELD
    public Optional<Double> get_DOUBLE_FIELD() {
    	return ExcelReader.getDoubleValue(sheet, _ROW, _COL);
    }

    public void set_DOUBLE_FIELD(double value) {
    	ExcelWriter.setValue(sheet, _ROW, _COL, value);
    }

    public void clear_DOUBLE_FIELD() {
    	ExcelWriter.clearCell(sheet, _ROW, _COL);
    }

    public boolean isEmpty_DOUBLE_FIELD() {
    	return ExcelReader.isEmpty(sheet, _ROW, _COL);
    }
//_END:get_DOUBLE_FIELD
    
//_START:get_DATE_FIELD:import java.text.ParseException;
    public Optional<Date> get_DATE_FIELD() {
    	return ExcelReader.getDateValue(sheet, _ROW, _COL);
    }

    public void set_DATE_FIELD(Date value) {
    	ExcelWriter.setValue(sheet, _ROW, _COL, value);
    }

    public void set_DATE_FIELD(String value) throws ParseException {
    	ExcelWriter.setDateValue(sheet, _ROW, _COL, value);
    }

    public void clear_DATE_FIELD() {
    	ExcelWriter.clearCell(sheet, _ROW, _COL);
    }

    public boolean isEmpty_DATE_FIELD() {
    	return ExcelReader.isEmpty(sheet, _ROW, _COL);
    }
//_END:get_DATE_FIELD
    
//_START:get_BOOLEAN_FIELD
    public Optional<Boolean> get_BOOLEAN_FIELD() {
    	return ExcelReader.getBooleanValue(sheet, _ROW, _COL);
    }

    public void set_BOOLEAN_FIELD(boolean value) {
    	ExcelWriter.setValue(sheet, _ROW, _COL, value);
    }

    public void clear_BOOLEAN_FIELD() {
    	ExcelWriter.clearCell(sheet, _ROW, _COL);
    }

    public boolean isEmpty_BOOLEAN_FIELD() {
    	return ExcelReader.isEmpty(sheet, _ROW, _COL);
    }
//_END:get_BOOLEAN_FIELD
    
//_START:get_STRING_LIST_FIELD
    public List<String> get_STRING_LIST_FIELD() {
    	return ExcelReader.getStringValues(sheet, _ROW, _COL, "_LIST_SEPARATOR");
    }

    public void set_STRING_LIST_FIELD(String... values) {
    	ExcelWriter.setValues(sheet, _ROW, _COL, "_LIST_SEPARATOR", values);
    }

    public void clear_STRING_LIST_FIELD() {
    	ExcelWriter.clearCell(sheet, _ROW, _COL);
    }

    public boolean isEmpty_STRING_LIST_FIELD() {
    	return ExcelReader.isEmpty(sheet, _ROW, _COL);
    }
//_END:get_STRING_LIST_FIELD
    
//_START:get_INTEGER_LIST_FIELD
    public List<Integer> get_INTEGER_LIST_FIELD() {
    	return ExcelReader.getIntegerValues(sheet, _ROW, _COL, "_LIST_SEPARATOR");
    }
    
    public void set_INTEGER_LIST_FIELD(int... values) {
    	ExcelWriter.setValues(sheet, _ROW, _COL, "_LIST_SEPARATOR", values);
    }

    public void clear_INTEGER_LIST_FIELD() {
    	ExcelWriter.clearCell(sheet, _ROW, _COL);
    }

    public boolean isEmpty_INTEGER_LIST_FIELD() {
    	return ExcelReader.isEmpty(sheet, _ROW, _COL);
    }
//_END:get_INTEGER_LIST_FIELD
    
//_START:get_DOUBLE_LIST_FIELD
    public List<Double> get_DOUBLE_LIST_FIELD() {
    	return ExcelReader.getDoubleValues(sheet, _ROW, _COL, "_LIST_SEPARATOR");
    }
    
    public void set_DOUBLE_LIST_FIELD(double... values) {
    	ExcelWriter.setValues(sheet, _ROW, _COL, "_LIST_SEPARATOR", values);
    }

    public void clear_DOUBLE_LIST_FIELD() {
    	ExcelWriter.clearCell(sheet, _ROW, _COL);
    }

    public boolean isEmpty_DOUBLE_LIST_FIELD() {
    	return ExcelReader.isEmpty(sheet, _ROW, _COL);
    }
//_END:get_DOUBLE_LIST_FIELD
    
//_START:get_DATE_LIST_FIELD:import java.text.ParseException;
    public List<Date> get_DATE_LIST_FIELD() throws ParseException {
    	return ExcelReader.getDateValues(sheet, _ROW, _COL, "_LIST_SEPARATOR");
    }
    
    public void set_DATE_LIST_FIELD(Date... values) {
    	ExcelWriter.setValues(sheet, _ROW, _COL, "_LIST_SEPARATOR", values);
    }

    public void set_DATE_LIST_FIELD(String... values) throws ParseException {
    	ExcelWriter.setDateValues(sheet, _ROW, _COL, "_LIST_SEPARATOR", values);
    }

    public void clear_DATE_LIST_FIELD() {
    	ExcelWriter.clearCell(sheet, _ROW, _COL);
    }

    public boolean isEmpty_DATE_LIST_FIELD() {
    	return ExcelReader.isEmpty(sheet, _ROW, _COL);
    }
//_END:get_DATE_LIST_FIELD
    
//_START:get_BOOLEAN_LIST_FIELD:import java.text.ParseException;
    public List<Boolean> get_BOOLEAN_LIST_FIELD() throws ParseException {
    	return ExcelReader.getBooleanValues(sheet, _ROW, _COL, "_LIST_SEPARATOR");
    }
    
    public void set_BOOLEAN_LIST_FIELD(boolean... values) {
    	ExcelWriter.setValues(sheet, _ROW, _COL, "_LIST_SEPARATOR", values);
    }

    public void clear_BOOLEAN_LIST_FIELD() {
    	ExcelWriter.clearCell(sheet, _ROW, _COL);
    }

    public boolean isEmpty_BOOLEAN_LIST_FIELD() {
    	return ExcelReader.isEmpty(sheet, _ROW, _COL);
    }
//_END:get_BOOLEAN_LIST_FIELD
    
//_START:get_TABLE
    public List<_TABLE_RECORD_T> get_TABLE() {
        List<_TABLE_RECORD_T> records = new ArrayList<>();
        for (int i = _FIRST_ROW; true; i++) {
            Row row = sheet.getRow(i);
            if (ExcelReader.isEmptyRowPart(row, _FIRST_COL, _LAST_COL))
                break;
            else
                records.add(new _TABLE_RECORD_T(row, _FIRST_COL, _LAST_COL));
        }
        return records;
    }
    
    public void clear_TABLE() {
    	ExcelWriter.clearTable(sheet, _FIRST_ROW, _FIRST_COL, _LAST_COL);
    }

    public boolean isEmpty_TABLE() {
    	return ExcelReader.isEmptyRowPart(sheet, _FIRST_ROW, _FIRST_COL, _LAST_COL);
    }
    
    public _TABLE_RECORD_T addRecordIn_TABLE() {
    	int lastRowNum = ExcelReader.getLastTableRowNum(sheet, _FIRST_ROW, _FIRST_COL, _LAST_COL);
		
    	if (!isEmpty_TABLE())
    		ExcelWriter.addEmptyTableRecord(sheet, lastRowNum, lastRowNum + 1, _FIRST_COL, _LAST_COL);
    	
    	return new _TABLE_RECORD_T(ExcelWriter.getRow(sheet, lastRowNum + 1), _FIRST_COL, _LAST_COL);
    }

    public void deleteLastRecordIn_TABLE() {
    	List<_TABLE_RECORD_T> records = get_TABLE();
    	records.get(records.size() - 1).deleteRecord();
    }
//_END:get_TABLE
    
//_START:get_FREE_POSITION_TABLE:import de.imise.excel_api.model_generator.entity_specification.Entity;
    public List<_FREE_POSITION_TABLE_RECORD_T> get_FREE_POSITION_TABLE() {
        Optional<Coordinates> coord = ExcelReader.find(Entity.FREE_POSITION_TABLE, "_TABLE_NAME", sheet);
        if (!coord.isPresent())
            return null;

        int firstRowNum = coord.get().getRow() + 2;
        int firstColNum = coord.get().getCol();
        int lastColNum = firstColNum + _FIELDS_NUMBER - 1;

        List<_FREE_POSITION_TABLE_RECORD_T> records = new ArrayList<>();
        for (int i = firstRowNum; true; i++) {
            Row row = sheet.getRow(i);
            if (ExcelReader.isEmptyRowPart(row, firstColNum, lastColNum))
                break;
            else
                records.add(new _FREE_POSITION_TABLE_RECORD_T(row, firstColNum, lastColNum));
        }
        return records;
    }
    
    public void clear_FREE_POSITION_TABLE() {
    	Optional<Coordinates> coord = ExcelReader.find(Entity.FREE_POSITION_TABLE, "_TABLE_NAME", sheet);
        if (!coord.isPresent())
            return;

        int firstRowNum = coord.get().getRow() + 2;
        int firstColNum = coord.get().getCol();
        int lastColNum = firstColNum + _FIELDS_NUMBER - 1;
        
    	ExcelWriter.clearTable(sheet, firstRowNum, firstColNum, lastColNum);
    }
    
    public boolean isEmpty_FREE_POSITION_TABLE() {
    	Optional<Coordinates> coord = ExcelReader.find(Entity.FREE_POSITION_TABLE, "_TABLE_NAME", sheet);
        if (!coord.isPresent())
            return true;

        int firstRowNum = coord.get().getRow() + 2;
        int firstColNum = coord.get().getCol();
        int lastColNum = firstColNum + _FIELDS_NUMBER - 1;
    	
    	return ExcelReader.isEmptyRowPart(sheet, firstRowNum, firstColNum, lastColNum);
    }
    
    public _FREE_POSITION_TABLE_RECORD_T addRecordIn_FREE_POSITION_TABLE() {
    	Optional<Coordinates> coord = ExcelReader.find(Entity.FREE_POSITION_TABLE, "_TABLE_NAME", sheet);
        if (!coord.isPresent())
            return null;

        int firstRowNum = coord.get().getRow() + 2;
        int firstColNum = coord.get().getCol();
        int lastColNum = firstColNum + _FIELDS_NUMBER - 1;
        int lastRowNum = ExcelReader.getLastTableRowNum(sheet, firstRowNum, firstColNum, lastColNum);
        
        if (!isEmpty_FREE_POSITION_TABLE())    		
    		ExcelWriter.addEmptyTableRecord(sheet, lastRowNum, lastRowNum + 1, firstColNum, lastColNum);
    	
    	return new _FREE_POSITION_TABLE_RECORD_T(ExcelWriter.getRow(sheet, lastRowNum + 1), firstColNum, lastColNum);
    }

    public void deleteLastRecordIn_FREE_POSITION_TABLE() {
    	List<_FREE_POSITION_TABLE_RECORD_T> records = get_FREE_POSITION_TABLE();
    	records.get(records.size() - 1).deleteRecord();
    }
//_END:get_FREE_POSITION_TABLE
    
//_START:get_DYNAMIC_TABLE
    public DynamicTable get_DYNAMIC_TABLE() {
		return ExcelReader.getDynamicTable(sheet, _ROW, _COL);
	}
//_END:get_DYNAMIC_TABLE
    
//_START:get_TREE
    public Tree get_TREE() {
		return ExcelReader.getTree(sheet, _ROW, _COL);
	}
//_END:get_TREE
    
//_START:get_TREE_TABLE
    public List<_TREE_NODE_T> get_TREE_TABLE() {
        int headRowNum = _ROW;
        int markCellColNum = _COL;
        int firstColNum = ExcelReader.getNextNotEmptyCellNumInRow(sheet.getRow(headRowNum), markCellColNum + 1);
        int lastColNum = firstColNum + _FIELDS_NUMBER - 1;
        Cell markCell = sheet.getRow(headRowNum).getCell(markCellColNum);

        List<_TREE_NODE_T> rootNodes = new ArrayList<>();
		
		for (Row row : markCell.getSheet()) {
			if (row.getRowNum() > headRowNum) {
				Cell rootCell = row.getCell(markCell.getColumnIndex());

				if (!ExcelReader.isEmpty(rootCell))
					rootNodes.add(new _TREE_NODE_T(rootCell, firstColNum, lastColNum));
			}
		}
		
        return rootNodes;
    }
    
    public boolean isEmpty_TREE_TABLE() {
    	int headRowNum = _ROW;
        int markCellColNum = _COL;
        int firstColNum = ExcelReader.getNextNotEmptyCellNumInRow(sheet.getRow(headRowNum), markCellColNum + 1);
        int lastColNum = firstColNum + _FIELDS_NUMBER - 1;    	
    	return ExcelReader.isEmptyRowPart(sheet, headRowNum + 1, markCellColNum, lastColNum);
    }
    
    public void clear_TREE_TABLE() {
    	for (Row row : sheet) {
    		if (row.getRowNum() > _ROW) {
    			for (Cell cell : row)
    				ExcelWriter.clearCell(cell);
    		}
    	}
    }
    
    public _TREE_NODE_T addRootNodeIn_TREE_TABLE(String nodeName) {
    	int headRowNum = _ROW;
        int markCellColNum = _COL;
        int firstColNum = ExcelReader.getNextNotEmptyCellNumInRow(sheet.getRow(headRowNum), markCellColNum + 1);
        int lastColNum = firstColNum + _FIELDS_NUMBER - 1;
		ExcelWriter.insertRow(sheet, headRowNum + 1);
		ExcelWriter.setValue(sheet, headRowNum + 1, markCellColNum, nodeName);
		ExcelWriter.copyRowStyles(sheet.getRow(headRowNum + 2), sheet.getRow(headRowNum + 1));
		return new _TREE_NODE_T(ExcelWriter.getCell(sheet, headRowNum + 1, markCellColNum), firstColNum, lastColNum);
	}
//_END:get_TREE_TABLE
}
