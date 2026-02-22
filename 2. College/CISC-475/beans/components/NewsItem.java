package com.warthog.domain.components; 
 
import java.io.*; 
import java.sql.*; 
import java.util.*;
import com.warthog.services.*; 


/**
*  NewsItem represents a single news item
*
*  @author Chris Heydt & Jamie Orlando
*  @version 1.0
*/ 
public class NewsItem 
{

   /**
   *  Initializes the NewsItem
   */
   public NewsItem() {} 

   /**
   *  Sets the URL
   *  @param url the URL
   */
   public void setUrl( String url ) 
   {  
      this.url = url;  
   } 

   /**
   *  Gets the URL
   *  @return the URL
   */
   public String getUrl()  
   { 
      return this.url; 
   }

   /**
   *  Sets the date posted
   *  @param body the date posted
   */
   public void setDatePosted( String datePosted )  
   { 
      this.datePosted = datePosted; 
   }

   /**
   *  Gets the date posted
   *  @return the date posted
   */
   public String getDatePosted()  
   { 
      return this.datePosted; 
   }

   /**
   *  Sets the title
   *  @param title the title
   */
   public void setTitle( String title )  
   { 
      this.title = title; 
   }

   /**
   *  Gets the title
   *  @return the title
   */
   public String getTitle()  
   { 
      return this.title; 
   }

   /**
   *  Sets the body
   *  @param body the body
   */
   public void setBody( String body ) 
   { 
      this.body = body; 
   }

   /**
   *  Gets the body
   *  @return the body
   */
   public String getBody()  
   { 
      return this.body; 
   }

   /**
   *  Saves the attributes of this to the database
   *  @return true if successful
   */
   public boolean save() throws SQLException 
   {
      DbServices database = new DbServices();
      if( !database.connect() )
         return false;
      Connection conn = database.getConnection();
      try {
         Statement connStatement = conn.createStatement();
	 String insert = "INSERT INTO NewsItem(url, datePosted, title," +
	    "body) values('" + this.url + "','" + this.datePosted + 
	    "','" + this.title + "','" + this.body + "')";
	 if( connStatement.executeUpdate( insert ) == 0 )
	    return false;
      }
      catch( SQLException ex ) { throw ex; } 
      finally { database.releaseConnection(); }
      return true;  
   }

   /**
   *  Retrieves the corresponding row in the database based on the
   *  URL provided
   *  @param url the URL
   *  @return the full list of NewsItems according to the URL
   */
   public static ArrayList select(String url) throws SQLException 
   {
      ArrayList newsItemList = new ArrayList();
      DbServices database = new DbServices();
      if( !database.connect() )
         return newsItemList;
      Connection conn = database.getConnection();
      try {
         PreparedStatement getRecords = conn.prepareStatement(
	    "SELECT * FROM NewsItem WHERE url = ?" );
         getRecords.setString(1, url);
         ResultSet results = getRecords.executeQuery();
         while(results.next())
         {
 	   NewsItem newsItem = new NewsItem();
           newsItem.setUrl(results.getString(1));
           newsItem.setDatePosted(results.getString(2));
           newsItem.setTitle(results.getString(2));
           newsItem.setBody(results.getString(2));
 	   newsItemList.add(newsItem);
         }
      }
      catch( SQLException ex ) { throw ex; }
      finally { 
	 database.releaseConnection(); 
         return newsItemList;
      }
   }
  
   private String url; 
   private String datePosted; 
   private String title; 
   private String body; 
}
