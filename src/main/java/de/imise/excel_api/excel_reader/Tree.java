package de.imise.excel_api.excel_reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;

import de.imise.excel_api.excel_writer.ExcelWriter;

public class Tree {
	
	private Cell markCell;
	HashMap<Integer, FreePositionTableRecord> table; 
	
	public Tree(Cell markCell) {
		this.markCell = markCell;
	}

	public Tree(Cell markCell, HashMap<Integer, FreePositionTableRecord> table) {
		this.markCell = markCell;
		this.table = table;
	}
	
	public List<TreeNode> getRootNodes() {
		List<TreeNode> rootNodes = new ArrayList<TreeNode>();
			
		for (Row row : markCell.getSheet()) {
			if (row.getRowNum() > markCell.getRowIndex()) {
				Cell rootCell = row.getCell(markCell.getColumnIndex());
				
				if (!ExcelReader.isEmpty(rootCell)) {
					if (hasTable())
						rootNodes.add(new TreeNode(rootCell, table));
					else
						rootNodes.add(new TreeNode(rootCell));
				}
			}
		}
		
		return rootNodes;
	}
	
	public void addRootNode(String node) {
		ExcelWriter.insertRow(markCell.getSheet(), markCell.getRowIndex() + 1);
		ExcelWriter.setValue(markCell.getSheet(), markCell.getRowIndex() + 1, markCell.getColumnIndex(), node);
//		return new TreeNode(markCell.getSheet().getRow(markCell.getRowIndex() + 1).getCell(markCell.getColumnIndex()));
	}
	
	public boolean hasTable() {
		return table != null && table.size() > 0;
	}
	
	public PositionsInSheet find(String name) {
		PositionsInSheet coordinates = new PositionsInSheet(markCell.getSheet());
		
		for (TreeNode node : getRootNodes()) {
			if (node.getName().trim().equalsIgnoreCase(name.trim()))
				coordinates.addCoordinates(node.getRowNum(), node.getColNum());
			
			node.find(name, coordinates);
		}
		
		return coordinates;
	}
	
	public void print() {
		for (TreeNode node : getRootNodes()) {
			node.print("");
			System.out.println();
		}
	}
}
