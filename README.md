# weather-forecast
Weather Forecast predicts weather corresponds to location based on past data

Supported locations (default): [Sydney](http://www.bom.gov.au/climate/dwo/IDCJDW2124.latest.shtml), [Canberra](http://www.bom.gov.au/climate/dwo/IDCJDW2801.latest.shtml), [Melbourne](http://www.bom.gov.au/climate/dwo/IDCJDW3033.latest.shtml), [Brisbane](http://www.bom.gov.au/climate/dwo/IDCJDW4019.latest.shtml), [Gold Coast](http://www.bom.gov.au/climate/dwo/IDCJDW4050.latest.shtml), [Adelaide](http://www.bom.gov.au/climate/dwo/IDCJDW5002.latest.shtml), [Perth](http://www.bom.gov.au/climate/dwo/IDCJDW6111.latest.shtml), [Townsville](http://www.bom.gov.au/climate/dwo/IDCJDW4128.latest.shtml), [Darwin](http://www.bom.gov.au/climate/dwo/IDCJDW8014.latest.shtml) & [Casey Antarctica](http://www.bom.gov.au/climate/dwo/IDCJDW9203.latest.shtml)

Supported timeframe: At 9am and 3pm, each day for a week, starting from the very next day of program execution (time based on local time of the corresponding location)

As far as valid past weather data is available in [Bureau of Meteorology, Commonwealth of Australia](http://www.bom.gov.au/climate/dwo/), the locations can be customized in [_input_locations.json_](WeatherForecast/target/props/input_locations.json)

## Build

### Prerequisites

* [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 1.7 or higher ([```JAVA_HOME```](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/) and [```PATH```](https://en.wikipedia.org/wiki/PATH_(variable)) set) for compile and execution
* [Apache Maven](https://maven.apache.org/download.cgi) 3.3 or higher ([```MVN_HOME```](https://maven.apache.org/install.html) and [```PATH```](https://en.wikipedia.org/wiki/PATH_(variable)) set) for build

Connection to Internet is required if past weather data is to be downloaded

### Installation

Goto the project base directory, [_WeatherForecast_](WeatherForecast/), the directory where [_pom.xml_](WeatherForecast/pom.xml) for [_weather-forecast_](WeatherForecast) is present. 

Execute the command:

<pre>
<b>mvn clean install</b>
</pre>


The following required outputs (*there will be other stuffs as well, along with the required*) will be generated in the [_target_](weatherforecast/target) directory:

* [_WeatherForecast-0.0.1-SNAPSHOT.jar_](WeatherForecast/target/WeatherForecast-0.0.1-SNAPSHOT.jar)
* [_props_](WeatherForecast/target/props)
* [_input_](WeatherForecast/target/input)
* [lib](WeatherForecast/target/lib)

If any issues in junit testcases or if you want to simply by-pass test execution, you can always skip the unit testcases by executing below mentioned command:
>
><pre>
><b>mvn clean install -Dmaven.test.skip=true</b>
></pre>

_The [WeatherForecast-0.0.1-SNAPSHOT.jar](WeatherForecast/target/WeatherForecast-0.0.1-SNAPSHOT.jar) is already created with latest classes and you should be able to use it right away with necessary inputs, without going for any more buid steps, unless if you have any changes_

## Execution

The program execution requires all four _build_ outputs ([_WeatherForecast-0.0.1-SNAPSHOT.jar](WeatherForecast/target/WeatherForecast-0.0.1-SNAPSHOT.jar), [_props_](WeatherForecast/target/props), [_input_](WeatherForecast/target/input)) to be in the same location, hence can be run from either:

1. from [_target_](WeatherForecast/target) directory, right after the _build_ or
2. copy the four artifacts to a common location and run from there


The following command is to be used for the execution of the program:

<pre>
<b>java -jar WeatherForecast-0.0.1-SNAPSHOT.jar</b>
</pre>


## Forecast Result

On execution of the program, result, the forecast weather data, will be displayed in the console.

format of each entry displayed on the console will be : ```Location|Position|Local Time|Conditions|Temperature|Pressure|Humidity```

where 
* Location is an optional label describing one or more positions,
* Position is a comma-separated triple containing latitude, longitude, and elevation in metres above sea level,
* Local time is an [ISO8601](https://en.wikipedia.org/wiki/ISO_8601) date time,
* Conditions is either Snow, Rain, Sunny,
* Temperature is in °C,
* Pressure is in hPa, and
* Relative humidity is a %

Example data: *`Brisbane|-27.47,153.03,22|2017-07-28T23:00:00Z|Rain|+24.0|1027.6|80`*

## How the program works
WeatherForecast application predicts TEMPERATURE, PRESSURE, and RELATIVE HUMIDITY by applying Simple Linear Regression Alogorim over a collection of historic data.

Consider the location name as Sydney and iatacode is IDCJDW2124 and the day for weather prediction is 24 Septemper 2017.

The program work as follows

 - Application will read all the data files that are available in month SEPTEMBER and for the location IDCJDW2124
 - It will take the temperature, pressure and relative humidity measurements from this files for the days Sep 21 to Sep 27, ie 24-3 and 24+3
 - A pair of day-value of temperature or pressure or relative humidity will be suppied to Simple Linear Regression Algorithm
 - Using Simple Linear Algorithm application will predict temperature, pressure and relative humidity for the day 24 Septemper

## Author / Contribution

Prijo Pauly
 
## Version

0.0.1 - Initial and complete release version

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) for details

