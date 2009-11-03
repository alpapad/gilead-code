/**
 * 
 */
package net.sf.gilead.sample.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate context utility class
 * @author bruno.marchesson
 *
 */
public class HibernateContext
{
	//----
	// Constants
	//----
	/**
	 * The stateful configuration file
	 */
	private static final String CONFIGURATION_FILE = 
		"hibernate.cfg.xml";
	
	
	//----
	// Attributes
	//----
	/**
	 * Log channel
	 */
	private static Log _log = LogFactory.getLog(HibernateContext.class);
	
	/**
	 * The current session factory
	 */
	private static SessionFactory _sessionFactory;
	
	//-------------------------------------------------------------------------
	//
	// Public interface
	//
	//-------------------------------------------------------------------------
	/**
	 * @return the created session factory
	 */
	public static SessionFactory getSessionFactory()
	{
		if (_sessionFactory == null)
		{
			try 
			{
				
				// Create the SessionFactory from hibernate.cfg.xml
				_sessionFactory = new Configuration().configure(CONFIGURATION_FILE).buildSessionFactory();
			} 
			catch (Throwable ex) 
			{
				// Make sure you log the exception, as it might be swallowed
				_log.error("Initial SessionFactory creation failed.", ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
		return _sessionFactory;
	}
}
