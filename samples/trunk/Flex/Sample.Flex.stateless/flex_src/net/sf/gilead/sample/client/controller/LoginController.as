package net.sf.gilead.sample.client.controller
{
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import net.sf.gilead.sample.client.domain.ApplicationModel;
	import net.sf.gilead.sample.client.domain.User;
	
	/**
	 * Controller class for login action
	 */
	public class LoginController
	{
		//----
		// Attributes
		//----
		/**
		 * The remote login service
		 */
		private var _loginService:RemoteObject;
		
		//----
		// Constructor
		//----
		/**
		 * Constructor
		 */
		public function LoginController()
		{
		//	Get Login remote service
		//
			getLoginRemoteObject();
		}
		
		//----
		// Public interface
		//----
		/**
		 * Call to login method on server side
		 */
		public function login(login:String, password:String):void
		{
		//	Remote object call
		//
			_loginService.authenticate(login,password);
		}
		
		/**
		 * Call to logout current user
		 */
		public function logout():void
		{
		//	Clean application model
	    //	
	    	ApplicationModel.getInstance().currentOperation = "login out...";
	    	ApplicationModel.getInstance().clear();
	    	
	    //	Show login panel
	   	//
	   		Application.application.loginPanel.visible = true;
	   		Application.application.mainPanel.visible = false;
	    	
		}
		
		//----
		// Event listeners
		//----
		/**
		 * Callback method for successful authentication
		 */
		public function onAuthenticate(event:ResultEvent):void
		{
		//	Authenticated
		//
			ApplicationModel.getInstance().connectedUser = event.result as User;
		//	ApplicationModel.getInstance().currentOperation = "Connected as " + ApplicationModel.getInstance().connectedUser.login;
			
		//	Change main panel
		//
			Application.application.loginPanel.visible = false;
			Application.application.mainPanel.visible = true;
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
		 * Create Login Remote Object
		 */
		protected function getLoginRemoteObject () : void
		{
   			_loginService = new RemoteObject();
   			_loginService.destination = "loginService";
   
   		//	Event listening
   		//
   			_loginService.addEventListener("fault", onError);
      		_loginService.authenticate.addEventListener("result",onAuthenticate);      
		}
	}
}