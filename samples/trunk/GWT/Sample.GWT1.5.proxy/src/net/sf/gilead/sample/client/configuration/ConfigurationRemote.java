/**
 * 
 */
package net.sf.gilead.sample.client.configuration;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


/**
 * Configuration management
 * @author bruno.marchesson
 *
 */
public interface ConfigurationRemote extends RemoteService
{
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static ConfigurationRemoteAsync instance;
		public static ConfigurationRemoteAsync getInstance(){
			if (instance == null) {
				instance = (ConfigurationRemoteAsync) GWT.create(ConfigurationRemote.class);
				ServiceDefTarget target = (ServiceDefTarget) instance;
				target.setServiceEntryPoint(GWT.getModuleBaseURL() + "/ConfigurationRemote");
			}
			return instance;
		}
	}
	
	/**
	 * Authenticate a user
	 */
	public String initServerConfiguration();
	
}
