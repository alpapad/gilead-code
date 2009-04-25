/**
 * 
 */
package net.sf.gilead.sample.server;

import java.lang.reflect.Field;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
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
			
			// create welcome messages
			Message message2 = new Message();
			message2.setMessage("Another message");
			message2.setDate(new Date());
			user.addMessage(message2);
				
			// save user (message are cascaded)
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
    	EntityManager session = null;
		EntityTransaction transaction = null;
		try
		{
		//	Get session
		//
			session = PersistenceContext.getEntityManagerFactory().createEntityManager();
			transaction = session.getTransaction();
			transaction.begin();
	
		//	Create query
		//
	    	StringBuffer hqlQuery = new StringBuffer();
	    	hqlQuery.append("select user from net.sf.gilead.sample.domain.User user where user.login=:login");
	    	
	    //	Fill query
	    //
			Query query = session.createQuery(hqlQuery.toString());
			query.setParameter("login", login);
			
		//	Execute query
		//
			User user = (User) query.getSingleResult();
			transaction.commit();

			return user;
		}
		catch (Exception e)
		{
		//	Rollback
		//
			transaction.rollback();
			throw new RuntimeException(e.getMessage(), e);
		}
		finally
		{
			if (session != null)
			{
				session.close();
			}
		}
	}

    /**
     * Load the user with the argument login
     */
    public User loadUserAndMessagesByLogin(String login)
	{
    	EntityManager session = null;
		EntityTransaction transaction = null;
		try
		{
		//	Get session
		//
			session = PersistenceContext.getEntityManagerFactory().createEntityManager();
			transaction = session.getTransaction();
			transaction.begin();
	
		//	Create query
		//
	    	StringBuffer hqlQuery = new StringBuffer();
	    	hqlQuery.append("select user from net.sf.gilead.sample.domain.User user");
	    	// NOT ALLOWED : hqlQuery.append(" left join fetch user.messageList");
	    	hqlQuery.append(" where user.login=:login");
	    		    	
	    //	Fill query
	    //
			Query query = session.createQuery(hqlQuery.toString());
			query.setParameter("login", login);
			
		//	Execute query
		//
			User user = (User) query.getSingleResult();
			user.getMessageList().size(); // force message loading
			transaction.commit();
			
			return user;
		}
		catch (RuntimeException e)
		{
		//	Rollback
		//
			transaction.rollback();
			throw e;
		}
		finally
		{
			if (session != null)
			{
				session.close();
			}
		}
	}
    
    /**
     * Save the argument user
     * @param user the user to save or create
     */
	public User saveUser(User user)
	{
		EntityManager session = null;
		EntityTransaction transaction = null;
		try
		{
		//	Get session
		//
			session = PersistenceContext.getEntityManagerFactory().createEntityManager();
			transaction = session.getTransaction();
			transaction.begin();

		//	Save user
		//
			user = session.merge(user);
			transaction.commit();
			
			return user;
		}
		catch (RuntimeException e)
		{
		//	Rollback
		//
			if (transaction != null)
			{
				try
				{
					transaction.rollback();
				}
				catch(Exception ex)
				{
					_log.error(e.getMessage(), e);
				}
			}
			throw e;
		}
		finally
		{
			if (session != null)
			{
				session.close();
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
		EntityManager session = null;
		EntityTransaction transaction = null;
		try
		{
		//	Get session
		//
			session  = PersistenceContext.getEntityManagerFactory().createEntityManager();
			transaction = session.getTransaction();
			transaction.begin();
			
		//	Create query
		//
			Query query = session.createQuery("select count(user.id) from net.sf.gilead.sample.domain.User user");
			
		//	Execute query
		//
			int result = ((Integer) query.getSingleResult()).intValue();
			System.out.println(result);
			transaction.commit();
			
			return result;
		}
		catch (RuntimeException e)
		{
		//	Rollback
		//
			if (transaction != null)
			{
				transaction.rollback();
			}
			throw e;
		}
		finally
		{
			if (session != null)
			{
				session.close();
			}
		}
	}
}