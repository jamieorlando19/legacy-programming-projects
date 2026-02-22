<%
  //Jamie Orlando
  //News Add Success Page
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>News Update A Success!</title>

<%@ page contentType="text/html" import="com.warthog.domain.core.*" %>
<%@ page contentType="text/html" import="com.warthog.domain.components.*" %>

<link type="text/css" rel="stylesheet" href="/warthog/css/warthogStyle.css">

<script type="text/javascript">
</script>

</head>

<body>

<%
  String fulldate = (String)session.getAttribute("fulldate");
  String title = (String)session.getAttribute("title");  
  String body = (String)session.getAttribute("body");
%>


<center>
<h1>News Update A Success!</h1>
<br>
<h2>News Item</h2>
<table border="0" cellpadding="25" width="50%"><tr><td bgcolor="#CC9933">
<h1>
<%=title%>
</h1>
<%=fulldate%>
<h3>
<hr width="50%">
<%=body%>
</h3>
</td></tr></table>
<br><br>
<a href="/warthog/editnews.jsp">News</a>
</center>
</body>
</html>
