package de.imise.excel_api.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {
	
	private static String[][] UMLAUT_REPLACEMENTS = { { "Ä", "Ae" }, { "Ü", "Ue" }, { "Ö", "Oe" }, { "ä", "ae" }, { "ü", "ue" }, { "ö", "oe" }, { "ß", "ss" } };
	private static String[] dateFormats = {"dd.MM.yyyy", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd"}; 
	public static String standardDateFormat = "yyyy-MM-dd'T'HH:mm:ss";
	public static String intFormat = "0";
	public static String doubleFormat = "0.00";
	
	public static String getJavaName(String s) {
		s = s.replace(".xlsx", "");
		s = s.replace(".xls", "");
		
		for (String[] uml : UMLAUT_REPLACEMENTS)
			s = s.replace(uml[0], uml[1]);

		StringBuffer name = new StringBuffer(s.replaceAll("[^a-zA-Z0-9\\s]", " ").trim().replaceAll("\\s+", "_").toLowerCase());
		
		int i;
		while ((i = name.indexOf("_")) > -1) {
			 name.setCharAt(i + 1, Character.toUpperCase(name.charAt(i + 1)));
			 name.deleteCharAt(i);
		}
		
		name.setCharAt(0, Character.toUpperCase(name.charAt(0)));
		
		return name.toString();
	}

	public static Optional<String> findInString(String pattern, String inString) {
		Matcher m = Pattern.compile(pattern).matcher(inString);
		if (m.find() && !m.group(1).trim().isEmpty())
			return Optional.of(m.group(1).trim());
		else
			return Optional.empty();
	}
	
	private static Optional<Date> parseDate(String s, SimpleDateFormat format) {
		try {
			return Optional.of(format.parse(s));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public static Optional<Date> parseDate(String s) {
		if (s == null || s.trim().isEmpty())
			return Optional.empty();
		
		for (String format : dateFormats) {
			Optional<Date> dateOpt = parseDate(s.trim(), new SimpleDateFormat(format));
			if (dateOpt.isPresent())
				return dateOpt;
		}
		
		return Optional.empty();
	}
	
	public static Optional<Integer> parseInt(String s) {
		try {
			return Optional.of(Integer.parseInt(s.trim()));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public static Optional<Double> parseDouble(String s) {
		s = s.replace(',', '.');
		try {
			return Optional.of(Double.parseDouble(s.trim()));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public static Optional<Boolean> parseBoolean(String s) {
		try {
			Optional<Double> dVal = StrUtil.parseDouble(s);
			if (dVal.isPresent())
				return Optional.of(dVal.get() > 0);
			else
				return Optional.of(Boolean.valueOf(s.trim()));
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	public static String formatDate(Date date) {
		return new SimpleDateFormat(standardDateFormat).format(date);
	}
}
