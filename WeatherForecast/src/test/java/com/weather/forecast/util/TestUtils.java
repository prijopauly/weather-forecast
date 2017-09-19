package com.weather.forecast.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Prijo Pauly
 * @version 0.0.1
 * @since Sep 15, 2017 Copyright (c) 2017, Prijo Pauly. All rights reserved.
 */
public class TestUtils {

	/**
	 * Test method for
	 * {@link com.weather.forecast.util.Utils#listFilesMatching(File, String)}
	 */
	@Test
	public void testListFilesMatching() {
		File testFolder = new File("test");
		if (testFolder.exists()) {
			deleteFolder(testFolder);
		}
		testFolder.mkdir();
		File f1 = new File("test/1.txt");
		File f2 = new File("test/2.txt");
		File f3 = new File("test/abc.txt");
		try {
			f1.createNewFile();
			f2.createNewFile();
			f3.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals(Utils.listFilesMatching(testFolder, "\\d{1}.txt").length, 2);
		deleteFolder(testFolder);
	}

	/**
	 * Test method for {@link com.weather.forecast.util.Utils#isNumeric(String)}
	 */
	@Test
	public void testIsNumeric() {
		String numeric = "25";
		String nonNumeric = "Hello123";
		assertTrue(Utils.isNumeric(numeric));
		assertFalse(Utils.isNumeric(nonNumeric));
	}

	/**
	 * Test method for
	 * {@link com.weather.forecast.util.Utils#getFormattedDateString(java.util.Date)}
	 */
	@Test
	public void testGetFormattedDateString() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		cal.set(Calendar.YEAR, 2017);
		cal.set(Calendar.MONDAY, Calendar.SEPTEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 15);
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		assertEquals(Utils.getFormattedDateString(cal.getTime()), "2017-09-15T12:00:00Z");
	}

	private static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) { // some JVMs return null for empty dirs
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}
}
