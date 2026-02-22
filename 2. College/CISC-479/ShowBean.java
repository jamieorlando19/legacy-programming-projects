//Jamie Orlando
//CISC 479
//Final Project
//ShowBean.java

//Bean which holds data about a band

package jsp.beans;

public class ShowBean {

   private String username, bandname, setlist, journal, bandmembers, showvenue, showcity, showstate;
   private int showyear, showmonth, showday;

   //Set the username
   public void setusername(String name)
   { username = name; }
   
   //Get the username
   public String getusername()
   { return username;  }

   //Set the bandname
   public void setbandname(String name)
   { bandname = name; }

   //Get the bandname
   public String getbandname()
   { return bandname; }

   //Set the year of the show
   public void setshowyear(int year)
   { showyear = year; }

   //Get the year of the show
   public int getshowyear()
   { return showyear; }

   //Set the month of the show
   public void setshowmonth(int month)
   { showmonth = month; } 
 
   //Get the month of the show
   public int getshowmonth()   
   { return showmonth; }

   //Set the day of the show
   public void setshowday(int day)
   { showday = day; } 
 
   //Get the day of the show
   public int getshowday()   
   { return showday; }

   //Set the setlist of the show
   public void setsetlist(String list)
   { setlist = list; }

   //Get the setlist of the show
   public String getsetlist()
   { return setlist; }

   //Set the journal for the show
   public void setjournal(String journ)
   { journal = journ; }

   //Get the journal for the show
   public String getjournal()
   { return journal; }

   //Set the band members
   public void setbandmembers(String members)
   { bandmembers = members; }

   //Get the band members
   public String getbandmembers()
   { return bandmembers; }

   //Set the venue of the show
   public void setshowvenue(String venue)
   { showvenue = venue; }

   //Get the venue of the show
   public String getshowvenue()
   { return showvenue; }

   //Set the city of the show
   public void setshowcity(String city)
   { showcity = city; }

   //Get the city of the show
   public String getshowcity()
   { return showcity; }

   //Set the state of the show
   public void setshowstate(String state)
   { showstate = state; }

   //Get the state of the show
   public String getshowstate()
   { return showstate; }
}
