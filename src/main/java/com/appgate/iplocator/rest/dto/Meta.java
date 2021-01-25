package com.appgate.iplocator.rest.dto;

import java.util.Date;
import java.util.UUID;

public class Meta {

	private String rsUid;
	private Date date;

	public Meta(){
		UUID uuid = UUID.randomUUID();
		this.rsUid = uuid.toString();
		this.date = new Date();
	}

	public Date getDate() {
		return date;
	}

	public String getRsUid() {
		return rsUid;
	}

}
