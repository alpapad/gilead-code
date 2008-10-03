/**
 * 
 */
package net.sf.gilead.sample.server.service.implementation;

import java.util.List;

import net.sf.gilead.sample.domain.Message;
import net.sf.gilead.sample.domain.User;
import net.sf.gilead.sample.server.dao.IMessageDAO;
import net.sf.gilead.sample.server.service.IMessageService;

/**
 * Implementation of the message service
 * @author bruno.marchesson
 *
 */
public class MessageService implements IMessageService
{
	//----
	// Attributes
	//----
	/**
	 * the associated DAO
	 */
	private IMessageDAO messageDAO;
	
	//----
	// Properties
	//----
	/**
	 * @return the messageDAO
	 */
	public IMessageDAO getMessageDAO() {
		return messageDAO;
	}

	/**
	 * @param messageDAO the messageDAO to set
	 */
	public void setMessageDAO(IMessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	//-------------------------------------------------------------------------
	//
	// Implementation of the message service
	//
	//-------------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.sample.server.service.IMessageService#loadAllMessage(int, int)
	 */
	public List<Message> loadMessagesForUser(User user)
	{
		return messageDAO.loadMessagesForUser(user);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.sample.server.service.IMessageService#loadMessageDetails(net.sf.gilead.sample.domain.IMessage)
	 */
	public Message loadMessageDetails(Message message)
	{
		return messageDAO.loadDetailedMessage(message.getId());
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.sample.server.service.IMessageService#saveMessage(net.sf.gilead.sample.domain.IMessage)
	 */
	public void saveMessage(Message message)
	{
		messageDAO.saveMessage(message);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.sample.server.service.IMessageService#deleteMessage(net.sf.gilead.sample.domain.IMessage)
	 */
	public void deleteMessage(Message message)
	{
		messageDAO.deleteMessage(message);
	}
}
