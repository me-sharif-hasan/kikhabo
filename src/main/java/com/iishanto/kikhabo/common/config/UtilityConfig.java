package com.iishanto.kikhabo.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.datasource.WeatherDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilityConfig {
    @Bean
    public Logger getLogger(){
        return LoggerFactory.getLogger("ii system log");
    }
}
