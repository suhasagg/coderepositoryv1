package com.websystique.springmvc.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.model.UserData;
import com.websystique.springmvc.model.Reports;

@Service("reportService")
@Transactional
public class ReportServiceImpl implements ReportService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	ReportDAOImpl repDAO = ReportDAOImpl.getInstance();
	
	
    public UserData extractUserDataUUID(String uuid){
	
    	UserData data = null;
		try {
			data = repDAO.extractUserDataUUID(uuid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return data;
        	
   
    }  
    
    
    public UserData extractUserDataEmail(String emailid){
    	
    	UserData data = null;
		try {
			data = repDAO.extractUserDataEmail(emailid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return data;
        	
   
  
    
   
    }

 public String InsertUserData(UserData data){
    	
    	String uuid = null;
		try {
			uuid = repDAO.PutUserData(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return uuid;
        	
   
  
    
   
    }
    
    
    
    
    
}
