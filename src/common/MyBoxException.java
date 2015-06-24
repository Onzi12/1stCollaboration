package common;


/**
 * An exception that provides information on a database access
 * error or other errors.
 */
@SuppressWarnings("serial")
public class MyBoxException extends Exception {
	
	public MyBoxException(String msg) {
		super(msg);
	}
	
}
