/**
 * 
 */
package net.sf.gilead.sample.server;

import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.servlet.ServletException;

import net.sf.gilead.adapter4appengine.EngineRemoteService;
import net.sf.gilead.sample.client.remote.UserRemote;
import net.sf.gilead.sample.domain.Message;
import net.sf.gilead.sample.domain.User;

import com.google.appengine.repackaged.org.apache.commons.logging.Log;
import com.google.appengine.repackaged.org.apache.commons.logging.LogFactory;

/**
 * Remote service for sample application
 * @author bruno.marchesson
 *
 */
public class UserRemoteService extends EngineRemoteService//RemoteServiceServlet
								implements UserRemote
{
	//----
	// Attributes
	//----
	/**
	 * Log channel
	 */
	private static Log _log = LogFactory.getLog(UserRemoteService.class);
	
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = 2680916805539898045L;
	
	//-------------------------------------------------------------------------
	//
	// Initialization of Remote service : must be done before any server call !
	//
	//-------------------------------------------------------------------------
	@Override
	public void init() throws ServletException
	{
		super.init();
		
		
	//	Init DB if needed
	//
		if (countUsers() == 0)
		{
		//	Create test user
		//
			User user = new User();
			user.setLogin("test");
			user.setPassword("secret");
			user.setFirstName("Test");
			user.setLastName("user");
				
			// create welcome messages
			Message message1 = new Message();
			message1.setMessage("First message");
			message1.setDate(new Date());
			user.addMessage(message1);
		
			// save user (message are cascaded)
			user = saveUser(user);
			
			message1.setMessage("Modified message");
			
			// create welcome messages
			Message message2 = new Message();
			message2.setMessage("Another message");
			message2.setDate(new Date());
			user.addMessage(message2);
			
			saveUser(user);
		}
	}

	//-------------------------------------------------------------------------
	//
	// Public interface
	//
	//-------------------------------------------------------------------------
    /**
     * Load the user with the argument login
     */
    public User loadUserByLogin(String login)
	{
    	PersistenceManager persistenceManager = null;
		Transaction transaction = null;
		try
		{
		//	Get PM
		//
			persistenceManager = PersistenceContext.getPersistenceManagerFactory().getPersistenceManager();
			persistenceManager.setDetachAllOnCommit(true);
			
			transaction = persistenceManager.currentTransaction();
			transaction.begin();
	
		//	Create query
		//
			Query query = persistenceManager.newQuery(User.class);
		    query.setFilter("login == loginParam");
		    query.declareParameters("String loginParam");
		    query.setUnique(true);

		//	Execute query
		//
			User user = (User) query.execute(login);
			transaction.commit();

			return user;
		}
		catch (Exception e)
		{
		//	Rollback
		//
			if ((transaction != null) &&
				(transaction.isActive()))
			{
				try
				{
					transaction.rollback();
				}
				catch(Exception ex)
				{
					_log.error(ex.getMessage(), ex);
				}
			}
			throw new RuntimeException(e.getMessage(), e);
		}
		finally
		{
			if (persistenceManager != null)
			{
				persistenceManager.close();
			}
		}
	}
    
    /**
     * Save the argument user
     * @param user the user to save or create
     */
	public User saveUser(User user)
	{
		PersistenceManager persistenceManager = null;
		Transaction transaction = null;
		try
		{
		//	Get PM
		//
			persistenceManager = PersistenceContext.getPersistenceManagerFactory().getPersistenceManager();
			persistenceManager.setDetachAllOnCommit(true);
			
			transaction = persistenceManager.currentTransaction();
			transaction.begin();
			
		//	Save user
		//
			user = persistenceManager.makePersistent(user);
			
			transaction.commit();
			
			return user;
		}
		catch (Exception e)
		{
		//	Rollback
		//
			if ((transaction != null) &&
				(transaction.isActive()))
			{
				try
				{
					transaction.rollback();
				}
				catch(Exception ex)
				{
					_log.error(ex.getMessage(), ex);
				}
			}
			throw new RuntimeException(e.getMessage(), e);
		}
		finally
		{
			if (persistenceManager != null)
			{
				persistenceManager.close();
			}
		}
	}
    
    //-------------------------------------------------------------------------
    //
    // Internal methods
    //
    //-------------------------------------------------------------------------
    /**
     * Count all the users
     */
	protected int countUsers()
	{
		PersistenceManager persistenceManager = null;
		Transaction transaction = null;
		try
		{
		//	Get PM
		//
			persistenceManager = PersistenceContext.getPersistenceManagerFactory().getPersistenceManager();
			transaction = persistenceManager.currentTransaction();
			transaction.begin();
	
		//	Create query
		//
			Query query = persistenceManager.newQuery(User.class);
			query.setResult("count(this)");
			
		//	Execute query
		//
			int result = ((Integer) query.execute()).intValue();
			transaction.commit();
			
			return result;
		}
		catch (Exception e)
		{
		//	Rollback
		//
			if ((transaction != null) &&
				(transaction.isActive()))
			{
				try
				{
					transaction.rollback();
				}
				catch(Exception ex)
				{
					_log.error(ex.getMessage(), ex);
				}
			}
			throw new RuntimeException(e.getMessage(), e);
		}
		finally
		{
			if (persistenceManager != null)
			{
				persistenceManager.close();
			}
		}
	}
}