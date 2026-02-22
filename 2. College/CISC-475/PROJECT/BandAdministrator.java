package com.warthog.domain.core;

import java.io.*;
import java.sql.*;
import com.warthog.services.*; 

/**
*  BandAdministrator represents all of the band's contact information
*
*  @author Chris Heydt & Jamie Orlando
*  @version 1.0
*/
public class BandAdministrator {

   /**
   *  Initializes the BandAdministrator
   */
   public BandAdministrator() {}

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
   *  Sets the first name
   *  @param fname the first name
   */
   public void setFirstName( String fname ) {
      this.firstName = fname;
   }

   /**
   *  Gets the first name
   *  @return the first name
   */
   public String getFirstName() {
      return this.firstName;
   }

   /**
   *  Sets the last name
   *  @param lname the last name
   */
   public void setLastName( String lname ) {
      this.lastName = lname;
   }

   /**
   *  Gets the last name
   *  @return the last name
   */
   public String getLastName() {
      return this.lastName;
   }

   /**
   *  Gets the full name
   *  @return the full name
   */
   public String getFullName() {
      return this.firstName + " " + this.lastName;
   }

   /**
   *  Sets the street
   *  @param street the street
   */
   public void setStreet( String street ) {
      this.street = street;
   }

   /**
   *  Gets the street
   *  @return the street
   */
   public String getStreet() {
      return this.street;
   }

   /**
   *  Sets the city
   *  @param city the city
   */
   public void setCity( String city ) {
      this.city = city;
   } 

   /**
   *  Gets the city
   *  @return the city
   */
   public String getCity() {
      return this.city;
   }

   /**
   *  Sets the state
   *  @param state the state
   */
   public void setState( String state ) {
      this.state = state;
   }

   /**
   *  Gets the state
   *  @return the state
   */
   public String getState() {
      return this.state;
   }

   /**
   *  Sets the zipcode
   *  @param zip the zipcode
   */
   public void setZip( String zip ) {
      this.zip = zip;
   }

   /**
   *  Gets the zipcode
   *  @return the zipcode
   */
   public String getZip() {
      return this.zip;
   }

   /**
   *  Sets the email
   *  @param email the email
   */
   public void setEmail( String email ) {
      this.email = email; 
   }

   /**
   *  Gets the email
   *  @return the email
   */
   public String getEmail() {
      return this.email;
   }

   /**
   *  Sets the phone number
   *  @param num the phone number
   */
   public void setPhoneNumber( String num ) {
      this.phoneNumber = num;
   }

   /**
   *  Gets the phone number
   *  @return the phone number
   */
   public String getPhoneNumber() {
      return this.phoneNumber;
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
	 String update = "UPDATE BandAdministrator SET firstName='" + 
	    this.firstName + "',lastName='" + this.lastName + "',street='" +
	    this.street + "',city='" + this.city + "',state='" + this.state +
	    "',zip=" + this.zip + ",email='" + this.email + 
	    "',phoneNumber='" + this.phoneNumber + "' WHERE url='" + this.url + "'";
         if( connStatement.executeUpdate(update) == 0 ) {
            String insert = new String( "INSERT INTO BandAdministrator(url, " + 
	       "firstName, lastName, street, city, state, zip, " + 
	       "email, phoneNumber) values('" + this.url + "','" + 
	       this.firstName + "','" + this.lastName + "','" + this.street + "','" +
               this.city + "','" + this.state + "'," + this.zip + ",'" + 
	       this.email + "','" + phoneNumber + "')" ); 
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
            "SELECT * FROM BandAdministrator WHERE url = ?");
         getRecords.setString(1, url);
         ResultSet results = getRecords.executeQuery();
            
         if(results.next())
         {
           setUrl(results.getString(1));
           setFirstName(results.getString(2));
           setLastName(results.getString(3));
           setStreet(results.getString(4));
           setCity(results.getString(5));
           setState(results.getString(6));
           setZip(results.getString(7));
           setEmail(results.getString(8));
           setPhoneNumber(results.getString(9));
           return true;
         }   
         else
           return false;
      }
      catch( SQLException ex ) { throw ex; }
      finally { database.releaseConnection(); }
   }


   private String url;
   private String firstName;
   private String lastName;
   private String street;
   private String city;
   private String state;
   private String zip;
   private String email;
   private String phoneNumber;
}