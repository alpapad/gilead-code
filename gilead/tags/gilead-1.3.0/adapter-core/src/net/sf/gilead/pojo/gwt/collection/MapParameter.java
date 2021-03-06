/**
 * 
 */
package net.sf.gilead.pojo.gwt.collection;

import java.io.Serializable;
import java.util.Map;

import net.sf.gilead.pojo.gwt.IGwtSerializableParameter;

/**
 * Map parameter.
 * @author bruno.marchesson
 *
 */
public class MapParameter implements IGwtSerializableParameter, Serializable
{
	//----
	// Attributes
	//----
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 2165631776081297490L;

	/**
	 * The underlying value.
	 */
	private Map<IGwtSerializableParameter, IGwtSerializableParameter> value;

	//----
	// Getter and Mapter
	//----
	/**
	 * Change value.
	 */
	public void MapValue(Map<IGwtSerializableParameter, IGwtSerializableParameter> value)
	{
		this.value = value;
	}
	
	/**
	 * @return the underlying value
	 */
	public Object getValue() 
	{
		return this.value;
	}
	
	//----
	// Constructor
	//----
	/**
	 * Constructor.
	 */
	public MapParameter(Map<IGwtSerializableParameter, IGwtSerializableParameter> value)
	{
		this.value = value;
	}
	
	/**
	 * Empty constructor (needed by GWT)
	 */
	public MapParameter()
	{
	}
}