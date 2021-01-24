package com.appgate.iplocator.rest;

import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.appgate.iplocator.dto.Location;
import com.appgate.iplocator.persistence.LocationRepository;

@Path("/iplocator")
public class IpLocatorResource {

	@Autowired
	private LocationRepository locationRepository;
	
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Location getLocation(@PathParam("id") Long id) {
		return locationRepository.getOne(id);
	}

	
}
