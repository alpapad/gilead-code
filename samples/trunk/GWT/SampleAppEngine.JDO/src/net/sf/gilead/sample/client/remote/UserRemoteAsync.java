package net.sf.gilead.sample.client.remote;

import net.sf.gilead.sample.domain.User;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Async Callback for User remote service
 * @author bruno.marchesson
 *
 */
public interface UserRemoteAsync
{
	/**
	 * load the user and messages from its login
	 */
	public void loadUserByLogin(String login, AsyncCallback<User> callback);
	

	/**
     * Save the argument user
     * @param user the user to save or create
     */
	public void saveUser(User user, AsyncCallback<User> callback);
}
