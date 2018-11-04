package com.websystique.springmvc.service;



import java.util.List;

import com.websystique.springmvc.model.UserData;
import com.websystique.springmvc.model.Reports;



public interface ReportService {
	

   UserData extractUserDataEmail(String emailId);
	
   UserData extractUserDataUUID(String uuid);

   String InsertUserData(UserData data);
   
}
