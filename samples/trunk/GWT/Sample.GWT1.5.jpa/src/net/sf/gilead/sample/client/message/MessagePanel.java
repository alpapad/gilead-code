/**
 * 
 */
package net.sf.gilead.sample.client.message;

import java.util.Date;

import net.sf.gilead.sample.client.core.ApplicationParameters;
import net.sf.gilead.sample.client.core.DefaultCallback;
import net.sf.gilead.sample.client.ui.IconEditableLabel;
import net.sf.gilead.sample.domain.Message;
import net.sf.gilead.sample.domain.User;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosureEvent;
import com.google.gwt.user.client.ui.DisclosureHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Message panel
 * @author bruno.marchesson
 *
 */
public class MessagePanel extends Composite
{
	//----
	// Attributes
	//----
	/**
	 * The Message to display
	 */
	protected Message _message;
	
	/**
	 * Disclosure panel
	 */
	protected DisclosurePanel _mainPanel;
	
	/**
	 * message label
	 */
	protected IconEditableLabel _messageLabel;
	
	/**
	 * Details panel
	 */
	protected Panel _detailsPanel;
	
	/**
	 * message date label
	 */
	protected Label _dateLabel;
	
	/**
	 * message id label
	 */
	protected Label _idLabel;
	
	/**
	 * message author label
	 */
	protected Label _authorLabel;
	
	//----
	// Properties
	//----
	/**
	 * @return the message to display
	 */
	public final Message getMessage()
	{
		return _message;
	}
	
	/**
	 * @return the message editable label
	 */
	public final IconEditableLabel getMessageLabel()
	{
		return _messageLabel;
	}
	
	/**
	 * @return the main panel
	 */
	public final DisclosurePanel getMainPanel()
	{
		return _mainPanel;
	}
	
	/**
	 * @param message the message to display
	 */
	public final void setMessage(Message message)
	{
		if (message != _message)
		{
			_message = message;
			
			// Hide details if needed
			if (_mainPanel.isOpen())
			{
				_mainPanel.setOpen(false);
			}
			
			displayMessage();
		}
	}
	
	//-------------------------------------------------------------------------
	//
	// Constructor
	//
	//-------------------------------------------------------------------------
	/**
	 * Constructor
	 */
	public MessagePanel()
	{
		init();
	}
	
	//-------------------------------------------------------------------------
	//
	// Internal methods
	//
	//-------------------------------------------------------------------------
	protected void init()
	{
	//	Main panel
	//
		_mainPanel = new DisclosurePanel();
		_mainPanel.setWidth("100%");
		
		// Message text
		_messageLabel = new IconEditableLabel("", new ChangeListener(){

			public void onChange(Widget sender)
			{
				saveMessage();
			}
			
		});
		_mainPanel.setHeader(_messageLabel);
		
		// Details panel
		_detailsPanel = createDetailsPanel();
		_mainPanel.setContent(_detailsPanel);
		
		// Disclosure handling
		_mainPanel.addEventHandler(new DisclosureHandler(){

			public void onClose(DisclosureEvent event)
			{ 
				if (_messageLabel.isInEditingMode())
				{
					_messageLabel.cancelLabelChange();
				}
			}

			public void onOpen(DisclosureEvent event)
			{
				if (_messageLabel.isFieldEditable())
				{
					_messageLabel.changeTextLabel();
				}
				loadDetails();
			}
			
		});
		
		initWidget(_mainPanel);
		
	}

	/**
	 * Details panel creation
	 */
	private Panel createDetailsPanel()
	{
		FlexTable detailsPanel = new FlexTable();
		int row = 0;
		
		// Message author
		detailsPanel.setWidget(row, 0, new Label("Posted by "));
		
		_authorLabel = new Label();
		detailsPanel.setWidget(row, 1, _authorLabel);
		row++;
		
		// Message date
		detailsPanel.setWidget(row, 0, new Label("Post date : "));
		
		_dateLabel = new Label();
		detailsPanel.setWidget(row, 1, _dateLabel);
		row++;
		
		// Message id
		detailsPanel.setWidget(row, 0, new Label("ID : "));
		_idLabel = new Label();
		detailsPanel.setWidget(row, 1, _idLabel);
		row++;
		
		return detailsPanel;
	}
	
	/**
	 * Save the current message
	 * This method is public to enable unit test
	 */
	public void saveMessage()
	{
		setStatusMessage("Saving message...");
		
	//	Data binding
	//
		_message.setMessage(_messageLabel.getText());
		_message.setDate(new Date());
		
		MessageRemote.Util.getInstance().saveMessage(_message, new DefaultCallback(){

			public void onSuccess(Object result)
			{
				setStatusMessage("The message has been successfully saved");
			//	Get saved message
			//
				Message message= (Message)result;
				setMessage(message);
			}
		});
	}

	/**
	 * load message details 
	 */
	private void loadDetails()
	{
		if ((_message != null) &&
			(_message.getAuthor() == null))
		{
		// 	Load details
		//
			setStatusMessage("Loading details");
			
			MessageRemote.Util.getInstance().getMessageDetails(_message, new DefaultCallback(){
	
				public void onSuccess(Object result)
				{
					setStatusMessage("Details loaded");
					
					_message = (Message) result;
					displayMessage();
				}
				
			});
		}
	}
	
	/**
	 * Display the current message
	 */
	private void displayMessage()
	{
		if (_message == null)
		{
		//	Clear fields
		//
			_messageLabel.setEditable(false);
			_messageLabel.setText("");
			_idLabel.setText("");
			_dateLabel.setText("");
			_authorLabel.setText("");
		}
		else
		{
		//	Update fields
		//
			boolean editable = MessageHelper.isEditable(_message);
			_messageLabel.setEditable(editable);
			
			_messageLabel.setText(_message.getMessage());
			
			_idLabel.setText(_message.getId() + " / Version : " + _message.getVersion().toString());
			_dateLabel.setText(DateTimeFormat.getFullDateFormat().format(_message.getDate()));
			
			// Author
			User author = _message.getAuthor();
			if (author == null)
			{
				_authorLabel.setText("Unknown");
			}
			else
			{
				_authorLabel.setText(author.getFirstName() + " " + author.getLastName());
			}
		}
	}
	
	/**
	 * Change the status message
	 * @param status the message to display
	 */
	private void setStatusMessage(String status)
	{
		ApplicationParameters.getInstance().getApplication().displayStatus(status);
	}
}
