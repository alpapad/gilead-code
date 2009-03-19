package net.sf.gilead.sample.server.service;

import java.util.List;

import net.sf.gilead.sample.domain.Message;

public interface IMessageService 
{
	//----
	// Constant
	//----
	/**
	 * The IoC name
	 */
	public static final String NAME = "messageService";
	
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
	 * Count all posted messages
	 */
	public int countAllMessage();
	
	/**
	 * Load the complete message, with associations
	 */
	public Message loadMessageDetails(Message message);
	
	/**
	 * Save the argument message
	 */
	public void saveMessage(Message message);
	
	/**
	 * Delete the argument message
	 */
	public void deleteMessage(Message message);

}