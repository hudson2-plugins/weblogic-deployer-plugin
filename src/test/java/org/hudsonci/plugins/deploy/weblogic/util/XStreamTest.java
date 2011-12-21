/**
 * 
 */
package org.hudsonci.plugins.deploy.weblogic.util;

import hudson.model.Hudson;

import java.io.File;
import java.io.FileInputStream;

import junit.framework.Assert;

import org.hudsonci.plugins.deploy.weblogic.configuration.WeblogicDeploymentConfiguration;
import org.hudsonci.plugins.deploy.weblogic.data.WeblogicEnvironment;
import org.junit.Test;


/**
 * @author rchaumie
 *
 */
public class XStreamTest {

	  /**
	   * 
	   */
	  @Test public void testObjectConvert() {
		  	WeblogicDeploymentConfiguration a = new WeblogicDeploymentConfiguration(new WeblogicEnvironment("sdg","sdg","sdg","sdg","sg"), new WeblogicEnvironment("sdg2","sdg2","sdg2","sdg2","sg2"));
			String xml = Hudson.XSTREAM.toXML(a);
	    	System.out.println(xml + "\n"); // for debugging
	    	WeblogicDeploymentConfiguration actual = (WeblogicDeploymentConfiguration) Hudson.XSTREAM.fromXML(xml);
	    	Assert.assertEquals(2, actual.getWeblogicEnvironments().length);
	  }
	  
	  /**
	   * 
	   * @throws Exception
	   */
	  @Test public void testFileConfigurationMarsahlling() throws Exception {
		  	WeblogicDeploymentConfiguration actual = (WeblogicDeploymentConfiguration) Hudson.XSTREAM.fromXML(new FileInputStream(new File(System.getProperty("user.dir")+"/src/test/resources/default.xml")));
		  	Assert.assertEquals(6, actual.getWeblogicEnvironments().length);
	  }
}
