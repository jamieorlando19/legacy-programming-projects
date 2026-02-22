package com.warthog.domain.core;

import java.io.*;
import java.sql.*;
import com.warthog.services.*;

/**
*  CreditCard represents all of the band's credit card info
*
*  @author Chris Heydt & Jamie Orlando
*  @version 1.0
*/
public class CreditCard {

   /**
   *  Initializes the CreditCard
   */
   public CreditCard() {}

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
   *  Sets the credit card number
   *  @param number the credit card number
   */
   public void setNumber( String number ) {
      this.number = number;
   }

   /**
   *  Gets the credit card number
   *  @return the credit card number
   */
   public String getNumber() {
      return this.number;
   }

   /**
   *  Sets the credit card provider
   *  @param provider the credit card provider
   */
   public void setProvider( String provider ) {
      this.provider = provider;
   }

   /**
   *  Gets the credit card provider
   *  @return the credit card provider
   */
   public String getProvider() {
      return this.provider;
   }

   /**
   *  Sets the credit card expiration date
   *  @param date the card card expiration date
   */
   public void setExpDate( String date ) {
      this.expDate = date;
   }

   /**
   *  Gets the credit card expiration date
   *  @return the credit card expiration date
   */
   public String getExpDate() {
      return this.expDate;
   }

   /**
   *  Sets the credit card holder's name
   *  @param name the credit card holder's name
   */
   public void setCardholderName( String name ) {
      this.cardholderName = name;
   }

   /**
   *  Gets the credit card holder's name
   *  @return the credit card holder's name
   */
   public String getCardholderName() {
      return this.cardholderName;
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
	 String update = "UPDATE CreditCard SET number='" + 
	    this.number + "',cardholderName='" + this.cardholderName + 
	    "',provider='" + this.provider + "',expDate='" + this.expDate +
	    "' WHERE url='" + this.url + "'";
         System.out.println( update );
         
	 if( connStatement.executeUpdate( update ) == 0 ) {
	    String insert = "INSERT INTO CreditCard(url, number, " +
	       "cardholderName, provider, expDate) values('" + this.url + 
	        "','" + this.number + "','" + this.cardholderName + "','" + 
	        this.provider + "','" + this.expDate + "')";
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
            "SELECT * FROM CreditCard WHERE url = ?");
         getRecords.setString(1, url);
         ResultSet results = getRecords.executeQuery();
            
         if(results.next())
         {
           setUrl(results.getString(1));
           setNumber(results.getString(2));
           setCardholderName(results.getString(3));
           setProvider(results.getString(4));
           setExpDate(results.getString(5));
           return true;
         }   
         else
           return false;
      }
      catch( SQLException ex ) { throw ex; }
      finally { database.releaseConnection(); }
   }

   private String url;
   private String number;
   private String provider;
   private String expDate;
   private String cardholderName; 
}
