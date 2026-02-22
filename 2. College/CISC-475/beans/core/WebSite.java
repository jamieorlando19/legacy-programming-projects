package com.warthog.domain.core;

import java.io.*;
import java.sql.*;
import java.util.*;

import com.warthog.domain.components.*;
import com.warthog.services.*;


/**
*  WebSite represents all of the data to display on the band's website
*
*  @author Chris Heydt & Jamie Orlando
*  @version 1.0
*/
public class WebSite {

   /**
   *  Initializes the WebSite
   */
   public WebSite() {}

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
   *  Sets the website's title
   *  @param title the website's title
   */
   public String setTitle( String title ) {
      return this.title = title;
   }

   /**
   *  Gets the website's title
   *  @return the website's title
   */
   public String getTitle() {
      return this.title;
   }

   /**
   *  Sets the website's layout
   *  @param layout the website's layout
   */
   public void setLayout( WebSiteLayout layout ) {
     this.layout = layout;
   }

   /**
   *  Gets the website's layout
   *  @return the website's layout
   */
   public WebSiteLayout getLayout() {
      return this.layout;
   }

   /**
   *  Sets the band bio
   *  @param bio the band bio 
   */
   public void setBio( Bio bio ) {
     this.bio = bio;
   }

   /**
   *  Gets the band bio
   *  @return the band bio
   */
   public Bio getBio() {
      return this.bio;
   }

   /**
   *  Adds a show date to the database
   *  @param date the show date to add
   */
   public void addShowDate( ShowDate date ) throws SQLException{
      this.showDates.add( date );
      date.save(); 	// save the show date to the database
   }

   /**
   *  Gets the list of show dates newer than today
   *  @param today the current date
   *  @return the list of show dates
   */
   public ArrayList getShowDates( java.sql.Date today ) {
      ArrayList currentAndFutureShowDates = new ArrayList( 25 );
      for( int i = 0; i < this.showDates.size(); i++ ) {
	 ShowDate sdate = (ShowDate)this.showDates.get(i); 
         if( sdate.compareTo(today) >= 0 )
            // add show dates occuring today or later to vector
            currentAndFutureShowDates.add( sdate );
      }
      return currentAndFutureShowDates; 
   }

   /**
   *  Adds a news item to the database
   *  @param news the news item to add
   */
   public void addNews( NewsItem news ) throws SQLException{
      this.news.add( news );
      news.save(); 	// save the news item to the DB
   }

   /**
   *  Gets the band's news
   *  @return the list of news items
   */
   public ArrayList getNews() {
      return this.news;
   }

   /**
   *  Sets the band's MP3 files
   *  @param mp3s the band's MP3 files 
   */
   public void setMP3s( MP3File mp3s) {
      this.mp3s = mp3s;
   }

   /**
   *  Gets the band's MP3 files
   *  @return the band's MP3 files
   */
   public MP3File getMP3s() {
      return this.mp3s;
   }

   /**
   *  Sets the band's image files
   *  @param image the band's image files
   */
   public void setImages( ImageFile image) {
     this.images = image;
   } 

   /**
   *  Gets the band's image files
   *  @return the band's image files
   */
   public ImageFile getImages() {
      return this.images;
   }

   /**
   *  Saves the attributes of this to the database
   *  @return true if successful
   */
   public boolean save() throws SQLException {
      Statement connStatement;
      DbServices database = new DbServices();
      if( !database.connect() )  
         return false;
      Connection conn = database.getConnection();
      try {
         connStatement = conn.createStatement();
	 String update = "UPDATE WebSite SET title='" + this.title + 
	    "' WHERE url='" + this.url + "'";	 
         if( connStatement.executeUpdate( update ) == 0 ) {
            String insert = "INSERT INTO WebSite(url, title) values('" +
	       this.url + "','" + this.title + "')";
            if( connStatement.executeUpdate( insert ) == 0 )
               return false;  
         }
      }
      // do nothing if createStatement or insertion threw an exception 
      catch( SQLException ex ) { throw ex; }
      finally { database.releaseConnection(); }
      return true;
   }

   /**
   *  Retrieves the corresponding row in the database based on the
   *  URL provided
   *  @param url the URL
   *  @return true if successufl
   */
   public boolean select(String url) throws SQLException {
      DbServices database = new DbServices();
      if( !database.connect() )
         return false;
      Connection conn = database.getConnection();
      
      try {
         PreparedStatement getRecords = conn.prepareStatement(
            "SELECT * FROM WebSite WHERE url = ?");
         getRecords.setString(1, url);
         ResultSet results = getRecords.executeQuery();
         
         if(results.next())
         {
           setUrl(results.getString(1));
           setTitle(results.getString(2));
           
           /*  
           ArrayList showDatesTemp = ShowDate.select(url); 
           ArrayList newsTemp = NewsItem.select(url); 

           WebSiteLayout layoutTemp = new WebSiteLayout();
           if(layoutTemp.select(url))
              setLayout(layoutTemp);

           Bio bioTemp = new Bio();                   
           if(bioTemp.select(url))
             setBio(bioTemp);

           MP3File mp3sTemp = new MP3File();
           if(mp3sTemp.select(url))
             setMP3s(mp3sTemp);

           ImageFile imagesTemp = new ImageFile();
           if(imagesTemp.select(url))
             setImages(imagesTemp);
           */ 
           return true;
         }
         else
           return false;
      }
      catch( SQLException ex ) { throw ex; }
      finally { database.releaseConnection(); }
   }

   private String url;
   private String title;
   private WebSiteLayout layout;
 
   private Bio bio;
   private ArrayList showDates;
   private ArrayList news;
   private MP3File mp3s;
   private ImageFile images;
}
