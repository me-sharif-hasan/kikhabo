package com.iishanto.kikhabo.infrastructure.services.weather;

import com.iishanto.kikhabo.domain.entities.weather.Weather;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface WeatherService {
    Weather getWeatherFromCoordinate(double lat, double lon);
    Weather getWeatherFromPlace(String place) throws IOException;
}
