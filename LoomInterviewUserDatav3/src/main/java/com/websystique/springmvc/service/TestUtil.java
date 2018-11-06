package com.websystique.springmvc.service;

import java.io.IOException;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.WriteBatch;

import com.loom.util.DBConnector;

public class TestUtil {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		DBConnector obj = new DBConnector();
		DB levelDBStore1 = obj.getPooledConnection();
	//	WriteBatch batch = levelDBStore1.createWriteBatch();
	//	byte[] data =  LevelDBUtil.serialize("cc");
	//	byte[] data1 =  LevelDBUtil.serialize("dd");
	//	batch.put(data,data);
	//    batch.put(data1,data1);
	  //  levelDBStore1.write(batch);
		
		//levelDBStore1.put(data,data);
		// byte[] data = levelDBStore.get("c".getBytes());
		 byte[] datav2 = levelDBStore1.get("dc53e3ffc18d935c1d4eb6463da7d53f8fde8c90".getBytes());
		 Object datav3 = LevelDBUtil.deserialize(datav2);
		 System.out.println(datav2);
	//	 String datav1 = (String)LevelDBUtil.deserialize(data);
	//	 System.out.println(datav1);
		 
		// batch.close();
		 levelDBStore1.close();
	}

}
