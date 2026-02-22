package com.warthog.domain.core;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.warthog.services.*;

/**
*  WebSiteLayout represents the layout of the band's site
*
*  @author Chris Heydt & Jamie Orlando
*  @version 1.0
*/
public class WebSiteLayout {

   /**
   *  Initializes the WebSiteLayout
   */
   public WebSiteLayout() {}

   /**
   *  Sets the URL
   *  @param url the URL
   */
   public void setUrl( String url ) {
      this.url = url;
   }

   /**
   *  Gets the URL
   *  @return the URL
   */
   public String getUrl() {
      return this.url;
   }

   /**
   *  Sets the background color
   *  @param color the background color
   */
   public void setBackgroundColor( String color ) { 
      this.backgroundColor = color; 
   }

   /**
   *  Gets the background color
   *  @return the background color
   */
   public String getBackgroundColor() { 
      return this.backgroundColor; 
   }

   /**
   *  Sets the text color
   *  @param color the text color
   */
   public void setTextColor( String color ) {
      this.textColor = color;
   }

   /**
   *  Gets the text color
   *  @return the text color
   */
   public String getTextColor() {
      return this.textColor;
   }

   /**
   *  Sets the layout type
   *  @param type the layout type
   */
   public void setLayoutType( String type ) { 
      this.layoutType = type; 
   }

   /**
   *  Gets the layout type
   *  @return the layout type
   */
   public String getLayoutType() { 
      return this.layoutType; 
   }

   /**
   *  Sets the flag to include a chatroom
   *  @param b the flag
   */
   public void setHasChatRoom(boolean b) {
      hasChatRoom = b;
   }

   /**
   *  Gets the chatroom flag
   *  @return the chatroom flag
   */
   public boolean getHasChatRoom() { 
      return this.hasChatRoom; 
   }

   /**
   *  Saves the attributes of this to the database
   *  @return true if successful
   */
   public boolean save() throws SQLException {
      DbServices database = new DbServices();
      if( !database.connect() ) 
         return false;
      Connection conn = database.getConnection();
      try {
         Statement connStatement = conn.createStatement();
         String strChatRoom = new String();
         String strMessageBoard = new String();
         if( hasChatRoom )
           strChatRoom = "1";
         else
           strChatRoom = "0";
         if( hasMessageBoard )
           strMessageBoard = "1";
         else
           strMessageBoard = "0";
	 String update = "UPDATE WebSiteLayout SET layoutType='" + 
	    this.layoutType + "',backgroundColor='" + this.backgroundColor +
	    "',textColor='" + this.textColor + "',hasMessageBoard=" +
	    strMessageBoard + ",hasChatRoom=" + strChatRoom +
	    " WHERE url='" + this.url + "'";
         if( connStatement.executeUpdate( update ) == 0 ) { 
            String insert = "INSERT INTO WebSiteLayout(url, layoutType, " +
	       "backgroundColor, textColor, hasMessageBoard, hasChatRoom) " +
	       "values('" + this.url + "','" + this.layoutType + "','" + 
	       this.backgroundColor + "','" + this.textColor + "'," +
	       strMessageBoard + "," + strChatRoom + ")"; 
            if( connStatement.executeUpdate( insert ) == 0 )
               return false;
         }
      } 
      catch( SQLException ex ) { throw ex; }
      finally{ database.releaseConnection(); }
      return true;
   }

   /**
   *  Retrieves the corresponding row in the database based on the
   *  URL provided
   *  @param url the URL
   *  @return true if succesful
   */
   public boolean select(String url) throws SQLException {
      DbServices database = new DbServices();
      if( !database.connect() )
         return false;
      Connection conn = database.getConnection();
         
      try {
         PreparedStatement getRecords = conn.prepareStatement(
            "SELECT * FROM WebSiteLayout WHERE url = ?");
         getRecords.setString(1, url);
         ResultSet results = getRecords.executeQuery();
            
         if(results.next())
         {
           setUrl(results.getString(1));
           setLayoutType(results.getString(2));
           setBackgroundColor(results.getString(3));
           setTextColor(results.getString(4));
           setHasChatRoom(results.getBoolean(6));
           return true;
         }   
         else
           return false;
      }
      catch( SQLException ex ) { throw ex; }
      finally { database.releaseConnection(); }
   }


   private String url;
   // unique identifier for a particular type of layout 
   private String layoutType;
   private String backgroundColor;
   private String textColor;
   private boolean hasMessageBoard;
   private boolean hasChatRoom;

   public static String LAYOUT1_DEFAULTBGCOLOR = "#000000";
   public static String LAYOUT2_DEFAULTBGCOLOR = "#ffffff";
   public static String LAYOUT3_DEFAULTBGCOLOR = "#a96e34";

   public static String LAYOUT1_DEFAULTTEXTCOLOR = "#ffffff";
   public static String LAYOUT2_DEFAULTTEXTCOLOR = "#000000";
   public static String LAYOUT3_DEFAULTTEXTCOLOR = "#000000";

}
