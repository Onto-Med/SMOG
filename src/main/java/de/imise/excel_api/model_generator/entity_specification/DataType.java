package de.imise.excel_api.model_generator.entity_specification;

public class DataType {
  public static final String INTEGER = "Integer";
  public static final String DOUBLE = "Double";
  public static final String DATE = "Date";
  public static final String STRING = "String";
  public static final String BOOLEAN = "Boolean";

  public static final String INTEGER_LIST = "IntegerL";
  public static final String DOUBLE_LIST = "DoubleL";
  public static final String DATE_LIST = "DateL";
  public static final String STRING_LIST = "StringL";
  public static final String BOOLEAN_LIST = "BooleanL";

  public static boolean isList(String dt) {
    return INTEGER_LIST.equals(dt)
        || DOUBLE_LIST.equals(dt)
        || DATE_LIST.equals(dt)
        || STRING_LIST.equals(dt)
        || BOOLEAN_LIST.equals(dt);
  }

  public static String getDataTypeOfList(String dt) {
    return dt.substring(0, dt.length() - 1);
  }
}
