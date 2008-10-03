package net.sf.gilead.sample.client.controller
{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.events.PropertyChangeEvent;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import net.sf.gilead.sample.client.domain.ApplicationModel;
	import net.sf.gilead.sample.client.domain.Message;
	import net.sf.gilead.sample.client.domain.User;
	
	/**
	 * Controller for main panel
	 */
	public class MainController
	{
		//----
		// Attributes
		//----
		/**
		 * The remote user service
		 */
		private var _userService:RemoteObject;
		
		//----
		// Constructor
		//----
		/**
		 * Constructor
		 */
		public function MainController()
		{
			getUserRemoteObject();
		}
		
		//----
		// Public interface
		//----
		/**
		 * Call to load user list on server side
		 */
		public function loadUserList():void
		{
		//	Remote object call
		//
			ApplicationModel.getInstance().currentOperation = "Loading user list...";
			_userService.getUserList();
		}
		
		/**
		 * Selection of a user
		 */
		public function selectUser(user:User):void
		{
		//	Load associated messages if needed
		//
			ApplicationModel.getInstance().selectedUser = user;
			ApplicationModel.getInstance().selectedMessage = null;
			if ((user != null) &&
			    (user.messageList == null))
		 	{
				_userService.loadUserDetails(user);
			}
			
		//	Show user details
		//
			Application.application.mainPanel.userDetails.visible = true;
			Application.application.mainPanel.messageDetails.visible = false;
		}
		
		/**
		 * Selection of a message
		 */
		public function selectMessage(message:Message):void
		{
			ApplicationModel.getInstance().selectedMessage = message;
			ApplicationModel.getInstance().selectedUser = null;
			
		//	Show message details
		//
			Application.application.mainPanel.userDetails.visible = false;
			Application.application.mainPanel.messageDetails.visible = true;
			
		//	Editable message ?
		//
			if (message.author.id == ApplicationModel.getInstance().connectedUser.id)
			{
				Application.application.mainPanel.messageDetails.currentState = "Editable";
			}
			else
			{
				Application.application.mainPanel.messageDetails.currentState = null;
			}
			
		}

		/**
		 * Handler for message reception
		 * (Push BlazeDS)
		 */
		public function onReceivedMessage(message:Message, deleted:Boolean):void
		{
		//	Is the message the current selected one ?
		//
			var selectedMessage:Message = ApplicationModel.getInstance().selectedMessage;
			if ((selectedMessage != null) &&
				(selectedMessage.id == message.id))
			{
				if (deleted == true)
				{
					ApplicationModel.getInstance().selectedMessage = null;
					Application.application.mainPanel.messageDetails.visible = false;
				}	
				else
				{
					ApplicationModel.getInstance().selectedMessage = message;
				}
			}
			
		//	Search owner in user list
		//
			for each (var user:User in ApplicationModel.getInstance().userList)
			{
				if (user.id == message.author.id)
				{
				//	Found the user : search the message
				//
					for each (var userMessage:Message in user.messageList)
					{
						if (userMessage.id == message.id)
						{
						//	Found the message
						//
							var index:int = user.messageList.getItemIndex(userMessage);
							if (deleted == true)
							{
							//	Remove message from user message list
							//
								ApplicationModel.getInstance().currentOperation = "Message deleted " + message;
								user.messageList.removeItemAt(index);
							}
							else
							{
							//	Replace message from user message list
							//
								ApplicationModel.getInstance().currentOperation = "Message updated " + message;
								user.messageList.setItemAt(message, index);
							}
							
						//	End of processing
						//
							return;
						}
					}
					
				//	The message has not been found : new message
				//
					ApplicationModel.getInstance().currentOperation = "New message " + message;
					if (user.messageList != null)
					{
						user.messageList.addItem(message);
					}
					
				//	End of processing
				//
					return;
				}
			}
			
		//	Author not found
		//
			ApplicationModel.getInstance().currentOperation = "Unknown author ("+ message.author +") for message " + message;
		}

		//----
		// Event listeners
		//----
		/**
		 * Callback method for successful RPC call
		 */
		public function onUserList(event:ResultEvent):void
		{
		//	User list
		//
			ApplicationModel.getInstance().currentOperation = "Received user list";
			ApplicationModel.getInstance().userList = event.result as ArrayCollection;
		}
		
		/**
		 * Callback method for successful RPC call
		 */
		public function onUserDetails(event:ResultEvent):void
		{
		//	Replace selected user with detailed user
		//
			ApplicationModel.getInstance().currentOperation = "Received user details";
			
			var oldSelectedUser : User = ApplicationModel.getInstance().selectedUser;
			var index:int = ApplicationModel.getInstance().userList.getItemIndex(oldSelectedUser);
			
			var updatedSelectedUser:User = event.result as User;
			ApplicationModel.getInstance().userList.setItemAt(updatedSelectedUser, index);
			ApplicationModel.getInstance().selectedUser = updatedSelectedUser;
			
			// Open tree node
			Application.application.mainPanel.userTree.expandItem(updatedSelectedUser, true, true);
		}

		/**
		 * Error handler for failed server calls
		 */
        public function onError(event:FaultEvent):void
        {
            Alert.show(event.fault.faultDetail, 'Error');
        	ApplicationModel.getInstance().currentOperation = event.fault.faultString;
        }

		//----
		// Internal methods
		//----
		/**
		 * Create User Remote Object
		 */
		protected function getUserRemoteObject () : void
		{
   			_userService = new RemoteObject();
   			_userService.destination = "userService";
   
   		//	Event listening
   		//
   			_userService.addEventListener("fault", onError);
      		_userService.getUserList.addEventListener("result",onUserList);
      		_userService.loadUserDetails.addEventListener("result",onUserDetails);      
		}
	}
}