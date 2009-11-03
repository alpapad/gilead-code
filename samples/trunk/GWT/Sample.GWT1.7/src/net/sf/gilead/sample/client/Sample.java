package net.sf.gilead.sample.client;

import net.sf.gilead.sample.client.remote.UserRemote;
import net.sf.gilead.sample.domain.Message;
import net.sf.gilead.sample.domain.User;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Sample implements EntryPoint
{
	//----
	// Attributes
	//-----
	/**
	 * The current user
	 */
	private User user;
	
	/**
	 * New message text box
	 */
	private TextBox newMessageTextBox;

	/**
	 * Post new message button
	 */
	private Button postButton;
	
	/**
	 * Current user login
	 */
	private TextBox loginTextBox;
	
	/**
	 * Current user first name 
	 */
	private TextBox firstNameTextBox;
	
	/**
	 * Current user last name
	 */
	private TextBox lastNameTextBox;
	
	/**
	 * Current user password 
	 */
	private TextBox passwordTextBox;
	
	/**
	 * The user associated message list box
	 */
	private ListBox messagesListBox;
	
	/**
	 * Simple load user button
	 */
	private Button loadUserButton;
	
	/**
	 * Load user and messages button
	 */
	private Button loadUserAndMessagesButton;
	
	/**
	 * Save user button
	 */
	private Button saveUserButton;
	
	//----
	// Properties
	//----
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	//-------------------------------------------------------------------------
	//
	// Public interface
	//
	//-------------------------------------------------------------------------
	/**
	 * Module entry point.
	 * Used to make graphic initialization
	 */
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get();

		final VerticalPanel verticalPanel = new VerticalPanel();
		rootPanel.add(verticalPanel, 0, 0);
		verticalPanel.setSize("100%", "100%");

		final Label gileadSampleApplicationLabel = new Label("Gilead sample application");
		gileadSampleApplicationLabel.setStyleName("title");
		verticalPanel.add(gileadSampleApplicationLabel);

		final Grid grid = new Grid();
		verticalPanel.add(grid);
		grid.resize(6, 3);

		final Label loginLabel = new Label("Login");
		grid.setWidget(0, 0, loginLabel);

		final Label firstNameLabel = new Label("First Name");
		grid.setWidget(1, 0, firstNameLabel);

		final Label lastNameLabel = new Label("Last Name");
		grid.setWidget(2, 0, lastNameLabel);

		final Label passwordLabel = new Label("Password");
		grid.setWidget(3, 0, passwordLabel);

		final Label messagesLabel = new Label("Messages");
		grid.setWidget(4, 0, messagesLabel);

		final Label newMessageLabel = new Label("New message");
		grid.setWidget(5, 0, newMessageLabel);

		loginTextBox = new TextBox();
		grid.setWidget(0, 1, loginTextBox);
		loginTextBox.setWidth("150px");

		firstNameTextBox = new TextBox();
		grid.setWidget(1, 1, firstNameTextBox);
		firstNameTextBox.setWidth("100%");

		lastNameTextBox = new TextBox();
		grid.setWidget(2, 1, lastNameTextBox);
		lastNameTextBox.setWidth("100%");

		passwordTextBox = new TextBox();
		grid.setWidget(3, 1, passwordTextBox);
		passwordTextBox.setWidth("100%");

		newMessageTextBox = new TextBox();
		grid.setWidget(5, 1, newMessageTextBox);
		newMessageTextBox.setWidth("100%");

		postButton = new Button();
		grid.setWidget(5, 2, postButton);
		postButton.setText("Post");

		final Label serverOnlyLabel = new Label("@ServerOnly");
		serverOnlyLabel.setStyleName("comment");
		grid.setWidget(3, 2, serverOnlyLabel);

		final Label readOnlyLabel = new Label("@ReadOnly");
		readOnlyLabel.setStyleName("comment");
		grid.setWidget(0, 2, readOnlyLabel);

		messagesListBox = new ListBox();
		messagesListBox.setStyleName("gwt-TextArea");
		grid.setWidget(4, 1, messagesListBox);
		messagesListBox.setVisibleItemCount(5);
		messagesListBox.setWidth("100%");

		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		verticalPanel.add(horizontalPanel);
		horizontalPanel.setWidth("100%");
		horizontalPanel.setSpacing(1);

		loadUserButton = new Button();
		horizontalPanel.add(loadUserButton);
		loadUserButton.setText("Load user without messages");
		loadUserButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadUser();
			}
		});

		loadUserAndMessagesButton = new Button();
		horizontalPanel.add(loadUserAndMessagesButton);
		loadUserAndMessagesButton.setText("Load user and messages");
		loadUserAndMessagesButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				loadUserWithMessages();
			}
		});
		

		saveUserButton = new Button();
		horizontalPanel.add(saveUserButton);
		saveUserButton.setText("Save User");
		saveUserButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				saveUser();
			}
		});
	}
	
	
	//-------------------------------------------------------------------------
	//
	// Internal methods
	//
	//-------------------------------------------------------------------------
	/**
	 * Load user without messages
	 */
	private void loadUser()
	{
		UserRemote.Util.getInstance().loadUserByLogin("test", new AsyncCallback<User>(){

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(User result)
			{
				setUser(result);
				updateDisplay();
			}
			
		});
	}
	
	/**
	 * Load user with messages
	 */
	private void loadUserWithMessages()
	{
		UserRemote.Util.getInstance().loadUserAndMessagesByLogin("test", new AsyncCallback<User>(){

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(User result)
			{
				setUser(result);
				updateDisplay();
			}
		});
	}
	
	/**
	 * Save user
	 */
	private void saveUser()
	{
	//	Update bean from text fields
	//
		updateUser();
		
		UserRemote.Util.getInstance().saveUser(user, new AsyncCallback<User>(){

			@Override
			public void onFailure(Throwable caught)
			{
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(User result)
			{
				setUser(result);
				updateDisplay();
			}
			
		});
	}
	
	/**
	 * Update the underlying user from text fields values
	 */
	private void updateUser()
	{
		user.setFirstName(firstNameTextBox.getText());
		user.setLastName(lastNameTextBox.getText());
		user.setLogin(loginTextBox.getText());
		user.setPassword(passwordTextBox.getText());
	}
	
	/**
	 * Update display from the current associated user
	 */
	private void updateDisplay()
	{
		firstNameTextBox.setText(user.getFirstName());
		lastNameTextBox.setText(user.getLastName());
		loginTextBox.setText(user.getLogin());
		passwordTextBox.setText(user.getPassword());
		
	//	Display messages
	//
		messagesListBox.clear();
		if (user.getMessageList() != null)
		{
			for(Message message : user.getMessageList())
			{
				messagesListBox.addItem(message.getMessage());
			}
		}
	}
}
