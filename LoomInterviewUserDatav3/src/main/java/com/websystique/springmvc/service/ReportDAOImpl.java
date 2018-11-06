package com.websystique.springmvc.service;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.WriteBatch;

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
			data=levelDBStore.get(LevelDBUtil.serialize(uuid));
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
			
			datafirstfetch=levelDBStore.get(LevelDBUtil.serialize(emailid));
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
			levelDBStore.put(LevelDBUtil.serialize(uuid),serialiseddata);
		   //Secondary Index for LevelDB
			levelDBStore.put(LevelDBUtil.serialize(data.getEmail_id()),LevelDBUtil.serialize(uuid));

		} catch (Exception e) {
			e.printStackTrace();
		}
             
		finally{
			
			levelDBStore.close();
			
			
		}
		
		
		
		return uuid;
	
	}


	public List<String> BatchUploadUserData(String filepath) throws IOException{
		 try {
		     System.setOut(new PrintStream(new File("BatchLog.txt")));
		    } catch (Exception e) {
		         e.printStackTrace();
		    }
		
		DBConnector obj = new DBConnector();
		DB levelDBStore = obj.getPooledConnection();
		List<String> uuiddata = new ArrayList<String>();
		UserData userdata = null;
		List<WorkerThread> tasklist = new ArrayList<WorkerThread>();

	 	byte [] serialiseddata = null;
		String uuid = null;
		WriteBatch batch = levelDBStore.createWriteBatch();
		
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()-1);
		
		FileInputStream fstream = new FileInputStream(filepath);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;
        Integer counter = 0;
		//Read File Line By Line
	    try{
        
        while ((strLine = br.readLine()) != null)   {
		  // Print the content on the console
		
        	
        	
        if(counter < 10000){
		if(strLine !=null){
        tasklist.add(new WorkerThread(strLine,batch));     
		counter++;	
		}
		
		
		}	
		else{
        	
        	counter = 0;
        
        	List<Future<String>> userIds = executor.invokeAll(tasklist);

        	levelDBStore.write(batch);
        	batch.close();
        	
        	
        	
        	tasklist.clear();
        	for (int i = 0; i < userIds.size(); i++)
     	    {
     	    
     	    	if(userIds!=null && userIds.get(i)!=null){
     	        uuid = userIds.get(i).get();
     	        uuiddata.add(uuid);
     	    	
     	    }
        	
     	   
     	    	
     	    } 	
     	 
        	 System.out.println("Processed Batch of Size :"+userIds.size());
        	// levelDBStore = obj.getPooledConnection();
        	 batch = levelDBStore.createWriteBatch();
		}
       
        
        
        }
        
        if(counter < 10000){
        	
           
        	counter = 0;
        	
        	List<Future<String>> userIds = executor.invokeAll(tasklist);
        	
        	levelDBStore.write(batch);
        	batch.close();
            
        	
        	tasklist.clear();
        	for (int i = 0; i < userIds.size(); i++)
     	    {
     	    
     	    	if(userIds!=null && userIds.get(i)!=null){
     	        uuid = userIds.get(i).get();
     	        uuiddata.add(uuid);
     	    	
     	    }
        	
     	   
        	
        	
       	
        }
        
        	 System.out.println("Processed Batch of Size :"+userIds.size());
        	// levelDBStore = obj.getPooledConnection();
        	 batch = levelDBStore.createWriteBatch();
        } 
        
	    } 
        catch(Exception e){
        	
        	e.printStackTrace();
        }
             
		finally{
			batch.close();
			levelDBStore.close();
			executor.shutdown();
			
		}
		
		
		
		return uuiddata;
	
	}
	
	
	
	
	
	public String PutUserDataUUID(UserData data,String uuid) throws IOException{
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
		String uuid2 = null;
		
		try {
			DBConnector obj = new DBConnector();
			levelDBStore = obj.getPooledConnection();
			serialiseddata = LevelDBUtil.serialize(data);
			levelDBStore.put(uuid.getBytes(),serialiseddata);
		   

		} catch (Exception e) {
			e.printStackTrace();
		}
             
		finally{
			
			levelDBStore.close();
			
			
		}
		
		
		
		return uuid;
	
	}

	
	
	
	
}
