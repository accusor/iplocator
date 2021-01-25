package com.appgate.iplocator.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appgate.iplocator.dto.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

	public Location findByCountryCodeAndCountryAndRegionAndCityAndLatitudeAndLongitudeAndIsp(String countryCode, 
			String country, String region, String city, Double latitude, Double longitude, String isp);
	
	public Location findByIdLocation(Long idLocation);
	
}
