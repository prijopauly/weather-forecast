package com.weather.forecast.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.weather.forecast.util.TestGoogleAPIHelper;
import com.weather.forecast.util.TestJsonHelper;
import com.weather.forecast.util.TestUtils;

/**
 * @author Prijo Pauly
 * @version 0.0.1
 * @since Sep 15, 2017 Copyright (c) 2017, Prijo Pauly. All rights reserved.
 */
@RunWith(Suite.class)
@SuiteClasses({ TestWeatherForecast.class, TestUtils.class, TestGoogleAPIHelper.class, TestJsonHelper.class, })
public class AllTests {

}