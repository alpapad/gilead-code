package net.sf.gilead.sample.server.dao;

import java.util.List;

import net.sf.gilead.sample.domain.Message;
import net.sf.gilead.sample.domain.User;

public interface IMessageDAO
{
	//----
	// Constant
	//----
	/**
	 * The IoC name
	 */
	public static final String NAME = "messageDAO";
	
	//-------------------------------------------------------------------------
	//
	// Public interface
	//
	//-------------------------------------------------------------------------
	/**
	 * Load all the posted messages from a user
	 * @param startIndex first index of the message to load
	 * @param maxResult max number of message to load
	 * @return a list of IMessage
	 */
	public List<Message> loadMessagesForUser(User user);
	
	/**
	 * Load the complete message
	 * @param id the ID of the message to load
	 * @return the message if found, null otherwise
	 */
	public Message loadDetailedMessage(Integer id);
	
	/**
	 * Save the argument message
	 */
	public void saveMessage(Message message);
	
	/**
	 * Delete the argument message
	 */
	public void deleteMessage(Message message);
}