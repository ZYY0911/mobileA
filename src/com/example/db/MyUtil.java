package com.example.db;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by ’≈Â≠Ïœ On Sep 28, 2020 9:32:06 AM
 */
public class MyUtil {
	public static String simpDate(String type, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(type);
		return format.format(date);
	}
}
