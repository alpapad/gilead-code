package net.sf.gilead.sample.client.command;

import net.sf.gilead.sample.client.core.ApplicationParameters;
import net.sf.gilead.sample.client.core.DefaultCallback;
import net.sf.gilead.sample.client.message.MessageRemote;
import net.sf.gilead.sample.domain.Message;

import com.google.gwt.user.client.Command;

/**
 * Message deletion command
 * @author bruno.marchesson
 */
public class DeleteMessageCommand implements Command
{
	//----
	// Attribute
	//----
	/**
	 * The message to delete
	 */
	private Message _message;
	
	//----
	// Constructor
	//----
	public DeleteMessageCommand(Message message)
	{
		_message = message;
	}
	
	//----
	// Public interface
	//----
	public void execute()
	{
	//	Call asynchronous server service
	//
		MessageRemote.Util.getInstance().deleteMessage(_message, new DefaultCallback(){
			
			public void onSuccess(Object result)
			{
				ApplicationParameters.getInstance().getApplication().displayStatus("Message deleted");
				
			//	Refresh message list
			//
				new RefreshMessageListCommand().execute();
			}
		});
	}
}