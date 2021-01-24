package com.appgate.iplocator.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Range {

	private @Id @GeneratedValue Long idRange;
	private Long ipFrom;
	private Long ipTo;
	
}
