package com.appgate.iplocator.dto;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.appgate.iplocator.utils.GsonUtils;

@Entity
public class Location {

	private @Id @GeneratedValue Long idRange;
	private String countryCode;
	private String country;
	private String region;
	private String city;
	private Double latitude;
	private Double longitude;
	private String isp;
	
	public Location() {
	}

	public Location(String countryCode, String country, String region, String city, Double latitude, Double longitude,
			String isp) {
		this.countryCode = countryCode;
		this.country = country;
		this.region = region;
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
		this.isp = isp;
	}

	public Long getIdRange() {
		return idRange;
	}

	public void setIdRange(Long idRange) {
		this.idRange = idRange;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Location)) {
			return false;
		}
		Location other = (Location) obj;
		return Objects.equals(this.idRange, other.idRange) &&
			   Objects.equals(this.countryCode, other.countryCode) &&
			   Objects.equals(this.country, other.country) &&
			   Objects.equals(this.region, other.region) &&
			   Objects.equals(this.city, other.city) &&
			   Objects.equals(this.latitude, other.latitude) &&
			   Objects.equals(this.longitude, other.longitude) &&
			   Objects.equals(this.isp, other.isp);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.idRange, this.countryCode, this.country, this.region,
				this.city, this.latitude, this.longitude, this.isp);
	}
	
	@Override
	public String toString() {
		return GsonUtils.toJson(this);
	}
	
}
