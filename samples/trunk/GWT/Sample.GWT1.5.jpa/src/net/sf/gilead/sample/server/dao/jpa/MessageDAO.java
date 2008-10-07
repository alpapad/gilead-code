/**
 * 
 */
package net.sf.gilead.sample.server.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.sf.gilead.sample.domain.Message;
import net.sf.gilead.sample.server.dao.IMessageDAO;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO for message beans.
 * @author bruno.marchesson
 *
 */
public class MessageDAO implements IMessageDAO
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
	/* (non-Javadoc)
	 * @see org.dotnetguru.hottrick.server.dao.hibernate.IMessageDAO#loadMessage(int)
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public Message loadLastMessage()
	{
	//	Create query
	//
		Query query = _entityManager.createQuery("from Message order by date desc");
		query.setMaxResults(1);
		
	//	Execute query
	//
		return (Message) query.getSingleResult();
	}
	
	/* (non-Javadoc)
	 * @see org.dotnetguru.hottrick.server.dao.hibernate.IMessageDAO#loadMessage(int)
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Message> loadAllMessage(int startIndex, int maxResult)
	{
	//	Create query
	//
		Query query = _entityManager.createQuery("from Message order by date desc");
		query.setFirstResult(startIndex);
		query.setMaxResults(maxResult);
		
	//	Execute query
	//
		return (List<Message>) query.getResultList();
	}
	
	/* (non-Javadoc)
	 * @see org.dotnetguru.hottrick.server.dao.hibernate.IMessageDAO#loadMessageAndUser(Integer)
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public Message loadMessageAndUser(Integer id)
	{
	//	Create query
	//
		StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append("from Message message");
		hqlQuery.append(" inner join fetch message.author");
		hqlQuery.append(" where message.id = :id");
		
	//	Fill query
	//
		Query query = _entityManager.createQuery(hqlQuery.toString());
		query.setParameter("id", id);
		
	//	Execute query
	//
		return (Message) query.getSingleResult();
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.sample.server.dao.IMessageDAO#saveMessage(net.sf.gilead.sample.domain.IMessage)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public Message saveMessage(Message message)
	{
		if (message.getId() > 0)
		{
			return _entityManager.merge(message);
		}
		else	   
		{
			_entityManager.persist(message);
			return message;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.sample.server.dao.IMessageDAO#saveMessage(net.sf.gilead.sample.domain.IMessage)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteMessage(Message message)
	{
		_entityManager.remove(_entityManager.merge(message));
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.sample.server.dao.IMessageDAO#countAllMessages()
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public int countAllMessages()
	{
	//	Create query
	//
		Query query = _entityManager.createQuery("select count(*) from Message");
		
	//	Execute query
	//
		return ((Long) query.getSingleResult()).intValue();
	}
}