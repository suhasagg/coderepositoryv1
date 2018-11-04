package com.loom.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A uid generator
 * 
 * 
 * 
 *
 */
public class UUIDGenerator {
	
	/**
	 * Generate digested code with given keys
	 * system.currentTimeMillis was also one of the paramters which to 
	 * make result unique
	 * 
	 * @param keys
	 * @return
	 */
	public static String generate(String... keys)  {
		MessageDigest m = null;
		try {
			//m = MessageDigest.getInstance(GlobalConfiguration.get("tcaptcha.digestMethod"));
			m = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		for(String key: keys){
			m.update(key.getBytes());
		}
		m.update(String.valueOf(System.nanoTime()).getBytes());
		byte[] result = m.digest();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<result.length; i++){
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	public static String generate()  {
		MessageDigest m = null;
		try {
			
			m = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		m.update(String.valueOf(System.nanoTime()).getBytes());
		byte[] result = m.digest();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<result.length; i++){
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	private UUIDGenerator(){
		
	}

}
