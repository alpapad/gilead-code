package net.sf.gilead.sample.server.dao;

import java.util.List;

import net.sf.gilead.sample.domain.User;

public interface IUserDAO {

	//-------------------------------------------------------------------------
	//
	// Public interface
	//
	//-------------------------------------------------------------------------
	/**
	 * Load the user with the argument ID
	 */
	public User loadUser(Integer id);
	
	/**
     * Load the user with the argument login
     */
	public User searchUserAndMessagesByLogin(String login);

	/**
     * Load all the users
     */
    public List<User> loadAll();
    
    /**
     * Count all the users
     */
    public int countAll();
    
	/**
	 * Save the argument user
	 * @param user the user to save or create
	 */
	public void saveUser(User user);

}