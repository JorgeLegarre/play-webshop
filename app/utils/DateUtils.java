package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static Date parse(String date, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			return sf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date parse(String date) {
		return parse(date, DEFAULT_DATE_FORMAT);
	}

	public static String format(Date date, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(date);
	}

	public static String format(Date date) {
		return format(date, DEFAULT_DATE_FORMAT);
	}

	public static Date reduceDate(Date date, String format) {
		SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		try {
			String txtDate = sf.format(date);
			date = sf.parse(txtDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;
	}

	public static Date reduceDate(Date date) {
		return reduceDate(date, DEFAULT_DATE_FORMAT);
	}
}
