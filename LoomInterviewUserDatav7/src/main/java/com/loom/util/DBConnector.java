package com.loom.util;


import static org.iq80.leveldb.impl.Iq80DBFactory.factory;

import java.io.File;
import java.io.IOException;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;



public class DBConnector
{
    
	public static DB levelDBStore =null;
   
	public synchronized DB getPooledConnection() throws IOException
   {
	   
	   //DB levelDBStore = null;
	   Options options = new Options();
	   if(levelDBStore == null){
	   levelDBStore = factory.open(new File("levelDBStore"),options);
       return levelDBStore;
       }
	   else
       return levelDBStore;
	   
   }
	
	
	public  DB getPooledConnection1() throws IOException
	   {
		   
		   //DB levelDBStore = null;
		   Options options = new Options();
		 
		   levelDBStore = factory.open(new File("levelDBStore"),options);
	      
	       return levelDBStore;
		   
	   }
	
	
	
 
}
