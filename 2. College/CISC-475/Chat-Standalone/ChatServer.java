package domain.chat;

import java.io.*;
import java.net.*;

/** 
  The main Chat Server.  Keep this file running on the server and it
  will open a ServerSocket on whatever port is specified in the
  ChatServerIP class.  ChatClient applets will connect to the
  Chat Server.

  @author Team Warthog
  @version 1.0
*/
public class ChatServer
{
  public static void main(String[] arguments)
  {
    try
    {
      ServerSocket serversocket = new ServerSocket(ChatServerIP.PORT);
      ChatServerUsers userList = new ChatServerUsers();

      System.out.println("Team Warthog ChatServer running on '" + 
        ChatServerIP.SITE + ":" + ChatServerIP.PORT + "'");

      while(true)
      {
        Socket incoming = serversocket.accept();
        ChatUserConnection newuser = 
          new ChatUserConnection( incoming, userList );
        String bandname = newuser.getBand();
        String username = newuser.getUser();

        Boolean success = 
          new Boolean(userList.addUser(bandname, username, newuser));
	newuser.sendToClient( success );
	if( success.booleanValue() ) {
	   newuser.start();
	}
      }
    }
    catch( BindException e )
    { System.out.println("ChatServer: BindException-- The server is " +
        "already running or the port is blocked!"); }
    catch( IOException e ) 
    { System.out.println("ChatServer: IOException-- " + e); }
  }
}
