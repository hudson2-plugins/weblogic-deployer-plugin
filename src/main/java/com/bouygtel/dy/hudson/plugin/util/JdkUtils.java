/**
 * 
 */
package com.bouygtel.dy.hudson.plugin.util;

import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.TaskListener;
import hudson.model.AbstractBuild;
import hudson.model.Hudson;
import hudson.model.JDK;
import hudson.util.StreamTaskListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.util.StringUtils;

import com.bouygtel.dy.hudson.plugin.exception.RequiredJDKNotFoundException;

/**
 * @author rchaumie
 *
 */
public class JdkUtils {
	
	public static final String JAVA_HOME_PROPERTY = "JAVA_HOME";
	
	public static final String JAVA_SPECIFICATION_VERSION_15 = "1.5";
	
	public static final String JAVA_VERSION_COMMAND_VERSION_LINE_REGEX = "^(java version )(\")(.+)(\")(.*\\r*\\n*)$";
	
	/**
	 * 
	 * @param jdkBinDir
	 * @param logger
	 * @return
	 */
	public static boolean isJDK15(JDK jdk, PrintStream logger){
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			TaskListener listener = new StreamTaskListener(out);
			Launcher launcher = Hudson.getInstance().createLauncher(listener);
			String cmd = jdk != null ? jdk.getExecutable().getAbsolutePath() : "java";
			int result = launcher.launch().cmds(cmd,"-version").stdout(out).join();
			//L'executable n'existe pas
			if(result  != 0){
				return false;
			}

			//Cas ou on verifie le jdk reference dans les proprietes System
			if(jdk == null){
				if(! "1.5".equalsIgnoreCase(System.getProperty("java.specification.version"))){
					return false;
				}
				
				return true;
			}
			
			//On verifie le jdk passe en parametre
			return extractAndCheckJavaVersion(out.toString(),JAVA_SPECIFICATION_VERSION_15);
		} catch (IOException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        } catch(IllegalStateException ise){
        	return false;
        } catch(IndexOutOfBoundsException ioobe) {
        	return false;
        }
	}
	
	/**
	 * 
	 * @param out
	 * @param javaSpecificationVersion
	 * @return
	 */
	public static boolean extractAndCheckJavaVersion(String out, String javaSpecificationVersion){
		boolean jdk15found = false;
		Vector<String> lines = StringUtils.lineSplit(out.toString());
		Iterator<String> it = lines.iterator();
		Pattern pattern = Pattern.compile(JAVA_VERSION_COMMAND_VERSION_LINE_REGEX);
		
		while(it.hasNext()) {
			String line = it.next();
			Matcher matcher = pattern.matcher(line);
			if(matcher.matches() && matcher.group(3).startsWith(javaSpecificationVersion)){
				jdk15found = true;
			}
		}
		return jdk15found;
	}
	
	/**
	 * 
	 * @param build
	 * @param listener
	 * @return
	 */
	public static JDK getRequiredJDK(AbstractBuild<?, ?> build, BuildListener listener) throws RequiredJDKNotFoundException {

		JDK out = null;
		
		//On verifie la valeur de l'executable java reference par defaut
		if(JdkUtils.isJDK15(null,listener.getLogger())){
			//On renvoit null 
			return null;
		}
		
		//On verifie la valeur de l'executable java reference dans le projet
		JDK recenltyProjectJdkUsed = build.getProject().getJDK();
		if(JdkUtils.isJDK15(recenltyProjectJdkUsed,listener.getLogger())){
			return recenltyProjectJdkUsed;
		}
		
		listener.getLogger().println("[HudsonWeblogicDeploymentPlugin] - plugin requires Java 1.5, but this plugin is using "+System.getProperty("java.home")+". Try to find an appropriate one...");
			
		//Liste des jdks referencee
		List<JDK> jdksList = Hudson.getInstance().getJDKs();
		for(JDK jdk : jdksList){
			listener.getLogger().println("[HudsonWeblogicDeploymentPlugin] - jdks : NAME "+jdk.getName()+" HOME " +  jdk.getHome());
			if(JdkUtils.isJDK15(jdk,listener.getLogger())){
				out = jdk;
			}
		}
		
		if(out == null) {
			throw new RequiredJDKNotFoundException();
		}
		
		return out;
	}

}
