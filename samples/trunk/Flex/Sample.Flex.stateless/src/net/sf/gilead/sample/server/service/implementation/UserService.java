/**
 * 
 */
package net.sf.gilead.sample.server.service.implementation;

import java.util.List;

import net.sf.gilead.sample.domain.User;
import net.sf.gilead.sample.server.dao.IUserDAO;
import net.sf.gilead.sample.server.service.IUserService;

/**
 * Implementation of the user service
 * @author bruno.marchesson
 *
 */
public class UserService implements IUserService
{
	//----
	// Attributes
	//----
	/**
	 * The user DAO
	 */
	private IUserDAO userDAO;

	//----
	// Properties
	//----
	/**
	 * @return the userDAO
	 */
	public IUserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	//-------------------------------------------------------------------------
	//
	// Public interface
	//
	//-------------------------------------------------------------------------
	/**
	 * Load the complete user and associated messages
	 */
	public User loadCompleteUser(User user)
	{
		return userDAO.searchUserAndMessagesByLogin(user.getLogin());
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.sample.server.service.IIdentificationService#loadUserList()
	 */
	public List<User> loadUserList()
	{
		return userDAO.loadAll();
	}
}
