package com.weather.forecast.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This util class provides methods to parse json.
 *
 * @author Prijo Pauly
 * @version 0.0.1
 * @since Sep 15, 2017 Copyright (c) 2017, Prijo Pauly. All rights reserved.
 */
public class JsonHelper {

	/**
	 * Parses the json string and returns JsonObject.
	 * 
	 * @param jsonString
	 *            json string
	 * @return JsonObject
	 */
	public static JsonObject parseJson(String jsonString) {
		JsonParser jsonParser = new JsonParser();
		return jsonParser.parse(jsonString).getAsJsonObject();
	}

	/**
	 * Parses the json file and returns JsonObject.
	 * 
	 * @param jsonFile
	 *            json file
	 * @return JsonObject
	 */
	public static JsonObject parseJson(File jsonFile) throws FileNotFoundException {
		FileReader fileReader = null;
		try {
			JsonObject jsonObject = new JsonObject();
			Gson gson = new GsonBuilder().create();
			fileReader = new FileReader(jsonFile);
			jsonObject = gson.fromJson(fileReader, JsonObject.class);
			return jsonObject;
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
