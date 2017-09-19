package com.weather.forecast.model;

/**
 * The LatLong is model class representing the latitude and longitude
 * corresponds to a location
 * 
 * @author Prijo Pauly
 * @version 0.0.1
 * @since Sep 15, 2017 Copyright (c) 2017, Prijo Pauly. All rights reserved.
 */
public class LatLong {

	private final double latitude;

	private final double longitude;

	/**
	 * Constructor with latitude and longitude as arguments.
	 * @param latitude latitude
	 * @param longitude longitude
	 */
	public LatLong(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return this.latitude + "," + this.longitude;
	}
}
