package com.appgate.iplocator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.appgate.iplocator.rest.IpLocatorResource;

@ApplicationPath("/resources")
public class RestConfig extends Application {
	public Set<Class<?>> getClasses(){
		return new HashSet<Class<?>>(
				Arrays.asList(IpLocatorResource.class));
	}
}
