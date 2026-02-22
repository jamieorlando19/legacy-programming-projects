//Jamie Orlando
//CISC 479
//Final Project
//MuscLogUsersDataBean.java

//Bean which processes a BandsMusiciansDataBean

package jsp.beans;

import java.io.*;
import java.sql.*;
import java.util.*;

public class MuscLogUsersDataBean {

   private Connection        connection;
   private PreparedStatement addRecord, getRecords;

   public MuscLogUsersDataBean() throws Exception
   {
      //Load cloudscape driver & connect to database
      Class.forName( "COM.cloudscape.core.RmiJdbcDriver" );
      connection = DriverManager.getConnection("jdbc:rmi://localhost:1124/jdbc:cloudscape:musclog" );

      getRecords = connection.prepareStatement("SELECT * FROM musclogusers WHERE username = ?");
      addRecord = connection.prepareStatement("INSERT INTO musclogusers VALUES (?, ?)");
   }

   //Gets returns info about a user according to the username passed
   public MuscLogUsersBean getUser(String username) throws SQLException
   {
      MuscLogUsersBean user = new MuscLogUsersBean();

      getRecords.setString(1, username);
 
      //Get list by executing the SQL query
      ResultSet results = getRecords.executeQuery();

      //Returns null if there are no results
      if(results.next()){}
      else return null;      

      user.setusername(results.getString(1));
      user.setpassword(results.getString(2));

      return user; 
   }
   
   //Adds a new user to the database
   public void addLogin(MuscLogUsersBean login) throws SQLException
   {
      addRecord.setString(1, login.getusername());
      addRecord.setString(2, login.getpassword());       
      addRecord.executeUpdate();
   }

   //Close statements and terminate database connection
   protected void close()
   {
      //Attempt to close database connection
      try 
      {
         getRecords.close();
         addRecord.close();
         connection.close();
      }
      catch ( SQLException sqlException )
      { sqlException.printStackTrace(); }
   }

   protected void finalize()
   { close(); }
}
