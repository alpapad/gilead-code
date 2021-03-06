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

package net.sf.gilead.gwt;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.beanlib.mapper.ProxyClassMapper;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPCCopy;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.SerializationPolicy;

/**
 * Abstract class for GWT remote service using persistent POJO 
 * @author bruno.marchesson
 *
 */
public abstract class PersistentRemoteService extends RemoteServiceServlet
{
	//----
	// Attribute
	//----
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = 7432874379586734765L;
	
	/**
	 * The Hibernate lazy manager
	 */
	protected PersistentBeanManager _beanManager;
	
	//----
	// Properties
	//----
	/**
	 * @return the Persistent Bean Manager
	 */
	public PersistentBeanManager getBeanManager()
	{
		return _beanManager;
	}

	/**
	 * @param manager the Hibernate Bean Manager to set
	 */
	public void setBeanManager(PersistentBeanManager manager)
	{
		_beanManager = manager;
	}
	
	//-------------------------------------------------------------------------
	//
	// Constructor
	//
	//-------------------------------------------------------------------------
	/**
	 * Empty constructor
	 */
	public PersistentRemoteService()
	{
	//	Default Hibernate Lazy Manager
	//
		_beanManager = PersistentBeanManager.getInstance();
	}
	
	/**
	 * Base constructor
	 */
	public PersistentRemoteService(PersistentBeanManager lazyManager)
	{
		_beanManager = lazyManager;
	}
	
	//-------------------------------------------------------------------------
	//
	// Hibernate Java 1.4 POJO methods
	//
	//-------------------------------------------------------------------------
	/**
	 * Clone and store (if needed) the hibernate POJO
	 */
	public Object clone(Object hibernatePojo)
	{
		return _beanManager.clone(hibernatePojo);
	}
	
	/**
	 * Retrieve and populate Hibernate pojo with gwt pojo values 
	 */
	public Object merge(Object gwtPojo)
	{
		return _beanManager.merge(gwtPojo);
	}
	
	//-------------------------------------------------------------------------
	//
	// Remote service servlet override
	//
	//-------------------------------------------------------------------------
	@Override
	protected SerializationPolicy doGetSerializationPolicy(
			HttpServletRequest request, String moduleBaseURL, String strongName) 
	{
		GileadRPCHelper.initClassLoader();
		return super.doGetSerializationPolicy(request, moduleBaseURL, strongName);
	}
	/**
	 * Servlet initialisation
	 */
	public void init() throws ServletException
	{
		super.init();
		
	//	Init proxy class loader if in proxy mode 
	//
		if ((_beanManager != null) &&
			(_beanManager.getClassMapper() instanceof ProxyClassMapper))
		{
			GileadRPCHelper.initClassLoader();
		}
	}
	
	/**
	 * Override of the RemoteServletService main method
	 */
	public String processCall(String payload) throws SerializationException
	{		
	//	Normal processing
	//
		RPCRequest rpcRequest = null;
		try
		{
		// Decode request
		//
	       rpcRequest = RPCCopy.getInstance().decodeRequest(payload, this.getClass(), this);
	      
	    //	Invoke method
	    //
	       GileadRPCHelper.parseInputParameters(rpcRequest, _beanManager, 
	    		   								   getThreadLocalRequest().getSession());
	       Object returnValue = RPCCopy.getInstance().invoke(this, rpcRequest.getMethod(), 
	    		  							   rpcRequest.getParameters(),
	    		  							   rpcRequest.getSerializationPolicy());
	      
	       returnValue = GileadRPCHelper.parseReturnValue(returnValue, _beanManager);
	      
	    //	Encode response
	    //  
	      return RPCCopy.getInstance().encodeResponseForSuccess(rpcRequest.getMethod(), 
	    		  								  				returnValue,
	    		  								  				rpcRequest.getSerializationPolicy());
	      
	    } 
		catch (InvocationTargetException e)
		{
			// Clone exception if needed
			Exception exception = (Exception) GileadRPCHelper.parseReturnValue(e.getCause(), _beanManager);
			
			return RPCCopy.getInstance().encodeResponseForFailure(rpcRequest.getMethod(), 
																  exception,
																  rpcRequest.getSerializationPolicy());
		}
		catch (IncompatibleRemoteServiceException ex)
		{
			// Clone exception if needed
			Exception exception = (Exception) GileadRPCHelper.parseReturnValue(ex, _beanManager);
			
			if (rpcRequest != null)
			{
				return RPCCopy.getInstance().encodeResponseForFailure(null, exception, rpcRequest.getSerializationPolicy());
			}
			else
			{
				return RPCCopy.getInstance().encodeResponseForFailure(null, exception);
			}
	    } 
	}
}
