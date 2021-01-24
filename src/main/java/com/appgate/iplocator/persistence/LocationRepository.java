package com.appgate.iplocator.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.appgate.iplocator.dto.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

}
