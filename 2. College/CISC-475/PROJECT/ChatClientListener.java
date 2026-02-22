//package com.warthog.domain.chat;

import java.io.*;
import java.lang.*;
import javax.swing.*;

/** 
  The ChatClientListener is a thread which listens to the Server
  from the ChatWindow

  @author Team Warthog
  @version 1.0
*/
public class ChatClientListener extends Thread
{

  /**
    Initializes the ChatClientListener object
    @param i the input stream
    @param t the textarea in the chatroom
    @param d the list of users in the chatroom
  */
  public ChatClientListener(ObjectInputStream i, JTextArea t, 
    DefaultListModel d)
  { 
    in = i;
    chatRoomTextArea = t;
    model = d;
    stayAlive = true;
  }

  /**
    Runs the ChatClientListener thread which listens for incoming data
  */
  public void run()
  {
    try
    {
      int x, y;
      ServertoClient stoc;
      String hold;
      String currentuser;
      
      while(stayAlive)
      {
        //Read data from the server
        stoc = (ServertoClient)in.readObject();
        if(stoc != null)
        {
          /*message*/
          if(stoc.isMessage())
            chatRoomTextArea.append(stoc.getUserName() + ": " + 
              stoc.getMessage() + "\n");

          /*login*/
          else if(stoc.isLogon())
          {
            x = model.getSize();
	    currentuser = stoc.getUserName();

            for(y = 0; y < x; y++)
            {
              hold = (String)model.get(y);

              if(hold.compareToIgnoreCase(currentuser) >0)
              {
                model.add(y, currentuser);
                y = x+1;
              }
            }

            if(y == x) model.addElement(currentuser);
            chatRoomTextArea.append(currentuser + 
              " has entered the room\n");
          }

          /*logoff*/
          else if(stoc.isLogoff())
          {
            model.removeElement(stoc.getUserName());
            chatRoomTextArea.append(stoc.getUserName() + 
              " has left the room\n");
          }
 
          chatRoomTextArea.setCaretPosition(
            chatRoomTextArea.getText().length());
        }
      }
    }
    catch( IOException e )
    {
      chatRoomTextArea.append("\n\nChatServer has shut down!  " +
        "Please try later!\n");
      chatRoomTextArea.setCaretPosition(
        chatRoomTextArea.getText().length() );
    }
    catch( ClassNotFoundException e )
    {
      chatRoomTextArea.append("\nServer not responding!  " +
        "Please try later!\n");
      chatRoomTextArea.setCaretPosition(
        chatRoomTextArea.getText().length() );
    }
  }

  /**
    Kills the ChatClientListener thread
  */
  public void killThread()
  {
    stayAlive = false;
  }

  private ObjectInputStream in;
  private JTextArea chatRoomTextArea;
  private DefaultListModel model;
  private boolean stayAlive;
}
