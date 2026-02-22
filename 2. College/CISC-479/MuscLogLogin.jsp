<!-- Jamie Orlando -->
<!-- CISC 479 -->
<!-- Final Project -->
<!-- MuscLogLogin.jsp -->

<!-- Creates a new user account or logs in an existing user -->

<?xml version =	"1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page import = "jsp.beans.*" %>
<%@ page errorPage = "MuscLogErrorPage.jsp" %>

<jsp:useBean id	= "login" scope = "page" class = "jsp.beans.MuscLogUsersBean" />
<jsp:useBean id = "loginnew" scope = "page" class = "jsp.beans.MuscLogUsersBean" />
<jsp:useBean id	= "loginData" scope = "request" class = "jsp.beans.MuscLogUsersDataBean" />

<html xmlns = "http://www.w3.org/1999/xhtml">

<head>
   <title>Jamie Orlando's Musician Performance Log Login Screen</title>
</head>

<body bgcolor="white">
   <center>

   <font size="5">Jamie Orlando's <br /> <i>Musician Performance Log</i></font><br />
   <font size="4"><b>Login Screen</font></b><br /><br />

<!-- Section for Existing Members -->

      <jsp:setProperty name = "login" property = "username" param = "exuser" />
      <jsp:setProperty name = "login" property = "password" param = "expass" />
<%
      if(login.getusername() == null || login.getpassword() == null)
      {
%>
         <form method =	"post" action =	"MuscLogLogin.jsp">
	    <p>Existing Members Sign In Here:</p>

	    <table>
	       <tr>
		  <td><b>Username</b></td>
		  <td><input type = "text" name = "exuser" maxlength="15" size="16" /></td>
	       </tr>
               <tr>
                  <td><b>Password</b></td>
                  <td><input type = "password" name = "expass" maxlength="15" size="16" /></td>  
               </tr>
	       <tr>
                  <td></td>
		  <td><input type = "submit" value = "Existing Member Sign In" /></td>
               </tr>
	    </table>
	 </form>
<%
      }
      else 
      {
         MuscLogUsersBean user = new MuscLogUsersBean();
         user = loginData.getUser(login.getusername());

         if(user == null)    //If no such user
         {
%>
            <jsp:forward page = "MuscLogLoginError.jsp">
               <jsp:param name = "arg" value = "1" />
            </jsp:forward>
<%
         }
         else if((user.getpassword()).compareTo(login.getpassword()) == 0)  //Password matches that in the database
         {
%>
            <jsp:forward page = "ShowView.jsp">
               <jsp:param name ="username" value ="<%= login.getusername() %>" />
               <jsp:param name ="arg" value ="." />
               <jsp:param name ="type" value="0" />
            </jsp:forward>
<%
         }
         else               //Invalid password
         {
%>
            <jsp:forward page = "MuscLogLoginError.jsp">
               <jsp:param name = "arg" value = "2" />
            </jsp:forward>
<%
         }
      }
%>

      <br /><br />

      <jsp:setProperty name = "loginnew" property = "username" param = "newuser" />
      <jsp:setProperty name = "loginnew" property = "password" param = "newpass" />

<!-- Section for New Members -->

<%
      if (loginnew.getusername() == null || loginnew.getpassword() == null)
      {
%>
         <form method = "post" action = "MuscLogLogin.jsp">
            <p>New Members Sign In Here:</p>

            <table>
               <tr>
                  <td><b>Username</b></td>
                  <td><input type = "text" name = "newuser" maxlength="15" size="16" /></td>
               </tr>
               <tr>
                  <td><b>Password</b></td>
                  <td><input type = "password" name = "newpass" maxlength="15" size="16" /></td>
               </tr>
               <tr>
                  <td></td>
                  <td colspan = "2"><input type = "submit" value = "New Member Sign In" /></td>
               </tr>
            </table>
         </form>
<%
      }
      else
      {
         MuscLogUsersBean user = new MuscLogUsersBean();
         user = loginData.getUser(loginnew.getusername());
         if(user == null)     //If no one has the username
         {
            loginData.addLogin(loginnew);
%>
            <jsp:forward page = "ShowView.jsp">
               <jsp:param name ="username" value ="<%= loginnew.getusername() %>" />
               <jsp:param name ="arg" value ="." />
               <jsp:param name ="type" value="0" />
            </jsp:forward>
<%
         }
         else                               //If the username is taken
         {
%>
            <jsp:forward page = "MuscLogLoginError.jsp">
               <jsp:param name = "arg" value = "3" />
            </jsp:forward>
<%
         }
      }
%>

</center>
</body>
</html>
