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

package net.sf.gilead.proxy.gwt;

import net.sf.gilead.proxy.ProxyManager;


/**
 * Proxy generator for GWT 1.4
 * @author bruno.marchesson
 *
 */
public class Gwt14ProxyGenerator extends AbstractGwtProxyGenerator
{
	//-------------------------------------------------------------------------
	//
	// Constructor
	//
	//-------------------------------------------------------------------------
	public Gwt14ProxyGenerator()
	{
		super(ProxyManager.JAVA_14_LAZY_POJO);
	}
}