//Bean which processes an Mp3sBean

import java.io.*;
import java.sql.*;
import java.util.*;

public class Mp3sDataBean {

   private Connection connection;
   private PreparedStatement addRecord, getRecords, deleteRecords;

   public Mp3sDataBean() throws Exception
   {
      //Connection crap goes here

      addRecord = connection.prepareStatement("INSERT INTO Mp3s VALUES (?, ?, ?, ?, ?, ?, ?)");
      getRecords = connection.prepareStatement("SELECT * FROM Mp3s WHERE Account_No = ?");
      deleteRecords = connection.prepareStatement("DELETE FROM Mp3s WHERE Account_No = ?");
   }
   
   public void addMp3s(Mp3sBean Mp3s) throws SQLException
   {
      addRecord.setString(1, Mp3s.getband_id());
      addRecord.setString(2, Mp3s.getaccount_no());
      addRecord.setObject(3, Mp3s.getMp3_1());
      addRecord.setObject(4, Mp3s.getMp3_2());
      addRecord.setObject(5, Mp3s.getMp3_3());
      addRecord.setObject(6, Mp3s.getMp3_4());
      addRecord.setObject(7, Mp3s.getMp3_5());

      addRecord.executeUpdate();
   }

   public Mp3sBean getMp3s(String acct) throws SQLException
   {
      Mp3sBean newbean = new Mp3sBean();

      getRecords.setString(1, acct);
 
      //Get list by executing the SQL query
      ResultSet results = getRecords.executeQuery();

      //Returns null if there are no results
      if(!results.next()) return null;      

      newbean.setband_id(results.getString(1));
      newbean.setaccount_no(results.getString(2));
      newbean.setMp3_1(results.getObject(3));
      newbean.setMp3_2(results.getObject(4));
      newbean.setMp3_3(results.getObject(5));
      newbean.setMp3_4(results.getObject(6));
      newbean.setMp3_5(results.getObject(7));

      return newbean; 
   }

   public void deleteMp3s(String acct) throws SQLException
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
