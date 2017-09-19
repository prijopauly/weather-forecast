package com.weather.forecast.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.weather.forecast.exceptions.GoogleAPIException;
import com.weather.forecast.model.LatLong;

/**
 * @author Prijo Pauly
 * @version 0.0.1
 * @since Sep 15, 2017 Copyright (c) 2017, Prijo Pauly. All rights reserved.
 */
public class TestGoogleAPIHelper {

	/**
	 * Test method for
	 * {@link com.weather.forecast.util.GoogleAPIHelper#getLatLong(String)}
	 * 
	 * @throws GoogleAPIException
	 *             GoogleAPIException
	 */
	@Test(expected = GoogleAPIException.class)
	public void testGetLatLong() throws GoogleAPIException {
		String locationName = "Sydney";
		assertEquals(GoogleAPIHelper.getLatLong(locationName).getClass(), LatLong.class);
		locationName = "";
		GoogleAPIHelper.getLatLong(locationName);
		//
	}

	/**
	 * Test method for
	 * {@link com.weather.forecast.util.GoogleAPIHelper#getTimeZone(LatLong)}
	 * 
	 * @throws GoogleAPIException
	 *             GoogleAPIException
	 */
	@Test(expected = GoogleAPIException.class)
	public void testGetTimeZone() throws GoogleAPIException {
		LatLong correctOne = new LatLong(-33.5781409, 151.3430209);
		assertEquals(GoogleAPIHelper.getTimeZone(correctOne).getClass(), String.class);
		LatLong wrongOne = new LatLong(2000000, 2000000);
		GoogleAPIHelper.getTimeZone(wrongOne);
	}

	/**
	 * Test method for
	 * {@link com.weather.forecast.util.GoogleAPIHelper#getElevation(LatLong)}
	 * 
	 * @throws GoogleAPIException
	 *             GoogleAPIException
	 */
	@Test(expected = GoogleAPIException.class)
	public void testGetElevation() throws GoogleAPIException {
		LatLong correctOne = new LatLong(-33.5781409, 151.3430209);
		GoogleAPIHelper.getElevation(correctOne);
		assertTrue(true);
		LatLong wrongOne = new LatLong(2000000, 2000000);
		GoogleAPIHelper.getTimeZone(wrongOne);
	}

}
