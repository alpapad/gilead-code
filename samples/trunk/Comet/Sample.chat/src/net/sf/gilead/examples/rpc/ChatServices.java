/**
 * 
 */
package net.sf.gilead.examples.rpc;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author Vincent Legendre
 *
 */
public interface ChatServices extends RemoteService
{

  public static final String SERVICE_URI = "ChatServices";

  public static class Util
  {

    public static ChatServicesAsync getInstance()
    {
      ChatServicesAsync instance = (ChatServicesAsync)GWT.create( ChatServices.class );
      ServiceDefTarget target = (ServiceDefTarget)instance;
      target.setServiceEntryPoint( GWT.getModuleBaseURL() + SERVICE_URI );
      // AppMain.instance().startLoading();
      return instance;
    }
  }

  /**
   * 
   * @param p_message
   * @throws RpcFmpException
   */
  public void sendChatMessage(ChatMessage p_message) throws Exception;


  /**
   * Get all changes in an fmp model since p_currentVersion and send back all needed data
   * to update the model.
   * @param p_lastVersion
   * @return model change between p_lastVersion date and current date.
   * @throws RpcFmpException
   */
  public ModelUpdate getModelUpdate(Date p_lastUpdate) throws Exception;

  public ModelUpdate getModelUpdateComet(Date p_lastUpdate) throws Exception;

  public ModelInit getModelInit() throws Exception;


}
