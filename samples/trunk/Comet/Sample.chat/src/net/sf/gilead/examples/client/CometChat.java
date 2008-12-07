/**
 * 
 */
package net.sf.gilead.examples.client;

import java.util.Date;

import net.sf.gilead.examples.rpc.ChatMessage;
import net.sf.gilead.examples.rpc.ChatServices;
import net.sf.gilead.examples.rpc.ModelInit;
import net.sf.gilead.examples.rpc.ModelUpdate;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincent Legendre
 *
 */
public class CometChat implements EntryPoint, KeyboardListener, ClickListener
{
  // UI
  private WgtConnectedUsers m_wgtUsers = new WgtConnectedUsers();
  private WgtMessages m_wgtMessages = new WgtMessages();
  private ScrollPanel m_scrollPanel = new ScrollPanel();
  private TextBox m_wgtTextInput = new TextBox();
  private Button m_btnOk = new Button( "OK" );

  // model
  private Date m_lastUpdate = new Date( 0 );

  public void onModuleLoad()
  {
    DockPanel panel = new DockPanel();
    panel.add( m_wgtUsers, DockPanel.WEST );
    m_scrollPanel.setPixelSize( 500, 400 );
    m_scrollPanel.add( m_wgtMessages );
    panel.add( m_scrollPanel, DockPanel.CENTER );
    m_wgtMessages.setSize( "100%", "100%" );
    m_wgtTextInput.addKeyboardListener( this );
    m_wgtTextInput.setFocus( true );
    m_btnOk.addClickListener( this );
    HorizontalPanel hpanel = new HorizontalPanel();
    hpanel.add( m_wgtTextInput );
    hpanel.add( m_btnOk );
    hpanel.setCellWidth( m_wgtTextInput, "100%" );
    panel.add( hpanel, DockPanel.SOUTH );
    RootPanel.get().add( panel );
    ChatServices.Util.getInstance().getModelInit( m_callbackInit );
  }


  private AsyncCallback<ModelInit> m_callbackInit = new AsyncCallback<ModelInit>()
  {

    /* (non-Javadoc)
     * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
     */
    public void onFailure(Throwable p_arg0)
    {
      m_updateTimer.schedule( 4000 );
      m_wgtMessages.addError( p_arg0 );
    }

    /* (non-Javadoc)
     * @see com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
     */
    public void onSuccess(ModelInit p_arg0)
    {
      m_wgtUsers.resfresh( p_arg0.getConnectedPlayer() );
      m_updateTimer.schedule( 10 );
    }
  };


  private AsyncCallback<ModelUpdate> m_callbackUpdate = new AsyncCallback<ModelUpdate>()
  {

    /* (non-Javadoc)
     * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
     */
    public void onFailure(Throwable p_arg0)
    {
      m_updateTimer.schedule( 4000 );
      m_wgtMessages.addError( p_arg0 );
    }

    /* (non-Javadoc)
     * @see com.google.gwt.user.client.rpc.AsyncCallback#onSuccess(java.lang.Object)
     */
    public void onSuccess(ModelUpdate p_arg0)
    {
      m_lastUpdate = p_arg0.getUpdate();
      m_wgtUsers.resfresh( p_arg0.getConnectedPlayer() );
      m_wgtMessages.addMessages( p_arg0.getChatMessages() );
      m_scrollPanel.scrollToBottom();
      m_updateTimer.schedule( 10 );
    }
  };

  /**
   * Create a new timer that calls Window.alert().
   */
  private Timer m_updateTimer = new Timer()
  {
    public void run()
    {
      cancel();
      ChatServices.Util.getInstance().getModelUpdateComet( m_lastUpdate, m_callbackUpdate );
    }
  };

  protected void sendMessage()
  {
    if( m_wgtTextInput.getText().trim().length() == 0 || !m_wgtTextInput.isEnabled() )
    {
      return;
    }
    ChatMessage message = new ChatMessage();
    message.setText( m_wgtTextInput.getText() );
    ChatServices.Util.getInstance().sendChatMessage( message, new AsyncCallback<Void>()
    {
      public void onFailure(Throwable p_caught)
      {
        m_wgtTextInput.setEnabled( true );
        m_wgtTextInput.setFocus( true );
        m_wgtMessages.addError( p_caught );
      }

      public void onSuccess(Void p_void)
      {
        m_wgtTextInput.setEnabled( true );
        m_wgtTextInput.setText( "" );
        m_wgtTextInput.setFocus( true );
      }
    } );

    m_wgtTextInput.setEnabled( false );
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.ClickListener#onClick(com.google.gwt.user.client.ui.Widget)
   */
  public void onClick(Widget p_sender)
  {
    if( p_sender == m_btnOk )
    {
      sendMessage();
    }
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.KeyboardListener#onKeyDown(com.google.gwt.user.client.ui.Widget, char, int)
   */
  public void onKeyDown(Widget p_sender, char p_arg1, int p_arg2)
  {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.KeyboardListener#onKeyPress(com.google.gwt.user.client.ui.Widget, char, int)
   */
  public void onKeyPress(Widget p_sender, char p_arg1, int p_arg2)
  {
    if( p_sender == m_wgtTextInput && p_arg1 == KEY_ENTER )
    {
      sendMessage();
    }

  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.KeyboardListener#onKeyUp(com.google.gwt.user.client.ui.Widget, char, int)
   */
  public void onKeyUp(Widget p_sender, char p_arg1, int p_arg2)
  {
    // TODO Auto-generated method stub

  }



}
