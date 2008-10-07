package net.sf.gilead.sample.server.dao;

import java.util.List;

import net.sf.gilead.sample.domain.Message;


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
	 * Load the last posted message
	 */
	public Message loadLastMessage();
	
	/**
	 * Load all the posted messages
	 * @param startIndex first index of the message to load
	 * @param maxResult max number of message to load
	 * @return a list of IMessage
	 */
	public List<Message> loadAllMessage(int startIndex, int maxResult);
	
	/**
	 * Load the complete message
	 * @param id the ID of the message to load
	 * @return the message if found, null otherwise
	 */
	public Message loadMessageAndUser(Integer id);
	
	/**
	 * Save the argument message
	 */
	public Message saveMessage(Message message);
	
	/**
	 * Delete the argument message
	 */
	public void deleteMessage(Message message);
	
	/**
	 * Count all messages
	 * @return number of posted messages
	 */
	public int countAllMessages();
}