<%
  //Jamie Orlando
  //Show Add Success Page
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>Show Update A Success!</title>

<%@ page contentType="text/html" import="com.warthog.domain.core.*" %>
<%@ page contentType="text/html" import="com.warthog.domain.components.*" %>

<link type="text/css" rel="stylesheet" href="/warthog/css/warthogStyle.css">

<script type="text/javascript">
</script>

</head>

<body>

<%
  String fulldate = (String)session.getAttribute("fulldate");
  String venue = (String)session.getAttribute("venue");  
  String city = (String)session.getAttribute("city");
  String state = (String)session.getAttribute("state");
  String comments = (String)session.getAttribute("comments");
%>

<center>
<h1>Show Update A Success!</h1>
<br>
<h2>New Show Date</h2>
<table border="0" cellpadding="25" width="50%"><tr><td align="center" bgcolor="#CC9933">
<h3>
<%=fulldate%>
</h3>
<h2>
Venue: <%=venue%><br>
City: <%=city%><br>
State: <%=state%><br><br>
Comments:<br>
<%=comments%>
</h2>
</td></tr></table>
<br><br>
<a href="/warthog/editdates.jsp">Shows</a>
</center>
</body>
</html>
