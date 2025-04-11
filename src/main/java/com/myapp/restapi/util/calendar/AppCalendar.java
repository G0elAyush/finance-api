package com.myapp.restapi.util.calendar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppCalendar {
	

	public static String DATE_FORMAT="yyyy-MMM-dd HH:mm:ss";
	
	public static String APP_TIME_ZONE = "UTC";
	
	
	@Value("${app.date.format}")
	public void setDateFormat(final String date_format) {
		DATE_FORMAT = date_format;
	  }
	
	
	@Value("${app.time.zone}")
	public void setAppTimeZone(final String app_time_zone) {
		APP_TIME_ZONE = app_time_zone;
	  }
	
	public static String getTimeUTC(Date date) {
		
		return getTime(date,TimeZone.getTimeZone("UTC"));
		
	}
	public static String getTime(Date date,TimeZone zone) {
		
		final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    sdf.setTimeZone(TimeZone.getTimeZone(zone.getID()));
	    final String utcTime = sdf.format(date);
		return utcTime;
	}
	public static String getDefaultTimeZoneTime(Date date) {
		System.out.println("Ayush" + APP_TIME_ZONE);
		TimeZone zone = TimeZone.getTimeZone(APP_TIME_ZONE);
	
		
		return getTime(date,zone);
	}


}
