/**
 * 
 */
package net.sf.gilead.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate context utility class
 * @author bruno.marchesson
 *
 */
public class HibernateContext
{
	//----
	// Enumeration
	//----
	public enum Context
	{
		stateful,
		stateless,
		java5,
		proxy,
		annotated
	};
	
	//----
	// Constants
	//----
	/**
	 * The stateful configuration file
	 */
	private static final String STATEFUL_CONFIGURATION_FILE = 
		"stateful/hibernate.cfg.xml";
	
	/**
	 * The stateless configuration file
	 */
	private static final String STATELESS_CONFIGURATION_FILE = 
		"stateless/hibernate.cfg.xml";
	
	/**
	 * The Java5 configuration file
	 */
	private static final String JAVA5_CONFIGURATION_FILE = 
		"java5/hibernate.cfg.xml";
	
	/**
	 * The Proxy configuration file
	 */
	private static final String PROXY_CONFIGURATION_FILE = 
		"proxy/hibernate.cfg.xml";
	
	/**
	 * The Java5 annotated (ServerOnly, ReadOnly) configuration file
	 */
	private static final String ANNOTATED_CONFIGURATION_FILE = 
		"annotated/hibernate.cfg.xml";
	
	
	//----
	// Attributes
	//----
	/**
	 * Log channel
	 */
	private static Log _log = LogFactory.getLog(HibernateContext.class);
	
	/**
	 * Current configuration
	 */
	private static Context _context;
	
	/**
	 * The current session factory
	 */
	private static SessionFactory _sessionFactory;
	
	//----
	// Properties
	//----
	/**
	 * @return the application context
	 */
	public static Context getContext()
	{
		return _context;
	}

	/**
	 * @param context the context to set
	 */
	public static void setContext(Context context)
	{
		if (_context != context)
		{
			_context = context;
			
			if (_sessionFactory != null)
			{
				_sessionFactory.close();
			}
			_sessionFactory = null;
		}
	}

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
				if ((_context == Context.java5) ||
					(_context == Context.annotated))
				{
					// Create the SessionFactory from hibernate.cfg.xml
					_sessionFactory = new AnnotationConfiguration().configure(getContextFile()).buildSessionFactory(); 	
				}
				else
				{
					// Create the SessionFactory from hibernate.cfg.xml
					_sessionFactory = new Configuration().configure(getContextFile()).buildSessionFactory();
				}
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
	
	//-------------------------------------------------------------------------
	//
	// Internal methods
	//
	//-------------------------------------------------------------------------
	/**
	 * @return the appropriate context file associated with the current application configuration
	 */
	private static String getContextFile()
	{
		if (_context == Context.stateful)
		{
			return STATEFUL_CONFIGURATION_FILE;
		}
		else if (_context == Context.stateless)
		{
			return STATELESS_CONFIGURATION_FILE;
		}
		else if (_context == Context.proxy)
		{
			return PROXY_CONFIGURATION_FILE;
		}
		else if (_context == Context.java5)
		{
			return JAVA5_CONFIGURATION_FILE;
		}
		else // if (_context == Context.annotated)
		{
			return ANNOTATED_CONFIGURATION_FILE;
		}
	}
}
