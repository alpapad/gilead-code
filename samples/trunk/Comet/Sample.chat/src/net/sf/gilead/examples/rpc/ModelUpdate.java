/**
 * 
 */
package net.sf.gilead.examples.rpc;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Vincent Legendre
 * This class contain all informations send from server to client
 * to update client model.
 * - game events
 * - chat messages
 * - player connection list
 */
public class ModelUpdate implements IsSerializable
{
  static final long serialVersionUID = 202;

  private Date m_update = new Date();
  private List<ChatMessage> m_chatMessages = null;

  // connected players (or any other peoples)
  private Set<String> m_connectedPlayer = null;

  /**
   * @return the chatMessages
   */
  public List<ChatMessage> getChatMessages()
  {
    return m_chatMessages;
  }

  /**
   * @param p_chatMessages the chatMessages to set
   */
  public void setChatMessages(List<ChatMessage> p_chatMessages)
  {
    m_chatMessages = p_chatMessages;
  }

  /**
   * @return the connectedPlayer
   */
  public Set<String> getConnectedPlayer()
  {
    return m_connectedPlayer;
  }

  /**
   * @param p_connectedPlayer the connectedPlayer to set
   */
  public void setConnectedPlayer(Set<String> p_connectedPlayer)
  {
    m_connectedPlayer = p_connectedPlayer;
  }

  /**
   * @return the update
   */
  public Date getUpdate()
  {
    return m_update;
  }

  /**
   * @param p_update the update to set
   */
  public void setUpdate(Date p_update)
  {
    m_update = p_update;
  }



}
