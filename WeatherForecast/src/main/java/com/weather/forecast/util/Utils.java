package com.weather.forecast.util;

import java.io.File;
import java.io.FileFilter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * The Utils Class has generic or commonly used functions
 *
 * @author Prijo Pauly
 * @version 0.0.1
 * @since Sep 15, 2017 Copyright (c) 2017, Prijo Pauly. All rights reserved.
 */
public class Utils {
	/**
	 * Returns an array files from a directory where the file names matches with
	 * the given regex.
	 * 
	 * @param root
	 *            directory name
	 * @param regex
	 *            regex
	 * @return return an array of files
	 */
	public static File[] listFilesMatching(File root, String regex) {
		if (!root.isDirectory()) {
			throw new IllegalArgumentException(root + " is no directory.");
		}
		final Pattern p = Pattern.compile(regex);
		return root.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return p.matcher(file.getName()).matches();
			}
		});
	}

	/**
	 * Determines the given is a numeric string or not.
	 * 
	 * @param str
	 *            given string
	 * @return returns true if the given string is a numeric one, returns false
	 *         otherwise
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns an date string.
	 * 
	 * @param date
	 *            date
	 * @return date string
	 */
	public static String getFormattedDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(date);
	}
}
