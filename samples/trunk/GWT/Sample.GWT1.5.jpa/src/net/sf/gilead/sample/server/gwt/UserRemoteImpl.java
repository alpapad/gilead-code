/**
 * 
 */
package net.sf.gilead.sample.server.gwt;

import java.util.List;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;
import net.sf.gilead.sample.client.user.UserRemote;
import net.sf.gilead.sample.domain.User;
import net.sf.gilead.sample.server.ApplicationContext;
import net.sf.gilead.sample.server.service.IIdentificationService;

/**
 * User remote service
 * @author bruno.marchesson
 *
 */
public class UserRemoteImpl extends PersistentRemoteService
							implements UserRemote
{
	//----
	// Attributes
	//----
	/**
	 * Serialisation ID																																	
	 */
	private static final long serialVersionUID = 5921199904102343567L;
	
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
	public UserRemoteImpl()
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
	 * Return the user list
	 */
	public List<User> getUserList()
	{
	//	Get the user list
	//
		return identificationService.loadUserList();
	}
}
