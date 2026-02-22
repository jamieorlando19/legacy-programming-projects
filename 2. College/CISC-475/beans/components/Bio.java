package com.warthog.domain.components; 
 
import java.io.*; 
import java.sql.*; 
import com.warthog.services.*; 

/**
*  Bio represents the band's bio
*
*  @author Chris Heydt & Jamie Orlando
*  @version 1.0
*/ 
public class Bio  
{
   /**
   *  Initializes the Bio
   */
   public Bio() {}

   /**
   *  Sets the URL
   *  @param url the URL
   */
   public void setUrl( String url ) 
   {  
      this.url = url;  
   }

   /**
   *  Gets the URL
   *  @return the URL
   */
   public String getUrl()  
   { 
      return this.url; 
   }

   /**
   *  Sets the bio body
   *  @param body the bio body
   */
   public void setBody( String body )  
   { 
      this.body = body;  
   } 

   /**
   *  Gets the bio body
   *  @return the bio body
   */
   public String getBody()  
   { 
      return this.body; 
   }

   /**
   *  Saves the attributes of this to the database
   *  @return true if successful
   */
   public boolean save() throws SQLException
   {
      DbServices database = new DbServices();
      if( !database.connect() ) {
         return false;
      }
      Connection conn = database.getConnection();
      try {
         Statement connStatement = conn.createStatement();
         String update = "UPDATE Bio SET bio='" + this.body + 
            "' WHERE url='" + this.url + "'";
         if( connStatement.executeUpdate( update ) == 0 ) 
         {
            String insert = "INSERT INTO Bio(url, bio) values('" +
	       this.url + "','" + this.body + "')"; 
            if( connStatement.executeUpdate( insert ) == 0 )
	       return false;
         }
      }
      catch( SQLException ex ) { throw ex; } 
      finally { database.releaseConnection(); }
      return true;  
   }

   /**
   *  Retrieves the corresponding row in the database based on the
   *  URL provided
   *  @param url the URL
   *  @return true if successful
   */
   public boolean select(String url) throws SQLException
   {
      DbServices database = new DbServices();
      if( !database.connect() )
        return false;

      Connection conn = database.getConnection();
      try {
         PreparedStatement getRecords = conn.prepareStatement("SELECT * FROM Bio WHERE url = ?");

         getRecords.setString(1, url);
         ResultSet results = getRecords.executeQuery();

         if(results.next())
         {
           setUrl(results.getString(1));
           setBody(results.getString(2));
           return true;
         }
         else
	   return false;
      }
      catch( SQLException ex ) { throw ex; }
      finally { database.releaseConnection(); }
   }

   private String url; 
   private String body; 
}
