package com.appgate.iplocator.rest;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
import com.appgate.iplocator.rest.dto.Logged;
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
	
	private static Date lastUpdated = null;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/DD HH:mm:ss.SSS");
	
	private static Boolean running = false;
	
	private static Date processStartedAt = null;
	
	@GET
	@Path("/locations")
	@Produces(MediaType.APPLICATION_JSON)
	@Logged
	public ApiResponse getAllLocations() {
		ApiResponse response = new ApiResponse();
		Meta meta = new Meta();
		response.setMeta(meta);
		List<Error> errors = new ArrayList<Error>();
		try {
			List<Location> locations = locationRepository.findAll();
			if (locations == null || locations.isEmpty()) {
				errors.add(Error.getError(Error.COD_LOC_EMPTY));
			}else {
				response.setData(locations);
			}
		}catch (Exception e) {
			LoggingController.setError("Error in getAllLocations", e);
			errors.add(Error.getError(Error.COD_UNKNOWN_ERROR));
		}
		response.setErrors(errors);
		return response;
	}
	
	@GET
	@Path("/locations/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Logged
	public ApiResponse getLocationById(@PathParam(value = "id") Long idRange) {
		ApiResponse response = new ApiResponse();
		Meta meta = new Meta();
		response.setMeta(meta);
		List<Error> errors = new ArrayList<Error>();
		try {
			Location location = locationRepository.findById(idRange).get();
			if (location == null) {
				errors.add(Error.getError(Error.COD_LOC_NOT_FOUND));
			}else {
				response.setData(location);
			}
		}catch (NoSuchElementException e) {
			errors.add(Error.getError(Error.COD_LOC_NOT_FOUND));
		}catch (Exception e) {
			LoggingController.setError("Error in getAllLocations", e);
			errors.add(Error.getError(Error.COD_UNKNOWN_ERROR));
		}
		response.setErrors(errors);
		return response;
	}
	
	@GET
	@Path("/ranges")
	@Produces(MediaType.APPLICATION_JSON)
	@Logged
	public ApiResponse getAllRanges() {
		ApiResponse response = new ApiResponse();
		Meta meta = new Meta();
		response.setMeta(meta);
		List<Error> errors = new ArrayList<Error>();
		try {
			List<Range> ranges = rangeRepository.findAll();
			if (ranges == null || ranges.isEmpty()) {
				errors.add(Error.getError(Error.COD_RNG_EMPTY));
			}else {
				response.setData(ranges);
			}
		}catch (Exception e) {
			LoggingController.setError("Error in getAllLocations", e);
			errors.add(Error.getError(Error.COD_UNKNOWN_ERROR));
		}
		response.setErrors(errors);
		return response; 
	}
	
	@GET
	@Path("/ranges/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Logged
	public ApiResponse getRangeById(@PathParam(value = "id") Long idLocation) {
		ApiResponse response = new ApiResponse();
		Meta meta = new Meta();
		response.setMeta(meta);
		List<Error> errors = new ArrayList<Error>();
		try {
			Range range = rangeRepository.findById(idLocation).get();
			if (range == null) {
				errors.add(Error.getError(Error.COD_RNG_NOT_FOUND));
			}else {
				response.setData(range);
			}
		}catch (NoSuchElementException e) {
			errors.add(Error.getError(Error.COD_RNG_NOT_FOUND));
		}catch (Exception e) {
			LoggingController.setError("Error in getAllLocations", e);
			errors.add(Error.getError(Error.COD_UNKNOWN_ERROR));
		}
		response.setErrors(errors);
		return response; 
	}
	
	@GET
	@Path("/loadDatabase")
	@Produces(MediaType.APPLICATION_JSON)
	@Logged
	public ApiResponse loadDatabase() {
		ApiResponse response = new ApiResponse();
		Meta meta = new Meta();
		response.setMeta(meta);
		List<Error> errors = new ArrayList<Error>();
		if (running) {
			errors.add(Error.getError(Error.COD_PROC_RUNNING));
			response.setErrors(errors);
			return response;
		}
		try {
			running = true;
			Boolean result = true;
			String message = "Success!";
			cachedMap = new HashMap<Integer, Long>();
			processStartedAt = new Date();
			try {
				rangeRepository.deleteAll();
				locationRepository.deleteAll();
				Reader reader = Files.newBufferedReader(Paths.get(properties.getFilepath()));
				CSVReader csvReader = new CSVReader(reader);
				String[] record;
				while ((record = csvReader.readNext()) != null) {
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
				    
				}
				cachedMap = null;
				message += " Total records: " + csvReader.getLinesRead();
				csvReader.close();
			}catch (Exception e) {
				message = e.toString();
				result = false;
			}
			running = false;
			lastUpdated = new Date();
			processStartedAt = null;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", result);
			map.put("desc", message);
			map.put("date", lastUpdated);
			response.setData(map);
			
		}catch (Exception e) {
			LoggingController.setError("Error in loadDatabase", e);
			errors.add(Error.getError(Error.COD_UNKNOWN_ERROR));
			response.setErrors(errors);
		}

		return response;
	}
	
	
	@GET
	@Path("/getStatus")
	@Produces(MediaType.APPLICATION_JSON)
	@Logged
	public ApiResponse getStatus() {
		ApiResponse response = new ApiResponse();
		Meta meta = new Meta();
		response.setMeta(meta);
		List<Error> errors = new ArrayList<Error>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {			
			Long total = rangeRepository.count();
			map.put("totalRecords", total);
			map.put("running", running);
			map.put("runningSince", getProcessStartedAt());
			map.put("lastUpdate", getLastUpdate());
		}catch (Exception e) {
			LoggingController.setError("Error in getStatus", e);
			errors.add(Error.getError(Error.COD_UNKNOWN_ERROR));
		}
		response.setData(map);
		response.setErrors(errors);
		return response;
	}

	@GET
	@Path("/locationByIp/{ip}")
	@Produces(MediaType.APPLICATION_JSON)
	@Logged
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

	private String getLastUpdate() {
		return (lastUpdated != null) ? sdf.format(lastUpdated) : "never";
	}
	
	private String getProcessStartedAt() {
		return (processStartedAt != null) ? sdf.format(processStartedAt) : "--";
	}
	
}
