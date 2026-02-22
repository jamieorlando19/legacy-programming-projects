<!-- Jamie Orlando -->
<!-- CISC 479 -->
<!-- Final Project -->
<!-- BandDelete.jsp -->

<!-- Deletes a band from the database and all its corresponding shows -->

<?xml version =	"1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%-- page settings --%>
<%@ page errorPage = "MuscLogErrorPage.jsp" %>
<%@ page import	= "java.util.*"	%>
<%@ page import	= "jsp.beans.*" %>
<%@ page import = "java.sql.*" %>

<jsp:useBean id = "bandData" scope = "request" class = "jsp.beans.BandsMusiciansDataBean" />
<jsp:useBean id = "showData" scope = "request" class = "jsp.beans.ShowDataBean" />

<html xmlns = "http://www.w3.org/1999/xhtml">

<%
   String username = request.getParameter("username"),   
          bandname = request.getParameter("bandname"),      //Bandname with underscores
          bandname2 = bandname.replace('_', ' ');           //Bandname with spaces
   int submitted = Integer.parseInt(request.getParameter("submitted"));    //If page was submitted
%>

   <head>
      <title>Deleting <%= bandname2 %> </title>
   </head>

   <body bgcolor="white">
   <center>
      <font size = "5">
         Deleting <%= bandname2 %>
      </font><br /><br />

<%
      //If button wasn't pressed
      if(submitted == 0)
      {
%>
         Warning: Erasing this band will erase all this band's shows<br />
         Are you sure you want to delete this band?<br /><br />
         <table>
            <tr>
               <td><font size="5">
     <a href ="BandDelete.jsp?username=<%=username%>&bandname=<%=bandname%>&submitted=1">
                        YES</a></font></td>
               <td><font size="5">
     <a href="BandNiceView.jsp?username=<%= username %>&bandname=<%=bandname%>">
                  NO</a>
               </font></td>
            </tr>
         </table>
<%           
      }
      //If button was pressed
      else
      {
         //Delete bands and shows where the bandname exists
         bandData.deleteTheBand(username, bandname2);
         showData.deleteTheBandShow(username, bandname2);
%>
         <jsp:forward page = "BandView.jsp">
            <jsp:param name ="username" value ="<%= username %>" />
            <jsp:param name ="bandname" value ="<%= bandname %>" />
         </jsp:forward>
<%
      }
%>
   </center>
   </body>
</html>
