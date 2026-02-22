//Jamie Orlando
//CISC 479
//Final Project
//BandsMusiciansBean.java

//Bean which holds data about a band

package jsp.beans;

public class BandsMusiciansBean {

   private String username,
                  bandname,
                  bandbio;
   int yearformed;

   //Set the username
   public void setusername(String name)
   { username = name; }

   //Get the username
   public String getusername()
   { return username; }

   //Set the bandname
   public void setbandname(String name)
   { bandname = name; }
   
   //Get the bandname
   public String getbandname()
   { return bandname;  }

   //Set the band's bio
   public void setbandbio(String bio)
   { bandbio = bio; }

   //Get the band's bio
   public String getbandbio()
   { return bandbio; }

   //Set the year the band was formed
   public void setyearformed(int year)
   { yearformed = year; }

   //Get the year the band was formed
   public int getyearformed()
   { return yearformed; }
}
