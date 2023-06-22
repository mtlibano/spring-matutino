package br.com.trier.springmatutino.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	
	private static DateTimeFormatter dtfBr = DateTimeFormatter.ofPattern("dd/MM/yyy");
	
	public static ZonedDateTime strToZonedDateTime(String dateStr) {
		return ZonedDateTime.parse(dateStr, dtfBr);
	}
	
	public static String zonedDateTimeToStr(ZonedDateTime date) {
		return dtfBr.format(date);
	}

}
