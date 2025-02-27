package com.iishanto.kikhabo;

import com.iishanto.kikhabo.domain.datasource.WeatherDataSource;
import com.iishanto.kikhabo.infrastructure.data.WeatherDataSourceImpl;
import com.iishanto.kikhabo.infrastructure.services.weather.OpenWeatherApiServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import tech.ailef.snapadmin.external.SnapAdminAutoConfiguration;

@ImportAutoConfiguration(SnapAdminAutoConfiguration.class)
@SpringBootApplication
public class kikhaboApplication {
	static Logger logger= LoggerFactory.getLogger("KI KHABO");
	public static void main(String[] args) {
		logger.info("Kikhabo Application Started. Login using {}","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZS5zaGFyaWYuaGFzYW5AZ21haWwuY29tIiwiaXNzIjoiSldUIFNlcnZpY2UiLCJpYXQiOjE3MjUxMjMzOTgsImV4cCI6MTc1NjY1OTM5OH0.LdfOqbvnMtWKegRNcCe5wy1uDUYiJgsLydrcHHBCq9FUF8CCPE6M54vuFijusKhbx81yO67j3hRvwVkVZNGuhQ");
		ApplicationContext appContext = new AnnotationConfigApplicationContext();
		SpringApplication.run(kikhaboApplication.class, args);
	}

}
