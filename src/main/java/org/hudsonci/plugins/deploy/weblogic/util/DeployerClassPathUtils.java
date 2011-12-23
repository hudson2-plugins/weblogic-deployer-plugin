/**
 * 
 */
package org.hudsonci.plugins.deploy.weblogic.util;

import org.codehaus.plexus.util.FileUtils;
import org.hudsonci.plugins.deploy.weblogic.properties.WebLogicDeploymentPluginConstantes;

/**
 * @author rchaumie
 *
 */
public class DeployerClassPathUtils {

	/**
	 * 
	 * @return
	 */
	public static boolean checkDefaultPathToWebLogicJar() {
		return FileUtils.fileExists(getDefaultPathToWebLogicJar());
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getDefaultPathToWebLogicJar() {
		String envWlHome = System.getenv(WebLogicDeploymentPluginConstantes.WL_HOME_ENV_VAR_NAME);
		return FileUtils.normalize(envWlHome+WebLogicDeploymentPluginConstantes.WL_HOME_LIB_DIR+WebLogicDeploymentPluginConstantes.WL_WEBLOGIC_LIBRARY_NAME);
	}
}
