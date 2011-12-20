/**
 * 
 */
package com.bouygtel.dy.hudson.plugin.util;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author rchaumie
 *
 */
public class JdkUtilsTestCase {

	
	@Test
	public void extractAndCheckJavaVersion(){
		
		String out0 = "$ P:\\Outils\\jdk1.6.0_25\\bin\\java.exe -version\r\njava version \"1.6.0_25\"\r\nJava(TM) SE Runtime Environment (build 1.6.0_25-b06)\r\nJava HotSpot(TM) Client VM (build 20.0-b11, mixed mode, sharing)\r\n";
		boolean result0 = JdkUtils.extractAndCheckJavaVersion(out0, JdkUtils.JAVA_SPECIFICATION_VERSION_15);
		Assert.assertEquals(false, result0);
		
		String out1 = "$ P:\\Outils\\jdk1.5.0_16\\bin\\java.exe -version\r\njava version \"1.5.0_16\"\r\nJava(TM) 2 Runtime Environment, Standard Edition (build 1.5.0_16-b02)\r\nJava HotSpot(TM) Client VM (build 1.5.0_16-b02, mixed mode, sharing)\r\n";
		boolean result1 = JdkUtils.extractAndCheckJavaVersion(out1, JdkUtils.JAVA_SPECIFICATION_VERSION_15);
		Assert.assertEquals(true, result1);
	}
}
