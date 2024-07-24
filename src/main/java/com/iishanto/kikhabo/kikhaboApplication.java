package com.iishanto.kikhabo;

import com.iishanto.kikhabo.domain.datasource.WeatherDataSource;
import com.iishanto.kikhabo.infrastructure.data.WeatherDataSourceImpl;
import com.iishanto.kikhabo.infrastructure.services.weather.OpenWeatherApiServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class kikhaboApplication {
	static Logger logger= LoggerFactory.getLogger("KI KHABO");
	public static void main(String[] args) {
		ApplicationContext appContext = new AnnotationConfigApplicationContext();
		SpringApplication.run(kikhaboApplication.class, args);
	}

}
