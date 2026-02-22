package com.warthog.domain.core; 

import java.io.*;
import java.sql.*;
import java.util.*;
import com.warthog.services.*;

/**
*  Account represents the band's account information
*
*  @author Chris Heydt & Jamie Orlando
*  @version 1.0
*/
public class Account {

   /**
   *  Initializes the Account
   */
   public Account() {}

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
   *  Sets the start date
   *  @param startDate the start date
   */
   public void setStartDate( String startDate ) {
      this.startDate = startDate;
   }

   /**
   *  Gets the start date
   *  @return the start date
   */
   public String getStartDate() {
      return this.startDate;
   }

   /**
   *  Sets the end date
   *  @param endDate the end date
   */
   public void setEndDate( String endDate ) {
      this.endDate = endDate;
   }

   /**
   *  Gets the end date
   *  @return the end date
   */
   public String getEndDate() {
      return this.endDate;
   }

   /**
   *  Sets the yearly rate
   *  @param rate the yearly rate
   */
   public void setYearlyRate( float rate ) {
      this.yearlyRate = rate;
   }

   /**
   *  Gets the yearly rate
   *  @return the yearly rate
   */
   public float getYearlyRate() {
      return this.yearlyRate;
   }

   /**
   *  Sets the credit card
   *  @param creditCard the credit card
   */
   public void setCreditCard( CreditCard creditCard ) {
      this.creditCard = creditCard;
   }

   /**
   *  Gets the credit card
   *  @return the credit card
   */
   public CreditCard getCreditCard() {
      return this.creditCard;
   }

   /**
   *  Sets the password
   *  @param password the password
   */
   public void setPassword( String password ) {
      this.password = password;
   }

   /**
   *  Gets the password
   *  @return the password
   */
   public String getPassword() {
      return this.password;
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
	 String update = "UPDATE Account SET startDate='" + 
	    this.startDate + "',endDate='" + this.endDate + 
	    "',yearlyRate=" + this.yearlyRate + ",password='" + 
	    this.password + "' WHERE url='" + this.url + "'";  
	 if( connStatement.executeUpdate(update) == 0 ) {
	    String insert = "INSERT INTO Account(url, startDate," +
	       "endDate, yearlyRate, password) values('" + this.url + 
       	       "','" + this.startDate + "','" + this.endDate + "'," +
	       this.yearlyRate + ",'" + this.password + "')"; 
	    if( connStatement.executeUpdate( insert ) == 0 ) 
	       return false; 
	 }
      }
      catch( SQLException ex ) { throw ex; }
      finally { database.releaseConnection(); }
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
	    "SELECT * FROM Account WHERE url = ?");
         getRecords.setString(1, url);
         ResultSet results = getRecords.executeQuery();

         if(results.next())
         {
           setUrl(results.getString(1));
           setStartDate(results.getString(2));
           setEndDate(results.getString(3));
           setYearlyRate(results.getFloat(4));
           setPassword(results.getString(5));
           return true;
         }
         else
           return false;
      }
      catch( SQLException ex ) { throw ex; }
      finally { database.releaseConnection(); }
   }

   private String url;
   private String startDate;
   private String endDate;
   private float yearlyRate;
   private String password;
   private CreditCard creditCard;
}
