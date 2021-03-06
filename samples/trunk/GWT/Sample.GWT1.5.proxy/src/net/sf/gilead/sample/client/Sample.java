package net.sf.gilead.sample.client;

import net.sf.gilead.sample.client.configuration.ConfigurationRemote;
import net.sf.gilead.sample.client.core.Action;
import net.sf.gilead.sample.client.core.ApplicationParameters;
import net.sf.gilead.sample.client.core.DefaultCallback;
import net.sf.gilead.sample.client.login.LoginPanel;
import net.sf.gilead.sample.client.message.MessageBoard;
import net.sf.gilead.sample.client.message.NewMessagePanel;
import net.sf.gilead.sample.client.user.HeaderPanel;
import net.sf.gilead.sample.domain.Message;
import net.sf.gilead.sample.domain.User;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sample implements EntryPoint, HistoryListener
{
	//----
	// Attributes
	//----
	/**
	 * The main panel
	 */
	private DockPanel mainPanel;
	
	/**
	 * Center panel
	 */
	private FlexTable applicationPanel;
	
	/**
	 * Header panel
	 */
	private HeaderPanel headerPanel;
	
	/**
	 * The message board
	 */
	private MessageBoard messageBoard;
	
	/**
	 * The new message panel
	 */
	private NewMessagePanel newMessagePanel;
	
	/**
	 * Authentication panel
	 */
	private LoginPanel loginPanel;

	//----
	// Properties (for unit tests)
	//----
	/**
	 * @return the message board
	 */
	public MessageBoard getMessageBoard()
	{
		return messageBoard;
	}
	
	/**
	 * @return the new  message Panel
	 */
	public NewMessagePanel getNewMessagePanel()
	{
		return newMessagePanel;
	}
	
	/**
	 * @return the login panel
	 */
	public LoginPanel getLoginPanel()
	{
		return loginPanel;
	}

	//-------------------------------------------------------------------------
	//
	// Program entry point
	//
	//-------------------------------------------------------------------------
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
	//	Force proxy generation
	//
		GWT.create(User.class);
		GWT.create(Message.class);
		GWT.create(Message.MessageType.class);
		
	//	Fill application parameters
	//
		ApplicationParameters.getInstance().setApplication(this);
		
	// 	Add History listener
	//
		History.addHistoryListener(this);
		
	//	 Create main panel
	//
		createMainPanel();
		createLoginPanel();
		
	//	Load server configuration
	//
		displayStatus("Server initialization...");
		ConfigurationRemote.Util.getInstance().initServerConfiguration(new DefaultCallback() {

			public void onSuccess(Object result)
			{
				displayStatus((String) result);
			}
			
		});
	}

	//-------------------------------------------------------------------------
	//
	// Public interface
	//
	//------------------------------------------------------------------------
	/**
	 * History listener entry point
	 */
	public void onHistoryChanged(String historyToken)
	{
	//	Deserialize command
	//
		Action command = Action.fromString(historyToken);
		if (Action.CONNECTED.equals(command.getName()) == true)
		{
		//	User connexion
		//
			headerPanel.setUser(ApplicationParameters.getInstance().getUser());
			createApplicationPanel();
		}
		else if (Action.AUTHENTICATION.equals(command.getName()) == true)
		{
		//	User log off
		//
			headerPanel.setUser(null);
			createLoginPanel();
		}
	}
	
	/**
	 * Display an error message
	 * @param errorMessage
	 */
	public void displayErrorMessage(String errorMessage) 
	{
		headerPanel.getStatusLabel().setHTML("<font color=\"red\">" + errorMessage+ "</font>");
	}
	
	/**
	 * Display the current status
	 * @param errorMessage
	 */
	public void displayStatus(String status) 
	{
		headerPanel.getStatusLabel().setHTML("<i>" + status + "</i>");
	}
	
	//-------------------------------------------------------------------------
	//
	// Internal methods
	//
	//-------------------------------------------------------------------------
	/**
	 * Create main panels
	 */
	private void createMainPanel()
	{
		mainPanel = new DockPanel();
		mainPanel.setSize("100%", "100%");
		mainPanel.setHorizontalAlignment(HasAlignment.ALIGN_CENTER);
		
		// Title and status
		VerticalPanel titlePanel = new VerticalPanel();
		titlePanel.setSize("100%", "60px");
		
		// title
		HTML title = new HTML("<H1>Gilead sample application</H1>(GWT 1.5 / dynamic proxy mode)<br>");
		titlePanel.add(title);
		titlePanel.setCellHorizontalAlignment(title, HasHorizontalAlignment.ALIGN_CENTER);
		
		// Header panel
		headerPanel = new HeaderPanel();
		titlePanel.add(headerPanel);
		titlePanel.setCellHorizontalAlignment(headerPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		
		mainPanel.add(titlePanel, DockPanel.NORTH);
		
		// Footer
		Label footer = new Label("(c) 2007 - 2009 | http://gilead.sourceforge.net");
		footer.setSize("100%", "20px");
		mainPanel.add(footer, DockPanel.SOUTH);
		
		RootPanel.get().add(mainPanel);
	}
	
	/**
	 * Login panel creation
	 */
	private void createLoginPanel()
	{
	//	Remove main panel if needed
	//
		if (applicationPanel != null)
		{
			mainPanel.remove(applicationPanel);
			applicationPanel = null;
		}
		
	//	Create login panel
	//
		loginPanel = new LoginPanel();
		mainPanel.add(loginPanel, DockPanel.CENTER);
		mainPanel.setCellHorizontalAlignment(loginPanel, HasAlignment.ALIGN_CENTER);
		mainPanel.setCellVerticalAlignment(loginPanel, HasAlignment.ALIGN_TOP);
	}
	
	/**
	 * Main application panels creation
	 */
	private void createApplicationPanel()
	{
	//	Remove Login panel
	//
		if (loginPanel != null)
		{
			mainPanel.remove(loginPanel);
			loginPanel = null;
		}
		
	// 	Create panels
	//
		applicationPanel = new FlexTable();
		applicationPanel.setWidth("100%");
		int row = 0;
		
		// create message panel
		newMessagePanel = new NewMessagePanel();
		applicationPanel.setWidget(row, 0, newMessagePanel);
		applicationPanel.getCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_LEFT);
		
		row++;
		
		// Message board
		messageBoard = new MessageBoard();
		applicationPanel.setWidget(row, 0, messageBoard);
				
		mainPanel.add(applicationPanel, DockPanel.CENTER);
		mainPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
	}
}
