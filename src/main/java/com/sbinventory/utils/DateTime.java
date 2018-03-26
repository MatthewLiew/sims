package com.sbinventory.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {
	
	public static String DateNow(){
		
		return ZonedDateTime.now(ZoneId.of("Asia/Singapore")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public static String DateOneMonthBefore(){
		
		return ZonedDateTime.now(ZoneId.of("Asia/Singapore")).minusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public static String TimeNow(){
		
		return ZonedDateTime.now(ZoneId.of("Asia/Singapore")).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	}
	
	public static String Now(){
		
		return ZonedDateTime.now(ZoneId.of("Asia/Singapore")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
