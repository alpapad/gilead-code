/**
 * 
 */
package net.sf.gilead.sample.server.gwt;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;
import net.sf.gilead.sample.client.login.LoginRemote;
import net.sf.gilead.sample.domain.User;
import net.sf.gilead.sample.server.ApplicationContext;
import net.sf.gilead.sample.server.service.IIdentificationService;

/**
 * Login remote service
 * @author bruno.marchesson
 *
 */
public class LoginRemoteImpl extends PersistentRemoteService
							 implements LoginRemote
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
	public LoginRemoteImpl()
	{
		setBeanManager((PersistentBeanManager)ApplicationContext.getInstance().getBean("beanManager"));
		identificationService = (IIdentificationService) ApplicationContext.getInstance().getBean(IIdentificationService.NAME);
	}

	//-------------------------------------------------------------------------
	//
	// Team management
	//
	//-------------------------------------------------------------------------
	/**
	 * Authenticate the user
	 */
	public User authenticate(String login, String password)
	{
	//	Authenticate the user
	//
		return  identificationService.authenticate(login, password);
	}
}
