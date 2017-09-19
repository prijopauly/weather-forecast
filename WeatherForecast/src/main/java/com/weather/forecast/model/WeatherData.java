package com.weather.forecast.model;

/**
 * The WeatherData is model class represents the output forecast result
 *
 * @author Prijo Pauly
 * @version 0.0.1
 * @since Sep 15, 2017 Copyright (c) 2017, Prijo Pauly. All rights reserved.
 */
public class WeatherData {

	private final String location;

	private final LatLong latLong;

	private final double elevation;

	private final String localTime;

	private final String weatherCondition;

	private final double temperature;

	private final double pressure;

	private final double humidity;

	/**
	 * Constructor of the model class WeatherData.
	 * @param location location name
	 * @param latLong model class object with latitude and longitude
	 * @param elevation elevation
	 * @param localTime local date string
	 * @param weatherCondition determines Sunny, Snow or Rainy
	 * @param temperature temperature
	 * @param pressure pressure
	 * @param humidity humidity
	 */
	public WeatherData(String location, LatLong latLong, double elevation, String localTime, String weatherCondition,
			double temperature, double pressure, double humidity) {
		this.location = location;
		this.latLong = latLong;
		this.elevation = elevation;
		this.localTime = localTime;
		this.weatherCondition = weatherCondition;
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
	}

	/**
	 * @return location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return latLong
	 */
	public LatLong getLatLong() {
		return latLong;
	}

	/**
	 * @return elevation
	 */
	public double getElevation() {
		return elevation;
	}

	/**
	 * @return localTime
	 */
	public String getLocalTime() {
		return localTime;
	}

	/**
	 * @return weatherCondition
	 */
	public String getWeatherCondition() {
		return weatherCondition;
	}

	/**
	 * @return temperature
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * @return pressure
	 */
	public double getPressure() {
		return pressure;
	}

	/**
	 * @return pressure
	 */
	public double getHumidity() {
		return pressure;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(location).append("|").append(String.format("%.2f", latLong.getLatitude()))
				.append(",").append(String.format("%.2f", latLong.getLongitude())).append(",").append((int) elevation)
				.append("|").append(localTime).append("|").append(weatherCondition).append("|")
				.append(String.format("%.1f", temperature)).append("|").append(String.format("%.1f", pressure))
				.append("|").append((int) humidity).toString();
	}
}
