// Chris Heydt
// web site layout class

package com.warthog.domain.core;

import java.io.*;
import java.util.*;
import java.sql.*;
import com.warthog.services.*;

public class WebSiteLayout {
   public WebSiteLayout() {}
   public void setUrl( String url ) {
      this.url = url;
   }
   public String getUrl() {
      return this.url;
   }
   public void setBackgroundColor( String color ) { 
      this.backgroundColor = color; 
   }
   public String getBackgroundColor() { 
      return this.backgroundColor; 
   }
   public void setLayoutType( String type ) { 
      this.layoutType = type; 
   }
   public String getLayoutType() { 
      return this.layoutType; 
   }

   public void setMessageBoard(boolean state){
      this.hasMessageBoard = state;
   }

   public boolean hasMessageBoard() { 
      return this.hasMessageBoard; 
   }

   public void setChatRoom(boolean state){
      this.hasChatRoom = state;
   }

   public boolean hasChatRoom() { 
      return this.hasChatRoom; 
   }
   public void save() throws SQLException {
      DbServices database = new DbServices();
      if( !database.connect() ) 
         return;
      Connection conn = database.getConnection();
      try {
         Statement connStatement = conn.createStatement();
	 String update = "UPDATE WebSiteLayout SET layoutType='" + 
	    this.layoutType + "',backgroundColor='" + this.backgroundColor +
	    "',textColor='" + this.textColor + "',hasMessageBoard=" +
	    this.hasMessageBoard + ",hasChatRoom=" + this.hasChatRoom +
	    " WHERE url='" + this.url + "'";
         if( connStatement.executeUpdate( update ) == 0 ) { 
            String insert = "INSERT INTO WebSiteLayout(url, layoutType, " +
	       "backgroundColor, textColor, hasMessageBoard, hasChatRoom) " +
	       "values('" + this.url + "','" + this.layoutType + "','" + 
	       this.backgroundColor + "','" + this.textColor + "'," +
	       this.hasMessageBoard + "," + this.hasChatRoom + ")"; 
            int returnVal = connStatement.executeUpdate( insert );
         }
      } 
      catch( SQLException ex ) { throw ex; }
      finally{ database.releaseConnection(); }
   }

   private String url;
   // unique identifier for a particular type of layout 
   private String layoutType;
   private String backgroundColor;
   private String textColor;
   private boolean hasMessageBoard;
   private boolean hasChatRoom;
}
