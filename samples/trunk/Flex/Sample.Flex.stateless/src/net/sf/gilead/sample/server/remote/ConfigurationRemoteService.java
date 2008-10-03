/**
 * 
 */
package net.sf.gilead.sample.server.remote;

import net.sf.gilead.sample.server.ApplicationContext;
import net.sf.gilead.sample.server.service.IStartupService;

/**
 * Configuration remote service
 * @author bruno.marchesson
 *
 */
public class ConfigurationRemoteService 
{
	//----
	// Attributes
	//----
	/**
	 * Serialisation ID																																	
	 */
	private static final long serialVersionUID = 8336011091333062487L;
	
	/**
	 * The startup service
	 */
	private IStartupService _startupService;
	
	//----
	// Properties
	//----
	/**
	 * @return the startup Service
	 */
	public IStartupService getStartupService() {
		return _startupService;
	}

	/**
	 * @param service the startup Service to set
	 */
	public void setStartupService(IStartupService service) {
		_startupService = service;
	}

	//-------------------------------------------------------------------------
	//
	// Constructor
	//
	//-------------------------------------------------------------------------
	/**
	 * Constructor
	 */
	public ConfigurationRemoteService()
	{
		_startupService = (IStartupService) ApplicationContext.getInstance().getBean(IStartupService.NAME);
	}

	//-------------------------------------------------------------------------
	//
	// Team management
	//
	//-------------------------------------------------------------------------
	/**
	 * @return the server configuration
	 */
	public String initServerConfiguration()
	{
	//	Check server initialisation
	//
		if (_startupService.isInitialized() == false)
		{
		//	First call on server
		//
			_startupService.initialize();
			return "Server initialized";
		}
		else
		{
		//	Server already initialized
		//
			return "Server initialized ok";
		}
	}
}
