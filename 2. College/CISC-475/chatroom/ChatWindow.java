//package com.domain.chat;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/** 
  The ChatWindow is a pop-up JFrame which is the GUI for the actual
  chatroom.  The ChatWindow interacts with a ChatClientListener thread
  to exchange data.

  @author Team Warthog
  @version 1.0
*/
public class ChatWindow extends JFrame
{

  /**
    Initializes the ChatWindow object
    @param b the bandname
    @param d the list of all users
    @param i the input stream
    @param o the output stream
    @param c the applet associated with the ChatWindow
  */
  public ChatWindow(String b, DefaultListModel d, ObjectInputStream i, 
    ObjectOutputStream o, ChatClient c)
  {
    bandname = b;
    model = d;
    in = i;
    out = o;
    client = c;
    
    setTitle(b + " Chat");
    setSize(625, 565);
    setResizable(false);
    
    ChatWindowPanel panel = new ChatWindowPanel();
    Container contentPane = getContentPane();
    contentPane.add(panel);
  }

  //The GUI JPanel inserted into the ChatWindow JFrame
  class ChatWindowPanel extends JPanel
  {
    public ChatWindowPanel()
    {
      //WindowListener which logs the user out if the window is closed
      addWindowListener(
        new WindowAdapter()
        {
          public void windowClosing(WindowEvent w)
          { handleLogout(); }
        } );

      //Chat Room Scrollable Text Area
      setLayout(null);
      chatRoomTextArea = new JTextArea();
      chatRoomTextArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
      chatRoomTextArea.setLineWrap(true);
      chatRoomTextArea.setWrapStyleWord(true);
      chatRoomTextArea.setEditable(false);
      chatRoomScroll = new JScrollPane(
        chatRoomTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      chatRoomScroll.setBounds(5,5,470,460);

      //Chatroom User List
      userNameList = new JList(model);   
      userNameScroll = new JScrollPane(userNameList, 
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      userNameScroll.setBounds(480,5,115,400);

      //Chatroom Data Field
      chatRoomTextField = new JTextField();
      chatRoomTextField.setDocument(new JTextUtil(150));
      chatRoomTextField.setBounds(5,470,470,25);
      chatRoomTextField.addKeyListener(
        new KeyAdapter()
        {
          public void keyPressed(KeyEvent key)
          {
            if(key.getKeyCode() == KeyEvent.VK_ENTER)
            { handleSend(); }
          }
        } );

      //Chatroom Send Button
      chatRoomSendButton = new JButton("Send");
      chatRoomSendButton.addActionListener(
      new ActionListener()
      { 
        public void actionPerformed(ActionEvent e) 
        { handleSend();  } 
      });
      chatRoomSendButton.setBounds(480,470,115,25);

      //Chatroom Logout Button    
      chatRoomLogoutButton = new JButton("Logout");
      chatRoomLogoutButton.addActionListener(
        new ActionListener()
        { 
          public void actionPerformed(ActionEvent e) 
          {
            handleLogout();
            dispose(); 
          } 
        } );
      chatRoomLogoutButton.setBounds(480,415,115,25);

      add(chatRoomScroll);
      add(userNameScroll);
      add(chatRoomTextField);
      add(chatRoomSendButton);
      add(chatRoomLogoutButton);

      //Starts the ChatClientListener thread which listens for data
      fromServer = new ChatClientListener(in, chatRoomTextArea, model);
      fromServer.start();
    }
  }

  /**
    Handles the Send button
  */
  public void handleSend()
  {
    try
    {
      String userInput = chatRoomTextField.getText().trim();

      if(userInput.compareTo("") != 0)
      {
        out.writeObject(
          new ClienttoServer(userInput, ClienttoServer.MESSAGE));
        chatRoomTextField.setText("");
      }
    }
    catch( IOException ex )
    {
      chatRoomTextArea.append("\nServer not responding!  " + 
        "Please try later!\n");
      chatRoomTextArea.setCaretPosition(
        chatRoomTextArea.getText().length() );
      chatRoomTextField.setText("");
    }
  }

  /**
    Handles the Logout button
  */
  public void handleLogout()
  {
    try
    {
      client.viewLogin(true);
      model.clear();
      fromServer.killThread();
      out.writeObject(new ClienttoServer(null, ClienttoServer.LOGOFF));
    }
    catch (IOException exc)
    {
      chatRoomTextArea.append("\nServer not responding!  " +
      "Please try later!\n");
      chatRoomTextArea.setCaretPosition(
        chatRoomTextArea.getText().length());
    }
  }


  //Applet Data
  private ChatClient client;
  private String bandname;

  //Connection
  private ObjectInputStream in;
  private ObjectOutputStream out;
  private ChatClientListener fromServer;

  //Chatroom GUI
  private JTextArea chatRoomTextArea;
  private JScrollPane chatRoomScroll, userNameScroll;
  private JTextArea chatRoomUserList;
  private JTextField chatRoomTextField;
  private JButton chatRoomSendButton;
  private JButton chatRoomLogoutButton;
  private DefaultListModel model;
  private JList userNameList;  
}