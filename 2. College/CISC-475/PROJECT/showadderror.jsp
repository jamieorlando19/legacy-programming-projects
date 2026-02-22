<%
  //Jamie Orlando
  //Show Error Page
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>Show Error!</title>
<link type="text/css" rel="stylesheet" href="/warthog/css/warthogStyle.css">

<script type="text/javascript">
</script>

</head>
<body>
<%
  String error = (String)session.getAttribute("error");
%>

<center>
<h1>Show Error!</h1>
<br>
<h3>There was a problem updating your shows.  Please try again later!</h3>
<br>
<%=error%>
<br><br>
<a href="/warthog/admin.jsp">Admin</a>
</center>
</body>
</html>
