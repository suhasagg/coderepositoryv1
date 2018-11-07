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
    
 public String UpdateUserData(UserData data, String uuid){
 	
 	String uuid1 = null;
 	
 	
		try {
			uuid1 = repDAO.PutUserDataUUID(data,uuid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     return uuid1;
     	


 

 }   
    
   
 public List<String> BatchUploadUserData(String  filepath){
	 	
	 	List<String> uuidslist = new ArrayList<String>();
	 	
	 	
			try {
				try {
					uuidslist = repDAO.BatchUploadUserData(filepath);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     return uuidslist;
	     	


	 

	 }   
 
 
 
    
}
