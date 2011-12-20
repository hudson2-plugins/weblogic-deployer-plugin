/**
 * 
 */
package com.bouygtel.dy.hudson.plugin.deployer;

/**
 * @author rchaumie
 *
 */
public enum WebLogicCommand {

	DEPLOY("deploy"),
	UNDEPLOY("undeploy");
	
	private String value;
	
	private WebLogicCommand(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
