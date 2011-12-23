/**
 * 
 */
package org.hudsonci.plugins.deploy.weblogic;

import hudson.maven.MavenBuild;
import hudson.maven.MavenModule;
import hudson.maven.MavenModuleSetBuild;
import hudson.maven.reporters.MavenAbstractArtifactRecord;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.Run.Artifact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * @author rchaumie
 *
 */
public class MavenJobArtifactSelectorImpl implements ArtifactSelector {
	
	private static transient final Pattern ARTIFACT_DEPLOYABLE_PATTERN = Pattern.compile(".*\\.(ear|war|jar)", Pattern.CASE_INSENSITIVE);
	
	/* (non-Javadoc)
	 * @see org.hudsonci.plugins.deploy.weblogic.TargetGeneratedSelector#getTargetGeneratedFilePath()
	 */
	@Override
	public Artifact selectArtifactRecorded(AbstractBuild<?, ?> build, BuildListener listener) throws IOException, XmlPullParserException, InterruptedException  {
		
		Artifact selectedArtifact = null;
		
      	List<MavenAbstractArtifactRecord<MavenBuild>> mars = getActions( build, listener);
        if(mars==null || mars.isEmpty()) {
            listener.getLogger().println("[HudsonWeblogicDeploymentPlugin] - No artifacts are recorded. Is this a Maven project?");
        }
        
        listener.getLogger().println("[HudsonWeblogicDeploymentPlugin] - Retrieving artifacts recorded...");
        List<Artifact> artifactsRecorded = new ArrayList<Artifact>();
        for (MavenAbstractArtifactRecord<MavenBuild> mar : mars) {
        	listener.getLogger().println("[HudsonWeblogicDeploymentPlugin] - "+mar.getBuild().getArtifacts().size()+ " artifacts recorded in "+mar.getBuild().getArtifactsDir());
            for(Artifact artifact : mar.getBuild().getArtifacts()){
            	//On ne conserve que les jar,ear et war
            	Matcher matcher = ARTIFACT_DEPLOYABLE_PATTERN.matcher(artifact.getFileName());
        		while (matcher.find()) {
        			listener.getLogger().println("[HudsonWeblogicDeploymentPlugin] - the following artifact recorded "+artifact.getFileName()+" is eligible.");
        		    artifactsRecorded.add(artifact);
        		}
            	
            }
        }
        
        if(artifactsRecorded.size() < 1){
        	throw new RuntimeException("[HudsonWeblogicDeploymentPlugin] - No artifact to deploy found.");
        }
        
        if(artifactsRecorded.size() > 1){
        	listener.getLogger().println("[HudsonWeblogicDeploymentPlugin] - More than 1 artifact found : The first one "+artifactsRecorded.get(0)+ " will be deployed!!!");
        }
        
        selectedArtifact = artifactsRecorded.get(0);
		
		// Erreur si l'artifact n'existe pas
		if(selectedArtifact == null){
			throw new RuntimeException("[HudsonWeblogicDeploymentPlugin] - No artifact to deploy found.");
		}
        
		return selectedArtifact;
	}
	
	/**
	 * 
	 * @param build
	 * @param listener
	 * @return
	 */
	protected List<MavenAbstractArtifactRecord<MavenBuild>> getActions(AbstractBuild<?, ?> build, BuildListener listener) {
        List<MavenAbstractArtifactRecord<MavenBuild>> actions = new ArrayList<MavenAbstractArtifactRecord<MavenBuild>>();
        if (!(build instanceof MavenModuleSetBuild)) {
            return actions;
        }
        for (Entry<MavenModule, MavenBuild> e : ((MavenModuleSetBuild)build).getModuleLastBuilds().entrySet()) {
            MavenAbstractArtifactRecord<MavenBuild> a = e.getValue().getAction(MavenAbstractArtifactRecord.class);
            if (a == null) {
                listener.getLogger().println("[HudsonWeblogicDeploymentPlugin] - No artifacts are recorded for module" + e.getKey().getName() + ". Is this a Maven project?");
            } else {
                actions.add(a);    
            }
            
        }
        return actions;
    }

}
