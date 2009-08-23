/**
 * 
 */
package net.sf.gilead.core.loading.gwt.client;

import net.sf.gilead.gwt.client.LoadingService;
import net.sf.gilead.gwt.client.LoadingServiceAsync;
import net.sf.gilead.pojo.java5.LightEntity;
import net.sf.gilead.test.domain.interfaces.IMessage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Remote lazy loading test for GWT
 * @author bruno.marchesson
 *
 */
public class GwtLoadingTest extends GWTTestCase
{
	//-------------------------------------------------------------------------
	//
	// Test init
	//
	//-------------------------------------------------------------------------
	/**
	 * Get module name
	 */
	public String getModuleName()
	{
		return "net.sf.gilead.Test";
	}
	
	//-------------------------------------------------------------------------
	//
	// Test methods
	//
	//-------------------------------------------------------------------------
	/**
	 * Test loading of a simple association
	 */
	public void testLoadSimpleAssociation()
	{
	//	1. Load test message
	//
		// Setup an asynchronous event handler.
		Timer timer = new Timer()
		{
			public void run()
			{
				// Call remote init service
				InitServiceAsync remoteService = (InitServiceAsync) GWT.create(InitService.class);
				((ServiceDefTarget) remoteService).setServiceEntryPoint( GWT.getModuleBaseURL() + "/InitService");
				
				remoteService.loadTestMessage(new AsyncCallback<IMessage>()
				{
					public void onFailure(Throwable caught)
					{
						fail(caught.toString());
						finishTest();
					}

					public void onSuccess(IMessage result)
					{
						testLoadSimpleAssociation(result);
					}
			
				});
			}
		};

		// Set a delay period significantly longer than the
		// event is expected to take.
		delayTestFinish(60000);

		// Schedule the event and return control to the test system.
		timer.schedule(100);
	}
	
	/**
	 * Test load simple association
	 */
	protected void testLoadSimpleAssociation(final IMessage message)
	{
		// Setup an asynchronous event handler.
		Timer timer = new Timer()
		{
			public void run()
			{
				// Call remote loading service
				LoadingServiceAsync remoteService = (LoadingServiceAsync) GWT.create(LoadingService.class);
				((ServiceDefTarget) remoteService).setServiceEntryPoint( GWT.getModuleBaseURL() + "/LoadingService");
				
				remoteService.loadAssociation((LightEntity)message, "author", new AsyncCallback<LightEntity>()
				{
					public void onFailure(Throwable caught)
					{
						fail(caught.toString());

						// tell the test system the test is now done
						finishTest();
					}

					public void onSuccess(LightEntity result)
					{
						assertNotNull(result);
						
						// tell the test system the test is now done
						finishTest();
					}
			
				});

			}
		};

		// Schedule the event and return control to the test system.
		timer.schedule(100);
	}
}
