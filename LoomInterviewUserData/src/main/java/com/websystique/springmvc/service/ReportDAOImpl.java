package com.websystique.springmvc.service;



import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;
import org.iq80.leveldb.DB;

import com.loom.util.DBConnector;
import com.loom.util.UUIDGenerator;
import com.websystique.springmvc.model.Reports;
import com.websystique.springmvc.model.UserData;





public class ReportDAOImpl {

	 
	  
	   
	  static {
	      
	      
	      ReportDAOImpl repDAO = ReportDAOImpl.getInstance();
	    
	    
	      //    System.out.println(citycodeMap);
	  }
	  
	  
	
	  
	private static ReportDAOImpl INSTANCE;

	private static final Logger logger = Logger.getLogger(ReportDAOImpl.class);

	public static ReportDAOImpl getInstance() {
		
		if(INSTANCE == null)
			return new ReportDAOImpl();
		else
		return INSTANCE;
	}

	public UserData extractUserDataUUID(String uuid) throws IOException{
		DB levelDBStore = null;
		Statement preparedStatement = null;
		UserData userdata = null;
		ResultSet rs = null;
		String QueryString = null;
		List<Reports> obj1 = null;
		List<Reports> obj2 = null;
	//	JSONObject jo = new JSONObject();
		List<Reports> obj3 = null;
		int ReportCode = 1;
		byte [] data = null;
		
		try {
			DBConnector obj = new DBConnector();
			levelDBStore = obj.getPooledConnection();
			data=levelDBStore.get(uuid.getBytes());
			userdata = (UserData)LevelDBUtil.deserialize(data);

		} catch (Exception e) {
			e.printStackTrace();
		}
             
		finally{
			
			levelDBStore.close();
			
		}
		
		
		
		return userdata;
	
	
	
	}

	public UserData extractUserDataEmail(String emailid) throws IOException{
		DB levelDBStore = null;
		Statement preparedStatement = null;
		UserData userdata = null;
		ResultSet rs = null;
		String QueryString = null;
		List<Reports> obj1 = null;
		List<Reports> obj2 = null;
	//	JSONObject jo = new JSONObject();
		List<Reports> obj3 = null;
		int ReportCode = 1;
		byte [] datafirstfetch = null;
		byte [] datasecondfetch = null;
		try {
			DBConnector obj = new DBConnector();
			levelDBStore = obj.getPooledConnection();
			
			datafirstfetch=levelDBStore.get(emailid.getBytes());
			datasecondfetch=levelDBStore.get(datafirstfetch);
			userdata = (UserData)LevelDBUtil.deserialize(datasecondfetch);

		} catch (Exception e) {
			e.printStackTrace();
		}
             
		finally{
			
			levelDBStore.close();
			
		}
		
		
		
		return userdata;
	
	
	}

	public String PutUserData(UserData data) throws IOException{
		DB levelDBStore = null;
		Statement preparedStatement = null;
		UserData userdata = null;
		ResultSet rs = null;
		String QueryString = null;
		List<Reports> obj1 = null;
		List<Reports> obj2 = null;
	//	JSONObject jo = new JSONObject();
		List<Reports> obj3 = null;
		int ReportCode = 1;
		byte [] serialiseddata = null;
		String uuid = null;
		
		try {
			DBConnector obj = new DBConnector();
			levelDBStore = obj.getPooledConnection();
			serialiseddata = LevelDBUtil.serialize(data);
			uuid = UUIDGenerator.generate(data.getEmail_id());
		    //Primary Index for LevelDB
			levelDBStore.put(uuid.getBytes(),serialiseddata);
		   //Secondary Index for LevelDB
			levelDBStore.put(data.getEmail_id().getBytes(),uuid.getBytes());

		} catch (Exception e) {
			e.printStackTrace();
		}
             
		finally{
			
			levelDBStore.close();
			
			
		}
		
		
		
		return uuid;
	
	}


}
