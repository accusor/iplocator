package com.appgate.iplocator.rest;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appgate.iplocator.config.IpLocatorProperties;
import com.appgate.iplocator.config.LoggingController;
import com.appgate.iplocator.dto.Location;
import com.appgate.iplocator.dto.Range;
import com.appgate.iplocator.persistence.LocationRepository;
import com.appgate.iplocator.persistence.RangeRepository;
import com.appgate.iplocator.rest.dto.ApiResponse;
import com.appgate.iplocator.rest.dto.Error;
import com.appgate.iplocator.rest.dto.Meta;
import com.appgate.iplocator.utils.LocatorUtils;
import com.opencsv.CSVReader;

@Component
@Path("/api/v1")
public class IpLocatorResource {

	@Autowired
	private RangeRepository rangeRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private IpLocatorProperties properties;
	
	private static Map<Integer, Long> cachedMap;
	
	@GET
	@Path("/locations")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Location> getAllLocations() {
		return locationRepository.findAll();
	}
	
	@GET
	@Path("/locations/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Location getLocationById(@PathParam(value = "id") Long idRange) {
		return locationRepository.findById(idRange).get();
	}
	
	@GET
	@Path("/ranges")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Range> getAllRanges() {
		return rangeRepository.findAll();
	}
	
	@GET
	@Path("/ranges/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Range getRangeById(@PathParam(value = "id") Long idLocation) {
		return rangeRepository.findById(idLocation).get();
	}
	
	@GET
	@Path("/loadDatabase")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> loadLocations() {
		Boolean result = true;
		String message = "Success!";
		cachedMap = new HashMap<Integer, Long>();
		try {
			rangeRepository.deleteAll();
			locationRepository.deleteAll();
			Reader reader = Files.newBufferedReader(Paths.get(properties.getFilepath()));
			CSVReader csvReader = new CSVReader(reader);
			String[] record;
			Integer cont = 0;
			while ((record = csvReader.readNext()) != null && cont <= 1000) {
				Double ipFrom = Double.parseDouble(record[0]);
				Double ipTo = Double.parseDouble(record[1]);
			    Double latitude = Double.parseDouble(record[6]);
			    Double longitude = Double.parseDouble(record[7]);
			    
			    Location location = new Location(record[2], record[3], record[4], record[5], latitude, longitude, record[8]);
			    
			    if(cachedMap.containsKey(location.hashCode())) {
			    	location = locationRepository.findByIdLocation(cachedMap.get(location.hashCode()));
			    }else {
			    	Integer hash = location.hashCode();
			    	location = locationRepository.save(location);
			    	cachedMap.put(hash, location.getIdLocation());
			    }
			    
			    Range range = new Range(ipFrom.longValue(), ipTo.longValue(), location);
			    
			    rangeRepository.save(range);
			    
			    cont++;
			}
			cachedMap = null;
			message += " Total records: " + cont;
			csvReader.close();
		}catch (Exception e) {
			message = e.toString();
			result = false;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", result);
		map.put("desc", message);
		return map;
	}
	

	@GET
	@Path("/locationByIp/{ip}")
	@Produces(MediaType.APPLICATION_JSON)
	public ApiResponse getLocationByIp(@PathParam(value = "ip") String ipv4) {
		ApiResponse response = new ApiResponse();
		Meta meta = new Meta();
		response.setMeta(meta);
		List<Error> errors = new ArrayList<Error>();
		try {
			Long ip = null;
			try {
				ip = LocatorUtils.ipv4ToDec(ipv4);
			}catch(IllegalArgumentException e) {
				errors.add(Error.getError(Error.COD_INVALID_IP_FORMAT));
			}
			if (errors.isEmpty()) {			
				Range range = rangeRepository.findByIp(ip);
				if (range != null) {
					Location location = range.getLocation();
					response.setData(location);
				}else {
					errors.add(Error.getError(Error.COD_IP_NOT_FOUND));
				}
			}
		}catch (Exception e) {
			LoggingController.setError("Error in getLocationByIp", e);
			errors.add(Error.getError(Error.COD_UNKNOWN_ERROR));
		}
		response.setErrors(errors);
		return response;
	}
	
}
