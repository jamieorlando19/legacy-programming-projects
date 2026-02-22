<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<%@ page contentType="text/html" 
	import="com.warthog.domain.core.*, 
		com.warthog.domain.components.*" %>

<% 
	String url = (String)session.getAttribute("url");
   	WebSite website = new WebSite();
   	WebSiteLayout layout = new WebSiteLayout();
   	Band band = new Band();
   	MP3File mp3s = new MP3File();
   	website.select(url);
   	layout.select(url);
   	band.select(url);
   	mp3s.select(url);
   	String mp3Root = "/warthog/uploads/mp3s/";
%>
	<title>MP3s For <%= band.getBandName() %></title>

	<style type="text/css">
		body {  background-color:
               		<%= layout.getBackgroundColor() %>;
               		color:
                       	<%= layout.getTextColor() %>;
                       }
        </style>

</head>
<body>
<h1 style="text-align: center"><%=website.getTitle()%>: Mp3s</h1>
<center>
<table border="0" cellpadding="0" cellspacing="2">
   <% int i; 
      for( i = 0; i < MP3File.MAX_MP3S && 
	 mp3s.getPath(i).compareTo("") != 0; i++ ) 
      { 
   %>
   <tr>
      <td align="center">
         <a href="<%= mp3Root + mp3s.getPath(i) %>">
	    <%= mp3s.getSongTitle(i) %></a>
      </td>
   </tr>
   <% } 
      if( i == 0 ) 
      { 
    %>
   <tr><td align="center"><i>No MP3 files available</i><br></td></tr>
   <% }
      if( layout.getLayoutType().compareTo("2") != 0 ) 
      { 
   %>
   <tr>
      <tr><td><br></td></tr>
      <td align="center">
	 <a href="/warthog/<%= url %>">Home</a>
      </td>
   </tr>
   <% } %>
</table>
</center>
</body>
</html>
