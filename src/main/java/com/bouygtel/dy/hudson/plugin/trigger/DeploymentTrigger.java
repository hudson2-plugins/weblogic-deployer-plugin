/**
 * 
 */
package com.bouygtel.dy.hudson.plugin.trigger;

import static hudson.Util.fixNull;
import hudson.Extension;
import hudson.model.BuildableItem;
import hudson.model.Cause;
import hudson.model.Item;
import hudson.scheduler.CronTabList;
import hudson.triggers.Trigger;
import hudson.triggers.TriggerDescriptor;
import hudson.triggers.TimerTrigger.TimerTriggerCause;
import hudson.util.FormValidation;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import com.bouygtel.dy.hudson.plugin.Messages;
import antlr.ANTLRException;

/**
 * @author rchaumie
 *
 */
public class DeploymentTrigger extends Trigger<BuildableItem> {
	
	@DataBoundConstructor
    public DeploymentTrigger(String schedule) throws ANTLRException {
        super(schedule);
    }

//    @Override
//    public void run() {
//        job.scheduleBuild(0, new DeploymentTriggerCause());
//    }
    
	/* (non-Javadoc)
	 * @see hudson.triggers.Trigger#getDescriptor()
	 */
	@Override
	public DeploymentTriggerDescriptor getDescriptor() {
		return (DeploymentTriggerDescriptor) super.getDescriptor();
	}
	
	
    /*
     * 
     */
    @Extension
    public static class DeploymentTriggerDescriptor extends TriggerDescriptor {
        
    	/*
    	 * (non-Javadoc)
    	 * @see hudson.triggers.TriggerDescriptor#isApplicable(hudson.model.Item)
    	 */
    	public boolean isApplicable(Item item) {
            return item instanceof BuildableItem;
        }
    	
    	/*
    	 * (non-Javadoc)
    	 * @see hudson.model.Descriptor#getDisplayName()
    	 */
        public String getDisplayName() {
            return Messages.DeploymentTrigger_DisplayName();
        }

        // backward compatibility
        public FormValidation doCheck(@QueryParameter String value) {
            return doCheckSchedule(value);
        }
        
        /**
         * Performs syntax check.
         */
        public FormValidation doCheckSchedule(@QueryParameter String value) {
            try {
                String msg = CronTabList.create(fixNull(value)).checkSanity();
                if(msg!=null)   return FormValidation.warning(msg);
                return FormValidation.ok();
            } catch (ANTLRException e) {
                return FormValidation.error(e.getMessage());
            }
        }
    }
    
//    /*
//     * 
//     */
//    public static class DeploymentTriggerCause extends Cause {
//        @Override
//        public String getShortDescription() {
//            return Messages.DeploymentTrigger_Cause_ShortDescription();
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            return o instanceof DeploymentTriggerCause;
//        }

//        @Override
//        public int hashCode() {
//            return 5;
//        }
//    }
}
