/**
 * 
 */
package net.sf.gilead.examples.rpc;

import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author Vincent Legendre
 * All data client need to load a games
 */
public class ModelInit implements IsSerializable
{
  static final long serialVersionUID = 203;


  private Set<String> m_connectedPlayer = null;


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



}
