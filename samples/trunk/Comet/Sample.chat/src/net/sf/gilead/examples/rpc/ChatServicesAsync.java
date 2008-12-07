/**
 * 
 */
package net.sf.gilead.examples.rpc;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Vincent Legendre
 *
 */
public interface ChatServicesAsync
{
  /**
   * 
   * @param p_message
   * @throws RpcFmpException
   */
  public void sendChatMessage(ChatMessage p_message, AsyncCallback<Void> callback);


  /**
   * Get all changes in an fmp model since p_currentVersion and send back all needed data
   * to update the model.
   * @param p_lastVersion
   * @return model change between p_lastVersion date and current date.
   * @throws RpcFmpException
   */
  public void getModelUpdate(Date p_lastUpdate, AsyncCallback<ModelUpdate> callback);

  public void getModelUpdateComet(Date p_lastUpdate, AsyncCallback<ModelUpdate> callback);

  public void getModelInit(AsyncCallback<ModelInit> callback);

}
