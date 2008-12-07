/**
 * 
 */
package net.sf.gilead.examples.client;

import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Vincent Legendre
 *
 */
public class WgtConnectedUsers extends Composite
{
  private VerticalPanel m_panel = new VerticalPanel();

  /**
   * 
   */
  public WgtConnectedUsers()
  {
    initWidget( m_panel );
  }

  public void resfresh(Set<String> p_users)
  {
    m_panel.clear();
    for( String user : p_users )
    {
      m_panel.add( new Label( user ) );
    }
  }

}
