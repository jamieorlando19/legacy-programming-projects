<!-- Jamie Orlando -->
<!-- CISC 479 -->
<!-- Final Project -->
<!-- ShowNiceView.jsp -->

<!-- Displays detailed info about a show -->

<?xml version =	"1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%-- page settings --%>
<%@ page errorPage = "MuscLogErrorPage.jsp" %>
<%@ page import	= "java.util.*"	%>
<%@ page import = "java.lang.*" %>
<%@ page import	= "jsp.beans.*" %>

<jsp:useBean id = "showData" scope = "request" class = "jsp.beans.ShowDataBean" />

<%
   String username = request.getParameter("username");
   int year = Integer.parseInt(request.getParameter("year")),
       month = Integer.parseInt(request.getParameter("month")),
       day = Integer.parseInt(request.getParameter("day"));
   ShowBean show = showData.getTheShow(username, year, month, day);    //Info about the show
%>

<html xmlns = "http://www.w3.org/1999/xhtml">

   <head>
      <title>Details for <%= username %>'s  show</title>
   </head>

   <body bgcolor="white">
   <center>
      <font size="5">Details for <%= username %>'s 

<%
   if(month != 0)
   {
%>
      <%= month %>/
<%
   }
   if(day != 0)
   {
%>
      <%= day %>/
<%
   }
%>

   <%= year %> show<br /><br />

<%
   String bandname = show.getbandname(),
         bandname2 = bandname.replace(' ', '_');
%>

      <a href="BandNiceView.jsp?username=<%= username %>&bandname=<%=bandname2%>"><%= bandname %></a> 
      Live in <%= show.getshowcity() %>, <%=show.getshowstate() %></font> <br /><br />

<%
      //Lists bandmembers
      if(show.getbandmembers() != null)
      {
         StringTokenizer memtok = new StringTokenizer(show.getbandmembers(), ",");
%>
         <font size="4">Band Member(s):</font><br />

         <table><tr><td>
         <ul>
<%
         while(memtok.hasMoreTokens())
         {
            String member = memtok.nextToken();
%>
            <li><a href="ShowView.jsp?username=<%=username%>&arg=<%=member.replace(' ', '_')%>&type=2"><%=member%></a></li>  
<%
         }
%>       
        </ul>
        </td></tr></table>
<%
      }
%>

<br /><br />

<%
      //Lists the setlist
      if(show.getsetlist() != null)
      {
         StringTokenizer settok= new StringTokenizer(show.getsetlist(), ",");
%>
         <font size="4">Setlist:</font><br />
       
         <table><tr><td>
         <ul>
<%
         while(settok.hasMoreTokens())
         {  
            String song = settok.nextToken();
%>
            <li><a href="ShowView.jsp?username=<%=username%>&arg=<%=song.replace(' ','_')%>&type=1"><%= song %></a></li>
<%
         }
%>
        </ul>
        </td></tr></table>
<%
      }
%>

      <br /><br />

<%
      if(show.getjournal() != null)
      {
%>
         <font size="4">Journal:</font><br />

         <table border = "0" width = "50%">
         <tr>
            <td>
             <center>
               <%= show.getjournal() %>
             </center>
            </td>
         </tr>
         </table>
<%
      }
%>

      <br /><br />
      <a href = "ShowView.jsp?username=<%= username %>&arg=0&type=.">View All Shows</a><br />
      <a href = 
   "ShowEdit.jsp?username=<%= username %>&year=<%= year%>&month=<%= month%>&day=<%= day%>&submitted=0">
         Edit this show</a><br />
      <a href = "ShowCreate.jsp?username=<%= username %>">Create new show</a> <br />
      <a href = 
   "ShowDelete.jsp?username=<%= username %>&year=<%= year%>&month=<%= month%>&day=<%= day%>&submitted=0">
         Delete this show</a> 
   </center>
   </body>
</html>
