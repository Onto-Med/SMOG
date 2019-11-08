package test.model;

import java.text.ParseException;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import de.imise.excel_api.excel_reader.ExcelReader;
import de.imise.excel_api.excel_writer.ExcelWriter;

public class Sheet3Tab1Record {
	
	private Row row;
	private int firstColNum;
	private int lastColNum;

	protected Sheet3Tab1Record(Row row, int firstColNum, int lastColNum) {
		this.row = row;
		this.firstColNum = firstColNum;
		this.lastColNum = lastColNum;
	}
	
	public int getRowNum() {
		return row.getRowNum();
	}

    public Sheet3Tab1Record insertRecordAfter() {
    	ExcelWriter.shiftTableRecordsDown(row.getSheet(), getRowNum() + 1, firstColNum, lastColNum);
    	return new Sheet3Tab1Record(ExcelWriter.getRow(row.getSheet(), getRowNum() + 1), firstColNum, lastColNum);
    }
    
    public void deleteRecord() {
    	ExcelWriter.shiftTableRecordsUp(row.getSheet(), getRowNum() + 1, firstColNum, lastColNum);
    }

    public Optional<String> getId() {
        return ExcelReader.getStringValue(row, 0);
    }
    
    public void setId(String value) {
    	ExcelWriter.setValue(row, 0, value);
    }

    public void clearId() {
    	ExcelWriter.clearCell(row, 0);
    }

    public boolean isEmptyId() {
    	return ExcelReader.isEmpty(row, 0);
    }

    public List<Sheet3Tab2Record> getTab2() {
        List<Sheet3Tab2Record> records = new ArrayList<>();
        Optional<String> colRefOwnVal = ExcelReader.getStringValue(row, 0);
    	if (!colRefOwnVal.isPresent())
    	    return records;
    		
        for (int i = 2; true; i++) {
            Row tabRow = row.getSheet().getRow(i);
            if (ExcelReader.isEmptyRowPart(tabRow, 3, 4))
                break;
            else {
                Optional<String> colRefForeignVal = ExcelReader.getStringValue(tabRow, 3);
                if(colRefForeignVal.isPresent() && colRefOwnVal.get().equals(colRefForeignVal.get()))
                    records.add(new Sheet3Tab2Record(tabRow, 3, 4));
            }  
        }

        return records;
    }

    public Optional<String> getName() {
        return ExcelReader.getStringValue(row, 1);
    }
    
    public void setName(String value) {
    	ExcelWriter.setValue(row, 1, value);
    }

    public void clearName() {
    	ExcelWriter.clearCell(row, 1);
    }

    public boolean isEmptyName() {
    	return ExcelReader.isEmpty(row, 1);
    }

    public Map<String, Object> getRecord() throws ParseException {
        Map<String, Object> record = new LinkedHashMap<>();
        record.put("Id", getId());
        record.put("Name", getName());

        return record;
    }

}