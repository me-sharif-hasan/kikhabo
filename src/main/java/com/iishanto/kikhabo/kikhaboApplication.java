package com.iishanto.kikhabo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class kikhaboApplication {
	static Logger logger= LoggerFactory.getLogger("KI KHABO");
	public static void main(String[] args) {
		ApplicationContext appContext = new AnnotationConfigApplicationContext();
		Environment env = appContext.getEnvironment();
		String temp = env.getProperty("GEMINI_KEY");
		logger.info("GEMINI KEY: {}",temp);

		SpringApplication.run(kikhaboApplication.class, args);
	}

}
