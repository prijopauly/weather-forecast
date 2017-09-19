package com.weather.forecast.model;

/**
 * The Constants class has unchanged constants used in various places in the
 * program.
 *
 * @author Prijo Pauly
 * @version 0.0.1
 * @since Sep 15, 2017 Copyright (c) 2017, Prijo Pauly. All rights reserved.
 */
public class Constants {
	// Below constants represent the base api urls used as source for various
	// input data
	public static final String API_BASE_GOOGLE_GEOCODE = "http://maps.googleapis.com/maps/api/geocode/json";
	public static final String API_BASE_GOOGLE_ELEVATION = "http://maps.googleapis.com/maps/api/elevation/json";
	public static final String API_BASE_GOOGLE_TIMEZONE = "https://maps.googleapis.com/maps/api/timezone/json";
	public static final String API_BASE_AUBOM_CLIMATE = "http://www.bom.gov.au/climate/dwo/";

	// Input JSON containing location name and code
	public static final String INPUT_LOCJSON = "props/input_locations.json";

	// Input CSV file containing folder name.
	public static final String INPUT_FILE_DIRECTORY = "input";
}
