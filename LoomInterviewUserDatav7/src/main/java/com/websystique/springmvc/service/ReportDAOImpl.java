package com.websystique.springmvc.service;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.WriteBatch;

import com.loom.util.Batch;
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
			
			//levelDBStore.close();
			
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
			
			//levelDBStore.close();
			
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
			
			//levelDBStore.close();
			
			
		}
		
		
		
		return uuid;
	
	}


	public List<String> BatchUploadUserData(String filepath) throws IOException, InterruptedException{
		 try {
		     System.setOut(new PrintStream(new File("BatchLog.txt")));
		    } catch (Exception e) {
		         e.printStackTrace();
		    }
		 List<Future<?>> futures = new ArrayList<Future<?>>();
		 List<Queuepopulator> tasklist = new ArrayList<Queuepopulator>();
		 int nThreads = Runtime.getRuntime().availableProcessors()-1;
		 ExecutorService queuepopulators = Executors.newFixedThreadPool(nThreads);
		 ExecutorService queueReaders = Executors.newFixedThreadPool(nThreads);
		// DBConnector obj = new DBConnector();
	//	 DB levelDBStore = obj.getPooledConnection();
		 
		 List<String> uuiddataglobal = new CopyOnWriteArrayList<>();
		 Batch b = new Batch(nThreads,uuiddataglobal);
	//	 try{
		
		
		//List<String> uuiddata = new ArrayList<String>();
		UserData userdata = null;
	
        String strLine;
	 	byte [] serialiseddata = null;
		String uuid = null;
		//WriteBatch batch = levelDBStore.createWriteBatch();
		
		
		
		
		
		for (MultiThreadedConcurrentBatchIngestion task: b.initialiseCallableTasks()){
		    queueReaders.submit(task);
			
		}
		FileInputStream fstream = new FileInputStream(filepath);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		
		while ((strLine = br.readLine()) != null)   {
	    queuepopulators.submit(new Queuepopulator(strLine,b.queue));
		}

		
		
		b.stopAll();
	
		queuepopulators.shutdown();
		queueReaders.shutdown();
		
		queueReaders.awaitTermination(59, TimeUnit.SECONDS);
		
		if(queueReaders.isTerminated() && queuepopulators.isTerminated()){
		//    b.closeDB();
			return uuiddataglobal;
		
		}		
		else
		 return null;
	
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
			
			//levelDBStore.close();
			
			
		}
		
		
		
		return uuid;
	
	}

	
	
	
	
}
