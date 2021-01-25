package com.appgate.iplocator.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "iplocator")
@Validated
public class IpLocatorProperties {

	@NotNull private String filepath = "~/tmp/ipgeo.csv";
	
	public String getFilepath() {
		return this.filepath;
	}
	
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
}
