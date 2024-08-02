package com.iishanto.kikhabo.infrastructure.data;

import com.iishanto.kikhabo.domain.datasource.WeatherDataSource;
import com.iishanto.kikhabo.domain.entities.weather.Weather;
import com.iishanto.kikhabo.infrastructure.services.weather.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

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
        try{
            return weatherService.getWeatherFromPlace(locationName);
        }catch (Exception e){
            Weather weather = new Weather();
            weather.setDate(DateFormat.getDateInstance().format(new Date()));
            return weather;
        }
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
