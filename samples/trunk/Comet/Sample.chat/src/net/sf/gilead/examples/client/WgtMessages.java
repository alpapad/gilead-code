/**
 * 
 */
package net.sf.gilead.examples.client;

import java.util.List;

import net.sf.gilead.examples.rpc.ChatMessage;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Vincent Legendre
 *
 */
public class WgtMessages extends Composite
{
  private VerticalPanel m_panel = new VerticalPanel();

  /**
   * 
   */
  public WgtMessages()
  {
    ScrollPanel panel = new ScrollPanel();
    panel.add( m_panel );
    initWidget( panel );
  }

  public void addMessages(List<ChatMessage> p_messages)
  {
    for( ChatMessage msg : p_messages )
    {
      m_panel.add( new HTML( "<b>" + msg.getFromLogin() + " : </b>" + msg.getText() ) );
    }
  }

  private void addError(String p_message)
  {
    m_panel.add( new HTML( "<b> Error : </b>" + p_message ) );
  }

  public void addError(Throwable p_caught)
  {
    if( p_caught.getMessage() == null || p_caught.getMessage().trim().length() == 0 )
    {
      addError( p_caught.toString() );
    }
    else
    {
      addError( p_caught.getMessage() );
    }
  }
}
