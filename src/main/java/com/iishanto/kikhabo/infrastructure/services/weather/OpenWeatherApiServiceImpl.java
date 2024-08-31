package com.iishanto.kikhabo.infrastructure.services.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.entities.weather.Weather;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@Component
public class OpenWeatherApiServiceImpl implements WeatherService{
    private final String openWeatherApiKey;
    private final RestClient restClient;
    private final String openWeatherEndpoint;
    ObjectMapper objectMapper = new ObjectMapper();
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
    public Weather getWeatherFromPlace(String place) throws IOException {
        String url=buildUrlFromLocationName(place);
        String res = restClient.get().uri(url).retrieve().body(String.class);
        JsonNode jsonNode=objectMapper.readTree(res);

        Weather weather=fromJson(jsonNode);
        logger.info(weather.toString());
        return weather;
    }

    private String buildUrlFromLocationName(String place){
        //https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
        return "%s?q=%s&appid=%s".formatted(openWeatherEndpoint,place,openWeatherApiKey);
    }

    private String buildUrlFromCoordinate(double lat,double lon){
        return "%s?lat=%f&lon=%f&appid=%s".formatted(openWeatherEndpoint,lat,lon,openWeatherApiKey);
    }


    public Weather fromJson(JsonNode root) throws IOException, JsonProcessingException {
        Weather weather = new Weather();
        weather.setLocationName(root.path("name").asText());
        weather.setTemperature(root.path("main").path("temp").floatValue());
        weather.setFeelsLike(root.path("main").path("feels_like").floatValue());
        weather.setTempMin(root.path("main").path("temp_min").floatValue());
        weather.setTempMax(root.path("main").path("temp_max").floatValue());
        weather.setPressure(root.path("main").path("pressure").floatValue());
        weather.setHumidity(root.path("main").path("humidity").floatValue());
        weather.setVisibility(root.path("visibility").intValue());
        weather.setWindSpeed(root.path("wind").path("speed").floatValue());
        weather.setWindDirection(root.path("wind").path("deg").floatValue());
        weather.setWindGust(root.path("wind").path("gust").floatValue());
        weather.setCloudiness(root.path("clouds").path("all").intValue());
        weather.setTimestamp(root.path("dt").longValue());
        weather.setSunrise(root.path("sys").path("sunrise").longValue());
        weather.setSunset(root.path("sys").path("sunset").longValue());
        weather.setCountry(root.path("sys").path("country").asText());
        return weather;
    }
}
