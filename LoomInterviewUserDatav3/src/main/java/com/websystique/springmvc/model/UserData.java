package com.websystique.springmvc.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5928799410637387001L;

	private String user_id;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	private String email_id;
		
    private String uuid;
	
    public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	} 
    
	private String miscellaneousData;
	
	public String getMiscellaneousData() {
		return miscellaneousData;
	}
	public void setMiscellaneousData(String miscellaneousData) {
		this.miscellaneousData = miscellaneousData;
	}
	

}
