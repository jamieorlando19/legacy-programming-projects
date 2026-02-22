// Chris Heydt
// Credit card class

package com.warthog.domain.core;

import java.io.*;
import java.sql.*;
import com.warthog.services.*;

public class CreditCard {
   public CreditCard() {}
   public void setUrl( String url ) {
      this.url = url;
   } 
   public String getUrl() {
      return this.url;
   } 
   public void setNumber( String number ) {
      this.number = number;
   }
   public String getNumber() {
      return this.number;
   }
   public void setProvider( String provider ) {
      this.provider = provider;
   }
   public String getProvider() {
      return this.provider;
   }
   public void setExpDate( java.sql.Date date ) {
      this.expDate = date;
   }
   public java.sql.Date getExpDate() {
      return this.expDate;
   }
   public void setCardholderName( String name ) {
      this.cardholderName = name;
   }
   public String getCardholderName() {
      return this.cardholderName;
   }
   public void save() throws SQLException {
      String sqlDateString = this.expDate.getYear() + "-" +
	 (this.expDate.getMonth() + 1) + "-" + this.expDate.getDate();

      DbServices database = new DbServices();
      if( !database.connect() )
         return;
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
	        this.provider + "','" + sqlDateString + "')";
            System.out.println( insert );
	    int returnVal = connStatement.executeUpdate( insert );
         }
      }
      // do nothing if createStatement or insertion threw an exception
      catch( SQLException ex ) { throw ex; } 
      finally { database.releaseConnection(); }
   }

   private String url;
   private String number;
   private String provider;
   private java.sql.Date expDate;
   private String cardholderName; 
}
