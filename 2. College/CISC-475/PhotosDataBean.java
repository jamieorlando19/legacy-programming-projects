//Bean which processes an PhotosBean

import java.io.*;
import java.sql.*;
import java.util.*;

public class PhotosDataBean {

   private Connection connection;
   private PreparedStatement addRecord, getRecords, deleteRecords;

   public PhotosDataBean() throws Exception
   {
      //Connection crap goes here

      addRecord = connection.prepareStatement("INSERT INTO Photos VALUES (?, ?, ?, ?, ?, ?, ?)");
      getRecords = connection.prepareStatement("SELECT * FROM Photos WHERE Account_No = ?");
      deleteRecords = connection.prepareStatement("DELETE FROM Photos WHERE Account_No = ?");
   }
   
   public void addPhotos(PhotosBean Photos) throws SQLException
   {
      addRecord.setString(1, Photos.getband_id());
      addRecord.setString(2, Photos.getaccount_no());
      addRecord.setObject(3, Photos.getphoto1());
      addRecord.setObject(4, Photos.getphoto2());
      addRecord.setObject(5, Photos.getphoto3());
      addRecord.setObject(6, Photos.getphoto4());
      addRecord.setObject(7, Photos.getphoto5());

      addRecord.executeUpdate();
   }

   public PhotosBean getPhotos(String acct) throws SQLException
   {
      PhotosBean newbean = new PhotosBean();

      getRecords.setString(1, acct);
 
      //Get list by executing the SQL query
      ResultSet results = getRecords.executeQuery();

      //Returns null if there are no results
      if(!results.next()) return null;      

      newbean.setband_id(results.getString(1));
      newbean.setaccount_no(results.getString(2));
      newbean.setphoto1(results.getObject(3));
      newbean.setphoto2(results.getObject(4));
      newbean.setphoto3(results.getObject(5));
      newbean.setphoto4(results.getObject(6));
      newbean.setphoto5(results.getObject(7));

      return newbean; 
   }

   public void deletePhotos(String acct) throws SQLException
   {
      deleteRecords.setString(1, acct);
      deleteRecords.executeQuery();
   }
   
   //Close statements and terminate database connection
   protected void close()
   {
      //Attempt to close database connection
      try 
      {
         addRecord.close();
         getRecords.close();
         deleteRecords.close();
         connection.close();
      }
      catch ( SQLException sqlException )
      { sqlException.printStackTrace(); }
   }

   protected void finalize()
   { close(); }
}
