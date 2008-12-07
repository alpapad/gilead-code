/**
 * 
 */
package net.sf.gilead.examples.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.gilead.annotations.Comet;
import net.sf.gilead.comet.CometRemoteService;
import net.sf.gilead.comet.PendingRequest;
import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.examples.rpc.ChatMessage;
import net.sf.gilead.examples.rpc.ChatServices;
import net.sf.gilead.examples.rpc.ModelInit;
import net.sf.gilead.examples.rpc.ModelUpdate;

import com.google.gwt.user.server.rpc.RPCRequest;

/**
 * @author Vincent Legendre
 *
 */
public class ChatServicesImpl extends CometRemoteService implements ChatServices
{
  public static final long serialVersionUID = 1;

  /**
   * Chat messages list aren't persist.
   * So they are temporary store in this list to serves clients.
   */
  private static List<ChatMessage> s_chatMessages = new ArrayList<ChatMessage>();



  /**
   * 
   */
  public ChatServicesImpl()
  {
    super();
  }

  /**
   * @param p_lazyManager
   */
  public ChatServicesImpl(PersistentBeanManager p_lazyManager)
  {
    // HibernateBeanManager.getInstance().setSessionFactory(
    // HibernateUtil.getSessionFactory() );
    // super( p_lazyManager );
  }

  private static Integer s_visitorIndex = 0;

  private String getRemoteUser()
  {
    if( getCurrentSession().getAttribute( "chatuser" ) == null )
    {
      synchronized( s_visitorIndex )
      {
        getCurrentSession().setAttribute( "chatuser", "CometVisitor" + s_visitorIndex );
        s_visitorIndex++;
      }
    }
    return (String)getCurrentSession().getAttribute( "chatuser" );
  }


  // ----------------------------
  // overloaded method
  // ----------------------------
  /* (non-Javadoc)
   * @see nc.kroc.fmp.server.HibernateCometService2#haveData(com.google.gwt.user.server.rpc.RPCRequest)
   */
  @Override
  protected boolean haveData(RPCRequest p_request)
  {
    if( p_request.getMethod().getName().equals( "getModelUpdateComet" ) )
    {
      return !getListChatMessages( (Date)p_request.getParameters()[0] ).isEmpty();
    }
    return false;
  }

  @Override
  protected void onCurrentRequestAbort(PendingRequest p_abortedRequest)
  {
    if( p_abortedRequest.getRpcRequest().getMethod().getName().equals( "getModelUpdateComet" ) )
    {
      disconnectUser( getRemoteUser() );
    }
  }

  private static Map<String, Date> s_connectedUser = new HashMap<String, Date>();

  /**
   * 
   * @param p_gameId
   * @return a set of user string connected to a given game. 
   */
  protected Set<String> getConnectedUser()
  {
    Set<String> connected = new HashSet<String>();
    synchronized( s_connectedUser )
    {
      // remove too old connected
      long tooOldTime = System.currentTimeMillis() - 120 * 1000; // 120 seconds
      for( Map.Entry<String, Date> entry : s_connectedUser.entrySet() )
      {
        if( entry.getValue().getTime() < tooOldTime )
        {
          connected.add( entry.getKey() );
        }
      }
      for( String user : connected )
      {
        s_connectedUser.remove( user );
      }
      // then copy users set
      connected = new HashSet<String>();
      for( String user : s_connectedUser.keySet() )
      {
        connected.add( user );
      }
    }
    // getServletContext().
    return connected;
  }

  protected void connectUser(String p_user)
  {
    synchronized( s_connectedUser )
    {
      // update last connection date for current user
      assert p_user != null;
      if( s_connectedUser.get( p_user ) != null )
      {
        s_connectedUser.get( p_user ).setTime( System.currentTimeMillis() );
      }
      else
      {
        // or simply add it to connected user
        s_connectedUser.put( p_user, new Date( System.currentTimeMillis() ) );
        broadCastUpdate();
      }
    }
  }

  /**
   * remove a user from connected user to a given game
   * @param p_gameId
   * @param user
   */
  protected void disconnectUser(String user)
  {
    synchronized( s_connectedUser )
    {
      s_connectedUser.remove( user );
    }
    broadCastUpdate();
  }

  protected List<ChatMessage> getListChatMessages(Date p_lastVersion)
  {
    List<ChatMessage> messages = new ArrayList<ChatMessage>();
    List<ChatMessage> tooOldMessages = new ArrayList<ChatMessage>();
    long tooOldTime = System.currentTimeMillis() - (100 * 1000); // 100 seconds
    // before
    long newTime = p_lastVersion.getTime();
    synchronized( s_chatMessages )
    {
      for( ChatMessage message : s_chatMessages )
      {
        if( message.getDate().getTime() > newTime )
        {
          messages.add( message );
        }
        if( message.getDate().getTime() < tooOldTime )
        {
          tooOldMessages.add( message );
        }
      }
      // remove too olad messages
      s_chatMessages.removeAll( tooOldMessages );
    }
    return messages;
  }

  protected void broadCastUpdate()
  {
    // comet stuff
    // //////////////
    synchronized( getAllPendingRequest() )
    {
      System.out.println( "broadCastUpdate() while " + getAllPendingRequest().size()
          + " requests are pending" );
      for( PendingRequest request : getAllPendingRequest() )
      {
        if( (request != null)
            && (request.getRpcRequest().getMethod().getName().equals( "getModelUpdateComet" )) )
        {
          try
          {
            invokeService( request );
          } catch( Throwable e )
          {
            // print error but continue broad casting message
            e.printStackTrace();
          }

        }
      }
    }
  }

  // ----------------------------
  // gwt services implementations
  // ----------------------------

  /* (non-Javadoc)
   * @see net.sf.gilead.examples.rpc.ChatServices#getModelInit()
   */
  public ModelInit getModelInit() throws Exception
  {
    ModelInit model = new ModelInit();
    connectUser( getRemoteUser() );
    model.setConnectedPlayer( getConnectedUser() );
    return model;
  }

  /* (non-Javadoc)
   * @see net.sf.gilead.examples.rpc.ChatServices#getModelUpdate(long, java.util.Date)
   */
  public ModelUpdate getModelUpdate(Date p_lastUpdate) throws Exception
  {
    ModelUpdate updates = new ModelUpdate();
    updates.setUpdate( new Date( System.currentTimeMillis() ) );
    updates.setChatMessages( getListChatMessages( p_lastUpdate ) );
    connectUser( getRemoteUser() );
    updates.setConnectedPlayer( getConnectedUser() );
    return updates;
  }

  @Comet(timeout = 50 * 1000)
  public ModelUpdate getModelUpdateComet(Date p_lastUpdate) throws Exception
  {
    return getModelUpdate( p_lastUpdate );
  }

  /* (non-Javadoc)
   * @see net.sf.gilead.examples.rpc.ChatServices#sendChatMessage(net.sf.gilead.examples.rpc.ChatMessage)
   */
  public void sendChatMessage(ChatMessage p_message) throws Exception
  {
    p_message.setFromLogin( getRemoteUser() );
    p_message.setDate( new Date( System.currentTimeMillis() ) );
    synchronized( s_chatMessages )
    {
      s_chatMessages.add( p_message );
    }
    broadCastUpdate();
  }

}
