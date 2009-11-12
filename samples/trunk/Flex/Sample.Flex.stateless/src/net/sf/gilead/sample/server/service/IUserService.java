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
	 * Save the user
	 */
	public User saveUser(User user);
	
	/**
	 * @return the list of all users
	 */
	public List<User> loadUserList();

}