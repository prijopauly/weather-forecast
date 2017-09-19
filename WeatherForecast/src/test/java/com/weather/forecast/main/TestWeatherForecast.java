package com.weather.forecast.main;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * @author Prijo Pauly
 * @version 0.0.1
 * @since Sep 15, 2017 Copyright (c) 2017, Prijo Pauly. All rights reserved.
 */
public class TestWeatherForecast {
	/**
	 * Test method for
	 * com.weather.forecast.main.WeatherForecast#getWeatherCondition(double,double)
	 */
	@Test
	public void testGetWeatherCondition() {
		try {
			Class<?> c = Class.forName("com.weather.forecast.main.WeatherForecast");
			Object o = c.newInstance();
			Method m = c.getDeclaredMethod("getWeatherCondition", double.class, double.class);
			m.setAccessible(true);
			String result = (String) m.invoke(o, -10, 10);
			assertEquals(result, "Snow");
			result = (String) m.invoke(o, 10, 81);
			assertEquals(result, "Rain");
			result = (String) m.invoke(o, 10, 10);
			assertEquals(result, "Sunny");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException
				| SecurityException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Test method for
	 * com.weather.forecast.main.WeatherForecast#getLowerLimitForDay(int,int)
	 */
	@Test
	public void testGetLowerLimitForDay() {
		try {
			Class<?> c = Class.forName("com.weather.forecast.main.WeatherForecast");
			Object o = c.newInstance();
			Method m = c.getDeclaredMethod("getLowerLimitForDay", int.class, int.class);
			m.setAccessible(true);
			int result = (int) m.invoke(o, 1, 2);
			assertEquals(1, result);
			result = (int) m.invoke(o, 3, 2);
			assertEquals(1, result);
			result = (int) m.invoke(o, 28, 2);
			assertEquals(25, result);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException
				| SecurityException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * com.weather.forecast.main.WeatherForecast#getUpperLimitForDay(int,int)
	 */
	@Test
	public void testGetUpperLimitForDay() {
		try {
			Class<?> c = Class.forName("com.weather.forecast.main.WeatherForecast");
			Object o = c.newInstance();
			Method m = c.getDeclaredMethod("getUpperLimitForDay", int.class, int.class);
			m.setAccessible(true);
			int result = (int) m.invoke(o, 28, 2);
			assertEquals(28, result);
			result = (int) m.invoke(o, 31, 3);
			assertEquals(31, result);
			result = (int) m.invoke(o, 30, 4);
			assertEquals(30, result);
			result = (int) m.invoke(o, 25, 4);
			assertEquals(28, result);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException
				| SecurityException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Test method for
	 * com.weather.forecast.main.WeatherForecast#getMaximumDaysInMonth(int)
	 */
	@Test
	// @Ignore
	public void testGetMaximumDaysInMonth() {
		try {
			Class<?> c = Class.forName("com.weather.forecast.main.WeatherForecast");
			Object o = c.newInstance();
			Method m = c.getDeclaredMethod("getMaximumDaysInMonth", int.class);
			m.setAccessible(true);
			int result = (int) m.invoke(o, 1);
			assertEquals(31, result);
			result = (int) m.invoke(o, 2);
			assertEquals(28, result);
			result = (int) m.invoke(o, 3);
			assertEquals(31, result);
			result = (int) m.invoke(o, 4);
			assertEquals(30, result);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException
				| SecurityException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
