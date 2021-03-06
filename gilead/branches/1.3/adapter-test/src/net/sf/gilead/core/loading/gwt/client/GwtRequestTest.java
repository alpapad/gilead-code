/**
 * 
 */
package net.sf.gilead.core.loading.gwt.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.gilead.gwt.client.RequestService;
import net.sf.gilead.gwt.client.RequestServiceAsync;
import net.sf.gilead.pojo.gwt.IGwtSerializableParameter;
import net.sf.gilead.pojo.gwt.basic.StringParameter;
import net.sf.gilead.test.domain.gwt.Message;
import net.sf.gilead.test.domain.gwt.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Remote request service test for GWT
 * @author bruno.marchesson
 *
 */
public class GwtRequestTest extends GWTTestCase
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
	 * Test loading of a request without parameters
	 */
	public void testRequestWithoutParameters()
	{
	//	1. Load test message
	//
		// Setup an asynchronous event handler.
		Timer timer = new Timer()
		{
			public void run()
			{
				// Call remote init service
				StatelessInitServiceAsync remoteService = (StatelessInitServiceAsync) GWT.create(StatelessInitService.class);
				remoteService.loadTestMessage(new AsyncCallback<Message>()
				{
					public void onFailure(Throwable caught)
					{
						fail(caught.toString());
						finishTest();
					}

					public void onSuccess(Message result)
					{
						simpleRequest();
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
	 * Test request with map parameters
	 */
	public void testRequestWithMapParameters()
	{
	//	1. Load test user
	//
		// Setup an asynchronous event handler.
		Timer timer = new Timer()
		{
			public void run()
			{
				// Call remote init service
				StatelessInitServiceAsync remoteService = (StatelessInitServiceAsync) GWT.create(StatelessInitService.class);
				remoteService.loadTestUser(new AsyncCallback<User>()
				{
					public void onFailure(Throwable caught)
					{
						fail(caught.toString());
						finishTest();
					}

					public void onSuccess(User result)
					{
						requestWithParameters(result);
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
	
	//-------------------------------------------------------------------------
	//
	// Internal methods
	//
	//-------------------------------------------------------------------------
	/**
	 * Test load simple association
	 */
	protected void simpleRequest()
	{
		// Setup an asynchronous event handler.
		Timer timer = new Timer()
		{
			public void run()
			{
				// Call remote loading service
				RequestServiceAsync<User> remoteService = (RequestServiceAsync<User>) GWT.create(RequestService.class);
				remoteService.executeRequest("from User", (List<IGwtSerializableParameter>) null, new AsyncCallback<List<User>>()
				{
					public void onFailure(Throwable caught)
					{
						fail(caught.toString());

						// tell the test system the test is now done
						finishTest();
					}

					public void onSuccess(List<User> result)
					{
						assertNotNull(result);
						assertTrue(result.size() > 0);
						
						// tell the test system the test is now done
						finishTest();
					}
			
				});

			}
		};

		// Schedule the event and return control to the test system.
		timer.schedule(100);
	}
	
	/**
	 * Test load list association
	 */
	protected void requestWithParameters(final User user)
	{
		// Setup an asynchronous event handler.
		Timer timer = new Timer()
		{
			public void run()
			{
				// Call remote loading service
				RequestServiceAsync<User> remoteService = (RequestServiceAsync<User>) GWT.create(RequestService.class);

				// Parameters
				Map<String, IGwtSerializableParameter> parameters = new HashMap<String, IGwtSerializableParameter>();
				parameters.put("login", new StringParameter(user.getLogin()));
				
				remoteService.executeRequest("from User u where u.login = :login", parameters, new AsyncCallback<List<User>>()
				{
					public void onFailure(Throwable caught)
					{
						fail(caught.toString());

						// tell the test system the test is now done
						finishTest();
					}

					public void onSuccess(List<User> result)
					{
						assertNotNull(result);
						assertFalse(result.isEmpty());
						assertEquals(1,result.size());
						
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
