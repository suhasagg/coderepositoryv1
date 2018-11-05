package com.loom.util;


import static org.iq80.leveldb.impl.Iq80DBFactory.factory;

import java.io.File;
import java.io.IOException;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;



public class DBConnector
{
    
    
   public  DB getPooledConnection() throws IOException
   {
	   DB levelDBStore;
	   Options options = new Options();
	   levelDBStore = factory.open(new File("levelDBStore"),options);

       return levelDBStore;
   
   }
 
}
