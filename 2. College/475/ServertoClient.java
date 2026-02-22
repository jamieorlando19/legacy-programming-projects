package domain.chat;

import java.io.*;

/**
  An object which is sent from the Server to the Client

  @author Team Warthog
  @version 1.0
*/
public class ServertoClient implements Serializable
{
  /**
    Initializes the ServertoClient object
    @param u the username
    @param m the message to be sent
    @param a specifies the type of action (MESSAGE, LOGON, LOGOFF)
  */
  public ServertoClient(String u, String m, int a)
  {
    userName = u;
    message = m;
    action = a;
    if( action < 1 || action > 3 )
      action = 1;
  }

  /**
    Returns the username
    @return the username
  */ 
  public String getUserName()
  { return userName; }

  /**
    Returns the message
    @return the message
  */ 
  public String getMessage()
  { return message; }

  /**
    Returns true if this is a MESSAGE
    @return true if this is a MESSAGE
  */ 
  public boolean isMessage()
  { return action == MESSAGE; }

  /**
    Returns true if this is a LOGON
    @return true if this is a LOGON
  */
  public boolean isLogon()
  { return action == LOGON; }

  /**
    Returns true if this is a LOGOFF
    @return true if this is a LOGOFF
  */ 
  public boolean isLogoff()
  { return action == LOGOFF; }

  /** MESSAGE constant */
  public static final int MESSAGE = 1;
  /** LOGON constant */
  public static final int LOGON = 2;
  /** LOGOFF constant */
  public static final int LOGOFF = 3;

  private int action;
  private String message; 
  private String userName;
}
