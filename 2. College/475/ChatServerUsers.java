package domain.chat;

import java.util.*; 

/** 
  A ChatServerUsers object represents all the chatrooms with their 
  respective users in each chatroom.

  @author Team Warthog
  @version 1.0
*/
public class ChatServerUsers 
{ 
  /**
    Initializes the ChatServerUsers object
  */
  public ChatServerUsers() 
  { users = new Hashtable(); } 
 
  /**
    Adds a new user
    @param bandname the name of the band's chatroom
    @param username the name of the user
    @param chatlistener the connection object associated with the user
    @return true if the user doesn't exist in the band's chatroom; 
            false if they already exist
  */
  public boolean addUser(String bandname, String username, 
                         ChatUserConnection chatlistener) 
  { 
    Hashtable newHash = new Hashtable();
 
    //If band and user doesn't exist
    if(!users.containsKey(bandname)) 
    { 
      newHash.put(username, chatlistener); 
      users.put(bandname, newHash); 
      return true; 
    } 
    else 
    { 
      newHash = usersInRoom(bandname); 
 
      //If band exists and user doesn't exist 
      if(!newHash.containsKey(username)) 
      { 
        newHash.put(username, chatlistener); 
        users.remove(bandname); 
        users.put(bandname, newHash); 
        return true; 
      } 
      //If band exists and user exists 
      else 
        return false; 
    } 
  } 

  /**
    Removes a new user
    @param bandname the name of the band's chatroom
    @param username the name of the user
    @return true if the user is removed successfully; 
            false if the user or band doesn't exist
  */ 
  public boolean removeUser(String bandname, String username) 
  {
    //If band exists 
    if(users.containsKey(bandname)) 
    { 
      Hashtable newHash = usersInRoom(bandname); 
   
      //If user exists 
      if(newHash.containsKey(username)) 
      { 
        //If room has more than one person in it 
        if(newHash.size() > 1) 
        { 
          newHash.remove(username); 
          users.remove(bandname); 
          users.put(bandname, newHash); 
          return true; 
        } 
        //If has one person in it 
        else 
        { 
          users.remove(bandname); 
          return true; 
        } 
      } 
      //If user doesn't exist 
      else 
        return false; 
    } 
    //If band doesn't exist 
    else 
      return false; 
  } 
 
  /**
    Sends a message to all the users in a specific band's chatroom
    @param bandname the name of the band's chatroom
    @param message the message to send to the users in the chatroom
  */ 
  public void sendMessage(String bandname, ServertoClient message)
  {
    try
    { 
      Hashtable bandhash = usersInRoom(bandname);
      Collection bandcol = bandhash.values();
      ChatUserConnection[] connectionList = 
     (ChatUserConnection[])bandcol.toArray(new ChatUserConnection[0]);

      for(int a = 0; a < connectionList.length; a++)
        connectionList[a].sendToClient(message);
    }
    catch(Exception e) 
    { }
  }

  /**
    Returns a Hashtable containing all the users in a 
    particular chatroom
    @param bandname the name of the band's chatroom
    @return Hashtable object containing the users in the chatroom
  */ 
  public Hashtable usersInRoom(String bandname) 
  { return (Hashtable)(users.get(bandname)); } 

  //Data-structure which stores the ChatServerUsers information
  private Hashtable users; 
}
