package com.iishanto.kikhabo.infrastructure.data;

import com.iishanto.kikhabo.domain.datasource.WeatherDataSource;
import com.iishanto.kikhabo.domain.entities.weather.Weather;
import com.iishanto.kikhabo.infrastructure.services.weather.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class WeatherDataSourceImpl implements WeatherDataSource {
    WeatherService weatherService;
    @Override
    public Weather getWeather(Double lat, Double lon) {
        weatherService.getWeatherFromCoordinate(lat, lon);
        return null;
    }

    @Override
    public Weather getWeather(String locationName) {
        return null;
    }

    @Override
    public String getSeason(String locationName) {
        return "";
    }

    @Override
    public String getSeason(Double lat, Double lon) {
        return "";
    }
}
