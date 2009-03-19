/**
 * 
 */
package net.sf.gilead.sample.client.message;

import java.util.Iterator;
import java.util.Set;

import net.sf.gilead.sample.client.core.ApplicationParameters;
import net.sf.gilead.sample.domain.Message;
import net.sf.gilead.sample.domain.User;

/**
 * Message static helper
 * @author bruno.marchesson
 *
 */
public class MessageHelper
{
	/**
	 * Indicate if the message is editable
	 */
	public static boolean isEditable(Message message)
	{
	//	Precondition checking
	//
		if (message == null)
		{
			return false;
		}
	//	Iterate over user messages
	//
		User currentUser = ApplicationParameters.getInstance().getUser();
		Set messageList = currentUser.getMessageList();
		Iterator iterator = messageList.iterator();
		
		// Note : the contains method does not work, event with 'equals' implementation :(
		while (iterator.hasNext())
		{
			Message userMessage = (Message) iterator.next();
			if (message.equals(userMessage))
			{
				return true;
			}
		}
	
		return false;
	}
}
