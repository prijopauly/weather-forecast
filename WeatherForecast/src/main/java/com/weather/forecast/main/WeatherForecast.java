package com.weather.forecast.main;

import static com.weather.forecast.model.Constants.INPUT_FILE_DIRECTORY;
import static com.weather.forecast.model.Constants.INPUT_LOCJSON;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.weather.forecast.exceptions.GoogleAPIException;
import com.weather.forecast.model.LatLong;
import com.weather.forecast.model.WeatherData;
import com.weather.forecast.util.GoogleAPIHelper;
import com.weather.forecast.util.JsonHelper;
import com.weather.forecast.util.Utils;

/**
 * The Main class is the main method which invoked while jar run The class
 * contains the entire flow of weather data forecast
 *
 * @author Prijo Pauly
 * @version 0.0.1
 * @since Sep 15, 2017 Copyright (c) 2017, Prijo Pauly. All rights reserved.
 */
public class WeatherForecast {
	private static final Logger LOGGER = LoggerFactory.getLogger(WeatherForecast.class);

	public static void main(String[] args) {
		PropertyConfigurator.configure("props/log4j.properties");
		WeatherForecast forecast = new WeatherForecast();
		forecast.forecast();
	}

	/**
	 * Forecasting weather conditions based on historic data available. This
	 * method reads location name from INPUT_LOCJSON file and reads
	 * corresponding historic weather data from INPUT folder. Based on this
	 * historic data,this will predicts temperature, pressure and relative
	 * humidity for a location mentioned earlier using Simple linear regression
	 * algorithm.
	 */
	private void forecast() {
		LOGGER.info("Excution begins");
		File file = new File(INPUT_LOCJSON);
		JsonObject jsonLocations = null;
		try {
			jsonLocations = JsonHelper.parseJson(file);
		} catch (FileNotFoundException e) {
			LOGGER.error("Exception while reading the INPUT_LOC_JSON file", e);
			LOGGER.info("Location Json file is missing, exiting");
			return;
		}

		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		JsonArray locationArray = jsonLocations.getAsJsonObject().getAsJsonArray("locations");
		for (JsonElement jsonElement : locationArray) {
			JsonObject jsonLocation = jsonElement.getAsJsonObject();
			String name = jsonLocation.get("name").getAsString();
			String iatacode = jsonLocation.get("iatacode").getAsString();
			WeatherData weatherData = null;
			try {
				weatherData = getForecastedWeatherData(name, iatacode, month, dayOfMonth);
			} catch (IOException | GoogleAPIException e) {
				LOGGER.error(new StringBuilder().append("Exception while fetching data for location ").append(name)
						.toString(), e);
			}
			if (weatherData != null) {
				LOGGER.info(weatherData.toString());
				System.out.println(weatherData);
			}

			month++;
			if (month >= 12) {
				month = 1;
			}
		}
	}

	/**
	 * This method creates a WeatherData object from historic data that are
	 * available in the form of CSV files. Simple linear regression algorithm is
	 * used to to predict temperature and
	 * 
	 * @param locationName
	 *            location name
	 * @param iatacode
	 *            iatacode for location
	 * @param month
	 *            predicts weather data for this month
	 * @param dayOfMonth
	 *            predicts weather data for this day
	 * @return returns the objects for WeatherData model class that represents
	 *         the weather information
	 * @throws IOException
	 *             throws when some IOException happens during reading the CSV
	 *             file
	 * @throws GoogleAPIException
	 *             throws when some exceptions happens while connecting to
	 *             Google API
	 */
	private WeatherData getForecastedWeatherData(String locationName, String iatacode, int month, int dayOfMonth)
			throws IOException, GoogleAPIException {
		LOGGER.debug(new StringBuilder().append("Predicting weather for location=").append(locationName)
				.append(" and iatacode=").append(iatacode).append(" with month=").append(month).append(" dayOfMonth=")
				.append(dayOfMonth).toString());
		int dayLowerLimit = getLowerLimitForDay(dayOfMonth, month);
		int dayUpperLimit = getUpperLimitForDay(dayOfMonth, month);
		dayLowerLimit = (dayUpperLimit == getMaximumDaysInMonth(month)) ? dayUpperLimit - 6 : dayLowerLimit;
		dayUpperLimit = (dayLowerLimit == 1) ? 7 : dayUpperLimit;
		String fileDirectory = new StringBuilder().append(INPUT_FILE_DIRECTORY).append("/").append(iatacode).toString();
		String regexMatchedFileNames = new StringBuilder().append(iatacode).append(".\\d{4}")
				.append(String.format("%02d", month)).append(".csv").toString();
		File[] files = Utils.listFilesMatching(new File(fileDirectory), regexMatchedFileNames);
		SimpleRegression regressionForTemp = new SimpleRegression();
		SimpleRegression regressionForRelHum = new SimpleRegression();
		SimpleRegression regressionForPress = new SimpleRegression();
		LOGGER.debug(String.format("There are %d historic data files are available", files.length));
		for (int i = 0; i < files.length; i++) {
			FileReader reader = new FileReader(files[i]);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withQuote(null).withDelimiter(',').parse(reader);
			for (CSVRecord csvRecord : records) {
				if (csvRecord.size() == 22) {
					// For checking if the csvRecord record has description or
					// required data
					if (Utils.isNumeric(csvRecord.get(17))) {

						String date = csvRecord.get(1);
						String[] parts = date.split("-");
						if (parts.length == 3) {
							int day = Integer.parseInt(parts[2]);
							if (day >= dayLowerLimit && day <= dayUpperLimit) {

								regressionForTemp.addData(day, Double.parseDouble(csvRecord.get(10)));
								regressionForTemp.addData(day, Double.parseDouble(csvRecord.get(16)));

								regressionForRelHum.addData(day, Double.parseDouble(csvRecord.get(11)));
								regressionForRelHum.addData(day, Double.parseDouble(csvRecord.get(17)));

								regressionForPress.addData(day, Double.parseDouble(csvRecord.get(15)));
								regressionForPress.addData(day, Double.parseDouble(csvRecord.get(21)));

							}
						}
					}
				}
			}
		}
		double predictedTemp = regressionForTemp.predict(dayOfMonth);
		double predictedRelHum = regressionForRelHum.predict(dayOfMonth);
		double predictedPress = regressionForPress.predict(dayOfMonth);

		LatLong latLong = GoogleAPIHelper.getLatLong(locationName);
		double elevation = GoogleAPIHelper.getElevation(latLong);
		String weatherCondition = getWeatherCondition(predictedTemp, predictedRelHum);

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		String date = Utils.getFormattedDateString(calendar.getTime());
		WeatherData weatherData = new WeatherData(locationName, latLong, elevation, date, weatherCondition,
				predictedTemp, predictedPress, predictedRelHum);
		return weatherData;
	}

	/**
	 * Returns the weather condition by considering the temperature and relative
	 * humidity.
	 * 
	 * @param temperature
	 *            temperature
	 * @param relativeHumidity
	 *            relative humidity
	 * @return weather condition
	 */
	private String getWeatherCondition(double temperature, double relativeHumidity) {
		String weatherCondition = "Sunny";
		if (temperature < 0)
			weatherCondition = "Snow";
		else if (relativeHumidity > 80)
			weatherCondition = "Rain";
		else
			weatherCondition = "Sunny";
		return weatherCondition;
	}

	/**
	 * Returns a lower limit for a day in a month. Lower limit is calculated by
	 * subtracting 3 from it.
	 * 
	 * @param dayOfMonth
	 *            day in a month
	 * @param month
	 *            month of the year
	 * @return upper limit for the day
	 */
	private int getLowerLimitForDay(int dayOfMonth, int month) {
		int lowerLimit = dayOfMonth - 3;
		lowerLimit = lowerLimit > 0 ? lowerLimit : 1;
		return lowerLimit;
	}

	/**
	 * Returns a upper limit for a day in a month. Upper limit calculated by
	 * adding 3 to it, taking the max number of month in to consideration.
	 * 
	 * @param dayOfMonth
	 *            day in a month
	 * @param month
	 *            month of the year
	 * @return upper limit for the day
	 */
	private int getUpperLimitForDay(int dayOfMonth, int month) {
		int max = getMaximumDaysInMonth(month);
		int upperLimit = dayOfMonth + 3;
		upperLimit = max - upperLimit > 0 ? upperLimit : max;
		return upperLimit;
	}

	/**
	 * Returns the the maximum number of days for a month.
	 * 
	 * @param month
	 *            month of the year
	 * @return the max number for the specified month
	 */
	private int getMaximumDaysInMonth(int month) {
		int max = 28;

		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			max = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			max = 30;
			break;
		case 2:
			max = 28;
			break;
		}
		return max;
	}
}
