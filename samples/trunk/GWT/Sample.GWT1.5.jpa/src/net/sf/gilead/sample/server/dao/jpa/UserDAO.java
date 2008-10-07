/**
 * 
 */
package net.sf.gilead.sample.server.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.gilead.sample.domain.User;
import net.sf.gilead.sample.server.dao.IUserDAO;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO for user beans.
 * @author bruno.marchesson
 *
 */
public class UserDAO implements IUserDAO
{
	//----
	// Attributes
	//----
    /**
     * The JPA entity Manager 
     */
    private EntityManager _entityManager;
    
    //----
    // Properties
    //----
   /**
     * Sets the associated Entity manager factory
     * @param factory
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager)
    {
    	_entityManager = entityManager;
    }
    
	//-------------------------------------------------------------------------
	//
	// Public interface
	//
	//-------------------------------------------------------------------------
    /**
     * Load the user with the argument ID
     */
    @Transactional(propagation=Propagation.SUPPORTS)
	public User loadUser(Integer id)
	{
	//	Create query
	//
		Query query = _entityManager.createQuery("from User user where user.id=:id");
		query.setParameter("id", id);
		
	//	Execute query
	//
		return (User) query.getSingleResult();
	}
    
    /**
     * Load the user with the argument login
     */
    @Transactional(propagation=Propagation.SUPPORTS)
	public User searchUserAndMessagesByLogin(String login)
	{
	//	Create query
	//
    	StringBuffer hqlQuery = new StringBuffer();
    	hqlQuery.append("from User user");
    	hqlQuery.append(" left join fetch user.messageList");
    	hqlQuery.append(" where user.login=:login");
    	
    //	Fill query
    //
		Query query = _entityManager.createQuery(hqlQuery.toString());
		query.setParameter("login", login);
		
	//	Execute query
	//
		return (User) query.getSingleResult();
	}
    
    /**
     * Load all the users
     */
    @SuppressWarnings("unchecked")
    @Transactional(propagation=Propagation.SUPPORTS)
	public List<User> loadAll()
	{
	//	Create query
	//
		Query query = _entityManager.createQuery("from User user");
		
	//	Execute query
	//
		return (List<User>) query.getResultList();
	}
    
    /**
     * Count all the users
     */
    @Transactional(propagation=Propagation.SUPPORTS)
	public int countAll()
	{
	//	Create query
	//
		Query query = _entityManager.createQuery("select count(*) from User user");
		
	//	Execute query
	//
		return ((Long) query.getSingleResult()).intValue();
	}
	
    /**
     * Save the argument user
     * @param user the user to save or create
     */
	@Transactional(propagation=Propagation.REQUIRED)
	public User saveUser(User user)
	{
		return _entityManager.merge(user);
	}
}