package com.warthog.domain.core;

import java.io.*;
import java.sql.*;
import com.warthog.services.*;

/**
*  Band represents all of the band's information
*
*  @author Chris Heydt & Jamie Orlando
*  @version 1.0
*/
public class Band {

   /**
   *  Initializes the Band
   */
   public Band() {}

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
   *  Sets the band name
   *  @param name the band name
   */
   public void setBandName( String name ) {
      this.bandName = name;
   }

   /**
   *  Gets the band name
   *  @return the band name
   */
   public String getBandName() {
      return this.bandName;
   }

   /**
   *  Gets the WebSite
   *  @return the WebSite
   */
   public WebSite getWebSite() {
      return this.website;
   }

   /**
   *  Gets the Account
   *  @return the Account
   */
   public Account getAccount() {
      return this.account;
   }

   /**
   *  Gets the BandAdministrator
   *  @return the BandAdministrator
   */
   public BandAdministrator getBandAdministrator() {
      return this.admin;
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
	 String update = "UPDATE Band SET bandName='" + 
	    this.bandName + "' WHERE url='" + this.url + "'";
         if( connStatement.executeUpdate(update) == 0 ) { 
            // update produced exception, so try inserting new record
	    String insert = "INSERT INTO Band(url, bandName) " +
	       "values('" + this.url + "','" + this.bandName + "')";  
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
   *  @return true if successful
   */
   public boolean select(String url) throws SQLException {
      DbServices database = new DbServices();
      if( !database.connect() )
         return false;
      Connection conn = database.getConnection();
         
      try {
         PreparedStatement getRecords = conn.prepareStatement(
            "SELECT * FROM Band WHERE url = ?");
         getRecords.setString(1, url);
         ResultSet results = getRecords.executeQuery();
            
         if(results.next())
         {
           setUrl(results.getString(1));
           setBandName(results.getString(2));
           
           this.admin = new BandAdministrator();
           admin.select(url);
           this.website = new WebSite();
           admin.select(url);
           this.account = new Account();
           account.select(url);

           return true;
         }
         else
           return false;
      }
      catch( SQLException ex ) { throw ex; }
      finally { database.releaseConnection(); }
   }

   public boolean url_taken(String url) throws SQLException {
      DbServices database = new DbServices();
      if( !database.connect() )
        throw new SQLException("Could not connect to database");
      Connection conn = database.getConnection();
         
      try {
         PreparedStatement getRecords = conn.prepareStatement(
            "SELECT * FROM Band WHERE url = ?");
         getRecords.setString(1, url);
         ResultSet results = getRecords.executeQuery();

         if(!results.last())    // if result set was empty
           return false;    // url is available
         else
           return true;   // url is taken
      }
      catch( SQLException ex ) { throw ex; }
      finally { database.releaseConnection(); }
   }

   
   private String url;
   private String bandName;
   private BandAdministrator admin;
   private WebSite website;
   private Account account;
}

