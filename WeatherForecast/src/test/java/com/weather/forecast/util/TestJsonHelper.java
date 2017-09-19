package com.weather.forecast.util;

import org.junit.Test;

import com.google.gson.JsonObject;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Prijo Pauly
 * @version 0.0.1
 * @since Sep 15, 2017 Copyright (c) 2017, Prijo Pauly. All rights reserved.
 */
public class TestJsonHelper {

	/**
	 * Test method for
	 * {@link com.weather.forecast.util.JsonHelper#parseJson(String)}
	 */
	@Test
	public void testParseJson() {
		String jsonString = "{ \"name\":\"John\", \"age\":31, \"city\":\"New York\" }";
		assertEquals(JsonHelper.parseJson(jsonString).getClass(), JsonObject.class);
	}

	/**
	 * Test method for
	 * {@link com.weather.forecast.util.JsonHelper#parseJson(File)}
	 * 
	 * @throws FileNotFoundException
	 *             FileNotFoundException
	 */
	@Test(expected = FileNotFoundException.class)
	public void testParseJson1() throws FileNotFoundException {
		File jsonFile = new File("test.json");
		try {
			if (!jsonFile.exists()) {
				jsonFile.createNewFile();
			}
			String jsonString = "{ \"name\":\"John\", \"age\":31, \"city\":\"New York\" }";
			FileWriter fileWriter = new FileWriter(jsonFile);
			fileWriter.write(jsonString);
			fileWriter.flush();
			fileWriter.close();
			assertEquals(JsonHelper.parseJson(jsonFile).getClass(), JsonObject.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jsonFile.delete();
		JsonHelper.parseJson(jsonFile);
	}
}
