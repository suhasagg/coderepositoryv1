package com.websystique.springmvc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.WriteBatch;

import com.loom.util.DBConnector;
import com.loom.util.UUIDGenerator;
import com.websystique.springmvc.model.UserData;

public class MultiThreadedConcurrentBatchIngestion implements Runnable {


	private boolean started = true;
    private BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private int batchLimit;
    DB levelDBStore = null;
    public List<String> uuiddataglobaldata = new CopyOnWriteArrayList<>();
    public MultiThreadedConcurrentBatchIngestion(int batchLimit, BlockingQueue<String> queue, List<String> uuiddatav1,DB levelDBStore) {
        this.batchLimit = batchLimit;
        this.queue = queue;
        this.uuiddataglobaldata = uuiddatav1;
        this.levelDBStore = levelDBStore;
    }

    @Override
    public void run(){
        
    	String emailid = null;
    	String uuid = null;
            
    		WriteBatch batch = levelDBStore.createWriteBatch();	
    		List<String> returnabledata = new ArrayList<String>();
    		while (!queue.isEmpty() || started) {
                    String line = null;
					try {
						line = queue.poll(10,TimeUnit.SECONDS);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    if(line!=null){
              	
            	    	String parts[] = line.split(";");
            	    	emailid = parts[0];
            	    	String username = parts[1];
            	    	String operation = parts[2];
            	    	String uuidfordeletion = parts[3];
            	    	UserData data = new UserData();
            	    	data.setUser_id(username);
            	        data.setEmail_id(emailid);
            	    	
            	        byte[] serialiseddata = null;
						try {
							serialiseddata = LevelDBUtil.serialize(data);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            		    uuid = UUIDGenerator.generate(data.getEmail_id());
            		    if(operation.equals("1")){
            		    
            		    if(uuid!=null && emailid!=null){
            		    try {
							batch.put(LevelDBUtil.serialize(uuid),serialiseddata);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            		    try {
							batch.put(LevelDBUtil.serialize(emailid),LevelDBUtil.serialize(uuid));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            		    }
            		    
            		    }
            		    if(operation.equals("2")){
            		   	if(uuidfordeletion!=null)
							try {
								batch.delete(LevelDBUtil.serialize(uuidfordeletion));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
            		    }
            	        
            		    returnabledata.add(uuid+";"+emailid);
            		    
            		    
            	       }
            		    
                    	if(returnabledata.size()== batchLimit){
                    	levelDBStore.write(batch);
                    	try {
							batch.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}                   	
                    	System.out.println("Processed Batch of Size :"+returnabledata.size());
                     	// levelDBStore = obj.getPooledConnection();
                     	batch = levelDBStore.createWriteBatch();
                     	
                     	uuiddataglobaldata.addAll(returnabledata);
                     	returnabledata.clear();
                    	}
                    	
                    	
                }
            
    		    if(returnabledata.size()>0 && returnabledata.size()< batchLimit){
            	levelDBStore.write(batch);
            	try {
					batch.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 	
            	System.out.println("Processed Batch of Size :"+returnabledata.size());
            	uuiddataglobaldata.addAll(returnabledata);
             	returnabledata.clear();
    		    
    		    }
    		
    		
    	
    		    
    		    // return returnabledata;		
    
    }
    

    public void stop() {
        started = false;
    }

}
