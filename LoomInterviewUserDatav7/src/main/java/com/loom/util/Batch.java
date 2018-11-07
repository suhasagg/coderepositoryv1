package com.loom.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import org.iq80.leveldb.DB;

import com.websystique.springmvc.service.MultiThreadedConcurrentBatchIngestion;

public class Batch {

	
	public List<MultiThreadedConcurrentBatchIngestion> tasklist = new ArrayList<MultiThreadedConcurrentBatchIngestion>();
	public List<String> uuiddataglobaldata = new CopyOnWriteArrayList<>();
	public BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    public static final int BATCH_SIZE = 10000;
    public int intThreads;
    
    
    DBConnector obj = new DBConnector();
	DB levelDBStore = null;
	
    
    public Batch(int nbThread, List<String> globaluuiddata) {
        this.intThreads = nbThread;
        this.uuiddataglobaldata = globaluuiddata;
        try {
			this.levelDBStore = obj.getPooledConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public List<MultiThreadedConcurrentBatchIngestion> initialiseCallableTasks(){
        for (int t=0; t<intThreads; t++) {
             tasklist.add(new MultiThreadedConcurrentBatchIngestion(BATCH_SIZE,queue,uuiddataglobaldata,levelDBStore));
        }
        return tasklist;
    
    }
	
    public void stopAll() {
        for (MultiThreadedConcurrentBatchIngestion t : tasklist) {
            t.stop();
        }
    }
    
    public void closeDB() {
          try {
			levelDBStore.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
    
    
    
}
