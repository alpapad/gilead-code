package net.sf.gilead.sample.client.domain
{
	import mx.collections.ArrayCollection;
	
	/**
	 * Locator for application model
	 */
	[Bindable]
	public class ApplicationModel
	{
		//----
		// Attributes
		//---
		/**
		 * Unique instance of singleton
		 */
		private static var _instance:ApplicationModel;
		
		/**
		 * The current operation log string
		 */
		private var _currentOperation:String;
		
		/**
		 * The connected user
		 */
		private var _connectedUser:User;
		
		/**
		 * The selected user
		 */
		private var _selectedUser:User;

		/**
		 * The selected message
		 */
		private var _selectedMessage:Message;
		
		/**
		 * The user list
		 */
		private var _userList:ArrayCollection;
		
		//---- 
		// Properties
		//----
		/**
		 * Returns the unique instance of the singleton
		 */
		public static function getInstance():ApplicationModel
		{
			if (_instance == null)
			{
				_instance = new ApplicationModel();
			}
			return _instance;
		}
		
		/**
		*	 Getter for property current Operation.
		**/
		public function get currentOperation():String {
			return _currentOperation;
		}

		/**
		*	 Setter for property _current Operation.
		**/
		public function set currentOperation(o:String):void {
			_currentOperation = o;
		}
		/**
		*	 Getter for property connectedUser.
		**/
		public function get connectedUser():User {
			return _connectedUser;
		}

		/**
		*	 Setter for property connectedUser.
		**/
		public function set connectedUser(o:User):void {
			_connectedUser = o;
		}
		
		/**
		*	 Getter for property _userList.
		**/
		public function get userList():ArrayCollection {
			return _userList;
		}

		/**
		*	 Setter for property _userList.
		**/
		public function set userList(o:ArrayCollection):void {
			_userList = o;
		}
		
		/**
		*	 Getter for property _selectedUser.
		**/
		public function get selectedUser():User {
			return _selectedUser;
		}

		/**
		*	 Setter for property _selectedUser.
		**/
		public function set selectedUser(o:User):void {
			_selectedUser = o;
		}
		
		/**
		*	 Getter for property _selectedMessage.
		**/
		public function get selectedMessage():Message {
			return _selectedMessage;
		}

		/**
		*	 Setter for property _selectedMessage.
		**/
		public function set selectedMessage(o:Message):void {
			_selectedMessage = o;
		}


		/**
		 * Ctor
		 */
		public function ApplicationModel()
		{
		}
		
		/**
		 * Clear model locator
		 */
		public function clear():void
		{
			_connectedUser = null;
			_selectedMessage = null;
			_selectedUser = null;
			_userList = null;
		}

	}
}