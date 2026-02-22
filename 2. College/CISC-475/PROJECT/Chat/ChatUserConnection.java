//package com.warthog.domain.chat;

import java.io.*;
import java.net.*;
import java.util.*;

/** 
  A ChatUserConnection object represents a single user connection.
  This also encapsulates such information as username, bandname & 
  i/o streams.

  @author Team Warthog
  @version 1.0
*/
public class ChatUserConnection extends Thread
{

  /**
    Initializes the ChatUserConnection object
    @param s the socket associated with the current user
    @param u the list of all users
  */
  public ChatUserConnection(Socket s, ChatServerUsers u)
  {
    try 
    {
      sock = s;
      input = new ObjectInputStream(sock.getInputStream()); 
      output = new ObjectOutputStream(sock.getOutputStream());
      userList = u;

      //get band name and user name from client 
      bandname = (String)input.readObject();
      username = (String)input.readObject();
    }
    catch(Exception e)
    { }
  }

  /**
    Runs the CharUserConnection thread which listens for incoming data
  */
  public void run()
  {
    ClienttoServer ctos;
    ServertoClient stoc;

    try 
    {
      // let other users know that this user has logged into the room 
      stoc = new ServertoClient(this.username, null, 
        ServertoClient.LOGON);  
      this.userList.sendMessage(this.bandname, stoc); 

      // sends client the list of names in the room
      getUsersInRoom();
      
      while(true)
      {
        ctos = (ClienttoServer)input.readObject();
        if(ctos.isMessage())
        {
	  stoc = new ServertoClient(this.username, ctos.getMessage(), 
            ServertoClient.MESSAGE);
	  this.userList.sendMessage(this.bandname, stoc); 
        }
	else if(ctos.isLogoff())
        {
	  //log this user out
	  stoc = new ServertoClient( this.username, new String(""), 
            ServertoClient.LOGOFF );
	  this.userList.sendMessage(this.bandname, stoc ); 
	  this.userList.removeUser( this.bandname, this.username );
	  break;
	} 
      }
      sock.close();
    }
    catch(EOFException e)
    { 
      stoc = new ServertoClient( this.username, new String(""), 
        ServertoClient.LOGOFF );
      this.userList.sendMessage(this.bandname,  stoc); 
      this.userList.removeUser( this.bandname, this.username );
    } 
    catch(Exception e)
    {
      stoc = new ServertoClient( this.username, new String(""), 
        ServertoClient.LOGOFF );
      this.userList.sendMessage(this.bandname, stoc); 
      this.userList.removeUser( this.bandname, this.username ); 
    }
  }

  /**
    Sends the list of user names to the output stream
  */
  public void getUsersInRoom()
  {
    try
    {
      Hashtable room = this.userList.usersInRoom(bandname);
      Set roomUsersSet = room.keySet();
      String[] roomUsers = 
        (String[])roomUsersSet.toArray(new String[0]);

      for(int a = 0; a < roomUsers.length; a++)
        output.writeObject(roomUsers[a]);

      output.writeObject("");
    }
    catch(IOException e)
    { }
  }

  /**
    Sends a ServertoClient object to the output stream
  */
  public void sendToClient(ServertoClient stoc) throws IOException
  {
    //don't send user his or her own login or logout
    if( stoc.isMessage() || stoc.isLogoff() || 
      !stoc.getUserName().equals(this.username))
      output.writeObject( stoc );
  }

  /**
    Sends a Boolean object to the output stream
  */
  public void sendToClient(Boolean b) throws IOException
  { output.writeObject( b ); }

  /**
    Returns the username
    @return the username
  */
  public String getUser()
  { return this.username; }

  /**
    Returns the bandname
    @return the bandname
  */ 
  public String getBand()
  { return this.bandname; }

  /**
    Returns the input stream
    @return the input stream
  */  
  public ObjectInputStream getInputStream()
  { return this.input; }

  /**
    Returns the output stream
    @return the output stream
  */ 
  public ObjectOutputStream getOutputStream()
  { return this.output; } 

  private ChatServerUsers userList;
  private String bandname;
  private String username; 
  private ObjectInputStream input; 
  private ObjectOutputStream output;
  private Socket sock;
}
