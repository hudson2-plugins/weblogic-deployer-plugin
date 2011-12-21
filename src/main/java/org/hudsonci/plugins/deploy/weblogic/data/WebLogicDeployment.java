/**
 * 
 */
package org.hudsonci.plugins.deploy.weblogic.data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rchaumie
 *
 */
public class WebLogicDeployment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1253012135022313012L;

	private int buildNumber;
	
	private Date executionDate;
	
	private WeblogicEnvironment target;
	
	
	public WebLogicDeployment(int buildNumber, Date executionDate, WeblogicEnvironment target) {
		super();
		this.buildNumber = buildNumber;
		this.executionDate = executionDate;
		this.target = target;
	}


	/**
	 * 
	 * @return
	 */
	public int getBuildNumber() {
		return buildNumber;
	}

	/**
	 * 
	 * @param buildNumber
	 */
	public void setBuildNumber(int buildNumber) {
		this.buildNumber = buildNumber;
	}

	/**
	 * 
	 * @return
	 */
	public Date getExecutionDate() {
		return executionDate;
	}

	/**
	 * 
	 * @param executionDate
	 */
	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}


	/**
	 * @return the target
	 */
	public WeblogicEnvironment getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(WeblogicEnvironment target) {
		this.target = target;
	}

}
