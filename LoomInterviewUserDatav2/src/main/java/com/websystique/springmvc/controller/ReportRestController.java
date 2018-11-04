package com.websystique.springmvc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loom.util.GlobalConfiguration;
import com.loom.util.Response;
import com.websystique.springmvc.model.UserData;
import com.websystique.springmvc.model.Reports;
import com.websystique.springmvc.service.ReportService;

//Application code - b7

@RestController
public class ReportRestController {

	
	//Controller receives data from Service API and generates a JSON feed 
	
	
	
	@Autowired
	ReportService reportService;  //Service which will do all data retrieval/manipulation work

	//-------------------Retrieve Report with Id--------------------------------------------------------
	
	
	@RequestMapping(value = "/getUserData/Email", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<Response> getUserDataEmail(@RequestParam("emailId") String emailId) {
	   
		Response response = new Response();
		response.setCode("200");
		response.setStatus("Success");
		response.setMessage("API Successful");

		Response response1 = new Response();
		response1.setCode("404");
		response1.setStatus("Error");
		response1.setMessage("User Not Found");
		
		
		UserData data = reportService.extractUserDataEmail(emailId);
		response.setData(data);
		if (data == null) {
			
			return new ResponseEntity<Response>(response1,HttpStatus.OK);
		}
			
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		
	
		
	}
	

	@RequestMapping(value = "/getUserData/UUID", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<Response> getUserDataUUID(@RequestParam("uuid") String uuid) {
	
		Response response = new Response();
		response.setCode("200");
		response.setStatus("Success");
		response.setMessage("API Successful");

		Response response1 = new Response();
		response1.setCode("404");
		response1.setStatus("Error");
		response1.setMessage("User Not Found");
		
		UserData data = reportService.extractUserDataUUID(uuid);
		response.setData(data);
		if (data == null) {
			
			return new ResponseEntity<Response>(response1,HttpStatus.OK);
		}
			
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		
	}
	


	@RequestMapping(value = "/UpdateUser", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<Response> putUserData(@RequestParam("emailId") String emailId,@RequestParam("username") String username, @RequestParam("uuid") String uuid) {
	
		Response response = new Response();
		response.setCode("200");
		response.setStatus("Success");
		response.setMessage("API Successful");

		Response response1 = new Response();
		response1.setCode("404");
		response1.setStatus("Error");
		response1.setMessage("Insertion Error Occured");
		
		UserData data =new UserData();
		data.setEmail_id(emailId);
		data.setUser_id(username);
		String key = reportService.UpdateUserData(data,uuid);
		response.setUuid(key);
		
		if (key == null) {
			
			return new ResponseEntity<Response>(response1,HttpStatus.OK);
		}
			
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		
	}
	
	//POST request for User Data Ingestion
	
	
	@RequestMapping(value = "/User", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<Response> putUserDatav1(@RequestParam("emailId") String emailId,@RequestParam("username") String username) {
	
		Response response = new Response();
		response.setCode("200");
		response.setStatus("Success");
		response.setMessage("API Successful");

		Response response1 = new Response();
		response1.setCode("404");
		response1.setStatus("Error");
		response1.setMessage("Insertion Error Occured");
		
		UserData data =new UserData();
		data.setEmail_id(emailId);
		data.setUser_id(username);
		String key = reportService.InsertUserData(data);
		response.setUuid(key);
		
		if (key == null) {
			
			return new ResponseEntity<Response>(response1,HttpStatus.OK);
		}
			
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		
	}


	@RequestMapping(value = "/BatchUploadUsers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<Response> putUserDatav1() {
	
		Response response = new Response();
		response.setCode("200");
		response.setStatus("Success");
		response.setMessage("API Successful");

		Response response1 = new Response();
		response1.setCode("404");
		response1.setStatus("Error");
		response1.setMessage("Insertion Error Occured");
		String filepath = GlobalConfiguration.get("BulkUploadFilePath");
		UserData data =new UserData();
		List<String> userdatalist = reportService.BatchUploadUserData(filepath);
		String [] parts = null;
		List<UserData> data1 = new ArrayList<UserData>();
		for(String userdata: userdatalist){
			
			parts=userdata.split(";");
			UserData userdata1 = new UserData();
			userdata1.setEmail_id(parts[1]);
			userdata1.setUuid(parts[0]);
		    data1.add(userdata1);
		
		}
		
		response.setBulkuserdata(data1);
		
		if (data1 == null) {
			
			return new ResponseEntity<Response>(response1,HttpStatus.OK);
		}
			
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		
	}
	
	
	
	
	

}
