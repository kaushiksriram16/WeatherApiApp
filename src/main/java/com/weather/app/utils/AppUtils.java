package com.weather.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {
	
	public String dateToStamp(String dateString) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date date = dateFormat.parse(dateString);
        long timeStamp = date.getTime() / 1000;
		return Long.toString(timeStamp);
	}
	
	public String tempToString(Double tempInKelvin) {
		double tempInCelsius = tempInKelvin - 273.15;
		double tempInFahrenheit = tempInCelsius * 9/5 + 32;
		return String.format("%.2f°C, %.2f°F, %.2fK", tempInCelsius, tempInFahrenheit, tempInKelvin);
	}
}
