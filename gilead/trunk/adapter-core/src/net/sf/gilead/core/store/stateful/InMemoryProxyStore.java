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

package net.sf.gilead.core.store.stateful;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.sf.gilead.core.IPersistenceUtil;
import net.sf.gilead.core.store.IProxyStore;
import net.sf.gilead.exception.NotHibernateObjectException;
import net.sf.gilead.exception.TransientHibernateObjectException;

/**
 * In Memory Proxy Information Store.
 * This class stores POJO in a simple hashmap
 * @author bruno.marchesson
 *
 */
public class InMemoryProxyStore implements IProxyStore
{
	//----
	// Attributes
	//----
	/**
	 * The store hashmap
	 */
	protected Map<String, Map<String, Serializable>> _map = 
										new HashMap<String, Map<String,Serializable>>();
	
	/**
	 * The associated persistence util
	 */
	protected IPersistenceUtil _persistenceUtil;
	
	//----
	// Properties
	//----
	/**
	 * @return the persistence Util implementation
	 */
	public IPersistenceUtil getPersistenceUtil() {
		return _persistenceUtil;
	}

	/**
	 * @param persistenceUtil the persistence Util to set
	 */
	public void setPersistenceUtil(IPersistenceUtil persistenceUtil) {
		this._persistenceUtil = persistenceUtil;
	}
	
	//-------------------------------------------------------------------------
	//
	// IProxyStore implementation
	//
	//-------------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.core.store.IProxyStore#storeProxyInformations(java.lang.Object, java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public void storeProxyInformations(Object pojo, Serializable pojoId, String property,
									   Map<String, Serializable> proxyInformations)
	{
		_map.put(computeKey(pojo, pojoId, property), proxyInformations);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.core.store.IProxyStore#getProxyInformations(java.lang.Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Serializable> getProxyInformations(Object pojo,
														  String property)
    {
		try
		{
			return _map.get(computeKey(pojo, property));
		}
		catch(TransientHibernateObjectException ex)
		{
			return null;
		}
		catch (NotHibernateObjectException e)
		{
			return null;
		}
	}


	
	/*
	 * (non-Javadoc)
	 * @see net.sf.gilead.core.store.IProxyStore#removeProxyInformations(java.lang.Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void removeProxyInformations(Object pojo, String property)
	{
		_map.remove(computeKey(pojo, property));
	}
	
	//-------------------------------------------------------------------------
	//
	// Internal methods
	//
	//-------------------------------------------------------------------------
	/**
	 * Compute the hashmap key
	 * @param pojo
	 * @param property
	 * @return
	 */
	protected String computeKey(Object pojo, Serializable id, String property)
	{
		Class<?> pojoClass = _persistenceUtil.getUnenhancedClass(pojo.getClass());
		return UniqueNameGenerator.generateUniqueName(id, pojoClass) + '.' + property;
	}
	
	/**
	 * Compute the hashmap key
	 * @param pojo
	 * @param property
	 * @return
	 */
	protected String computeKey(Object pojo, String property)
	{
		return UniqueNameGenerator.generateUniqueName(_persistenceUtil, pojo) + '.' + property;
	}
	
}
