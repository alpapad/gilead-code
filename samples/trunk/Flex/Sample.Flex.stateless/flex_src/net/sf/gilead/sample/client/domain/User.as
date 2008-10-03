package net.sf.gilead.sample.client.domain
{
	import mx.collections.ArrayCollection;
	
	import net.sf.gilead.pojo.actionscript.LightEntity;

	/**
	 * Actionscript User class
	 */
	[Bindable]
	[RemoteClass(alias="net.sf.gilead.sample.domain.User")]
	public class User extends LightEntity
	{
		//----
		// Attributes
		//----
		private var _id:int;
		private var _version:int;
		private var _login:String;
		private var _firstName:String;
		private var _lastName:String;
		private var _password:String;
		
		private var _messageList:ArrayCollection;

		//----
		// Properties
		//----
	    /**
		*	 Getter for property _id.
		**/
		public function get id():int {
			return _id;
		}

		/**
		*	 Setter for property _id.
		**/
		public function set id(o:int):void {
			_id = o;
		}
		/**
		*	 Getter for property _version.
		**/
		public function get version():int {
			return _version;
		}

		/**
		*	 Setter for property _version.
		**/
		public function set version(o:int):void {
			_version = o;
		}
		/**
		*	 Getter for property _login.
		**/
		public function get login():String {
			return _login;
		}

		/**
		*	 Setter for property _login.
		**/
		public function set login(o:String):void {
			_login = o;
		}
		/**
		*	 Getter for property _firstName.
		**/
		public function get firstName():String {
			return _firstName;
		}

		/**
		*	 Setter for property _firstName.
		**/
		public function set firstName(o:String):void {
			_firstName = o;
		}
		/**
		*	 Getter for property _lastName.
		**/
		public function get lastName():String {
			return _lastName;
		}

		/**
		*	 Setter for property _lastName.
		**/
		public function set lastName(o:String):void {
			_lastName = o;
		}
		/**
		*	 Getter for property _password.
		**/
		public function get password():String {
			return _password;
		}

		/**
		*	 Setter for property _password.
		**/
		public function set password(o:String):void {
			_password = o;
		}
		
		/**
		*	 Getter for property _messageList.
		**/
		public function get messageList():ArrayCollection {
			return _messageList;
		}

		/**
		*	 Setter for property _messageList.
		**/
		public function set messageList(o:ArrayCollection):void {
			_messageList = o;
		}
		
		//----
		// Constructor
		//----
		public function User()
		{
			super();
		}
		
		//----
		// Public interface
		//----
		/**
		 * Add message to collection
		 */
		public function addMessage(msg:Message):void
		{
			msg.author = this;	
			
			if (_messageList == null)
			{
				_messageList = new ArrayCollection();
			}
			_messageList.addItem(msg);
		}
		
		/**
		 * Add message to collection
		 */
		public function removeMessage(msg:Message):void
		{
			msg.author = null;	
			_messageList.removeItemAt(_messageList.getItemIndex(msg));
		}
		
		/**
		 * String conversion
		 */
		public override function toString():String
		{
			return firstName + " " + lastName;
		}
		
		/**
		 * Children for Tree representation
		 */
		public function get children():ArrayCollection
		{
			return _messageList;
		}
	}
}