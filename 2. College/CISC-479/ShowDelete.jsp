<!-- Jamie Orlando -->
<!-- CISC 479 -->
<!-- Final Project -->
<!-- ShowDelete.jsp -->

<!-- Deletes a show from the database  -->

<?xml version =	"1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%-- page settings --%>
<%@ page errorPage = "MuscLogErrorPage.jsp" %>
<%@ page import	= "java.util.*"	%>
<%@ page import	= "jsp.beans.*" %>
<%@ page import = "java.sql.*" %>

<jsp:useBean id = "showData" scope = "request" class = "jsp.beans.ShowDataBean" />

<html xmlns = "http://www.w3.org/1999/xhtml">

<%
   String username = request.getParameter("username");
   int year = Integer.parseInt(request.getParameter("year")),
       month = Integer.parseInt(request.getParameter("month")),
       day = Integer.parseInt(request.getParameter("day")),
       submitted = Integer.parseInt(request.getParameter("submitted"));  //if the form was submitted
%>

   <head>
      <title>Deleting <%= username %>'s  show</title>
   </head>

   <body bgcolor="white">
   <center>
      <font size = "5">
         Deleting <%= username %>'s 
      
<%
      if(month == 0)
      {
%>
         ??
<%
      }
      else
      {
%>
         <%= month %>/
<%
      }
%>

<%
      if(day == 0)
      {
%>
         ??
<%
      }
      else
      {
%>
         <%= day %>/
<%
      } 
%>

      <%= year %> show
      </font><br /><br />

<%
      if(submitted == 0)
      {
%>
         Are you sure you want to delete this show?<br /><br />
         <table>
            <tr>
               <td><font size="5">
     <a href ="ShowDelete.jsp?username=<%=username%>&year=<%=year%>&month=<%=month%>&day=<%=day%>&submitted=1">
                        YES</a></font></td>
               <td><font size="5">
     <a href="ShowNiceView.jsp?username=<%= username %>&year=<%= year %>&month=<%= month %>&day=<%= day %>">
                  NO</a>
               </font></td>
            </tr>
         </table>
<%           
      }
      //If yes was clicked
      else
      {
         showData.deleteTheShow(username, year, month, day);
%>
            <jsp:forward page = "ShowView.jsp">
               <jsp:param name ="username" value ="<%= username %>" />
               <jsp:param name ="arg" value ="." />
               <jsp:param name ="type" value="0" />
            </jsp:forward>
<%
      }
%>
   </center>
   </body>
</html>
