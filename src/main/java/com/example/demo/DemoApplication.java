package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableEncryptableProperties
@PropertySource(name="EncryptedProperties", value = "classpath:encrypted.properties")
public class DemoApplication {
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	
	@Value("${secret.property}")
	private static String prop;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		logger.info("Info logger Message!");
		logger.warn("Warn logger Message!");
		logger.error("Error logger Message!");
		logger.trace("Trace logger Message!");
		logger.debug("Debug logger Message!");

		logger.info(prop);
	}

}
