package com.iishanto.kikhabo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class kikhaboApplication {

	public static void main(String[] args) {
		ApplicationContext appContext = new AnnotationConfigApplicationContext();
		Environment env = appContext.getEnvironment();
// env.getProperty() works!!!
		String temp = env.getProperty("GEMINI_KEY");
		System.out.println("GEMINI KEY: "+temp) ;

		SpringApplication.run(kikhaboApplication.class, args);
	}

}
