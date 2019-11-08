package test.model;

import java.text.ParseException;
import de.imise.excel_api.model_generator.entity_specification.Entity;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import de.imise.excel_api.excel_reader.*;
import de.imise.excel_api.excel_writer.ExcelWriter;

public class Sheet1 {

	private Sheet sheet;
	
	public Sheet1(Sheet sheet) {
        this.sheet = sheet;
    }

    public Optional<String> getField1() {
        return ExcelReader.getStringValue(sheet, 14, 1);
    }

    public void setField1(String value) {
    	ExcelWriter.setValue(sheet, 14, 1, value);
    }

    public void clearField1() {
    	ExcelWriter.clearCell(sheet, 14, 1);
    }

    public boolean isEmptyField1() {
    	return ExcelReader.isEmpty(sheet, 14, 1);
    }

    public List<String> getField6() {
    	return ExcelReader.getStringValues(sheet, 14, 7, "|");
    }

    public void setField6(String... values) {
    	ExcelWriter.setValues(sheet, 14, 7, "|", values);
    }

    public void clearField6() {
    	ExcelWriter.clearCell(sheet, 14, 7);
    }

    public boolean isEmptyField6() {
    	return ExcelReader.isEmpty(sheet, 14, 7);
    }

    public Optional<Integer> getField2() {
    	return ExcelReader.getIntegerValue(sheet, 15, 1);
    }

    public void setField2(int value) {
    	ExcelWriter.setValue(sheet, 15, 1, value);
    }

    public void clearField2() {
    	ExcelWriter.clearCell(sheet, 15, 1);
    }

    public boolean isEmptyField2() {
    	return ExcelReader.isEmpty(sheet, 15, 1);
    }

    public List<Integer> getField7() {
    	return ExcelReader.getIntegerValues(sheet, 15, 7, "|");
    }
    
    public void setField7(int... values) {
    	ExcelWriter.setValues(sheet, 15, 7, "|", values);
    }

    public void clearField7() {
    	ExcelWriter.clearCell(sheet, 15, 7);
    }

    public boolean isEmptyField7() {
    	return ExcelReader.isEmpty(sheet, 15, 7);
    }

    public Optional<Double> getField3() {
    	return ExcelReader.getDoubleValue(sheet, 16, 1);
    }

    public void setField3(double value) {
    	ExcelWriter.setValue(sheet, 16, 1, value);
    }

    public void clearField3() {
    	ExcelWriter.clearCell(sheet, 16, 1);
    }

    public boolean isEmptyField3() {
    	return ExcelReader.isEmpty(sheet, 16, 1);
    }

    public List<Double> getField8() {
    	return ExcelReader.getDoubleValues(sheet, 16, 7, "|");
    }
    
    public void setField8(double... values) {
    	ExcelWriter.setValues(sheet, 16, 7, "|", values);
    }

    public void clearField8() {
    	ExcelWriter.clearCell(sheet, 16, 7);
    }

    public boolean isEmptyField8() {
    	return ExcelReader.isEmpty(sheet, 16, 7);
    }

    public Optional<Date> getField4() {
    	return ExcelReader.getDateValue(sheet, 17, 1);
    }

    public void setField4(Date value) {
    	ExcelWriter.setValue(sheet, 17, 1, value);
    }

    public void setField4(String value) throws ParseException {
    	ExcelWriter.setDateValue(sheet, 17, 1, value);
    }

    public void clearField4() {
    	ExcelWriter.clearCell(sheet, 17, 1);
    }

    public boolean isEmptyField4() {
    	return ExcelReader.isEmpty(sheet, 17, 1);
    }

    public List<Date> getField9() throws ParseException {
    	return ExcelReader.getDateValues(sheet, 17, 7, "|");
    }
    
    public void setField9(Date... values) {
    	ExcelWriter.setValues(sheet, 17, 7, "|", values);
    }

    public void setField9(String... values) throws ParseException {
    	ExcelWriter.setDateValues(sheet, 17, 7, "|", values);
    }

    public void clearField9() {
    	ExcelWriter.clearCell(sheet, 17, 7);
    }

    public boolean isEmptyField9() {
    	return ExcelReader.isEmpty(sheet, 17, 7);
    }

    public Optional<Date> getField4a() {
    	return ExcelReader.getDateValue(sheet, 18, 1);
    }

    public void setField4a(Date value) {
    	ExcelWriter.setValue(sheet, 18, 1, value);
    }

    public void setField4a(String value) throws ParseException {
    	ExcelWriter.setDateValue(sheet, 18, 1, value);
    }

    public void clearField4a() {
    	ExcelWriter.clearCell(sheet, 18, 1);
    }

    public boolean isEmptyField4a() {
    	return ExcelReader.isEmpty(sheet, 18, 1);
    }

    public List<Boolean> getField10() throws ParseException {
    	return ExcelReader.getBooleanValues(sheet, 18, 7, "|");
    }
    
    public void setField10(boolean... values) {
    	ExcelWriter.setValues(sheet, 18, 7, "|", values);
    }

    public void clearField10() {
    	ExcelWriter.clearCell(sheet, 18, 7);
    }

    public boolean isEmptyField10() {
    	return ExcelReader.isEmpty(sheet, 18, 7);
    }

    public Optional<Boolean> getField5() {
    	return ExcelReader.getBooleanValue(sheet, 19, 1);
    }

    public void setField5(boolean value) {
    	ExcelWriter.setValue(sheet, 19, 1, value);
    }

    public void clearField5() {
    	ExcelWriter.clearCell(sheet, 19, 1);
    }

    public boolean isEmptyField5() {
    	return ExcelReader.isEmpty(sheet, 19, 1);
    }

    public List<Sheet1Tab4Record> getTab4() {
        List<Sheet1Tab4Record> records = new ArrayList<>();
        for (int i = 9; true; i++) {
            Row row = sheet.getRow(i);
            if (ExcelReader.isEmptyRowPart(row, 6, 10))
                break;
            else
                records.add(new Sheet1Tab4Record(row, 6, 10));
        }
        return records;
    }
    
    public void clearTab4() {
    	ExcelWriter.clearTable(sheet, 9, 6, 10);
    }

    public boolean isEmptyTab4() {
    	return ExcelReader.isEmptyRowPart(sheet, 9, 6, 10);
    }
    
    public Sheet1Tab4Record addRecordInTab4() {
    	int lastRowNum = ExcelReader.getLastTableRowNum(sheet, 9, 6, 10);
		
    	if (!isEmptyTab4())
    		ExcelWriter.addEmptyTableRecord(sheet, lastRowNum, lastRowNum + 1, 6, 10);
    	
    	return new Sheet1Tab4Record(ExcelWriter.getRow(sheet, lastRowNum + 1), 6, 10);
    }

    public void deleteLastRecordInTab4() {
    	List<Sheet1Tab4Record> records = getTab4();
    	records.get(records.size() - 1).deleteRecord();
    }

    public List<Sheet1Tab3Record> getTab3() {
        List<Sheet1Tab3Record> records = new ArrayList<>();
        for (int i = 9; true; i++) {
            Row row = sheet.getRow(i);
            if (ExcelReader.isEmptyRowPart(row, 0, 4))
                break;
            else
                records.add(new Sheet1Tab3Record(row, 0, 4));
        }
        return records;
    }
    
    public void clearTab3() {
    	ExcelWriter.clearTable(sheet, 9, 0, 4);
    }

    public boolean isEmptyTab3() {
    	return ExcelReader.isEmptyRowPart(sheet, 9, 0, 4);
    }
    
    public Sheet1Tab3Record addRecordInTab3() {
    	int lastRowNum = ExcelReader.getLastTableRowNum(sheet, 9, 0, 4);
		
    	if (!isEmptyTab3())
    		ExcelWriter.addEmptyTableRecord(sheet, lastRowNum, lastRowNum + 1, 0, 4);
    	
    	return new Sheet1Tab3Record(ExcelWriter.getRow(sheet, lastRowNum + 1), 0, 4);
    }

    public void deleteLastRecordInTab3() {
    	List<Sheet1Tab3Record> records = getTab3();
    	records.get(records.size() - 1).deleteRecord();
    }

    public List<Sheet1Tab2Record> getTab2() {
        List<Sheet1Tab2Record> records = new ArrayList<>();
        for (int i = 2; true; i++) {
            Row row = sheet.getRow(i);
            if (ExcelReader.isEmptyRowPart(row, 6, 9))
                break;
            else
                records.add(new Sheet1Tab2Record(row, 6, 9));
        }
        return records;
    }
    
    public void clearTab2() {
    	ExcelWriter.clearTable(sheet, 2, 6, 9);
    }

    public boolean isEmptyTab2() {
    	return ExcelReader.isEmptyRowPart(sheet, 2, 6, 9);
    }
    
    public Sheet1Tab2Record addRecordInTab2() {
    	int lastRowNum = ExcelReader.getLastTableRowNum(sheet, 2, 6, 9);
		
    	if (!isEmptyTab2())
    		ExcelWriter.addEmptyTableRecord(sheet, lastRowNum, lastRowNum + 1, 6, 9);
    	
    	return new Sheet1Tab2Record(ExcelWriter.getRow(sheet, lastRowNum + 1), 6, 9);
    }

    public void deleteLastRecordInTab2() {
    	List<Sheet1Tab2Record> records = getTab2();
    	records.get(records.size() - 1).deleteRecord();
    }

    public List<Sheet1Tab1Record> getTab1() {
        List<Sheet1Tab1Record> records = new ArrayList<>();
        for (int i = 2; true; i++) {
            Row row = sheet.getRow(i);
            if (ExcelReader.isEmptyRowPart(row, 0, 4))
                break;
            else
                records.add(new Sheet1Tab1Record(row, 0, 4));
        }
        return records;
    }
    
    public void clearTab1() {
    	ExcelWriter.clearTable(sheet, 2, 0, 4);
    }

    public boolean isEmptyTab1() {
    	return ExcelReader.isEmptyRowPart(sheet, 2, 0, 4);
    }
    
    public Sheet1Tab1Record addRecordInTab1() {
    	int lastRowNum = ExcelReader.getLastTableRowNum(sheet, 2, 0, 4);
		
    	if (!isEmptyTab1())
    		ExcelWriter.addEmptyTableRecord(sheet, lastRowNum, lastRowNum + 1, 0, 4);
    	
    	return new Sheet1Tab1Record(ExcelWriter.getRow(sheet, lastRowNum + 1), 0, 4);
    }

    public void deleteLastRecordInTab1() {
    	List<Sheet1Tab1Record> records = getTab1();
    	records.get(records.size() - 1).deleteRecord();
    }

    public List<Sheet1Tab5Record> getTab5() {
        Optional<Coordinates> coord = ExcelReader.find(Entity.FREE_POSITION_TABLE, "Tab5", sheet);
        if (!coord.isPresent())
            return null;

        int firstRowNum = coord.get().getRow() + 2;
        int firstColNum = coord.get().getCol();
        int lastColNum = firstColNum + 5 - 1;

        List<Sheet1Tab5Record> records = new ArrayList<>();
        for (int i = firstRowNum; true; i++) {
            Row row = sheet.getRow(i);
            if (ExcelReader.isEmptyRowPart(row, firstColNum, lastColNum))
                break;
            else
                records.add(new Sheet1Tab5Record(row, firstColNum, lastColNum));
        }
        return records;
    }
    
    public void clearTab5() {
    	Optional<Coordinates> coord = ExcelReader.find(Entity.FREE_POSITION_TABLE, "Tab5", sheet);
        if (!coord.isPresent())
            return;

        int firstRowNum = coord.get().getRow() + 2;
        int firstColNum = coord.get().getCol();
        int lastColNum = firstColNum + 5 - 1;
        
    	ExcelWriter.clearTable(sheet, firstRowNum, firstColNum, lastColNum);
    }
    
    public boolean isEmptyTab5() {
    	Optional<Coordinates> coord = ExcelReader.find(Entity.FREE_POSITION_TABLE, "Tab5", sheet);
        if (!coord.isPresent())
            return true;

        int firstRowNum = coord.get().getRow() + 2;
        int firstColNum = coord.get().getCol();
        int lastColNum = firstColNum + 5 - 1;
    	
    	return ExcelReader.isEmptyRowPart(sheet, firstRowNum, firstColNum, lastColNum);
    }
    
    public Sheet1Tab5Record addRecordInTab5() {
    	Optional<Coordinates> coord = ExcelReader.find(Entity.FREE_POSITION_TABLE, "Tab5", sheet);
        if (!coord.isPresent())
            return null;

        int firstRowNum = coord.get().getRow() + 2;
        int firstColNum = coord.get().getCol();
        int lastColNum = firstColNum + 5 - 1;
        int lastRowNum = ExcelReader.getLastTableRowNum(sheet, firstRowNum, firstColNum, lastColNum);
        
        if (!isEmptyTab5())    		
    		ExcelWriter.addEmptyTableRecord(sheet, lastRowNum, lastRowNum + 1, firstColNum, lastColNum);
    	
    	return new Sheet1Tab5Record(ExcelWriter.getRow(sheet, lastRowNum + 1), firstColNum, lastColNum);
    }

    public void deleteLastRecordInTab5() {
    	List<Sheet1Tab5Record> records = getTab5();
    	records.get(records.size() - 1).deleteRecord();
    }

}