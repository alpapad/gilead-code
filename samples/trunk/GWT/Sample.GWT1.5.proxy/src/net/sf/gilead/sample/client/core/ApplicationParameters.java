package net.sf.gilead.sample.client.core;

import net.sf.gilead.sample.client.Sample;
import net.sf.gilead.sample.domain.User;

/**
 * Global application parameters
 * @author bruno.marchesson
 */
public class ApplicationParameters
{
	//----
	// Singleton
	//----
	/**
	 * The unique instance of the singleton
	 */
	private static ApplicationParameters instance = null;

	/**
	 * @return the instance of the singleton
	 */
	public static ApplicationParameters getInstance()
	{
		if (instance == null)
		{
			instance = new ApplicationParameters();
		}
		return instance;
	}
	
	//----
	// Attributes
	//----
	/**
	 * The root application
	 */
	private Sample application;
	
	/**
	 * The authenticated user
	 */
	private User user;
	
	//----
	// Properties
	//----
	/**
	 * The application
	 */
	public Sample getApplication()
	{
		return application;
	}

	/**
	 * @param application the top application
	 */
	public void setApplication(Sample application)
	{
		this.application = application;
	}

	/**
	 * @return the user
	 */
	public User getUser()
	{
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user)
	{
		this.user = user;
	}

	//-------------------------------------------------------------------------
	//
	// Constructor
	//
	//-------------------------------------------------------------------------
	/**
	 * Protected constructor
	 */
	protected ApplicationParameters()
	{
	}
}
