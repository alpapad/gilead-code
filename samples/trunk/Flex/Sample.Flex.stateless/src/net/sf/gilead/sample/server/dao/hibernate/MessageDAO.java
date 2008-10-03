/**
 * 
 */
package net.sf.gilead.sample.server.dao.hibernate;

import java.util.List;

import net.sf.gilead.sample.domain.Message;
import net.sf.gilead.sample.domain.User;
import net.sf.gilead.sample.server.dao.IMessageDAO;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO for message beans.
 * This implementation use HQL to work seamlessly with all implementation of the Message domain class
 * (Java 1.4 _ stateful or stateless _ and Java5)
 * @author bruno.marchesson
 *
 */
public class MessageDAO implements IMessageDAO
{
//	----
	// Attributes
	//----
    /**
     * The Hibernate session factory
     */
    private SessionFactory _sessionFactory;
    
    //----
    // Properties
    //----
    /**
     * @return the Hibernate session factory
     */
    public SessionFactory getSessionFactory()
    {
        return _sessionFactory;
    }

    /**
     * Sets the associated Hibernate session facgtory
     * @param factory
     */
    public void setSessionFactory(SessionFactory factory)
    {
    	_sessionFactory = factory;
    }
    
	//-------------------------------------------------------------------------
	//
	// Public interface
	//
	//-------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see org.dotnetguru.hottrick.server.dao.hibernate.IMessageDAO#loadMessage(int)
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Message> loadMessagesForUser(User user)
	{
	//	Create query
	//
		StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append("from Message message");
		hqlQuery.append(" where message.author = :author");
		hqlQuery.append(" order by message.date desc");
	
	//	Fill query
	//
		Query query = _sessionFactory.getCurrentSession().createQuery(hqlQuery.toString());
		query.setParameter("author", user);
		
	//	Execute query
	//
		return (List<Message>) query.list();
	}
	
	/* (non-Javadoc)
	 * @see org.dotnetguru.hottrick.server.dao.hibernate.IMessageDAO#loadDetailedMessage(Integer)
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public Message loadDetailedMessage(Integer id)
	{
	//	Create query
	//
		StringBuffer hqlQuery = new StringBuffer();
		hqlQuery.append("from Message message");
		hqlQuery.append(" inner join fetch message.author");
		hqlQuery.append(" where message.id = :id");
		
	//	Fill query
	//
		Query query = _sessionFactory.getCurrentSession().createQuery(hqlQuery.toString());
		query.setInteger("id", id);
		
	//	Execute query
	//
		Message message = (Message) query.uniqueResult();
		
		return message;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.sample.server.dao.IMessageDAO#saveMessage(net.sf.gilead.sample.domain.IMessage)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveMessage(Message message)
	{
		_sessionFactory.getCurrentSession().saveOrUpdate(message);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.sample.server.dao.IMessageDAO#saveMessage(net.sf.gilead.sample.domain.IMessage)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteMessage(Message message)
	{
		_sessionFactory.getCurrentSession().delete(message);
	}
}