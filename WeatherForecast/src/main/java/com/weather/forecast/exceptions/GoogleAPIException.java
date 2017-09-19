package com.weather.forecast.exceptions;

/**
 * Custom exception class GoogleAPIException is used to track all the exception
 * related to Google API.
 * 
 * @author Prijo Pauly
 * @version 0.0.1
 * @since Sep 15, 2017 Copyright (c) 2017, Prijo Pauly. All rights reserved.
 */
public class GoogleAPIException extends Exception {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 45L;

	public GoogleAPIException(String exception) {
		super(exception);
	}
}
