package com.one.zone;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeZoneUtil {
	public static String long2UTCstring(long timestamp) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("UTC"));
		ZonedDateTime utcZone = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
		String str = utcZone.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME); 
		return str;
	}
	
	public static String long2AnyISO8601string(long timestamp, ZoneId zoneid) {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("UTC"));
		ZonedDateTime utcZone = ZonedDateTime.of(localDateTime, ZoneId.of("UTC")).withZoneSameInstant(zoneid);
		String str = utcZone.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME); 
		return str;
	}
	
	public static long utc2long(String UTCString) {
		ZonedDateTime x = ZonedDateTime.parse(UTCString);
		return x.toInstant().toEpochMilli();
	}
	
	public static long anyISO86012long(String ISOString) {
		return utc2long(ISOString);
	}
}