package com.warthog.domain.components; 
 
import java.io.*; 
import java.sql.*; 
import java.util.ArrayList;
import com.warthog.services.*; 

/**
*  ShowDate represents a single show date
*
*  @author Chris Heydt & Jamie Orlando
*  @version 1.0
*/  
public class ShowDate  
{

   /**
   *  Initializes the ShowDate
   */
   public ShowDate() {}

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
   public String getUlr()  
   { 
      return this.url; 
   }

   /**
   *  Sets the date of the show
   *  @param date the date of the show
   */
   public void setDate( Date date )  
   { 
      this.date = date;  
   } 

   /**
   *  Gets the date of the show
   *  @return the date of the show
   */
   public Date getDate()  
   { 
      return this.date; 
   } 

   /**
   *  Sets the venue of the show
   *  @param venue the venue of the show
   */
   public void setVenue( String venue )  
   { 
      this.venue = venue; 
   }

   /**
   *  Gets the venue of the show
   *  @return the venue of the show
   */
   public String getVenue() 
   { 
      return this.venue;  
   }

   /**
   *  Sets the city of the show
   *  @param city the city of the show
   */
   public void setCity( String city ) 
   { 
      this.city = city; 
   } 

   /**
   *  Gets the city of the show
   *  @return the city of the show
   */
   public String getCity() 
   { 
      return this.city; 
   }

   /**
   *  Sets the state of the show
   *  @param state the state of the show
   */
   public void setState( String state ) 
   { 
      this.state = state; 
   }

   /**
   *  Gets the state of the show
   *  @return the state of the show
   */
   public String getState()  
   { 
      return this.state; 
   } 
  
   /**
   *  Sets the comments for the show
   *  @param comments the comments for the show
   */
   public void setComments( String comments ) 
   { 
      this.comments = comments; 
   } 

   /**
   *  Gets the comments of the show
   *  @return the comments
   */
   public String getComments()  
   { 
      return this.comments; 
   } 

   /**
   *  Compares a date to the date stored
   *  @param date the date to compare
   *  @return 0 if the dates are equal
   */
   public int compareTo( Date date ) 
   {
      return this.date.compareTo( date );
   }

   /**
   *  Saves the attributes of this to the database
   *  @return true if successful
   */
   public boolean save() throws SQLException 
   {
      DbServices database = new DbServices();
      if( !database.connect() )
         return false;
      Connection conn = database.getConnection();
      try {
         Statement connStatement = conn.createStatement();
	 String insert = "INSERT INTO ShowDate(url, date, venue, " +
	    "city, state, comments) values('" + this.url + 
	    "','" + this.date + "','" + this.venue + "','" + this.city +
	    "','" + this.state + "'," + 
	    this.comments + "')";
	 if( connStatement.executeUpdate( insert ) == 0 )
	    return false;
      }
      catch( SQLException ex ) { throw ex; } 
      finally { database.releaseConnection(); }
      return true;  
   }

   /**
   *  Retrieves the corresponding row in the database based on the
   *  URL provided
   *  @param url the URL
   *  @return the full list of ShowDates according to the URL
   */
   public ArrayList select(String url) throws SQLException 
   {
      ArrayList showDateList = new ArrayList();
      DbServices database = new DbServices();
      if( !database.connect() )
         return showDateList;
      Connection conn = database.getConnection();
      try {
         PreparedStatement getRecords = conn.prepareStatement(
	    "SELECT * FROM ShowDate WHERE url = ?");
         getRecords.setString(1, url);
         ResultSet results = getRecords.executeQuery();

         while(results.next())
         {
	   ShowDate showDate = new ShowDate();
           showDate.setUrl(results.getString(1));
           showDate.setDate(results.getDate(2));
           showDate.setVenue(results.getString(3));
           showDate.setCity(results.getString(4));
           showDate.setState(results.getString(5));
           showDate.setComments(results.getString(6));
	   showDateList.add(showDate);
         }
      }
      catch( SQLException ex ) { throw ex; }
      finally { 
	 database.releaseConnection(); 
         return showDateList;
      }
   }
 
   private String url; 
   private Date date; 
   private String venue; 
   private String city; 
   private String state; 
   private String comments; 
}
