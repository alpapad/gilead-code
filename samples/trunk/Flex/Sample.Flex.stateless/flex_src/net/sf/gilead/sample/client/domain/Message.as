package net.sf.gilead.sample.client.domain
{
	import net.sf.gilead.pojo.actionscript.LightEntity;

	/**
	 * Actionscript Message class
	 */
	[Bindable]
	[RemoteClass(alias="net.sf.gilead.sample.domain.Message")]
	public class Message extends LightEntity
	{
		//----
		// Attributes
		//----
		private var _id:int;
		private var _version:int;
		private var _message:String;
		private var _date:Date;
		private var _author:User;

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
		*	 Getter for property _message.
		**/
		public function get message():String {
			return _message;
		}

		/**
		*	 Setter for property _message.
		**/
		public function set message(o:String):void {
			_message = o;
		}
		/**
		*	 Getter for property _date.
		**/
		public function get date():Date {
			return _date;
		}

		/**
		*	 Setter for property _date.
		**/
		public function set date(o:Date):void {
			_date = o;
		}
		/**
		*	 Getter for property _author.
		**/
		public function get author():User {
			return _author;
		}

		/**
		*	 Setter for property _author.
		**/
		public function set author(o:User):void {
			_author = o;
		}

	    //----
	    // Constructor
	    //----
        /**
        * Empty constructor
        */
		public function Message()
		{
			super();
		}
		
		//----
		// Public interface
		//----
		/**
		 * String conversion
		 */
		public override function toString():String
		{
			return _message;
		}
	}
}