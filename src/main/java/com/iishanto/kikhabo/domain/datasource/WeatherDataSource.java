package com.iishanto.kikhabo.domain.datasource;

import com.iishanto.kikhabo.domain.entities.weather.Weather;

public interface WeatherDataSource {
    Weather getWeather(Double lat, Double lon);
    Weather getWeather(String locationName);
    String getSeason(String locationName);
    String getSeason(Double lat, Double lon);
}
