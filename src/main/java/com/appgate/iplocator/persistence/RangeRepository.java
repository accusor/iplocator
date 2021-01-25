package com.appgate.iplocator.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.appgate.iplocator.dto.Range;

public interface RangeRepository extends JpaRepository<Range, Long>{

	@Query("SELECT r FROM Range r WHERE :ip BETWEEN r.ipFrom and r.ipTo")
	public Range findByIp(@Param("ip") Long ip);
	
}
