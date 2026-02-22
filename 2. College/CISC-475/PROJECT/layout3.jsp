<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
	<head>
		<%@ page contentType="text/html" 
			import="com.warthog.domain.core.*" %>

	<% 
		String url = (String)session.getAttribute("url"); 
		WebSite website = new WebSite();
		WebSiteLayout layout = new WebSiteLayout();
		website.select(url);
	%>

		<title><%= website.getTitle() %></title>
	</head>

	<frameset rows="20%, *" border="0">
		<frame src="../layout3-top.jsp" name="menu" scrolling="no">
		</frame>
		<frame src="../loadbio.jsp" name="main"></frame>
	</frameset>
</html>
