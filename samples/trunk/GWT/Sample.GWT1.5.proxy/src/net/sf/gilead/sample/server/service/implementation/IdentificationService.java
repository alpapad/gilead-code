/**
 * 
 */
package net.sf.gilead.sample.server.service.implementation;

import java.util.List;

import net.sf.gilead.sample.domain.User;
import net.sf.gilead.sample.domain.exception.IdentificationException;
import net.sf.gilead.sample.server.dao.IUserDAO;
import net.sf.gilead.sample.server.service.IIdentificationService;

/**
 * Implementation of the identification service
 * @author bruno.marchesson
 *
 */
public class IdentificationService implements IIdentificationService
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
	 * Authenticates the user from its login and password
	 */
	public User authenticate(String login, String password)
	{
	//	Search the user
	//
		User user = userDAO.searchUserAndMessagesByLogin(login);
		if (user == null)
		{
			throw new IdentificationException(login);
		}
		
	//	Check password
	//
		String userPassword = user.getPassword();
		if (userPassword == null)
		{
			if ((password != null) &&
				(password.length() > 0))
			{
				throw new IdentificationException(login);
			}
			else
			{
				return user;
			}
		}
		else
		{
			if (userPassword.equals(password) == false)
			{
				throw new IdentificationException(login);
			}
			else
			{
				return user;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.hibernate4gwt.sample.server.service.IIdentificationService#loadUserList()
	 */
	public List<User> loadUserList()
	{
		return userDAO.loadAll();
	}
}
