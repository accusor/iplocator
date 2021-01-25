package com.appgate.iplocator.rest.dto;

import java.util.HashMap;
import java.util.Map;

public class Error {

	private Integer code;
	private String title;
	private String detail;
	private String source;

	public static final String SOURCE_SERVICE = "SERVICE";
	public static final String SOURCE_CLIENT = "CLIENT";
	public static final String SOURCE_UNKNOWN = "UNKNOWN";
	
	public static final Integer COD_UNKNOWN_ERROR = 9999;
	public static final Integer COD_IP_NOT_FOUND = 1000;
	public static final Integer COD_INVALID_IP_FORMAT = 1001;
	public static final Integer COD_PROC_RUNNING = 2000;
	public static final Integer COD_LOC_NOT_FOUND = 4000;
	public static final Integer COD_LOC_EMPTY = 4001;
	public static final Integer COD_RNG_NOT_FOUND = 5000;
	public static final Integer COD_RNG_EMPTY = 5001;
	
	private static Map<Integer, Error> ERROR_LIST = new HashMap<Integer, Error>();
	static {
		Error error = new Error();
		error.setCode(Error.COD_UNKNOWN_ERROR);
		error.setTitle("Unknown error");
		error.setDetail("Unknown error");
		error.setSource(Error.SOURCE_UNKNOWN);
		ERROR_LIST.put(Error.COD_UNKNOWN_ERROR, error);
		
		error = new Error();
		error.setCode(Error.COD_IP_NOT_FOUND);
		error.setTitle("IP Address not found");
		error.setDetail("IP Address could not be found on Database");
		error.setSource(Error.SOURCE_CLIENT);
		ERROR_LIST.put(Error.COD_IP_NOT_FOUND, error);
		
		error = new Error();
		error.setCode(Error.COD_INVALID_IP_FORMAT);
		error.setTitle("Invalid IP Adress");
		error.setDetail("Invalid IP Address. Make sure you're sending a valid IPv4 IP Address");
		error.setSource(Error.SOURCE_CLIENT);
		ERROR_LIST.put(Error.COD_INVALID_IP_FORMAT, error);
		
		error = new Error();
		error.setCode(Error.COD_PROC_RUNNING);
		error.setTitle("Process already runnning");
		error.setDetail("There's an instance of the process already running. Please wait for it to finish");
		error.setSource(Error.SOURCE_CLIENT);
		ERROR_LIST.put(Error.COD_PROC_RUNNING, error);
		
		error = new Error();
		error.setCode(Error.COD_LOC_NOT_FOUND);
		error.setTitle("Location not Found");
		error.setDetail("Location could not be found on Database with givend id");
		error.setSource(Error.SOURCE_CLIENT);
		ERROR_LIST.put(Error.COD_LOC_NOT_FOUND, error);
		
		error = new Error();
		error.setCode(Error.COD_LOC_EMPTY);
		error.setTitle("Location: empty Database");
		error.setDetail("There are no Location elements on Database");
		error.setSource(Error.SOURCE_CLIENT);
		ERROR_LIST.put(Error.COD_LOC_EMPTY, error);
		
		error = new Error();
		error.setCode(Error.COD_RNG_NOT_FOUND);
		error.setTitle("Process already runnning");
		error.setDetail("Range could not be found on Database with givend id");
		error.setSource(Error.SOURCE_CLIENT);
		ERROR_LIST.put(Error.COD_RNG_NOT_FOUND, error);
		
		error = new Error();
		error.setCode(Error.COD_RNG_EMPTY);
		error.setTitle("Range: empty Database");
		error.setDetail("There are no Range elements on Database");
		error.setSource(Error.SOURCE_CLIENT);
		ERROR_LIST.put(Error.COD_RNG_EMPTY, error);
		
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public static Error getError(Integer error_code){
		return ERROR_LIST.get(error_code);
	}
	
}
