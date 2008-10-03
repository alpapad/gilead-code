/**
 * 
 */
package net.sf.gilead.sample.domain.exception;


/**
 * Identification exception
 * @author bruno.marchesson
 *
 */
public class IdentificationException extends RuntimeException
{
	//----
	// Attributes
	//----
	/**
	 * Serializartion ID
	 */
	private static final long serialVersionUID = -3974804157089131282L;

	/**
	 * The failed user name
	 */
	private String _userName;
	
	//----
	// Properties
	//----
	/**
	 * @return the user name
	 */
	public String getUserName()
	{
		return _userName;
	}
	
	/**
	 * Sets the user name
	 */
	public void setUserName(String userName)
	{
		_userName = userName;
	}

	//-----
	// Constructor
	//----
	/**
	 * Empty constructor
	 */
	public IdentificationException()
	{
	}
	
	/**
	 * Constructor
	 */
	public IdentificationException(String userName)
	{
		_userName = userName;
	}
	
	//----
	// Public interface
	//----
	public String getMessage()
	{
		return "Invalid user or password for " + _userName;
	}
}
