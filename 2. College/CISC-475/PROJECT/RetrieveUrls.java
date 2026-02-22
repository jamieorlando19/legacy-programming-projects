package com.warthog.domain.core; 
import java.io.*;
import java.sql.*;
import java.util.*;
import com.warthog.services.*;

/**
*  Account represents the band's account information
*
*  @author Chris Gillin
*  @version 1.0
*/
public class RetrieveUrls {

   /**
   *  Initializes the Account
   */
   public RetrieveUrls() {}

   public static ArrayList retrieve() throws SQLException
   {
      ArrayList urlList = new ArrayList();
      DbServices database = new DbServices();
      if( !database.connect() )
         return urlList;
      Connection conn = database.getConnection();
      try {
         PreparedStatement getRecords = conn.prepareStatement(
            "SELECT url FROM Account");
         ResultSet results = getRecords.executeQuery();
         
         while(results.next())
         {
           urlList.add(results.getString(1));
         }
      }
      catch( SQLException ex ) { throw ex; }
      finally {
         database.releaseConnection();
         return urlList;
      }
   }
}
