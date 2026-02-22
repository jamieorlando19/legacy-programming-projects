// Chris Heydt
// Website class

package com.warthog.domain.core;

import java.io.*;
import java.sql.*;
import java.util.*;
import com.warthog.services.*;

public class WebSite {
   public WebSite() {}
   public void setUrl( String url ) {
      this.url = url;
   }
   public String getUrl() {
      return this.url;
   }
   public String setTitle( String title ) {
      return this.title = title;
   }
   public String getTitle() {
      return this.title;
   }
   /* public void setBio( Bio bio ) {
      this.bio = bio;
   }
   public Bio getBio() {
      return this.bio;
   }
   public void addShowDate( ShowDate date ) {
      this.showDates.add( date );
   }
   public Vector getShowDates( Date today ) {
      // arguments to vector constructor are initial capacity, growth increment 
      Vector currentAndFutureShowDates = new Vector( 50, 25 );
      for( Iterator i = this.showDates.iterator(); i.hasNext(); ) { 
	 ShowDate sdate = i.next(); 
         if( sdate.compareTo(today) >= 0 )
            // add show dates occuring before or after today to vector
            currentAndFutureShowDates.add( sdate );
      }
      return currentAndFutureShowDates; 
   }
   public void addNews( NewsItem news ) {
      this.news.add( news );
   }
   public Vector getNews() {
      return this.news;
   }
   public void addMP3( MP3File mp3 ) throws Exception {
      for( int i = 0; i < MAX_MP3s; i++ )
         if( this.mp3s[i] == null ) {
            this.mp3s[i] = mp3;
            return;
         }
      throw new Exception( "WebSite.addMP3: attempted to add to full array" );
   }
   public MP3File[] getMp3s() {
      return this.mp3s;
   }
   public void addImage( ImageFile image ) throws Exception {
      for( i = 0; i < MAX_IMAGES; i++ )  
         if( this.images[i] == null ) {
            // add image to this unfilled position in the array 
            this.images[i] = images;
            return;
         }
      throw new Exception( "WebSite.addImage: attempted to add to full array" );
   } 
   public ImageFile[] getImages() {
      return this.images;
   }
   */
   public void save() throws SQLException {
      Statement connStatement;
      DbServices database = new DbServices();
      if( !database.connect() )  
         return;
      Connection conn = database.getConnection();
      try {
         connStatement = conn.createStatement();
	 String update = "UPDATE WebSite SET title='" + this.title + 
	    "' WHERE url='" + this.url + "'";	 
         if( connStatement.executeUpdate( update ) == 0 ) {
            String insert = "INSERT INTO WebSite(url, title) values(" +
	       this.url + "," + this.title + ")";
            int returnVal = connStatement.executeUpdate( insert ); 
         }
         /* 
         this.layout.save();  
         this.Bio.save();  
         // save the show dates associated with this site
         for( Iterator i = showDates.iterator(); i.hasNext(); ) 
            i.next().save();
         // save the news items associated with this site
         for( Iterator i = news.iterator(); i.hasNext(); ) 
            i.next().save();
         // save the MP3s associated with this site
         for( int i = 0; i < MAX_MP3s && mp3s[i] != null; i++ ) 
            mp3s[i].save();    
         // save the images associated with this site
         for( int i = 0; i < MAX_IMAGES && images[i] != null; i++ ) 
            images[i].save();    
         */
      }
      // do nothing if createStatement or insertion threw an exception 
      catch( SQLException ex ) { throw ex; }
      finally { database.releaseConnection(); }
   }
   private String url;
   private String title; 
   private WebSiteLayout layout;
   /*
   private Bio bio;
   private Vector showDates;
   private Vector news;
   private MP3File mp3s[];
   private ImageFile images[]; */

   static final int MAX_MP3s = 5;
   static final int MAX_IMAGES = 5;
}


