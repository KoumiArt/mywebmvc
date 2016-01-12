package org.koumi.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	private static String DEFAULT_FORMAT = "yyyy-mm-dd";
	
	public int getDays(LocalDate beginDate,LocalDate endDate){
		return Period.between(beginDate, endDate).getDays();
	}
	
	public int getDays(String beginDateStr,String endDateStr){
		LocalDate beginDate = LocalDate.parse(beginDateStr);
		LocalDate endDate = LocalDate.parse(endDateStr);
		return getDays(beginDate, endDate);
	}
	
	public static LocalDate parse(String dateStr) {
		return LocalDate.parse(dateStr,DateTimeFormatter.ofPattern(DEFAULT_FORMAT));
	}
	
	public static LocalDate parse(String dateStr,String pattern) {
		return LocalDate.parse(dateStr,DateTimeFormatter.ofPattern(pattern));
	}
	
	public static String format(LocalDate localDate) {
		return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}
	
	public static String format(LocalDate localDate,String pattern) {
		return localDate.format(DateTimeFormatter.ofPattern(DEFAULT_FORMAT));
	}
}
