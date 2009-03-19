package net.sf.gilead.sample.client.configuration;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ConfigurationRemoteAsync
{
	/**
	 * Returns the server configuration
	 */
	public void initServerConfiguration(AsyncCallback callback);
}
