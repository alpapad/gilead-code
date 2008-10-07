package net.sf.gilead.sample.client.message;

import net.sf.gilead.sample.domain.Message;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MessageRemoteAsync
{
	/**
	 * Get the last message
	 */
	public void getLastMessage(AsyncCallback callback);
	
	/**
	 * Get all the posted messages
	 */
	public void getAllMessages(int startIndex, int maxResult, AsyncCallback callback);
	
	/**
	 * Count all posted messages
	 */
	public void countAllMessages(AsyncCallback callback);
	
	/**
	 * Get the message details
	 */
	public void getMessageDetails(Message message, AsyncCallback callback);
	
	/**
	 * Save the argument message
	 */
	public void saveMessage(Message message, AsyncCallback callback);
	
	/**
	 * Delete the argument message
	 */
	public void deleteMessage(Message message, AsyncCallback callback);
}
