<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
	<%@ page contentType="text/html" 
   		import="com.warthog.domain.core.*,
			com.warthog.domain.components.*, 
			java.util.ArrayList" %>

	<% 
		String url = (String)session.getAttribute("url");
	   	WebSite website = new WebSite();
   		WebSiteLayout layout = new WebSiteLayout();
   		Band band = new Band();
   		website.select(url);
   		layout.select(url);
   		band.select(url);
   		ArrayList newsList = NewsItem.select(url);
	%>

	<title>News For <%= band.getBandName() %></title>
	
	<style type="text/css">
       		body {  background-color:
               		<%= layout.getBackgroundColor() %>;
                       	color:
                       	<%= layout.getTextColor() %>;
                       }
	</style>

</head>
<body>  
<h1 style="text-align: center"><%=website.getTitle()%>: News</h1>
<table border="0" cellpadding="0" cellspacing="2">
   <% for( int i = 0; i < newsList.size(); i++ ) { 
      NewsItem newsItem = (NewsItem) newsList.get(i); %> 
   <tr>
      <td align="left"><%= newsItem.getDatePosted() %></td>
      <td width="200"></td>
      <td align="left"><i><%= newsItem.getTitle() %></i></td>
   </tr>
   <tr><td colspan="3" width="100%"> </td></tr>
   <tr>
      <td colspan="3" align="left"><br><%= newsItem.getBody() %></td>
   </tr>
   <tr><td colspan="3" width="100%" align="left"><hr></td></tr>
   <% } %>
</table>
</body>
</html>
