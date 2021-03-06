/*
 * Copyright 2007 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sf.gilead.pojo.java14;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.sf.gilead.pojo.base.ILightEntity;

/**
 * Abstract POJO for lazy property handling.
 * This class is Java 1.4 and holds GWT xdoclets
 * @author bruno.marchesson
 *
 */
public abstract class LightEntity implements ILightEntity, Serializable
{
	//-----
	// Attributes
	//-----
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = 535611044803301746L;

	/**
	 * Map of proxy informations
	 * The key is the property name, the value is a map with
	 * persistence informations filled by the persistence util
	 * implementation.
	 * @gwt.typeArgs <java.lang.String, java.lang.String>
	 */
	protected Map _proxyInformations;

	//----
	// Properties
	//----
	/**
	 * @return the persistent collections properties
	 * @gwt.typeArgs <java.lang.String, java.lang.String>
	 */
	public Map getProxyInformations()
	{
		return _proxyInformations;
	}

	/**
	 * @param properties the persistent collection properties to set
	 * @gwt.typeArgs properties <java.lang.String, java.lang.String>
	 */
	public void setProxyInformations(Map properties)
	{
		_proxyInformations = properties;
	}
	
	//-------------------------------------------------------------------------
	//
	// Constructor
	//
	//-------------------------------------------------------------------------
	/**
	 * Constructor
	 */
	public LightEntity()
	{
		super();
	}

	//-------------------------------------------------------------------------
	//
	// Public interface
	//
	//-------------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.pojo.base.ILightEntity#addProxyInformations(java.lang.String, java.lang.Class)
	 */
	public void addProxyInformation(String property,
									String proxyInformations)
	{
		if (_proxyInformations == null)
		{
			_proxyInformations = new HashMap();
		}
		_proxyInformations.put(property, proxyInformations);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.pojo.base.ILightEntity#removeProxyInformations(java.lang.String)
	 */
	public void removeProxyInformation(String property)
	{
		if (_proxyInformations != null)
		{
			_proxyInformations.remove(property);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.pojo.base.ILightEntity#getProxyInformations(java.lang.String)
	 */
	public String getProxyInformation(String property)
	{
		if (_proxyInformations != null)
		{
			return (String) _proxyInformations.get(property);
		}
		else
		{
			return null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.pojo.base.ILightEntity#getDebugString()
	 */
	public String getDebugString()
	{
		if (_proxyInformations != null)
		{
			return _proxyInformations.toString();
		}
		else
		{
			return null;
		}
	}
}
