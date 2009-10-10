package net.sf.gilead.sample.client;

import java.util.Date;

import net.sf.gilead.sample.client.remote.UserRemote;
import net.sf.gilead.sample.domain.Message;
import net.sf.gilead.sample.domain.User;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SampleAppEngine implements EntryPoint {
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
	private Button addMessageButton;
	
	/**
	 * Modify current message button
	 */
	private Button modifyMessageButton;
	
	/**
	 * Delete current message button
	 */
	private Button deleteMessageButton;
	
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

		final Label gileadSampleApplicationLabel = new Label("Gilead AppEngine sample application");
		gileadSampleApplicationLabel.setStyleName("title");
		verticalPanel.add(gileadSampleApplicationLabel);

		
		verticalPanel.add(createUserPanel());
		
		verticalPanel.add(new HTML("<hr/>"));
		
		verticalPanel.add(createUploadPanel());
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
	 * Save user
	 */
	private void saveUser()
	{
	//	Precondition checking
	//
		if (user == null)
		{
			return;
		}
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
				
				Window.alert("User has been saved");
			}
			
		});
	}
	
	private void addMessage()
	{
	//	Precondition checking
	//
		if ((user == null) ||
			(newMessageTextBox.getText() == null))
		{
			return;
		}
		
	//	Create new message
	//
		Message message = new Message();
		message.setDate(new Date());
		message.setMessage(newMessageTextBox.getText());
		user.addMessage(message);
		
		newMessageTextBox.setText(null);
		
	//	Update display
	//
		updateDisplay();
	}
	
	private void modifyMessage(String itemText)
	{
	//	Search message
	//
		for (Message message : user.getMessageList())
		{
			if (itemText.equals(message.getMessage()))
			{
			//	Found !
			//
				message.setMessage(itemText + "!");
				break;
			}
		}
		
		updateDisplay();
	}
	
	private void deleteMessage(String itemText)
	{
	//	Search message
	//
		for (Message message : user.getMessageList())
		{
			if (itemText.equals(message.getMessage()))
			{
			//	Found !
			//
				user.removeMessage(message);
				break;
			}
		}
		
		updateDisplay();
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
	
	private Grid createUserPanel()
	{
		final Grid grid = new Grid();
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
		loginTextBox.setTitle("This field will never get updated when back on server.");

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

		addMessageButton = new Button();
		grid.setWidget(5, 2, addMessageButton);
		addMessageButton.setText("Add");
		addMessageButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event)
			{
				addMessage();
			}
		});

		messagesListBox = new ListBox();
		grid.setWidget(4, 1, messagesListBox);
		messagesListBox.setVisibleItemCount(5);
		messagesListBox.setWidth("100%");
		
		modifyMessageButton = new Button();
		modifyMessageButton.setText("Modify");
		modifyMessageButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event)
			{
				if (messagesListBox.getSelectedIndex() != -1)
				{
					modifyMessage(messagesListBox.getItemText(messagesListBox.getSelectedIndex()));
				}
			}
		});
		
		deleteMessageButton = new Button();
		deleteMessageButton.setText("Delete");
		deleteMessageButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event)
			{
				if (messagesListBox.getSelectedIndex() != -1)
				{
					deleteMessage(messagesListBox.getItemText(messagesListBox.getSelectedIndex()));
				}
			}
		});
		
		VerticalPanel buttonPanel = new VerticalPanel();
		buttonPanel.add(modifyMessageButton);
		buttonPanel.add(deleteMessageButton);
		grid.setWidget(4, 2, buttonPanel);
		
		final HorizontalPanel horizontalPanel = new HorizontalPanel();
		grid.setWidget(5, 0, horizontalPanel);
		horizontalPanel.setWidth("100%");
		horizontalPanel.setSpacing(1);

		loadUserButton = new Button();
		horizontalPanel.add(loadUserButton);
		loadUserButton.setText("Load user");
		loadUserButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				loadUser();
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
		
		return grid;
	}
	
	private FormPanel createUploadPanel()
	{
		// Create a FormPanel and point it at a service.
	    final FormPanel form = new FormPanel();
	    form.setAction("/sampleappengine/uploadHandler");

	    // Because we're going to add a FileUpload widget, we'll need to set the
	    // form to use the POST method, and multipart MIME encoding.
	    form.setEncoding(FormPanel.ENCODING_MULTIPART);
	    form.setMethod(FormPanel.METHOD_POST);

	    // Create a panel to hold all of the form widgets.
	    VerticalPanel panel = new VerticalPanel();
	    form.setWidget(panel);

	    // Create a FileUpload widget.
	    FileUpload upload = new FileUpload();
	    upload.setName("upload");
	    panel.add(upload);

	    // Add a 'submit' button.
	    panel.add(new Button("Submit", new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        form.submit();
	      }
	    }));

	    // Add an event handler to the form.
	    form.addSubmitHandler(new SubmitHandler() {
	      public void onSubmit(SubmitEvent event) {
	        // This event is fired just before the form is submitted. We can take
	        // this opportunity to perform validation.
	    	  Window.alert(event.toDebugString());
	      }
	    });
	    
	    form.addSubmitCompleteHandler(new SubmitCompleteHandler(){
	      public void onSubmitComplete(SubmitCompleteEvent event) {
	        // When the form submission is successfully completed, this event is
	        // fired. Assuming the service returned a response of type text/html,
	        // we can get the result text here (see the FormPanel documentation for
	        // further explanation).
	        Window.alert(event.getResults());
	      }
	    });

	    return form;
	}
}
