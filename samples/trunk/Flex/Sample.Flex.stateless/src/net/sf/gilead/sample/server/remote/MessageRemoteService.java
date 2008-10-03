/**
 * 
 */
package net.sf.gilead.sample.server.remote;


import net.sf.gilead.sample.domain.Message;
import net.sf.gilead.sample.server.ApplicationContext;
import net.sf.gilead.sample.server.service.IMessageService;
import flex.messaging.MessageBroker;
import flex.messaging.MessageException;
import flex.messaging.messages.AsyncMessage;
import flex.messaging.util.UUIDUtils;

/**
 * Message remote service
 * @author bruno.marchesson
 *
 */
public class MessageRemoteService
{
	//----
	// Constants
	//----
	private static final String MESSAGE_CHANNEL = "messageChannel";
	
	//----
	// Attributes
	//----
	/**
	 * Serialisation ID																																	
	 */
	private static final long serialVersionUID = -7208813584472295675L;

	/**
	 * The Flex Messaging client id
	 */
	private String clientID;
	
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
	public MessageRemoteService()
	{
		messageService = (IMessageService) ApplicationContext.getInstance().getBean(IMessageService.NAME);
		clientID = UUIDUtils.createUUID();
	}

	//-------------------------------------------------------------------------
	//
	// Team management
	//
	//-------------------------------------------------------------------------
	/**
	 * Save the argument message
	 */
	public void saveMessage(Message message)
	{
	//	Just save the message
	//
		messageService.saveMessage(message);
		
	//	Notify message change to every connected client
	//
		sendNotification(message, false);
	}
	
	/**
	 * Delete the argument message
	 */
	public void deleteMessage(Message message)
	{
	//	Just delete the message
	//
		messageService.deleteMessage(message);
		
	//	Notify message change to every connected client
	//
		sendNotification(message, true);
	}
	
	//-------------------------------------------------------------------------
	//
	// Internal method
	//
	//-------------------------------------------------------------------------
	/**
	 * Send a notification for message save or delete
	 * @param message the saved or deleted message
	 * @param deleted indicates the operation made on message
	 */
	protected void sendNotification(Message message, boolean deleted)
	{
	//	Get message broker
	//
		MessageBroker msgBroker = MessageBroker.getMessageBroker(null);
		if (msgBroker == null)
		{
			throw new MessageException("No message broker found !");
		}
		
	// 	Create message
	//
		AsyncMessage msg = new AsyncMessage();
		msg.setDestination(MESSAGE_CHANNEL);
		msg.setClientId(clientID);
		msg.setMessageId(UUIDUtils.createUUID());
		msg.setTimestamp(System.currentTimeMillis());
		
		msg.setHeader("deleted", deleted);
		msg.setBody(message);

	// 	Send message
	//
		msgBroker.routeMessageToService(msg, null);
	}
}
