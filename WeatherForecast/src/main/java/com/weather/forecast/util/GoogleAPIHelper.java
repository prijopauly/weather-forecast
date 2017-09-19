package com.weather.forecast.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.weather.forecast.exceptions.GoogleAPIException;
import com.weather.forecast.model.Constants;
import com.weather.forecast.model.LatLong;

/**
 * This util class provides methods to connect Google API to retrieve latitude,
 * longitude, elevation and time zone for the given location.
 *
 * @author Prijo Pauly
 * @version 0.0.1
 * @since Sep 15, 2017 Copyright (c) 2017, Prijo Pauly. All rights reserved.
 */
public class GoogleAPIHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleAPIHelper.class);

	/**
	 * This method returns time zone id for the given latitude and longitude.
	 * 
	 * @param latLong
	 *            object of the model class LatLong
	 * @return time zone id
	 * @throws GoogleAPIException
	 *             throws when some IO exception or network related exception
	 *             happens while connection to Google API
	 */
	public static String getTimeZone(LatLong latLong) throws GoogleAPIException {
		String timeZone = null;
		try {
			LOGGER.debug(new StringBuilder().append("Fetching time zone from Google api, ").append(latLong).toString());
			long timeInSeconds = new Date().getTime() / 1000;
			String url = new StringBuilder().append(Constants.API_BASE_GOOGLE_TIMEZONE).append("?location=")
					.append(latLong.getLatitude()).append(",").append(latLong.getLongitude()).append("&timestamp=")
					.append(timeInSeconds).toString();
			String jsonString = getHttpResponse(url);
			JsonObject jsonObject = JsonHelper.parseJson(jsonString);
			timeZone = jsonObject.getAsJsonObject().get("timeZoneId").getAsString();
		} catch (Exception e) {
			LOGGER.error("Exception while fetching time zone from google api", e);
			throw new GoogleAPIException("IO Exception while connecting to google api");
		}

		return timeZone;
	}

	/**
	 * This method returns elevation value for the given latitude and longitude.
	 * 
	 * @param latLong
	 *            object of the model class LatLong
	 * @return elevation
	 * @throws GoogleAPIException
	 *             throws when some IO exception or network related exception
	 *             happens while connection to Google API
	 */
	public static double getElevation(LatLong latLong) throws GoogleAPIException {
		double elevation = 0;
		try {
			LOGGER.debug(new StringBuilder().append("Fetching elevation from Google api, ").append(latLong).toString());
			String url = new StringBuilder().append(Constants.API_BASE_GOOGLE_ELEVATION).append("?locations=")
					.append(latLong.getLatitude()).append(",").append(latLong.getLongitude()).toString();
			String jsonString = getHttpResponse(url);
			JsonObject jsonObject = JsonHelper.parseJson(jsonString);
			JsonArray jsonArray = jsonObject.getAsJsonObject().getAsJsonArray("results");
			if (jsonArray.size() > 0) {
				elevation = jsonArray.get(0).getAsJsonObject().get("elevation").getAsDouble();
			}
		} catch (Exception e) {
			LOGGER.error("Exception while fetching elevation from google api", e);
			throw new GoogleAPIException("IO Exception while connecting to google api");
		}

		return elevation;
	}

	/**
	 * This method returns latitude and longitude id for the given location
	 * name.
	 * 
	 * @param locationName
	 *            location name
	 * @return object of the model class LatLong
	 * @throws GoogleAPIException
	 *             throws when some IO exception or network related exception
	 *             happens while connection to Google API
	 */
	public static LatLong getLatLong(String locationName) throws GoogleAPIException {
		try {
			LOGGER.debug(new StringBuilder().append("Fetching latitude and longitude from Google api, ")
					.append(locationName).toString());
			String url = new StringBuilder().append(Constants.API_BASE_GOOGLE_GEOCODE).append("?address=")
					.append(URLEncoder.encode(locationName, "UTF-8")).toString();
			String jsonString = getHttpResponse(url);
			JsonObject jsonObject = JsonHelper.parseJson(jsonString);
			JsonObject latLongJsonObject = jsonObject.getAsJsonObject().getAsJsonArray("results").get(0)
					.getAsJsonObject().getAsJsonObject("geometry").getAsJsonObject("location");

			LatLong latLong = new LatLong(latLongJsonObject.get("lat").getAsDouble(),
					latLongJsonObject.get("lng").getAsDouble());
			return latLong;
		} catch (Exception e) {
			LOGGER.error("Exception while fetching latitude and longitude from google api", e);
			throw new GoogleAPIException("IO Exception while connecting to google api");
		}
	}

	private static String getHttpResponse(String url) throws IOException {
		BufferedReader br = null;
		try {
			LOGGER.debug(new StringBuilder().append("Http get request to url ").append(url).toString());
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				StringBuilder result = new StringBuilder();
				String line = "";
				while ((line = br.readLine()) != null) {
					result.append(line);
				}
				LOGGER.debug(new StringBuilder().append("Http get response").append(result.toString()).toString());
				return result.toString();
			}
			throw new IOException(new StringBuilder().append("Http status code is ").append(statusCode).toString());
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}
}
