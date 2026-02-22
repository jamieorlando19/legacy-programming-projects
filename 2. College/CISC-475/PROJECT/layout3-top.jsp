<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
 "http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
	<head>
 		<%@ page contentType="text/html" 
			import="com.warthog.domain.core.*, 
				com.warthog.domain.components.*"%>
	<%
		String url = (String)session.getAttribute("url");
                WebSite website = new WebSite();
                WebSiteLayout layout = new WebSiteLayout();
                website.select(url);
 		Band band = new Band();
	    	band.select(url);
       		layout.select(url);
          	BandAdministrator admin = band.getBandAdministrator();
	%>

		<title><%= website.getTitle() %></title>

		<style type="text/css">
			body {	background-color:
				<%= layout.getBackgroundColor() %>;
				color:
				<%= layout.getTextColor() %>;
			     }
		</style>

	</head>

	<body>
		<h1 style="text-align: center"><%=website.getTitle()%></h1>
		
		<!-- home, images, mp3s, news, shows, chat -->
		
		<p style="text-align: center">
		<a href="loadbio.jsp" target="main">Home</a> | 
		<a href="loadnews.jsp" target="main">News</a> |
		<a href="loadshows.jsp" target="main">Shows</a> |
		<a href="loadmp3s.jsp" target="main">Mp3s</a> |
		<%
			if(layout.getHasChatRoom()) {
		%>
			
			<a href="ChatClientWindow.jsp?bandname=
				'<%=band.getBandName()%>'" target='_blank' 
				onclick="window.open(this.href, this.target,
				'width=305,height=140,scrollbars,top=0,left=0');
				return false;"><%=band.getBandName()%>'s Chat
			</a> |
		<%
				}
		%>
		<a href="<%=request.getContextPath()%>" target="_top">
			WarthogBands main</a>
		</p>
	</body>
</html>



