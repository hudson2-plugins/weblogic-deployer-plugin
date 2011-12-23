/**
 * 
 */
package org.hudsonci.plugins.deploy.weblogic;

import hudson.model.Action;
import hudson.model.AbstractBuild;

import java.io.Serializable;

import org.hudsonci.plugins.deploy.weblogic.data.WebLogicDeploymentStatus;
import org.hudsonci.plugins.deploy.weblogic.data.WeblogicEnvironment;
import org.hudsonci.plugins.deploy.weblogic.properties.WebLogicDeploymentPluginConstantes;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

/**
 * @author rchaumie
 *
 */
@ExportedBean(defaultVisibility = 999)
public class WatchingWeblogicDeploymentLogsAction implements Action, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5479554061667005120L;

	private static transient final String iconFileName = WebLogicDeploymentPluginConstantes.PLUGIN_RESOURCES_PATH + "/icons/48x48/BEA.jpg";
	
	private static transient final String urlName = "deploymentLogs";
	
	@Exported(name="status")
	public WebLogicDeploymentStatus deploymentActionStatus;
	
	private AbstractBuild<?, ?> build;
	
	@Exported(name="target")
	public WeblogicEnvironment target;
	
	private transient boolean isLogsAvailable = false;
	
	/**
	 * 
	 */
	public WatchingWeblogicDeploymentLogsAction(){
		super();
	}
	
	/**
	 * 
	 * @param deploymentActionStatus
	 * @param b
	 */
	public WatchingWeblogicDeploymentLogsAction(WebLogicDeploymentStatus deploymentActionStatus, AbstractBuild<?, ?> b, WeblogicEnvironment target){
		this.build = b;
		this.deploymentActionStatus = deploymentActionStatus;
		this.target = target;
		//lien vers les logs uniquement si pas d'execution
		if(! WebLogicDeploymentStatus.ABORTED.equals(deploymentActionStatus) && ! WebLogicDeploymentStatus.DISABLED.equals(deploymentActionStatus)){
			this.isLogsAvailable = true;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see hudson.model.Action#getDisplayName()
	 */
	public String getDisplayName() {
		return this.isLogsAvailable ? Messages.WatchingWeblogicDeploymentLogsAction_DisplayName() : Messages.WatchingWeblogicDeploymentLogsAction_MissingLogs();
	}

	/*
	 * (non-Javadoc)
	 * @see hudson.model.Action#getIconFileName()
	 */
	public String getIconFileName() {
		return iconFileName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see hudson.model.Action#getUrlName()
	 */
	public String getUrlName() {
		return this.isLogsAvailable ? urlName : "#";
	}
	
	/**
	 * 
	 * @return
	 */
	public WebLogicDeploymentStatus getDeploymentActionStatus() {
		return deploymentActionStatus;
	}
	
	/**
	 * 
	 * @return
	 */
	public AbstractBuild<?, ?> getBuild() {
        return build;
    }

	/**
	 * @return the target
	 */
	public WeblogicEnvironment getTarget() {
		return target;
	}
	
}
