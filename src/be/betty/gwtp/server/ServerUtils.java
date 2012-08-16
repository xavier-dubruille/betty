package be.betty.gwtp.server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServerUtils {

	public static String getSha255Hex(String pwd) {
		String hash = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] dataBytes = pwd.getBytes("utf8");
 
			md.update(dataBytes, 0, dataBytes.length);
     
			byte[] mdbytes = md.digest();
 
			//convert the byte to hex format
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < mdbytes.length; i++) {
			  sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			}
 
			hash = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
		}
		
		return hash ;
	}

}
