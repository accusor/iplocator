package com.appgate.iplocator.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CallLog {

	private @Id @GeneratedValue Long idCallLog;
	private String method;
	private String remoteAddress;
	private Date date;
	
	public CallLog() {}
	
	public CallLog(String method, String remoteAddress, Date date) {
		this.method = method;
		this.remoteAddress = remoteAddress;
		this.date = date;
	}

	public Long getIdCallLog() {
		return idCallLog;
	}

	public void setIdCallLog(Long idCallLog) {
		this.idCallLog = idCallLog;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
