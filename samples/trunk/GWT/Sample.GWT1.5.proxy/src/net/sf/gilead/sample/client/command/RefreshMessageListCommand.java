/**
 * 
 */
package net.sf.gilead.sample.client.command;

import net.sf.gilead.sample.client.core.ApplicationParameters;

import com.google.gwt.user.client.Command;

/**
 * Force message list refresh
 * @author bruno.marchesson
 *
 */
public class RefreshMessageListCommand implements Command
{

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.Command#execute()
	 */
	public void execute()
	{ 
	//	Just refresh message board
	//
		ApplicationParameters.getInstance().getApplication().getMessageBoard().refresh();
	}

}
