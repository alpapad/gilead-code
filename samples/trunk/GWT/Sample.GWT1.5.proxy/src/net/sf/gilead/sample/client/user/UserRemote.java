/**
 * 
 */
package net.sf.gilead.sample.client.user;

import java.util.List;

import net.sf.gilead.sample.domain.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


/**
 * User update
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
	 * @return the user list
	 */
	public List<User> getUserList();
	
}
