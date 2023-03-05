package com.weather.app.entiity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "weather_data", schema = "public")
@Data
@Entity
public class WeatherData {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String for_date;
	private String pincode;
	private String main;
	private String description;
	private String temperature;
	private String humidity;
	
	public WeatherData(String for_date, String pincode, String main, String description, String temperature, String humidity) {
		this.for_date = for_date;
		this.pincode = pincode;
		this.main = main;
		this.description = description;
		this.temperature = temperature;
		this.humidity = humidity;
	}
	
	
	public String getFor_date() {
		return for_date;
	}

	public void setFor_date(String for_date) {
		this.for_date = for_date;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}