package com.websystique.springmvc.service;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.iq80.leveldb.WriteBatch;

import com.loom.util.UUIDGenerator;
import com.websystique.springmvc.model.UserData;

import java.util.concurrent.LinkedBlockingQueue;


	public class Queuepopulator implements Callable<String> {
	
		BlockingQueue queue;
		String line;
		
		public Queuepopulator(String line, BlockingQueue queue){
	        
			this.line = line;
	       
			this.queue = queue; 
	    }

	    public String call(){
	    	//Handle the hit...
	    	try {
				queue.offer(line,10,TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         return line;
	    }
			
	   
    
		
	 
	
	
}
