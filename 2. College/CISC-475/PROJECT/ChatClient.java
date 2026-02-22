//package com.warthog.domain.chat;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.*;
import java.lang.*;
import java.net.*;
import javax.swing.*;

/** 
  The ChatClient which is an applet.  In order for this to function,
  the ChatServer must be running.  The ChatClient will connect to a
  port on the Server as specified by the ChatServerIP file.  The
  ChatClient is the main GUI which the user will user to chat with
  other users logged in.

  @author Team Warthog
  @version 1.0
*/
public class ChatClient extends JApplet
{
  /** Initializes the applet by getting the bandname from the site */
  public void init()
  {
    bandName = getParameter("BandName");
  }

  /** Starts the applet */
  public void start()
  {
    contentPane = getContentPane();
    setupGUI();
  }

  /** Sets up the GUI interface for the applet */
  public void setupGUI()
  {
    userNamePrompt = new JLabel("Please enter your desired " + 
      "user name:");

    userNameTextField = new JTextField();
    userNameTextField.setDocument(new JTextUtil(15));    

    userNameSubmitButton = new JButton("Submit");
    userNameSubmitButton.addActionListener(
      new ActionListener()
      { 
        public void actionPerformed(ActionEvent e) 
        { handleSubmit(); } 
      } );

    errorMessage1 = new JLabel();
    errorMessage2 = new JLabel();
    chatLabel1 = new JLabel("Thank you for chatting!");
    chatLabel2 = new JLabel("Keep this window open to chat.");

    userNamePrompt.setBounds(15, 5, 240, 30);
    userNameTextField.setBounds(5, 40, 150, 22);
    userNameSubmitButton.setBounds(165, 37, 80, 28);
    errorMessage1.setBounds(5, 63, 200, 20);
    errorMessage2.setBounds(5, 77, 200, 20);
    chatLabel1.setBounds(5, 63, 200, 20);
    chatLabel2.setBounds(5, 77, 200, 20);

    userNamePanel = new JPanel();
    userNamePanel.setLayout(null);
    userNamePanel.add(userNamePrompt);
    userNamePanel.add(userNameTextField);
    userNamePanel.add(userNameSubmitButton);
    userNamePanel.add(errorMessage1);
    userNamePanel.add(errorMessage2);

    chatPanel = new JPanel();
    chatPanel.setLayout(null);
    chatPanel.add(chatLabel1);
    chatPanel.add(chatLabel2);

    viewLogin(true);
  }

  /**Initializes the ChatWindow Frame */
  public void initializeChat()
  {
    chatRoom = new ChatWindow(bandName, model, in, out, this);
    chatRoom.show();
    viewLogin(false);
  }

  /**Logic for the Submit button */
  public void handleSubmit()
  {
    //Clear the error messages
    errorMessage1.setText("");
    errorMessage2.setText("");

    //Get data from the username field
    userName = userNameTextField.getText().trim();
    model = new DefaultListModel();

    if( userName.compareTo("") != 0 )
    {
      try
      {
        //Make a connection to the server
        s = new Socket(ChatServerIP.SITE, ChatServerIP.PORT);
        out = new ObjectOutputStream(s.getOutputStream());
        in = new ObjectInputStream(s.getInputStream());

        //Send the server the username and bandname
        out.writeObject(bandName);
        out.writeObject(userName);

        //Server confirms validity of username
        Boolean valid = (Boolean)in.readObject();

        userNameTextField.setText("");

        //Set up list of usernames in chatroom
        if(valid.booleanValue())
        {
	  int x = 0, y;
          String currentuser;
	  String hold;
          currentuser = (String)in.readObject();

          while( currentuser.compareTo("") != 0 )
          {
	    if(x > 0)
            {
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
            }
            else model.addElement(currentuser);  
            x++;
            currentuser = (String)in.readObject();
          }

          initializeChat();
        }
        else
        {
          errorMessage1.setText("USER NAME IN USE!");
          errorMessage2.setText("Please choose another name.");
          s.close();
	}
      }
      catch (IOException exc)
      {
        errorMessage1.setText("Chat-Server isn't running!");
        errorMessage2.setText("Please try later.");
      }
      catch (ClassNotFoundException exc)
      {
        errorMessage1.setText("Unknown error!");
        errorMessage2.setText("Please try later.");
      }
    }
  }

  /**
    Choose between viewing the login screen or the chat screen
    @param b true if you wish to view the login screen
  */
  public void viewLogin(boolean b)
  {
    contentPane.removeAll();
    if(b)
    {
      contentPane.add(userNamePanel);
      userNamePanel.setVisible(true);
      chatPanel.setVisible(false);
    }
    else
    {
      contentPane.add(chatPanel);
      userNamePanel.setVisible(false);
      chatPanel.setVisible(true);
    }
  }

  private Container contentPane;

  //Data
  private String bandName;
  private String userName;

  //Connection
  private Socket s;
  private ObjectOutputStream out;
  private ObjectInputStream in;

  //Login View
  private JPanel userNamePanel;
  private JTextField userNameTextField;
  private JLabel userNamePrompt;
  private JLabel errorMessage1;
  private JLabel errorMessage2;
  private JButton userNameSubmitButton;

  //Chat View
  private JPanel chatPanel;
  private JLabel chatLabel1;
  private JLabel chatLabel2;


  //Chat Window Attributes
  private ChatWindow chatRoom;
  private DefaultListModel model; 
}
