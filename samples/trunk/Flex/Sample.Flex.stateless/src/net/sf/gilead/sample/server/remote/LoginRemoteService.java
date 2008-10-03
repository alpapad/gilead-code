/**
 * 
 */
package net.sf.gilead.sample.server.remote;

import net.sf.gilead.sample.domain.User;
import net.sf.gilead.sample.server.ApplicationContext;
import net.sf.gilead.sample.server.service.IIdentificationService;

/**
 * Login remote service
 * @author bruno.marchesson
 *
 */
public class LoginRemoteService
{
	//----
	// Attributes
	//----
	/**
	 * Serialisation ID																																	
	 */
	private static final long serialVersionUID = -5399410538322914497L;
	
	/**
	 * The message Service
	 */
	private IIdentificationService identificationService;
	
	//----
	// Properties
	//----
	/**
	 * @return the identification  Service
	 */
	public IIdentificationService getIdentificationService() {
		return identificationService;
	}

	/**
	 * @param identifcationService the identification Service to set
	 */
	public void setIdentifitcationService(IIdentificationService identificationService) {
		this.identificationService = identificationService;
	}

	//-------------------------------------------------------------------------
	//
	// Constructor
	//
	//-------------------------------------------------------------------------
	/**
	 * Constructor
	 */
	public LoginRemoteService()
	{
		identificationService = (IIdentificationService) ApplicationContext.getInstance().getBean(IIdentificationService.NAME);
	}

	//-------------------------------------------------------------------------
	//
	// Team management
	//
	//-------------------------------------------------------------------------
	/**
	 * Return the last message
	 */
	public User authenticate(String login, String password)
	{
	//	Just authenticate the user
	//
		return identificationService.authenticate(login, password);
	}
}
