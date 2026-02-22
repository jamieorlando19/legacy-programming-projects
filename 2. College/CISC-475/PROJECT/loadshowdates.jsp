<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<%@ page contentType="text/html" 
   import="com.warthog.domain.core.*,com.warthog.domain.components.*,
 	  java.util.*" %>
<% String url = (String)session.getAttribute("url");
   WebSite website = new WebSite();
   WebSiteLayout layout = new WebSiteLayout();
   Band band = new Band();
   website.select(url);
   layout.select(url);
   band.select(url);
   ArrayList newShows = ShowDate.select(url);
   ArrayList oldShows = new ArrayList( newShows.size()/2 ); 
   String today = 
      (new java.sql.Date(System.currentTimeMillis())).toString(); 
   // loop through list, remove old shows and put them in separate list
   for( int i = 0; i < newShows.size(); ) 
   {
      ShowDate show = (ShowDate) newShows.get(i);
      if( show.compareTo(today) < 0 ) 
      {   
	  newShows.remove(i);
          oldShows.add(show);
      }
      else i++; 
   }
%>
<title>Show Dates For <%= band.getBandName() %></title>
	<style type="text/css">
		body {  background-color:
               		<%= layout.getBackgroundColor() %>;
                       	color:
                       	<%= layout.getTextColor() %>;
        </style>
</head>
<body>
<table width="90%" border="0" cellpadding="0" cellspacing="5">
   <tr>
      <td colspan="7" align="center">
         <h1 style="text-align: center">
         <%=website.getTitle()%>:Shows</h1>
      </td>
   </tr>
   <% if( newShows.isEmpty() ) 
      {
   %>
   <tr><td><i>No current or future show dates</i></td></tr>
   <% }  
      else 
      {
   %>
   <tr>
      <td align="left"><b>Date</b></td>
      <td width="60"></td>
      <td align="left"><b>Venue</b></td>
      <td width="60"></td>
      <td align="left"><b>Location</b></td>
      <td width="60"></td>
      <td width="80%" align="left"><b>Comments</b></td>
   </tr>
   <tr><td><br></td></tr>
   <% }
      for( int i = 0; i < newShows.size(); i++ ) 
      { 
	 ShowDate show = (ShowDate) newShows.get(i); 
   %>
   <tr>
   <tr>
      <td align="left"><i><%= show.getDate()  %></i></td>
      <td width="60"></td>
      <td width="100" align="left"><%= show.getVenue() %></td>
      <td width="60"></td>
      <td align="left">
         <nobr><%= show.getCity() %>, <%= show.getState() %></nobr>
      </td>
      <td width="60"></td>
      <td align="left"><%= show.getComments() %></td>
   </tr>
   <tr><td colspan="7" width="100%" align="center"><hr><br></td></tr>
   <% } 
      if( !oldShows.isEmpty() ) 
      {
   %>
   <tr>
      <td colspan="7" align="center">
         <h2 style="text-align: center">Past Show Dates</h2>
      </td>
   </tr>
   <tr>
      <td align="left"><b>Date</b></td>
      <td width="60"></td>
      <td align="left"><b>Venue</b></td>
      <td width="60"></td>
      <td align="left"><b>Location</b></td>
      <td width="60"></td>
      <td align="left"><b>Comments</b></td>
   </tr>
   <tr><td><br></td></tr>
   <% }
      for( int i = 0; i < oldShows.size(); i++ ) 
      {
         ShowDate show = (ShowDate) oldShows.get(i); 
   %>
   <tr>
      <td align="left"><i><%= show.getDate()  %></i></td>
      <td width="60"></td>
      <td width="100" align="left"><%= show.getVenue() %></td>
      <td width="60"></td>
      <td align="left">
         <nobr><%= show.getCity() %>, <%= show.getState() %></nobr>
      </td>
      <td width="60"></td>
      <td align="left"><%= show.getComments() %></td>
   </tr>
   <tr><td colspan="7" width="100%" align="center"><hr></td></tr>
   <% }		 
      if( layout.getLayoutType().compareTo("2") != 0 ) 
      { 
   %>
   <tr>
      <td colspan="7" align="center">
         <a href="/warthog/<%= url %>">Home</a>
      </td>
   </tr>
   <% } %>
</table>
</body>
</html>
