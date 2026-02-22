<%
  //Jamie Orlando
  //Bio Success Page
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>Bio Update A Success!</title>

<%@ page contentType="text/html" import="com.warthog.domain.core.*" %>
<%@ page contentType="text/html" import="com.warthog.domain.components.*" %>

<link type="text/css" rel="stylesheet" href="/warthog/css/warthogStyle.css">

<script type="text/javascript">
</script>

</head>

<body>

<%
  String url = (String)session.getAttribute("url");
  url.trim();
  Bio thebio = new Bio();
  thebio.select(url);
%>


<center>
<h1>Bio Update A Success!</h1>
<br>
<h2>New Bio</h2>
<table border="0" cellpadding="25" width="50%"><tr><td align="left" bgcolor="#CC9933">
<h3>
&nbsp;&nbsp;&nbsp;&nbsp;<%= thebio.getBody() %>
</h3>
</td></tr></table>
<br><br>
<a href="/warthog/admin.jsp">Admin</a>
</center>
</body>
</html>
