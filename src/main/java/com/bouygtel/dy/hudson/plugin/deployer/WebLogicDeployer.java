/**
 * 
 */
package com.bouygtel.dy.hudson.plugin.deployer;

import hudson.model.Run.RunnerAbortedException;
import hudson.remoting.Callable;
import hudson.remoting.Which;
import hudson.util.ArgumentListBuilder;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

/**
 * @author rchaumie
 *
 */
public class WebLogicDeployer {

	
	private static final String WEBLOGIC_TOOL_DEPLOYER_MAIN_CLASS = "weblogic.Deployer";
	
	/**
	 * 
	 * @author rchaumie
	 * @deprecated find another way to load outside remoting jar
	 */
	@Deprecated
	public static final class GetRemotingJar implements Callable<String,IOException> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 4132805045587298491L;

		public String call() throws IOException {
            
			Class<?> classReference = null;
			
			try {
				classReference = Class.forName(WEBLOGIC_TOOL_DEPLOYER_MAIN_CLASS);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
            
            return Which.jarFile(classReference).getPath();
            
        }
    }
	
	/**
	 * 
	 * @param parameter
	 * @return
	 */
	public static final String[] getWebLogicCommandLine(WebLogicDeployerParameters parameter) {
		ArgumentListBuilder args = new ArgumentListBuilder();
        
		//jdk
		if(parameter.getUsedJdk() == null) {
            args.add("java");
        } else {
            args.add(parameter.getBuild().getProject().getJDK().getExecutable());
        }
        
		//java options specifique
        if(StringUtils.isNotBlank(parameter.getJavaOpts())){
        	//On parse l'ensemble des options et on les rajoute des le args[]
        	String[] javaOptions = StringUtils.split(parameter.getJavaOpts(), ' ');
        	args.add(javaOptions);
        }
		
        //gestion du classpath
        args.add("-cp");
        
        // remoting.jar
        String remotingJar = null;
        if(StringUtils.isNotBlank(parameter.getClasspath())){
        	remotingJar = parameter.getClasspath();
        } else {
        	//Recuperation de la librairie weblogic interne
        	try {
    	        remotingJar =parameter.getLauncher().getChannel().call(new WebLogicDeployer.GetRemotingJar());
            } catch (Exception e){
            	parameter.getListener().error("Failed to determine the location of weblogic-9.2.jar");
                throw new RunnerAbortedException();
            }
            if(remotingJar==null) {// this shouldn't be possible, but there are still reports indicating this, so adding a probe here.
            	parameter.getListener().error("Failed to determine the location of weblogic-9.2.jar");
                throw new RunnerAbortedException();
            }
        }
        args.add(remotingJar);
        args.add(WEBLOGIC_TOOL_DEPLOYER_MAIN_CLASS);
        
        
        
        // mode debug force
        args.add("-debug");
        
        //Cas d'une application stage uniquement au deploiement
        if(! WebLogicCommand.UNDEPLOY.equals(parameter.getCommand()) && !parameter.isLibrary()){
        	// TODO stage provoque la generation du config....
        	args.add("-stage");
//        	args.add("-nostage");
        }
        
        args.add("-remote");
        args.add("-verbose");
        
        //Cas d'une application
        // Pour une librairie on copie sur le serveur puis on deploie
        if(! WebLogicCommand.UNDEPLOY.equals(parameter.getCommand()) && !parameter.isLibrary()){
        	args.add("-upload");
        }
        
        if(parameter.isSilentMode()){
        	args.add("-noexit");
        }
        
        args.add("-name");
        String targetedDeploymentName = StringUtils.isBlank(parameter.getDeploymentName()) ? parameter.getDeploymentName() : parameter.getArtifactId();
        if(StringUtils.isBlank(targetedDeploymentName)){
        	// TODO
        }
        args.add(targetedDeploymentName);
        
        if(StringUtils.isNotBlank(parameter.getSource())) {
        	args.add("-source");
        	args.add(parameter.getSource());
        }

        args.add("-targets");
        args.add(parameter.getDeploymentTargets());
        args.add("-adminurl");
        args.add("t3://" +parameter.getEnvironment().getHost()+":"+parameter.getEnvironment().getPort());
        args.add("-user");
        args.add(parameter.getEnvironment().getLogin());
        args.add("-password");
        args.add(parameter.getEnvironment().getPassword());
        
        args.add("-"+parameter.getCommand().getValue());
        
        if(parameter.isLibrary()) {
        	args.add("-library");
        }
        
        return args.toCommandArray();
	}

}
