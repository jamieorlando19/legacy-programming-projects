package com.warthog.domain.components; 
 
import java.io.*; 
import java.sql.*; 
import com.warthog.services.*; 


/**
*  ImageFile represents the band's images
*
*  @author Chris Heydt & Jamie Orlando
*  @version 1.0
*/
public class ImageFile  
{ 

   /**
   *  Initializes the ImageFile
   */
   public ImageFile() {}

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
   *  Sets the image caption at the specified index
   *  @param i the index
   *  @param caption the caption of the image
   */
   public void setCaption( int i, String caption ) throws ArrayIndexOutOfBoundsException 
   { 
      this.captions[i] = caption; 
   } 

   /**
   *  Gets the image caption at the specified index
   *  @param i the index
   *  @return the caption
   */
   public String getCaption( int i ) throws ArrayIndexOutOfBoundsException
   { 
      return this.captions[i];  
   } 

   /**
   *  Sets the image path at the specified index
   *  @param i the index
   *  @param path the path of the image
   */
   public void setPath( int i, String path ) throws ArrayIndexOutOfBoundsException
   { 
      this.paths[i] = path; 
   } 

   /**
   *  Gets the image path at the specified index
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
         String update = "UPDATE ImageFile SET path1 ='" + 
            this.paths[0] + "', path2 ='" + 
            this.paths[1] + "', path3 ='" + 
            this.paths[2] + "', path4 ='" + 
            this.paths[3] + "', path5 ='" + 
            this.paths[4] + "', caption1 = '" + 
            this.captions[0] + "', caption2 = '" + 
            this.captions[1] + "', caption3 = '" + 
            this.captions[2] + "', caption4 = '" + 
            this.captions[3] + "', caption5 = '" + 
            this.captions[4] + "' WHERE url='" + this.url + "'";
         if( connStatement.executeUpdate( update ) == 0 )
         {
            String insert = "INSERT INTO ImageFile VALUES('" +
               this.url + "','" + this.paths[0] + "','" +   
               this.paths[1] + "','" + this.paths[2] + "','" +
               this.paths[3] + "','" + this.paths[4] + "','" +
               this.captions[0] + "','" + this.captions[1] + "','" +
               this.captions[2] + "','" + this.captions[3] + "','" +
               this.captions[4] + "')";
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
         PreparedStatement getRecords = conn.prepareStatement("SELECT * FROM ImageFile WHERE url = ?");
         getRecords.setString(1, url);
         ResultSet results = getRecords.executeQuery();
      
         if(results.next())
         {
           setUrl(results.getString(1));
           setPath(0, results.getString(2));
           setPath(1, results.getString(2));
           setPath(2, results.getString(3));
           setPath(3, results.getString(4));
           setPath(4, results.getString(5));
           setCaption(0, results.getString(7));
           setCaption(1, results.getString(8));
           setCaption(2, results.getString(9));
           setCaption(3, results.getString(10));
           setCaption(4, results.getString(11));
           return true;
         }
         return false;
      }
      catch( SQLException ex ) { throw ex; }
      finally { database.releaseConnection(); }
   }   

  
   private String url; 
   private String[] captions = new String[5]; 
   private String[] paths = new String[5]; 
}
