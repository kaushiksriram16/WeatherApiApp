package com.weather.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weather.app.entiity.WeatherData;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherData, Integer> {

}
