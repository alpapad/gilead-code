package net.sf.gilead.sample.client.controller
{
	import mx.controls.Alert;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import net.sf.gilead.sample.client.domain.ApplicationModel;
	
	/**
	 * Controller class for configuration action
	 */
	public class ConfigurationController
	{
		//----
		// Attributes
		//----
		/**
		 * The remote configuration service
		 */
		private var _configurationService:RemoteObject;
		
		//----
		// Constructor
		//----
		/**
		 * Constructor
		 */
		public function ConfigurationController()
		{
		//	Get Configuration remote service
		//
			getConfigurationRemoteObject();
		}
		
		//----
		// Public interface
		//----
		/**
		 * Call to inti method on server side
		 */
		public function init():void
		{
		//	Remote object call
		//
			_configurationService.initServerConfiguration();
		}
		
		//----
		// Event listeners
		//----
		/**
		 * Callback method for successful authentication
		 */
		public function onSuccess(event:ResultEvent):void
		{
			ApplicationModel.getInstance().currentOperation = event.result.toString();
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
		 * Create Configuration Remote Object
		 */
		protected function getConfigurationRemoteObject () : void
		{
   			_configurationService = new RemoteObject();
   			_configurationService.destination = "configurationService";
   
   		//	Event listening
   		//
   			_configurationService.addEventListener("fault", onError);
      		_configurationService.initServerConfiguration.addEventListener("result",onSuccess);
		}
	}
}