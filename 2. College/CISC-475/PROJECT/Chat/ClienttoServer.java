//package com.warthog.domain.chat;

import java.io.*;

/**
  An object which is sent from the Client to the Server
  @author Team Warthog
  @version 1.0
*/
public class ClienttoServer implements Serializable
{
  /**
    Initializes the ClienttoServer object
    @param m the message to be sent
    @param a specifies the type of action (MESSAGE, LOGOFF)
  */
  public ClienttoServer(String m, int a)
  {
    message = m;
    action = a;
    if( action < 1 || action > 2 )
      action = 1;
  }

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
    Returns true if this is a LOGOFF
    @return true if this is a LOGOFF
  */ 
  public boolean isLogoff()
  { return action == LOGOFF; }

   /** MESSAGE constant */
  public static final int MESSAGE = 1;
   /** LOGOFF constant */
  public static final int LOGOFF = 2;

  private String message;
  private int action;
}
