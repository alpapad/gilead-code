package net.sf.gilead.sample.client.controller
{
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import net.sf.gilead.sample.client.domain.ApplicationModel;
	import net.sf.gilead.sample.client.domain.Message;
	
	/**
	 * Controller for message operations
	 */
	public class MessageController
	{
		//----
		// Attributes
		//----
		/**
		 * The remote message service
		 */
		private var _messageService:RemoteObject;
		
		//----
		// Constructor
		//----
		/**
		 * Constructor
		 */
		public function MessageController()
		{
			getMessageRemoteObject();
		}
		
		//----
		// Public interface
		//----
		/**
		 * Call to save a message on server side
		 */
		public function saveMessage(message:Message):void
		{
		//	Remote object call
		//
			ApplicationModel.getInstance().currentOperation = "Saving message...";
			_messageService.saveMessage(message);
		}
		
		/**
		 * Call to delete a message on server side
		 */
		public function deleteMessage(message:Message):void
		{
		//	Remote object call
		//
			ApplicationModel.getInstance().currentOperation = "Deleting message...";
			_messageService.deleteMessage(message);
		}
		
		/**
		 * Call to create a message on server side
		 */
		public function createMessage(text:String):void
		{
		//	Create message
		//
			var message:Message = new Message();
			message.message = text;
			message.author = ApplicationModel.getInstance().connectedUser;
			message.date = new Date();
			
		//	Save message
		//
			saveMessage(message);
		}

		//----
		// Event listeners
		//----
		/**
		 * Callback method for successful RPC call
		 */
		public function onSavedMessage(event:ResultEvent):void
		{
			ApplicationModel.getInstance().currentOperation = "Message saved";
		}
		
		/**
		 * Callback method for successful RPC call
		 */
		public function onDeletedMessage(event:ResultEvent):void
		{
			ApplicationModel.getInstance().currentOperation = "Message deleted";
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
		protected function getMessageRemoteObject () : void
		{
   			_messageService = new RemoteObject();
   			_messageService.destination = "messageService";
   
   		//	Event listening
   		//
   			_messageService.addEventListener("fault", onError);
      		_messageService.saveMessage.addEventListener("result",onSavedMessage);
      		_messageService.deleteMessage.addEventListener("result",onDeletedMessage);      
		}
	}
}