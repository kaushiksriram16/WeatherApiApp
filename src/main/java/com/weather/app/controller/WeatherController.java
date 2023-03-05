package com.weather.app.controller;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.weather.app.entiity.WeatherData;
import com.weather.app.service.GeocodingService;
import com.weather.app.service.WeatherService;

@RestController
public class WeatherController {
	
	private final WeatherService weatherService;
	private final GeocodingService geocodingService;
	
	public WeatherController(WeatherService weatherService,
							 GeocodingService geocodingService
							 ) {
		this.weatherService = weatherService;
		this.geocodingService = geocodingService;
	}
	
	@GetMapping("/weather/{pincode}")
	public ResponseEntity<WeatherData> getcurrentWeather(@PathVariable String pincode) throws IOException{
		String[] coordinates = geocodingService.getCoordinates(pincode);
		return new ResponseEntity<>(weatherService.getCurrentWeather(coordinates, pincode), HttpStatus.OK);
	}
	
	@GetMapping("/weather/{pincode}/{date}")
	public ResponseEntity<WeatherData> getOldWeather(@PathVariable String pincode, @PathVariable String date) throws ParseException, JsonMappingException, JsonProcessingException{
		String[] coordinates = geocodingService.getCoordinates(pincode);
		return new ResponseEntity<>(weatherService.getOldWeather(coordinates, pincode, date), HttpStatus.OK);
	}
	
}
