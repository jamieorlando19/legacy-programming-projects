<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<%@ page contentType="text/html" import="com.warthog.domain.core.*" %>
<%@ page contentType="text/html" import="com.warthog.domain.components.*" %>
<% String url = (String)session.getAttribute("url");
   WebSite website = new WebSite();
   WebSiteLayout layout = new WebSiteLayout();
   Band band = new Band();
   ImageFile images = new ImageFile();
   website.select(url);
   layout.select(url);
   band.select(url);
   images.select(url);
   String imagesRoot = "/warthog/uploads/images/";
%>
<title>Images For <%= band.getBandName() %></title>
        <style type="text/css">
                body {  background-color:
                        <%= layout.getBackgroundColor() %>;
                        color:
                        <%= layout.getTextColor() %>;
                       }
        </style>
</head>
<body>

<h1 style="text-align: center"><%=website.getTitle()%>:Images</h1>
<center>
<table border="0" cellpadding="0" cellspacing="2">
   <% int i; 
      for( i = 0; i < ImageFile.MAX_IMAGES &&
	 images.getPath(i).compareTo("") != 0; i++ ) 
      { 
   %>
   <tr>
      <td align="center">
         <a href="<%= imagesRoot + images.getPath(i) %>">
	    <%= images.getCaption(i) %></a>
      </td>
   </tr>
   <% } 
      if( i == 0 ) 
      {
   %>
   <tr><td align="center"><i>No images available</i></td></tr>
   <% } 
      if( layout.getLayoutType().compareTo("2") != 0 ) 
      { 
    %>
   <tr>
      <td><td><br></td></tr>
      <td colspan="7" align="center">
         <a href="/warthog/<%= url %>">Home</a>
      </td>
   </tr>
   <% } %>
</table>
</center>
</body>
</html>
