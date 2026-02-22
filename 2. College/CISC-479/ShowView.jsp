<!-- Jamie Orlando -->
<!-- CISC 479 -->
<!-- Final Project -->
<!-- ShowView.jsp -->

<!-- Displays a list of all shows -->

<?xml version =	"1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%-- page settings --%>
<%@ page errorPage = "MuscLogErrorPage.jsp" %>
<%@ page import	= "java.util.*"	%>
<%@ page import	= "jsp.beans.*" %>

<jsp:useBean id = "showData" scope = "request" class = "jsp.beans.ShowDataBean" />

<%
         String username = request.getParameter("username"),
                arg = (request.getParameter("arg")).replace('_', ' ');   //Argument (song or musician)
         int type = Integer.parseInt(request.getParameter("type"));      //Type of filter
%>


<html xmlns = "http://www.w3.org/1999/xhtml">

   <head>
      <title>List of <%= username %>'s shows</title>
   </head>

   <body bgcolor="white">

   <center>
      <font size="5">List of shows for <%= username %>
<%
       //Song filter
       if(type == 1)
       {
%>
          , where <%= arg %> was played
<%
       }
       //Musician filter
       else if(type == 2)
       {
%>
          played with <%= arg %>
<%
       }
%>
       </font><br /><br />
       <table border = 2>
         <tr>
            <td><b>Year</b></td>
            <td><b>Month</b></td>
            <td><b>Day</b></td>
            <td><b>Band</b></td>
            <td><b>State</b></td>
            <td><b>City</b></td>
            <td><b>Venue</b></td>
         </tr>
<%       
         List showList = showData.getList(username, type, arg);        

         Iterator showit = showList.iterator();
         ShowBean show;
         int numshows = 0;  
         while(showit.hasNext()) 
         {
            numshows++;
            show = ( ShowBean )showit.next();
%>
            <tr>
               <td><%= show.getshowyear() %></td>
               <td>
<%
                  if(show.getshowmonth() == 0)
                  {
%>
                     ??
<%
                  }
                  else
                  {
%>
                     <%= show.getshowmonth() %>   
<%
                  }
%>
               </td>
               <td>
<%
                  if(show.getshowday() == 0)
                  {
%>
                     ??
<%
                  }
                  else
                  {
%>
                     <%= show.getshowday() %>     
<% 
                  }
%>
               </td>
               <td><%= show.getbandname() %></td>
               <td><%= show.getshowstate() %></td>
               <td>
<%
                  if(show.getshowcity() == null)   
                  {
%>
                     ?????
<%
                  }
                  else
                  {
%>
                     <%= show.getshowcity() %>
<%
                  }
%>
               </td>
               <td>
<%
                  if(show.getshowvenue() == null)   
                  {
%>
                     ?????
<%
                  }
                  else
                  {
%>
                     <%= show.getshowvenue() %>
<%
                  }
%>
               </td>
               <td>
                  <b>
   <a href="ShowNiceView.jsp?username=<%= username %>&year=<%= show.getshowyear() %>&month=<%= show.getshowmonth() %>&day=<%= show.getshowday() %>">
                     More Info</a>
                  </b>
               </td>
            </tr>
<%
      } 
%>
      </table>
<%
   if(numshows == 0)
   {
%>
      <b>No shows entered yet!</b><br /><br />
<%
   }
%>
   <br /><br />
<%
   if(type == 1 || type == 2)
   {
%>
      <a href="ShowView.jsp?username=<%=username%>&type=0&arg=.">View All Shows</a><br />
<%
   }
%>
   <a href = "ShowCreate.jsp?username=<%= username %>">Add A New Show</a><br />
   <a href = "BandView.jsp?username=<%= username %>">View All Bands</a><br />
   <a href= "MuscLogLogin.jsp">Log Out</a>

   </center>
   </body>
</html>
