/**
 * 
 */
package net.sf.gilead.sample.server;

import java.util.Date;

import javax.servlet.ServletException;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.core.store.stateless.StatelessProxyStore;
import net.sf.gilead.gwt.PersistentRemoteService;
import net.sf.gilead.sample.client.remote.UserRemote;
import net.sf.gilead.sample.domain.Message;
import net.sf.gilead.sample.domain.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Remote service for sample application
 * @author bruno.marchesson
 *
 */
public class UserRemoteService extends PersistentRemoteService
								implements UserRemote
{
	//----
	// Attributes
	//----
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
		
	//	Bean Manager initialization
	//
		HibernateUtil persistenceUtil = new HibernateUtil();
		persistenceUtil.setSessionFactory(HibernateContext.getSessionFactory());

		PersistentBeanManager beanManager = new PersistentBeanManager();
		beanManager.setPersistenceUtil(persistenceUtil);
		beanManager.setProxyStore(new StatelessProxyStore());
		setBeanManager(beanManager);
		
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
			message1.setMessage("Fiest message");
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
    	Session session = null;
		Transaction transaction = null;
		try
		{
		//	Get session
		//
			session = HibernateContext.getSessionFactory().openSession();
			transaction = session.beginTransaction();
	
		//	Create query
		//
	    	StringBuffer hqlQuery = new StringBuffer();
	    	hqlQuery.append("from User user where user.login=:login");
	    	
	    //	Fill query
	    //
			Query query = session.createQuery(hqlQuery.toString());
			query.setString("login", login);
			
		//	Execute query
		//
			User user = (User) query.uniqueResult();
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
     * Load the user with the argument login
     */
    public User loadUserAndMessagesByLogin(String login)
	{
    	Session session = null;
		Transaction transaction = null;
		try
		{
		//	Get session
		//
			session = HibernateContext.getSessionFactory().openSession();
			transaction = session.beginTransaction();
	
		//	Create query
		//
	    	StringBuffer hqlQuery = new StringBuffer();
	    	hqlQuery.append("from User user");
	    	hqlQuery.append(" left join fetch user.messageList");
	    	hqlQuery.append(" where user.login=:login");
	    	
	    //	Fill query
	    //
			Query query = session.createQuery(hqlQuery.toString());
			query.setString("login", login);
			
		//	Execute query
		//
			User user = (User) query.uniqueResult();
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
	public void saveUser(User user)
	{
		Session session = null;
		Transaction transaction = null;
		try
		{
		//	Get session
		//
			session = HibernateContext.getSessionFactory().openSession();
			transaction = session.beginTransaction();

		//	Save user
		//
			session.saveOrUpdate(user);
			transaction.commit();
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
		Session session = null;
		Transaction transaction = null;
		try
		{
		//	Get session
		//
			session = HibernateContext.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
		//	Create query
		//
			Query query = session.createQuery("select count(*) from User user");
			
		//	Execute query
		//
			int result = ((Long) query.uniqueResult()).intValue();
			transaction.commit();
			
			return result;
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
}