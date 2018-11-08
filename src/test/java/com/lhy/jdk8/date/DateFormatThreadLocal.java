package com.lhy.jdk8.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 通过ThreadLocal管理SimpleDateFormat
 *
 */
public class DateFormatThreadLocal {
	
	private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){
		protected DateFormat initialValue(){
			return new SimpleDateFormat("yyyyMMdd");
		}
	};
	
	public static final Date convert(String source) throws ParseException{
		return df.get().parse(source);
	}
}
