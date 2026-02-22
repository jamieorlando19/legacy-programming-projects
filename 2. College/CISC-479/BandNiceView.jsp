<!-- Jamie Orlando -->
<!-- CISC 479 -->
<!-- Final Project -->
<!-- BandNiceView.jsp -->

<!-- Displays detailed info about a band -->

<?xml version =	"1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%-- page settings --%>
<%@ page errorPage = "MuscLogErrorPage.jsp" %>
<%@ page import	= "java.util.*"	%>
<%@ page import = "java.lang.*" %>
<%@ page import	= "jsp.beans.*" %>

<jsp:useBean id = "bandData" scope = "request" class = "jsp.beans.BandsMusiciansDataBean" />

<%
   String username = request.getParameter("username"),
          bandname = request.getParameter("bandname"),    //Bandnname with underscores
          bandname2 = bandname.replace('_', ' ');         //Bandname with spaces
%>

<html xmlns = "http://www.w3.org/1999/xhtml">

   <head>
      <title>Details for <%= bandname2 %></title>
   </head>

   <body bgcolor="white">
   <center>
      <font size="5">Details for <%= bandname2 %></font><br /><br />

<%
      BandsMusiciansBean band = bandData.getTheBand(username, bandname2);

      if(band.getyearformed() != 0)
      {
%>
         <font size="4">
           Formed in: <%= band.getyearformed() %><br /><br />
         </font>
<%
      }

      if(band.getbandbio() != null)
      {
%>

         <font size="5">Band Bio:</font><br />

         <table border = "0" width = "50%">
         <tr>
            <td>
            <center>
               <%= band.getbandbio() %>
            </center>
            </td>
         </tr>
         </table><br /><br />
<%
      }
%>

      <br /><br />
      <a href = "BandView.jsp?username=<%= username %>">View all bands</a><br />
      <a href = "BandEdit.jsp?username=<%= username %>&bandname=<%= bandname %>&submitted=0">Edit this band</a><br />
      <a href = "NewBand.jsp?username=<%= username %>">Create a new band</a> <br />
      <a href = "BandDelete.jsp?username=<%= username %>&bandname=<%= bandname %>&submitted=0">Delete this band</a> 
   </center>
   </body>
</html>
