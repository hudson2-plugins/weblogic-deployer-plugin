/**
 * 
 */
package org.hudsonci.plugins.deploy.weblogic;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;

import hudson.FilePath;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.BuildBadgeAction;
import hudson.model.AbstractBuild;
import hudson.model.DirectoryBrowserSupport;
import hudson.model.Hudson;
import hudson.model.TaskAction;
import hudson.security.ACL;
import hudson.security.Permission;

import org.hudsonci.plugins.deploy.weblogic.build.WebLogicDeploymentStatus;
import org.hudsonci.plugins.deploy.weblogic.data.WeblogicEnvironment;
import org.hudsonci.plugins.deploy.weblogic.properties.WebLogicDeploymentPluginConstantes;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerProxy;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

import org.hudsonci.plugins.deploy.weblogic.Messages;

/**
 * @author rchaumie
 *
 */
@ExportedBean(defaultVisibility = 999)
public class BuildSeeWeblogicDeploymentLogsAction implements Action, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5479554061667005120L;

	private static transient final String iconFileName = WebLogicDeploymentPluginConstantes.PLUGIN_RESOURCES_PATH + "/icons/48x48/BEA.jpg";
	
	@Exported(name="status")
	public WebLogicDeploymentStatus deploymentActionStatus;
	
	private AbstractBuild<?, ?> build;
	
	@Exported(name="target")
	public WeblogicEnvironment target;
	
	/**
	 * 
	 */
	public BuildSeeWeblogicDeploymentLogsAction(){
		super();
	}
	
	/**
	 * 
	 * @param deploymentActionStatus
	 * @param b
	 */
	public BuildSeeWeblogicDeploymentLogsAction(WebLogicDeploymentStatus deploymentActionStatus, AbstractBuild<?, ?> b, WeblogicEnvironment target){
		this.build = b;
		this.deploymentActionStatus = deploymentActionStatus;
		this.target = target;
	}
	
	/*
	 * (non-Javadoc)
	 * @see hudson.model.Action#getDisplayName()
	 */
	public String getDisplayName() {
		return Messages.BuildWeblogicDeploymentAction_DisplayName();
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
		return "deploymentLogs";
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