package org.hudsonci.plugins.deploy.weblogic;

import java.io.IOException;

import org.apache.maven.project.MavenProject;

import hudson.maven.MavenBuildProxy;
import hudson.maven.MavenReporter;
/**
 * 
 */
import hudson.model.BuildListener;

/**
 * @author rchaumie
 *
 */
public class MavenArtifactRecorder extends MavenReporter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8297012013470022790L;

	/* (non-Javadoc)
	 * @see hudson.maven.MavenReporter#postBuild(hudson.maven.MavenBuildProxy, org.apache.maven.project.MavenProject, hudson.model.BuildListener)
	 */
	@Override
	public boolean postBuild(MavenBuildProxy build, MavenProject pom, BuildListener listener) throws InterruptedException, IOException {
		System.out.println("Je suis dans mon post Build");
		return true;
	}
	
	

}
