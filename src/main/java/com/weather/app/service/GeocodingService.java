package com.weather.app.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeocodingService {
	
	private RestTemplate restTemplate;
	private ObjectMapper objectMapper;
	
	public GeocodingService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
		this.objectMapper = new ObjectMapper();
	}
	
	String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=";
	private String apiKey = "<YOUR API KEY>";
	
	@Cacheable("coordinates")
	public String[] getCoordinates(String pinCode) throws JsonMappingException, JsonProcessingException {
		String[] coordinates = new String[2];
		String response = restTemplate.getForObject(apiUrl+pinCode+"&key="+apiKey, String.class);
		JsonNode jsonNode = objectMapper.readTree(response); 
		JsonNode resultsNode = jsonNode.get("results").get(0);
		JsonNode locationNode = resultsNode.get("geometry").get("location");
		coordinates[0] = locationNode.get("lat").asText();
		coordinates[1] = locationNode.get("lng").asText();
		return coordinates;
	}
}
