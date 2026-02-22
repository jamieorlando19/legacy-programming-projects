//Jamie Orlando
//CISC 479
//Final Project
//ShowDataBean.java

//Bean which processes a ShowBean

package jsp.beans;

import java.io.*;
import java.sql.*;
import java.util.*;

public class ShowDataBean {

   private Connection connection;

   private PreparedStatement addRecord;
   private PreparedStatement getRecords;
   private PreparedStatement getShow;
   private PreparedStatement deleteShow;
   private PreparedStatement deleteBandShow;
   private PreparedStatement getShowSong;
   private PreparedStatement getShowMusician;

   public ShowDataBean() throws Exception
   {
      //Load cloudscape driver & connect to database
      Class.forName( "COM.cloudscape.core.RmiJdbcDriver" );
      connection = DriverManager.getConnection("jdbc:rmi://localhost:1124/jdbc:cloudscape:musclog" );

      getRecords = connection.prepareStatement("SELECT * FROM show" +
                                     " WHERE username = ?" +
                                     " ORDER BY showyear DESC, showmonth DESC, showday DESC");

      addRecord = connection.prepareStatement("INSERT INTO show VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

      getShow = connection.prepareStatement("SELECT * FROM show" +
                                   " WHERE username = ? AND showyear = ?" +
                                   " AND showmonth = ? AND showday = ?");

      deleteShow = connection.prepareStatement("DELETE FROM show" +
                                   " WHERE username = ? AND showyear = ?" +
                                   " AND showmonth = ? AND showday = ?");

      deleteBandShow = connection.prepareStatement("DELETE FROM show" +
                                   " WHERE username = ? AND bandname = ?");

      getShowSong = connection.prepareStatement("SELECT * FROM show WHERE username = ? AND setlist LIKE ?" +
                                     " ORDER BY showyear DESC, showmonth DESC, showday DESC");

      getShowMusician = connection.prepareStatement("SELECT * FROM show WHERE username = ? AND bandmembers LIKE ?" +
                                     " ORDER BY showyear DESC, showmonth DESC, showday DESC");
   }

   //Gets a list of shows (filtered by an argument)
   public List getList(String username, int type, String arg) throws SQLException
   {
      List showList = new ArrayList();
      ResultSet results;

      //If user wants a list of all the shows
      if(type == 0)
      {      
         getRecords.setString(1, username);
         results = getRecords.executeQuery();
      }
      //If user wants a list of shows where a certain song was played
      else if(type == 1)
      {
         getShowSong.setString(1, username);
         arg = "%" + arg + "%";
         getShowSong.setString(2, arg);
         results = getShowSong.executeQuery();
      }
      //If user wants a list of shows where a certain musician was
      else
      {
         getShowMusician.setString(1, username);
         arg = "%" + arg + "%";
         getShowMusician.setString(2, arg);
         results = getShowMusician.executeQuery();
      }

      // get row data
      while (results.next())
      {
         ShowBean show = new ShowBean();

         show.setusername(results.getString(1));
         show.setbandname(results.getString(2));
         show.setshowyear(results.getInt(3));
         show.setshowmonth(results.getInt(4));
         show.setshowday(results.getInt(5));
         show.setsetlist(results.getString(6));
         show.setjournal(results.getString(7));
         show.setbandmembers(results.getString(8));
         show.setshowvenue(results.getString(9));
         show.setshowcity(results.getString(10));
         show.setshowstate(results.getString(11));
         
         showList.add(show); 
      }         

      return showList; 
   }
   
   //Adds a new show to the database
   public void addShow(ShowBean show) throws SQLException
   {
      addRecord.setString(1, show.getusername());
      addRecord.setString(2, show.getbandname());
      addRecord.setInt(3, show.getshowyear());
      addRecord.setInt(4, show.getshowmonth());
      addRecord.setInt(5, show.getshowday());
      addRecord.setString(6, show.getsetlist());
      addRecord.setString(7, show.getjournal());
      addRecord.setString(8, show.getbandmembers());
      addRecord.setString(9, show.getshowvenue());
      addRecord.setString(10, show.getshowcity());
      addRecord.setString(11, show.getshowstate());

      addRecord.executeUpdate();
   }

   //Gets a show from the database according to the username, year, month and day
   public ShowBean getTheShow(String username, int year, int month, int day) throws SQLException
   {
      ShowBean show = new ShowBean();

      getShow.setString(1, username);
      getShow.setInt(2, year);
      getShow.setInt(3, month);
      getShow.setInt(4, day);

      ResultSet results = getShow.executeQuery();
      results.next();

      show.setusername(results.getString(1));
      show.setbandname(results.getString(2));
      show.setshowyear(results.getInt(3));
      show.setshowmonth(results.getInt(4));
      show.setshowday(results.getInt(5));
      show.setsetlist(results.getString(6));
      show.setjournal(results.getString(7));
      show.setbandmembers(results.getString(8));
      show.setshowvenue(results.getString(9));
      show.setshowcity(results.getString(10));
      show.setshowstate(results.getString(11));

      return show;
   }

   //Deletes a show from the database according to the username, year, month and day
   public void deleteTheShow(String username, int year, int month, int day) throws SQLException
   {
      deleteShow.setString(1, username);
      deleteShow.setInt(2, year);
      deleteShow.setInt(3, month);
      deleteShow.setInt(4, day);

      deleteShow.executeUpdate();
   }

   //Deletes shows from the database according to the username and bandname
   public void deleteTheBandShow(String username, String bandname) throws SQLException
   {
      deleteBandShow.setString(1, username);
      deleteBandShow.setString(2, bandname);

      deleteBandShow.executeUpdate();
   }

   //Close statements and terminate database connection
   public void close()
   {
      //close database connection
      try 
      {
         getRecords.close();
         addRecord.close();
         getShow.close();
         deleteShow.close();
         deleteBandShow.close();
         getShowSong.close();
         getShowMusician.close();
         connection.close();
      }
      catch ( SQLException sqlException )
      { sqlException.printStackTrace(); }
   }

   protected void finalize()
   { close(); }
}
