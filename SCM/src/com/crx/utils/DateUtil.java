package com.crx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String getByPattern(String string) {
		// TODO Auto-generated method stub
		String now = new SimpleDateFormat(string).format(new Date());
		return now;
	}

}
