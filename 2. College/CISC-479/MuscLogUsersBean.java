//Jamie Orlando
//CISC 479
//Final Project
//MuscLogUsersBean.java

//Bean which holds data about a user

package jsp.beans;

public class MuscLogUsersBean {

   private String username, password;

   //Set the username
   public void setusername( String name )
   { username = name; }
   
   //Get the username
   public String getusername()
   { return username;  }

   //Set the password
   public void setpassword( String pw )
   { password = pw; }

   //Get the password
   public String getpassword()
   { return password; }
}
