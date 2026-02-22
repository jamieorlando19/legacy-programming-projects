//Jamie Orlando
//CISC 479
//Final Project
//BandsMusiciansDataBean.java

//Bean which processes a BandsMusiciansDataBean

package jsp.beans;

import java.io.*;
import java.sql.*;
import java.util.*;

public class BandsMusiciansDataBean {

   private Connection connection;
   private PreparedStatement getRecords, addRecord, getBand, deleteBand;

   public BandsMusiciansDataBean() throws Exception
   {
      //Load cloudscape driver & connect to database
      Class.forName( "COM.cloudscape.core.RmiJdbcDriver" );
      connection = DriverManager.getConnection("jdbc:rmi://localhost:1124/jdbc:cloudscape:musclog" );

      getRecords = connection.prepareStatement("SELECT * FROM bandsmusicians WHERE username = ? ORDER BY yearformed DESC");
      addRecord = connection.prepareStatement("INSERT INTO bandsmusicians VALUES (?, ?, ?, ?)");
      getBand = connection.prepareStatement("SELECT * FROM bandsmusicians WHERE username = ? AND bandname = ?");
      deleteBand = connection.prepareStatement("DELETE FROM bandsmusicians WHERE username = ? AND bandname = ?");
   }

   //Returns a list of bands
   public List getList(String username) throws SQLException
   {
      List bandList = new ArrayList();
      getRecords.setString(1, username);

      //Get list by executing the SQL query
      ResultSet results = getRecords.executeQuery();

      // get row data
      while (results.next())
      {
         BandsMusiciansBean band = new BandsMusiciansBean();

         band.setusername(results.getString(1));
         band.setbandname(results.getString(2));
         band.setbandbio(results.getString(3));
         band.setyearformed(results.getInt(4));

         bandList.add(band); 
      }         

      return bandList; 
   }
   
   //Adds a band to the database
   public void addBand(BandsMusiciansBean band) throws SQLException
   {
      addRecord.setString(1, band.getusername());
      addRecord.setString(2, band.getbandname());
      addRecord.setString(3, band.getbandbio());
      addRecord.setInt(4, band.getyearformed());       
      addRecord.executeUpdate();
   }

   //Returns info about a band according to the username and bandname
   public BandsMusiciansBean getTheBand(String uname, String bname) throws SQLException
   {
      BandsMusiciansBean band = new BandsMusiciansBean();

      getBand.setString(1, uname);
      getBand.setString(2, bname);
      
      ResultSet results = getBand.executeQuery();
      results.next();

      band.setusername(results.getString(1));
      band.setbandname(results.getString(2));
      band.setbandbio(results.getString(3));
      band.setyearformed(results.getInt(4));   

      return band;
   }

   //Deletes a band according to the username and bandname
   public void deleteTheBand(String username, String bandname) throws SQLException
   {
      deleteBand.setString(1, username);
      deleteBand.setString(2, bandname);
      
      deleteBand.executeUpdate();
   }


   //Close statements and terminate database connection
   protected void close()
   {
      //Attempt to close database connection
      try 
      {
         getRecords.close();
         addRecord.close();
         getBand.close();
         deleteBand.close();
         connection.close();
      }
      catch ( SQLException sqlException )
      { sqlException.printStackTrace(); }
   }
    
   protected void finalize()
   { close(); }
}
