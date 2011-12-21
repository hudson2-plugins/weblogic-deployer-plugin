/**
 * 
 */
package org.hudsonci.plugins.deploy.weblogic.exception;

/**
 * @author rchaumie
 *
 */
public class TransfertFileException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6077869008290630833L;
	
	/**
	 * 
	 */
	public TransfertFileException(){
		super();
	}
	
	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public TransfertFileException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * 
	 * @param message
	 */
	public TransfertFileException(String message) {
		super(message);
	}
	
	/**
	 * 
	 * @param cause
	 */
	public TransfertFileException(Throwable cause) {
		super(cause);
	}
	
	

}
