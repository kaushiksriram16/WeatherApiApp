package com.weather.app.service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.Cacheable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.app.entiity.WeatherData;
import com.weather.app.repository.WeatherRepository;
import com.weather.app.utils.AppUtils;

import jakarta.transaction.Transactional;


@Service
public class WeatherService {
	
	@Autowired
	private WeatherRepository repository;
	
	private final String ApiUrl = "https://api.openweathermap.org/data/3.0/onecall";
	private final String apiKey = "<YOUR API KEY>";
	
	private RestTemplate restTemplate;
	private ObjectMapper objectMapper;
	private AppUtils appUtils;
	
	public WeatherService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.objectMapper = new ObjectMapper();
		this.appUtils = new AppUtils();
	}
	
	@Cacheable("weatherData")
	public WeatherData getCurrentWeather(String[] coordinates, String pincode) throws IOException {
		String lat = coordinates[0];
		String lon = coordinates[1];
		String response = restTemplate.getForObject(ApiUrl+"?lat="+lat+"&lon="+lon+"&exclude=hourly,daily"+"&appid="+apiKey, String.class);
				
		JsonNode jsonNode = objectMapper.readTree(response);
		JsonNode weatherNode = jsonNode.get("current").get("weather").get(0);
		String main = weatherNode.get("main").asText();
		String description = weatherNode.get("description").asText();
		JsonNode tempHum = jsonNode.get("current");
		
		double tempInKelvin = tempHum.get("temp").asDouble();
		String tempString = appUtils.tempToString(tempInKelvin);
		String humidity = tempHum.get("humidity").asText()+"%";
		
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		String formattedDate = currentDate.format(formatter);
	
		WeatherData weatherData = new WeatherData(formattedDate, pincode, main, description, tempString, humidity);
		return saveWeather(weatherData);
	}
	
	@Cacheable("weatherData")
	public WeatherData getOldWeather(String[] coordinates, String pincode, String date) throws ParseException, JsonMappingException, JsonProcessingException {
		String lat = coordinates[0];
		String lon = coordinates[1];
		String dateStamp = appUtils.dateToStamp(date);
		
		String response = restTemplate.getForObject(ApiUrl+"/timemachine?&lat="+lat+"&lon="+lon+"&dt="+dateStamp+"&appid="+apiKey, String.class);
				
		JsonNode jsonNode = objectMapper.readTree(response);
		JsonNode dataNode = jsonNode.get("data").get(0);
		
		double tempInKelvin = dataNode.get("temp").asDouble();
		String tempString = appUtils.tempToString(tempInKelvin);
		String humidity = dataNode.get("humidity").asText() + "%";
		
		JsonNode weatherNode = dataNode.get("weather").get(0);
		String main = weatherNode.get("main").asText();
		String description = weatherNode.get("description").asText();
		WeatherData weatherData = new WeatherData(date, pincode, main, description, tempString, humidity);
		return saveWeather(weatherData);
	}
	
	@Transactional
	public WeatherData saveWeather(WeatherData weatherData) {
		return repository.save(weatherData);
	}
}
