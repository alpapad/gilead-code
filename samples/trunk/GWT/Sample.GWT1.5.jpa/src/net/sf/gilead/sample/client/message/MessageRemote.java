/**
 * 
 */
package net.sf.gilead.sample.client.message;

import java.util.List;

import net.sf.gilead.sample.domain.Message;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


/**
 * Message remote service
 * @author bruno.marchesson
 *
 */
public interface MessageRemote extends RemoteService
{
	/**
	 * Utility class for simplifing access to the instance of async service.
	 */
	public static class Util {
		private static MessageRemoteAsync instance;
		public static MessageRemoteAsync getInstance(){
			if (instance == null) {
				instance = (MessageRemoteAsync) GWT.create(MessageRemote.class);
				ServiceDefTarget target = (ServiceDefTarget) instance;
				target.setServiceEntryPoint(GWT.getModuleBaseURL() + "/MessageRemote");
			}
			return instance;
		}
	}
	
	/**
	 * Get the last messages
	 */
	public List<Message> getAllMessages(int startIndex, int maxResult);
	
	/**
	 * Get the messages count
	 */
	public int countAllMessages();
	
	/**
	 * Get the last message
	 */
	public Message getLastMessage();
	
	/**
	 * Get the message details
	 */
	public Message getMessageDetails(Message message);
	
	/**
	 * Save the argument message
	 */
	public Message saveMessage(Message message);
	
	/**
	 * Delete the argument message
	 */
	public void deleteMessage(Message message);
}
