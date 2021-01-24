package com.appgate.iplocator.persistence.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.appgate.iplocator.dto.Location;
import com.appgate.iplocator.persistence.LocationRepository;

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
	CommandLineRunner initDatabase(LocationRepository repository) {
		return args -> {
			log.info("Preloading: " + repository.save(new Location("CO", "Colombia", "Colombia", "?", 4.0, 
					-72.0, "PETERSBURG INTERNET NETWORK LTD.")));
			log.info("Preloading: " + repository.save(new Location("AR", "Argentina", "Argentina", "?", -34.0, 
					-64.0, "PETERSBURG INTERNET NETWORK LTD.")));
		};
	}
	
}
