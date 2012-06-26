package be.betty.gwtp.server;

import java.io.IOException;

/**
 * 
 * This Exception is use when the input file is not in a correct format
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas
 * 
 */
public class NotSuportedFileException extends IOException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotSuportedFileException(){
		super();
	}
	
	public NotSuportedFileException(String msg){
		super(msg);
	}
}
