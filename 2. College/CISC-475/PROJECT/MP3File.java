package com.warthog.domain.components; 
 
import java.io.*; 
import java.sql.*; 
import com.warthog.services.*; 

/**
*  MP3File represents the band's mp3s
*
*  @author Chris Heydt & Jamie Orlando
*  @version 1.0
*/
public class MP3File  
{

   /**
   *  Initializes the MP3File
   */
   public MP3File() {}

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
   *  Sets the song title at the specified index
   *  @param i the index
   *  @param songTitle the title of the song
   */
   public void setSongTitle( int i, String songTitle ) throws ArrayIndexOutOfBoundsException 
   { 
      this.songTitles[i] = songTitle; 
   }

   /**
   *  Gets the song title at the specified index
   *  @param i the index
   *  @return the song title
   */
   public String getSongTitle( int i ) throws ArrayIndexOutOfBoundsException 
   { 
      return this.songTitles[i]; 
   }

   /**
   *  Sets the MP3 path at the specified index
   *  @param i the index
   *  @param path the path of the MP3
   */
   public void setPath( int i, String path ) throws ArrayIndexOutOfBoundsException 
   { 
      this.paths[i] = path; 
   }

   /**
   *  Gets the song path at the specified index
   *  @param i the index
   *  @return the path
   */
   public String getPath( int i ) throws ArrayIndexOutOfBoundsException  
   { 
      return this.paths[i]; 
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
         String update = "UPDATE MP3File SET path1 ='" +
            this.paths[0] + "', path2 ='" +
            this.paths[1] + "', path3 ='" +
            this.paths[2] + "', path4 ='" +
            this.paths[3] + "', path5 ='" +
            this.paths[4] + "', songTitle1 = '" +
            this.songTitles[0] + "', songTitle2 = '" +
            this.songTitles[1] + "', songTitle3 = '" +
            this.songTitles[2] + "', songTitle4 = '" +
            this.songTitles[3] + "', songTitle5 = '" +
            this.songTitles[4] + "' WHERE url='" + this.url + "'";
         if( connStatement.executeUpdate( update ) == 0 )
         {
            String insert = "INSERT INTO MP3File VALUES('" +
               this.url + "','" + this.paths[0] + "','" +
               this.paths[1] + "','" + this.paths[2] + "','" +
               this.paths[3] + "','" + this.paths[4] + "','" +
               this.songTitles[0] + "','" + this.songTitles[1] + "','" +
               this.songTitles[2] + "','" + this.songTitles[3] + "','" +
               this.songTitles[4] + "')";
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
         PreparedStatement getRecords = conn.prepareStatement("SELECT * FROM MP3File WHERE url = ?");
         getRecords.setString(1, url);
         ResultSet results = getRecords.executeQuery();
         
         if(results.next())
         { 
           setUrl(results.getString(1));
           setPath(0, results.getString(2));
           setPath(1, results.getString(3));
           setPath(2, results.getString(4));
           setPath(3, results.getString(5));
           setPath(4, results.getString(6));
           setSongTitle(0, results.getString(7));
           setSongTitle(1, results.getString(8));
           setSongTitle(2, results.getString(9));
           setSongTitle(3, results.getString(10));
           setSongTitle(4, results.getString(11));
           return true;
         }
         return false;
      }
      catch( SQLException ex ) { throw ex; }
      finally { database.releaseConnection(); }
   }
   
   // useful static variable for loops setting and getting MP3File objects
   public static final int MAX_MP3S = 5;
   private String url; 
   private String[] songTitles = new String[MAX_MP3S];
   private String[] paths = new String[MAX_MP3S];
}
