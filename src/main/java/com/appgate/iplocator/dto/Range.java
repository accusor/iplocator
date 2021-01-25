package com.appgate.iplocator.dto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Range {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long idRange;
	private Long ipFrom;
	private Long ipTo;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Location location;

	public Range() {
	}
	
	public Range(Long ipFrom, Long ipTo, Location location) {
		this.ipFrom = ipFrom;
		this.ipTo = ipTo;
		this.location = location;
	}
	

	public Long getIdRange() {
		return idRange;
	}

	public void setIdRange(Long idRange) {
		this.idRange = idRange;
	}

	public Long getIpFrom() {
		return ipFrom;
	}

	public void setIpFrom(Long ipFrom) {
		this.ipFrom = ipFrom;
	}

	public Long getIpTo() {
		return ipTo;
	}

	public void setIpTo(Long ipTo) {
		this.ipTo = ipTo;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
}
