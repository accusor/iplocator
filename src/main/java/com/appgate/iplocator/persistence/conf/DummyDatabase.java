package com.appgate.iplocator.persistence.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appgate.iplocator.dto.Location;
import com.appgate.iplocator.dto.Range;
import com.appgate.iplocator.persistence.LocationRepository;
import com.appgate.iplocator.persistence.RangeRepository;

/**
 * Clase para probar las entidades de la base de datos
 * 
 * @author accusor
 *
 */
@Configuration
public class DummyDatabase {

	private static final Logger log = LoggerFactory.getLogger(DummyDatabase.class);
	
	@Bean
	CommandLineRunner initDatabase(LocationRepository locationRepo, RangeRepository rangeRepo) {
		Location location = new Location("CO", "Colombia", "Colombia", "?", 4.0, -72.0, "PETERSBURG INTERNET NETWORK LTD.");
		
		Range range = new Range(0L, 0L, location);
		
		Range range2 = new Range(1L, 255L, location);
		
		return args -> {
			log.info("Preloading: " + locationRepo.save(location));
			log.info("Preloading: " + rangeRepo.save(range));
			log.info("Preloading: " + rangeRepo.save(range2));
		};
	}
	
}
