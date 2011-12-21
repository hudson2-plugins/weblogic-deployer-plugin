/**
 * 
 */
package org.hudsonci.plugins.deploy.weblogic.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.model.Model;

/**
 * @author rchaumie
 *
 */
public class MavenModelUtils {

	private static transient final Pattern patternProperty = Pattern.compile("^(\\$\\{)(.*)(\\})", Pattern.CASE_INSENSITIVE);
	
	/**
	 * 
	 * @param tagValue
	 * @param defaultValue
	 * @param model
	 * @return
	 */
	public static final String resolveTagValue(String tagValue, String defaultValue, Model model) {
		
		String value = null;
		
		Matcher matcher = patternProperty.matcher(tagValue);
		if(matcher.matches()) {
			//Recuperation de la properties
			value = model.getProperties().getProperty(matcher.group(2), defaultValue);
		}			
		else if(StringUtils.isNotBlank(tagValue)) {
			value = tagValue;
		}
		else {
			value = defaultValue;
		}
		
		return value;
	}
}
