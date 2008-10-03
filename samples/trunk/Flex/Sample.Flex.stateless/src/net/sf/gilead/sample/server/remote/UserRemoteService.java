/**
 * 
 */
package net.sf.gilead.sample.server.remote;

import java.util.List;

import net.sf.gilead.sample.domain.User;
import net.sf.gilead.sample.server.ApplicationContext;
import net.sf.gilead.sample.server.service.IUserService;

/**
 * User remote service
 * @author bruno.marchesson
 *
 */
public class UserRemoteService
{
	//----
	// Attributes
	//----
	/**
	 * Serialisation ID																																	
	 */
	private static final long serialVersionUID = 5921199904102343567L;
	
	/**
	 * The message Service
	 */
	private IUserService userService;
	
	//----
	// Properties
	//----
	/**
	 * @return the user Service
	 */
	public IUserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the user service to set.
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	//-------------------------------------------------------------------------
	//
	// Constructor
	//
	//-------------------------------------------------------------------------
	/**
	 * Constructor
	 */
	public UserRemoteService()
	{
		userService = (IUserService) ApplicationContext.getInstance().getBean(IUserService.NAME);
	}

	//-------------------------------------------------------------------------
	//
	// Team management
	//
	//-------------------------------------------------------------------------
	/**
	 * Return the user list
	 */
	public List<User> getUserList()
	{
	//	Just load the user list
	//
		return userService.loadUserList();
	}
	
	/**
	 * Return the complete user 
	 */
	public User loadUserDetails(User user)
	{
	//	Just load the user list
	//
		return userService.loadCompleteUser(user);
	}
}
