package com.appgate.iplocator.rest.dto;

import java.util.List;

public class ApiResponse {

	private Meta meta = null;
	private List<Error> errors = null;
	private Object data = null;

	public Meta getMeta() {
		return meta;
	}

	public Object getData() {
		return data;
	}

	public List<Error> getErrors() {
		return errors;
	}
	
	public void setMeta(Meta meta){
		this.meta = meta;
	}
	
	public void setData(Object data){
		this.data = data;
	}
	
	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

}