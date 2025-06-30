package com.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRestApi03Application {

	private final static Logger logger = LoggerFactory.getLogger(SpringBootRestApi03Application.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApi03Application.class, args);
		logger.info("Application starts successfully..");
	}

}
