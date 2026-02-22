// Chris Heydt
// Band class

package com.warthog.domain.core;

import java.io.*;
import java.sql.*;
import com.warthog.services.*;

public class Band {
   public Band() {}
   public void setUrl( String url ) {
      this.url = url;
   }
   public String getUrl() {
      return this.url;
   }
   public void setBandName( String name ) {
      this.bandName = name;
   }
   public String getBandName() {
      return this.bandName;
   }
   public WebSite getWebSite() {
      return this.website;
   }
   public Account getAccount() {
      return this.account;
   }
   public BandAdministrator getBandAdministrator() {
      return this.admin;
   }
   public void save() throws SQLException {
      DbServices database = new DbServices();
      if( !database.connect() ) 
         return;
      Connection conn = database.getConnection();
      try {
         Statement connStatement = conn.createStatement();
	 String update = "UPDATE Band SET bandName='" + 
	    this.bandName + "' WHERE url='" + this.url + "'";
         if( connStatement.executeUpdate(update) == 0 ) { 
            // update produced exception, so try inserting new record
	    String insert = "INSERT INTO Band(url, bandName) " +
	       "values('" + this.url + "','" + this.bandName + "')";  
            int returnVal = connStatement.executeUpdate( insert );
         }
         /* this.admin.save();
         this.website.save();
         this.account.save(); */
      } 
      catch( SQLException ex ) { throw ex; }
      finally{ database.releaseConnection(); }
   }
   
   private String url;
   private String bandName;
   private BandAdministrator admin;
   private WebSite website;
   private Account account;
}

