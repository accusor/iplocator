package com.appgate.iplocator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.appgate.iplocator.config.IpLocatorProperties;
import com.appgate.iplocator.config.LoggingController;

@SpringBootApplication
@EnableConfigurationProperties(IpLocatorProperties.class)
public class IpLocatorApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(IpLocatorApplication.class, args);
		LoggingController.setInfo("App Started!");
	}

}
