/**
 * 
 */
package net.sf.gilead.core.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

import net.sf.gilead.exception.ConvertorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Serialization manager singleton.
 * It serializes Serializable instances to simple byte array and
 * deserializes them when back.
 * (needed for proxy informations, since GWT does not like
 * Serializable type in Map<String, Serializable>)
 * @author bruno.marchesson
 *
 */
public class ByteStringProxySerialization implements IProxySerialization
{	
	//----
	// Attributes
	//----
	/**
	 * Log channel.
	 */
	private static Log _log = LogFactory.getLog(ByteStringProxySerialization.class);

	//-------------------------------------------------------------------------
	//
	// Constructor
	//
	//-------------------------------------------------------------------------
	/**
	 * Constructor.
	 */
	public ByteStringProxySerialization()
	{
	}
	
	//-------------------------------------------------------------------------
	//
	// Public interface
	//
	//-------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see net.sf.gilead.core.serialization.IProxySerialization#serializeToBytes(java.io.Serializable)
	 */
	public String serialize(Serializable serializable)
	{
		if (_log.isDebugEnabled())
		{
			_log.debug("Serialization of " + serializable);
		}
	//	Precondition checking
	//
		if (serializable == null)
		{
			return null;
		}
		
	//	Serialize using Java mechanism
	//
		try
		{
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		    ObjectOutputStream oos = new ObjectOutputStream(out);
		    oos.writeObject(serializable);
		    oos.close();
		    
		    return Base64.encodeToString(out.toByteArray(), false);
		}
		catch(IOException ex)
		{
			throw new ConvertorException("Error converting Serializable", ex);
		}
	}
	
	/* (non-Javadoc)
	 * @see net.sf.gilead.core.serialization.IProxySerialization#unserializeFromBytes(byte[])
	 */
	public Serializable unserialize(String object)
	{
		if (object == null)
		{
			return null;
		}
		
		byte[] bytes = Base64.decodeFast(object);
		if (_log.isDebugEnabled())
		{
			_log.debug("Unserialization of " + Arrays.toString(bytes));
		}
		
	//	Precondition checking
	//
		if ((bytes == null) ||
			(bytes.length == 0))
		{
			return null;
		}
		
	//	Convert back to Serializable
	//
		try
		{
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(in);
			return (Serializable) ois.readObject();
		}
		catch (Exception e)
		{
			throw new ConvertorException("Error converting Serializable", e);
		}

	}
}
