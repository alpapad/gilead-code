package net.sf.gilead.pojo.actionscript
{	
    [RemoteClass(alias="net.sf.gilead.pojo.java5.LightEntity")]
	public class LightEntity
	{
		/**
		 * The internal proxy informations
		 * (a map of <String, Map<String, Serializable>>)
		 */
		private var _proxyInformations:Object;
		
		/**
	 	 * Map of persistence initialisation state.
	 	 * (Map<String,Boolean>
	 	 */
		private var _initializationMap:Object;
		
		/**
		 * Constructor of Lazy Pojo abstract class.
		 **/
		public function LightEntity()
		{
			_proxyInformations = new Object();
			_initializationMap = new Object();
		}
		
		/**
		 * Getter for proxy informations
		 */
		public function get proxyInformations():Object
		{
			return _proxyInformations;
		}
		
		/**
		 * Setter for proxy informations
		 */
		public function set proxyInformations(value:Object):void
		{
			_proxyInformations = value;
		}
		
		/**
		 * Getter for initialization map
		 */
		public function get initializationMap():Object
		{
			return _initializationMap;
		}
		
		/**
		 * Setter for initialization map
		 */
		public function set initializationMap(value:Object):void
		{
			_initializationMap = value;
		}
		
		/**
		 * Indicates if the property was initialized on server side
		 */ 
		public function isInitialized(property:String):Boolean
		{
			return _initializationMap[property];
		}
		
		/**
		 * Indicates that the property has been initialized on client side
		 */ 
		public function setInitialized(property:String):void
		{
			_initializationMap[property] = true;
		}
		
		/**
		 * toString overrides
		 */
		public function debugString():String
		{
			var result:String;
			result = "[proxyInformations : ";
			result += _proxyInformations + " (";
			for(var key:String in _proxyInformations)
			{
				result += key + ":" + _proxyInformations[key] + ","; 
			}
			result += ")], [initializationMap : "; 
			result += _initializationMap + " (";
			for(var key2:String in _initializationMap)
			{
				result += key2 + ":" + _initializationMap[key2] + ","; 
			}
			
			result += ")]";
			return result;
		}
	}
}
