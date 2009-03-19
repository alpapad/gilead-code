/**
 * 
 */
package net.sf.gilead.sample.client.user;

import net.sf.gilead.sample.client.core.Action;
import net.sf.gilead.sample.domain.User;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;

/**
 * Header panel
 * Contains user name, status message and log out link
 * @author bruno.marchesson
 *
 */
public class HeaderPanel extends Composite
{
	//----
	// Attributes
	//----
	/**
	 * The welcome panel
	 */
	protected Label _userLabel;
	
	/**
	 * The status label
	 */
	protected HTML _statusLabel;
	
	/**
	 * User list box
	 */
	protected Hyperlink _logoutLink;
	
	//----
	// Properties
	//----
	/**
	 * @return the status label
	 */
	public HTML getStatusLabel()
	{
		return _statusLabel;
	}	

	//-------------------------------------------------------------------------
	//
	// Constructor
	//
	//-------------------------------------------------------------------------
	/**
	 * Constructor
	 */
	public HeaderPanel()
	{
		init();
	}
	
	//-------------------------------------------------------------------------
	//
	// Public interface
	//
	//-------------------------------------------------------------------------
	/**
	 * User change
	 */
	public void setUser(User user)
	{
		if (user != null)
		{
			_userLabel.setText("Welcome " + user.getLogin());
			_logoutLink.setVisible(true);
		}
		else
		{
			_userLabel.setText("");
			_logoutLink.setVisible(false);
		}
	}
	
	//-------------------------------------------------------------------------
	//
	// Internal methods
	//
	//-------------------------------------------------------------------------
	/**
	 * Graphic init
	 */
	protected void init()
	{
	//	Main panel
	//
		Grid mainPanel = new Grid(1,3);
		mainPanel.setWidth("100%");
		initWidget(mainPanel);
		
		// User label
		_userLabel = new Label();
		mainPanel.setWidget(0, 0, _userLabel);
		mainPanel.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		
		// Status label
		_statusLabel = new HTML();
		mainPanel.setWidget(0, 1, _statusLabel);
		mainPanel.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		
		// Log off 
		_logoutLink = new Hyperlink();
		_logoutLink.setText("log out");
		_logoutLink.setVisible(false);
		_logoutLink.setTargetHistoryToken(new Action(Action.AUTHENTICATION).toString());
		
		mainPanel.setWidget(0, 2, _logoutLink);
		mainPanel.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);
	}
}
