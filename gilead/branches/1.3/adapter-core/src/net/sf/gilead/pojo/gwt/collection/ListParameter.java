/**
 * 
 */
package net.sf.gilead.pojo.gwt.collection;

import java.io.Serializable;
import java.util.List;

import net.sf.gilead.pojo.gwt.IGwtSerializableParameter;

/**
 * List parameter.
 * @author bruno.marchesson
 *
 */
public class ListParameter implements IGwtSerializableParameter, Serializable
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
	private List<IGwtSerializableParameter> value;

	//----
	// Getter and setter
	//----
	/**
	 * Change value.
	 */
	public void setValue(List<IGwtSerializableParameter> value)
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
	public ListParameter(List<IGwtSerializableParameter> value)
	{
		this.value = value;
	}
	
	/**
	 * Empty constructor (needed by GWT)
	 */
	public ListParameter()
	{
	}
}
