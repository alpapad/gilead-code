package net.sf.gilead.sample.server.service;

import java.util.List;

import net.sf.gilead.sample.domain.User;

/**
 * Interface of the authentication service
 * @author bruno.marchesson
 *
 */
public interface IIdentificationService {

	//----
	// Constant
	//----
	/**
	 * The IoC name
	 */
	public static final String NAME = "identificationService";
	
	//-------------------------------------------------------------------------
	//
	// Public interface
	//
	//-------------------------------------------------------------------------
	/**
	 * Authenticates the user from its login and password
	 */
	public User authenticate(String login, String password);
	
	/**
	 * @return the list of all users
	 */
	public List<User> loadUserList();

}