// Chris Heydt
// Band Administrator class

package com.warthog.domain.core;

import java.io.*;
import java.sql.*;
import com.warthog.services.*; 

public class BandAdministrator {
   public BandAdministrator() {}
   public void setUrl( String url ) {
      this.url = url;
   }
   public String getUrl() {
      return this.url;
   }
   public void setFirstName( String fname ) {
      this.firstName = fname;
   }
   public String getFirstName() {
      return this.firstName;
   }
   public void setLastName( String lname ) {
      this.firstName = lname;
   }
   public String getLastName() {
      return this.lastName;
   }
   public void setStreet( String street ) {
      this.street = street;
   }
   public String getStreet() {
      return this.street;
   }
   public void setCity( String city ) {
      this.city = city;
   } 
   public String getCity() {
      return this.city;
   }
   public void setState( String state ) {
      this.state = state;
   }
   public String getState() {
      return this.state;
   }
   public void setZip( int zip ) {
      this.zip = zip;
   }
   public int getZip() {
      return this.zip;
   }
   public void setEmail( String email ) {
      this.email = email; 
   }
   public String getEmail() {
      return this.email;
   }
   public void setPhoneNumber( String num ) {
      this.phoneNumber = num;
   }
   public String getPhoneNumber() {
      return this.phoneNumber;
   }
   public void save() throws SQLException {
      DbServices database = new DbServices();
      if( !database.connect() ) 
         return;
      Connection conn = database.getConnection();
      try {
         Statement connStatement = conn.createStatement();
	 String update = "UPDATE BandAdministrator SET firstName='" + 
	    this.firstName + "',lastName='" + this.lastName + "',street='" +
	    this.street + "',city='" + this.city + "',state='" + this.state +
	    "',zip=" + this.zip + ",email='" + this.email + 
	    "',phoneNumber='" + this.phoneNumber + "' WHERE url='" + this.url + "'";
         if( connStatement.executeUpdate(update) == 0 ) {
            String insert = new String( "INSERT INTO BandAdministrator(url, " + 
	       "firstName, lastName, street, city, state, zip, " + 
	       "email, phoneNumber) values('" + this.url + "','" + 
	       this.firstName + "','" + this.lastName + "','" + this.street +
               this.city + "','" + this.state + "'," + this.zip + ",'" + 
	       this.email + "','" + phoneNumber + "')" ); 
            int returnVal = connStatement.executeUpdate( insert );
         }
      } 
      catch( SQLException ex ) { throw ex; }
      finally{ database.releaseConnection(); }
   }
   private String url;
   private String firstName;
   private String lastName;
   private String street;
   private String city;
   private String state;
   private int zip;
   private String email;
   private String phoneNumber;
}
