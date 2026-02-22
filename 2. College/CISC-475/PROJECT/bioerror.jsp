<%
  //Jamie Orlando
  //Bio Error Page
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>Bio Error!</title>
<link type="text/css" rel="stylesheet" href="/warthog/css/warthogStyle.css">

<script type="text/javascript">
</script>

</head>

<body>

<%
  String error = (String)session.getAttribute("error");
%>

<center>
<h1>Bio Error!</h1>
<br>
<h3>There was a problem updating your bio.  Please try again later!</h3>
<br>
<%=error%>
<a href="/warthog/admin.jsp">Admin</a>
</center>
</body>
</html>
