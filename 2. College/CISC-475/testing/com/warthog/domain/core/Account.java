// Chris Heydt & Jamie Orlando
// Account class

package com.warthog.domain.core; 

import java.io.*;
import java.sql.*;
import com.warthog.services.*;

public class Account {
   public Account() {}

   public void setUrl( String url ) {
      this.url = url;
   }
   public String getUrl() {
      return this.url;
   }
   public void setStartDate( Date startDate ) {
      this.startDate = startDate;
   }
   public Date getStartDate() {
      return this.startDate;
   }
   public void setEndDate( Date endDate ) {
      this.endDate = endDate;
   }
   public void setYearlyRate( float rate ) {
      this.yearlyRate = rate;
   }
   public float getYearlyRate() {
      return this.yearlyRate;
   }
   public void setCreditCard( CreditCard creditCard ) {
      this.creditCard = creditCard;
   }
   public CreditCard getCreditCard() {
      return this.creditCard;
   }
   public void setPassword( String password ) {
      this.password = password;
   }
   public String getPassword() {
      return this.password;
   }

   public void save() throws SQLException {
      String sqlStartDate = formatDateForDB( this.startDate );
      String sqlEndDate = formatDateForDB( this.endDate );

      DbServices database = new DbServices();
      if( !database.connect() )  
         return;
      Connection conn = database.getConnection();
      try {
         Statement connStatement = conn.createStatement();
	 String update = "UPDATE Account SET startDate='" + 
	    sqlStartDate + "',endDate='" + sqlEndDate + 
	    "',yearlyRate=" + this.yearlyRate + ",password='" + 
	    this.password + "' WHERE url='" + this.url + "'";  
	 if( connStatement.executeUpdate(update) == 0 ) {
	    String insert = "INSERT INTO Account(url, startDate," +
	       "endDate, yearlyRate, password) values('" + this.url + 
       	       "','" + sqlStartDate + "','" + sqlEndDate + "'," +
	       this.yearlyRate + ",'" + this.password + "')"; 
	    int returnVal = connStatement.executeUpdate( insert ); 
	 }
      }
      catch( SQLException ex ) { throw ex; }
      finally { database.releaseConnection(); }
   }
   private final String formatDateForDB( Date date ) {
      return date.getYear() + "-" + (date.getMonth() + 1) + 
         "-" + date.getDate();
   }

   public boolean select(String url) throws SQLException {
      DbServices database = new DbServices();
      if( !database.connect() )
         return false;
      Connection conn = database.getConnection();

      try {
         PreparedStatement getRecords = conn.prepareStatement("SELECT * FROM Account WHERE url = ?");
         getRecords.setString(1, url);
         ResultSet results = getRecords.executeQuery();

         if(results.next())
         {
           setUrl(results.getString(1));
           setStartDate(results.getDate(2));
           setEndDate(results.getDate(3));
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
   private Date startDate;
   private Date endDate;
   private float yearlyRate;
   private String password;
   private CreditCard creditCard;
}
