package com.loom.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.websystique.springmvc.model.UserData;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Response {

	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private UserData data;
	
	public UserData getData() {
		return data;
	}
	public void setData(UserData data) {
		this.data = data;
	}
	private String code;
	private String status;
	private String message;
	private String uuid;

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
