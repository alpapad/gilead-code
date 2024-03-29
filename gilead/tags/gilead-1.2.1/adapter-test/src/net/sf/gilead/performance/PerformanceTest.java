/**
 * 
 */
package net.sf.gilead.performance;

import java.util.List;

import junit.framework.TestCase;
import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.TestHelper;
import net.sf.gilead.test.DAOFactory;
import net.sf.gilead.test.dao.IUserDAO;
import net.sf.gilead.test.domain.interfaces.IUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Abstract class for performance test
 * @author bruno.marchesson
 *
 */
public abstract class PerformanceTest extends TestCase
{
	//----
	// Attributes
	//----
	/**
	 * Log channel
	 */
	private static Log _log = LogFactory.getLog(PerformanceTest.class);
	
	/**
	 * Persistent lazy manager
	 */
	protected PersistentBeanManager _beanManager;
	
	//-------------------------------------------------------------------------
	//
	// Test initialisation
	//
	//-------------------------------------------------------------------------
	/**
	 * Test init
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		
	//	Init db if needed
	//
		if (TestHelper.isInitialized() == false)
		{
			TestHelper.initializeDB();
		}
		if (TestHelper.isLotOfDataCreated() == false)
		{
			TestHelper.initializeLotOfData(100);
		}
	}
	
	//-------------------------------------------------------------------------
	//
	// Test methods
	//
	//-------------------------------------------------------------------------
	/**
	 * Test clone of a loaded user and associated messages
	 */
	public void testPerformanceOnCloneAndMergeUserAndMessages()
	{
	//	Get UserDAO
	//
		IUserDAO userDAO = DAOFactory.getUserDAO();
		assertNotNull(userDAO);
		
	//	Load user
	//
		IUser user = userDAO.searchUserAndMessagesByLogin(TestHelper.VOLUMETRY_LOGIN);
		assertNotNull(user);
		assertNotNull(user.getMessageList());
		assertFalse(user.getMessageList().isEmpty());
		
	//	Clone user
	//
		long start = System.currentTimeMillis();
		IUser cloneUser = (IUser) _beanManager.clone(user);
		long end = System.currentTimeMillis();
		assertNotNull(cloneUser);
		
		_log.info("Clone user took " + (end - start) + " ms.");
		
	//	Merge user
	//
		start = System.currentTimeMillis();
		IUser mergeUser = (IUser) _beanManager.merge(cloneUser);
		_log.info("Merge user took " + (System.currentTimeMillis() - start) + " ms.");
		
	//	Test merged user
	//
		assertNotNull(mergeUser);
	}
	
	/**
	 * Test clone of a list of user and associated messages
	 */
	public void testPerformanceOnCloneAndMergeAllUserAndMessages()
	{
	//	Get UserDAO
	//
		IUserDAO userDAO = DAOFactory.getUserDAO();
		assertNotNull(userDAO);
		
	//	Load users
	//
		List<IUser> userList = userDAO.loadAllUserAndMessages();

	//	Clone user
	//
		long start = System.currentTimeMillis();
		List<IUser> cloneUserList = (List<IUser>) _beanManager.clone(userList);
		long end = System.currentTimeMillis();
		assertNotNull(cloneUserList);
		
		_log.info("Clone user list took " + (end - start) + " ms.");
		
	//	Merge user
	//
		start = System.currentTimeMillis();
		List<IUser> mergeUserList = (List<IUser>) _beanManager.merge(cloneUserList);
		_log.info("Merge user list took " + (System.currentTimeMillis() - start) + " ms.");
		
	//	Test merged user
	//
		assertNotNull(mergeUserList);
	}
}
