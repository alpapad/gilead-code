/**
 * 
 */
package net.sf.gilead.sample.server.gwt;


import java.util.List;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;
import net.sf.gilead.sample.client.message.MessageRemote;
import net.sf.gilead.sample.domain.Message;
import net.sf.gilead.sample.server.ApplicationContext;
import net.sf.gilead.sample.server.service.IMessageService;

/**
 * Message remote service
 * @author bruno.marchesson
 *
 */
public class MessageRemoteImpl extends PersistentRemoteService
							implements MessageRemote
{
	//----
	// Attributes
	//----
	/**
	 * Serialisation ID																																	
	 */
	private static final long serialVersionUID = -7208813584472295675L;

	/**
	 * The message Service
	 */
	private IMessageService messageService;
	
	//----
	// Properties
	//----
	/**
	 * @return the messageService
	 */
	public IMessageService getMessageService() {
		return messageService;
	}

	/**
	 * @param messageService the messageService to set
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	//-------------------------------------------------------------------------
	//
	// Constructor
	//
	//-------------------------------------------------------------------------
	/**
	 * Constructor
	 */
	public MessageRemoteImpl()
	{
		setBeanManager((PersistentBeanManager)ApplicationContext.getInstance().getBean("beanManager"));
		messageService = (IMessageService) ApplicationContext.getInstance().getBean(IMessageService.NAME);
	}

	//-------------------------------------------------------------------------
	//
	// Team management
	//
	//-------------------------------------------------------------------------
	/**
	 * Return the last posted messages
	 */
	public List<Message> getAllMessages(int startIndex, int maxResult)
	{
	//	Just load the message
	//
		return messageService.loadAllMessage(startIndex, maxResult);
	}
	
	/**
	 * Count all posted messages
	 */
	public int countAllMessages()
	{
		return messageService.countAllMessage();
	}
	
	/**
	 * Return the last message
	 */
	public Message getLastMessage()
	{
	//	Just load the message
	//
		return messageService.loadLastMessage();
	}
	
	/**
	 * @return the Message loaded with all associations
	 */
	public Message getMessageDetails(Message message)
	{
	//	Just call the service
	//
		return messageService.loadMessageDetails(message);
	}
	
	/**
	 * Save the argument message
	 */
	public Message saveMessage(Message message)
	{
	//	Just save the message
	//
		messageService.saveMessage(message);
		return message;
	}
	
	/**
	 * Delete the argument message
	 */
	public void deleteMessage(Message message)
	{
	//	Just delete the message
	//
		messageService.deleteMessage(message);
	}
}
