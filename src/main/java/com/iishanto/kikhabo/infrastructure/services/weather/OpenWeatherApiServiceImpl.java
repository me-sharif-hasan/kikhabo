package com.iishanto.kikhabo.infrastructure.services.weather;

import com.iishanto.kikhabo.domain.entities.weather.Weather;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenWeatherApiServiceImpl implements WeatherService{
    private final String openWeatherApiKey;
    private final RestClient restClient;
    private final String openWeatherEndpoint;
    Logger logger;
    public OpenWeatherApiServiceImpl(@Value("${openweather.apikey}") String openWeatherApiKey,RestClient restClient,@Value("${openwather.endpoint}") String openWeatherEndpoint,Logger logger) {
        this.openWeatherApiKey = openWeatherApiKey;
        this.restClient=restClient;
        this.openWeatherEndpoint=openWeatherEndpoint;
        this.logger=logger;
    }
    @Override
    public Weather getWeatherFromCoordinate(double lat, double lon) {
        String res = restClient.get().uri(buildUrlFromCoordinate(lat,lon)).retrieve().body(String.class);
        logger.info(res);
        return null;
    }

    @Override
    public Weather getWeatherFromPlace(String place) {
        return null;
    }

    private String buildUrlFromCoordinate(double lat,double lon){
        return "%s?lat=%f&lon=%f&appid=%s".formatted(openWeatherEndpoint,lat,lon,openWeatherApiKey);
    }
}
