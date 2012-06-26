package be.betty.gwtp.server;

import java.io.IOException;

/**
 * 
 * This Exception is use when a empty Path name have been send
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas
 * 
 */
public class NoFileException extends IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoFileException() {
		super();
	}

	public NoFileException(String msg) {
		super(msg);
	}

}
