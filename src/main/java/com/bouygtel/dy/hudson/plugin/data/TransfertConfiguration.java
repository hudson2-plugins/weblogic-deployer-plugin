/**
 * 
 */
package com.bouygtel.dy.hudson.plugin.data;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author rchaumie
 *
 */
public class TransfertConfiguration {

	@NotNull(message="FTP host must not be null")
	private String host;
	
	@NotNull(message="FTP user must not be null")
	private String user;
	
	
	private String password;
	
	@NotNull(message="FTP localFilePath variable must not be null")
	private String localFilePath;
	
	@NotNull(message="FTP remoteFilePath variable must not be null") 
	private String remoteFilePath;
	
	/**
	 * 
	 * @param host
	 * @param user
	 * @param password
	 * @param localFilePath
	 * @param remoteFilePath
	 */
	public TransfertConfiguration(String host, String user, String password,
			String localFilePath, String remoteFilePath) {
		super();
		this.host = host;
		this.user = user;
		this.password = password;
		this.localFilePath = localFilePath;
		this.remoteFilePath = remoteFilePath;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocalFilePath() {
		return localFilePath;
	}

	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}

	public String getRemoteFilePath() {
		return remoteFilePath;
	}
	
	/**
	 * 
	 * @param remoteFilePath
	 */
	public void setRemoteFilePath(String remoteFilePath) {
		this.remoteFilePath = remoteFilePath;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
