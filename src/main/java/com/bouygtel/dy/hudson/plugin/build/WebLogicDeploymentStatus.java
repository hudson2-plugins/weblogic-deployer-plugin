/**
 * 
 */
package com.bouygtel.dy.hudson.plugin.build;


/**
 * @author rchaumie
 *
 */
public enum WebLogicDeploymentStatus {

	UNKNOWN(0),
	DISABLED(1),
	ABORTED(2),
	FAILED(3),
	SUCCEEDED(4);
	
	private int value;
	
	/**
	 * @param value
	 */
	private WebLogicDeploymentStatus(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
}
