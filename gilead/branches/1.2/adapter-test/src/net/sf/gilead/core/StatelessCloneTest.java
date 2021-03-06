/**
 * 
 */
package net.sf.gilead.core;

import net.sf.gilead.test.domain.interfaces.IMessage;
import net.sf.gilead.test.domain.interfaces.IUser;
import net.sf.gilead.test.domain.stateless.User;

/**
 * Clone test for stateless mode
 * @author bruno.marchesson
 *
 */
public class StatelessCloneTest extends CloneTest
{
	/**
	 * Test setup
	 */
	@Override
	protected void setUp() throws Exception
	{
	//	Init bean manager
	//
		_beanManager = TestHelper.initStatelessBeanManager();
		
	//	Init domain and clone classes
	//
		_domainMessageClass = net.sf.gilead.test.domain.stateless.Message.class;
		_domainUserClass = net.sf.gilead.test.domain.stateless.User.class;
		_domainEmployeeClass = net.sf.gilead.test.domain.stateless.Employee.class;
		
		_cloneMessageClass = _domainMessageClass;
		_cloneUserClass = _domainUserClass;
		_cloneEmployeeClass = _domainEmployeeClass;
		
	//	Call base setup
	//
		super.setUp();
	}
	
	/**
	 * Change the author for clone message
	 */
	protected void changeAuthorForClone(IMessage message, IUser user)
	{
		((net.sf.gilead.test.domain.stateless.Message)message).
				setAuthor((User)user);
	}
	
	/**
	 * Change the author of the message
	 */
	protected void changeAuthorForDomain(IMessage message, IUser user)
	{
		((net.sf.gilead.test.domain.stateless.Message)message).
				setAuthor((User)user);
	}
}
