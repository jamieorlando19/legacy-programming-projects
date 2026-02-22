<!-- Jamie Orlando -->
<!-- CISC 479 -->
<!-- Final Project -->
<!-- MuscLogErrorPage.jsp -->

<!-- Generic Error Page which catches exceptions and more importantly SQLExceptions -->
<!-- ***This code was borrowed heavily from the guestbook example in the textbook --> 

<?xml version =	"1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%-- page settings --%>
<%@ page isErrorPage = "true" %>
<%@ page import	= "java.util.*"	%>
<%@ page import	= "java.sql.*" %>

<html xmlns = "http://www.w3.org/1999/xhtml">

<head>
   <title>Error!</title>
</head>

<body bgcolor="white">
   <center><font size="5">
   <p>
      WARNING!!!<br />
<% 
      if (exception instanceof SQLException) 
      {
%>
         An SQLException
<%
      } 
      else if (exception instanceof ClassNotFoundException) 
      {
%>
         A ClassNotFoundException
<% 
      }
      else
      { 
%>
         An exception
<%
      }
%>
      occurred while interacting with the Musician's Performance Log database.
   </p>

  <p>
    The error message was:<br />
    <%= exception.getMessage() %>
  </p>

  <p>Please try again later or correct your data.</p>
</font>
</center>
</body>
</html>
