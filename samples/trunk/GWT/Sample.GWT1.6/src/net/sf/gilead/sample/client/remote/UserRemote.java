/**
 * 
 */
package net.sf.gilead.sample.client.remote;

import net.sf.gilead.sample.domain.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


/**
 * User remote service definition
 * @author bruno.marchesson
 *
 */
public interface UserRemote extends RemoteService
{
	/**
	 * Utility class for simplifing access to the instance of async service.
	 */
	public static class Util {
		private static UserRemoteAsync instance;
		public static UserRemoteAsync getInstance(){
			if (instance == null) {
				instance = (UserRemoteAsync) GWT.create(UserRemote.class);
				ServiceDefTarget target = (ServiceDefTarget) instance;
				target.setServiceEntryPoint(GWT.getModuleBaseURL() + "/UserRemote");
			}
			return instance;
		}
	}
	
	/**
	 * load the user (without message from its login)
	 */
	public User loadUserByLogin(String login);
	
	/**
	 * load the user and associated messages from its login
	 */
	public User loadUserAndMessagesByLogin(String login);
	
	/**
     * Save the argument user
     * @param user the user to save or create
     */
	public void saveUser(User user);
	
}
