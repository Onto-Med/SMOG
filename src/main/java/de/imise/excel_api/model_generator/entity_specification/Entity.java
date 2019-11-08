package de.imise.excel_api.model_generator.entity_specification;

public class Entity {
	
	public static final String CLASS = "Class:";
	public static final String FIELD = "Field:";
	public static final String TABLE = "Table:";
	public static final String DYNAMIC_TABLE = "DynamicTable:";
	public static final String FREE_POSITION_TABLE = "FreePositionTable:";
	public static final String TREE = "Tree:";
	public static final String TREE_TABLE = "TreeTable:";
	
	public static final String[] entities = {CLASS, FIELD, TABLE, DYNAMIC_TABLE, FREE_POSITION_TABLE, TREE, TREE_TABLE};

	public static final String DATA_TYPE_PATTERN = "\\[(.+?)\\]";
	
	public static final String LIST_SEPARATOR_PATTERN = "\\<(.+?)\\>";
	public static final String DEFAULT_LIST_SEPARATOR = "|";
	
	public static final String COL_REF_PATTERN   = "\\{(.+?)\\}";
	public static final String COL_REF_SEPARATOR = ":";	
}
