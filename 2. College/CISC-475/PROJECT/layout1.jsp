<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<%@ page contentType="text/html" import="com.warthog.domain.core.*,
   com.warthog.domain.components.*" %>
<% String url = (String)session.getAttribute("url"); 
   WebSite website = new WebSite();
   Band band = new Band(); 
   WebSiteLayout layout = new WebSiteLayout();
   website.select(url);
   band.select(url);
   layout.select(url);
   BandAdministrator admin = band.getBandAdministrator();
   Bio bio = new Bio();
   bio.select(url);
%>
<title><%= website.getTitle() %></title>
</head>
<body> 
 <style type="text/css">
    body {  background-color:
	    <%= layout.getBackgroundColor() %>;
	    color:
	    <%= layout.getTextColor() %>;
    }
 </style>
<h1 style="text-align: center"><%= website.getTitle() %></h1>
<table border="0" cellpadding="0" cellspacing="0">
   <tr>
      <td align="center">
	 <table border="0" cellpadding="0" cellspacing="0">
	    <tr>
	       <td align="center">
		  <a href="/warthog/loadimages.jsp?url=<%= url %>">
		     Images</a>
	       </td>
	       <td width="80" align="center"></td>
	       <td align="center">
		  <a href="/warthog/loadmp3s.jsp?url=<%= url %>">MP3s</a>
	       </td>
	       <td width="80" align="center"></td>
	       <td align="center">
		  <a href="/warthog/loadshowdates.jsp?url=<%= url %>">
		     Show Dates</a>
	       </td>
	       <% if( layout.getHasChatRoom() )  { %>
	       <td width="80" align="center"></td>
	       <td align="center">
		  <a href="/warthog/ChatClientWindow.jsp?bandname=
		     <%= band.getBandName() %>"
		     onclick="window.open(this.href, this.target,
		     'width=305,height=140,scrollbars,top=0,left=0'); 
		     return false;">Chat</a>
               </td>
	       <% } %>
	       <td width="80" align="center"></td>
	       <td align="center"><a href="/warthog">Warthog Bands</a></td>
	    </tr>
	 </table>
      </td>
   </tr>
   <tr><td><br></td></tr>
   <tr>
      <td align="center">
	 <p style="text-align:center;padding-left:50px;padding-right:50px">
	   <%= bio.getBody() %></p>
      </td>
   </tr>
   <tr>
      <td> <br> <br> <br> </td>
   </tr>
   <tr>
      <td align="center">
	 <small>Contact: 
            <a href="mailto:<%= admin.getEmail() %>">
	       <%= admin.getFirstName() %> <%= admin.getLastName() %>
            </a>
         </small>
      </td>
   </tr>
</table>
</body>
</html>
