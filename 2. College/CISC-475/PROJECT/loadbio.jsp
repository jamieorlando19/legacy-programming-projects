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
System.out.println("url in loadbio=" + url);
	   	WebSite website = new WebSite();
   		WebSiteLayout layout = new WebSiteLayout();
   		Band band = new Band();
   		website.select(url);
   		layout.select(url);
   		band.select(url);
		Bio bio = website.getBio();
	%>
	
	<title><%= band.getBandName() %></title>
	<style type="text/css">
     		body {  background-color:
               		<%= layout.getBackgroundColor() %>;
                       	color:
                        <%= layout.getTextColor() %>;
                        }
	</style>

</head>
<body>
	<%
		//if (!layout.getLayoutType().equals("3")) {
	%>
		<h1 style="text-align: center"><%=website.getTitle() %></h1>
	<%
	//	}
	%>
	
	<p style="text-align: center">
	<%= bio.getBody() %>
	</p>

</body>
</html>
