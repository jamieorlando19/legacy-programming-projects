<!-- Jamie Orlando -->
<!-- CISC 479 -->
<!-- Final Project -->
<!-- BandView.jsp -->

<!-- Displays a list of all bands -->

<?xml version =	"1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%-- page settings --%>
<%@ page errorPage = "MuscLogErrorPage.jsp" %>
<%@ page import	= "java.util.*"	%>
<%@ page import	= "jsp.beans.*" %>

<jsp:useBean id = "bandData" scope = "request" class = "jsp.beans.BandsMusiciansDataBean" />

<html xmlns = "http://www.w3.org/1999/xhtml">

<%
   String username = request.getParameter("username");
%>

   <head>
      <title>List of <%= username %>'s bands</title>
   </head>

   <body bgcolor = "white">

   <center>
      <font size="5">
         List of <%= username %>'s bands
      </font><br /><br />
<%
      List bandlist = bandData.getList(username);   //A list of all the bands
      if(bandlist.size() == 0)
      {
%>
         No bands entered yet.
<%
      }
      else
      {
         Iterator bandit = bandlist.iterator();
         BandsMusiciansBean curband;

         //Make a bulleted list of the bands
%>
         <table><tr><td>
         <ul>
<%
         while (bandit.hasNext())
         {
            curband = (BandsMusiciansBean)bandit.next();             
%>
            <li><font size="4">

        <a href="BandNiceView.jsp?username=<%= username%>&bandname=<%= (curband.getbandname()).replace(' ', '_') %>">
                <%=curband.getbandname()%></a></font></li>
<%
         }
      }
%>
         </ul>
         </td></tr></table>
      <br /><br />
      <a href = "NewBand.jsp?username=<%= username %>&refer=2&year=0&month=0&day=0">Add a new band</a><br />
      <a href = "ShowView.jsp?username=<%= username %>&type=0&arg=.">View all shows</a><br />
      <a href= "MuscLogLogin.jsp">Log Out</a>
   </center>
   </body>
</html>
