package net.sf.gilead.sample.server.service;

import java.util.List;

import net.sf.gilead.sample.domain.User;

/**
 * Interface of the user service
 * @author bruno.marchesson
 *
 */
public interface IUserService {

	//----
	// Constant
	//----
	/**
	 * The IoC name
	 */
	public static final String NAME = "userService";
	
	//-------------------------------------------------------------------------
	//
	// Public interface
	//
	//-------------------------------------------------------------------------
	/**
	 * Load the complete user and associated messages
	 */
	public User loadCompleteUser(User user);
	
	/**
	 * @return the list of all users
	 */
	public List<User> loadUserList();

}