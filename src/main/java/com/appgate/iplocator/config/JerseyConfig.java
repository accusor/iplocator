package com.appgate.iplocator.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.appgate.iplocator.rest.IpLocatorResource;

@Component
@ApplicationPath("/iplocator")
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
		register(IpLocatorResource.class);
	}
	
}
