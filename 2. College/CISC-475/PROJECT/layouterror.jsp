<%
  //Jamie Orlando
  //Layout Error Page
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>Layout Error!</title>
<link type="text/css" rel="stylesheet" href="/warthog/css/warthogStyle.css">

<script type="text/javascript">
</script>

</head>

<body>
<%
  String error = (String)session.getAttribute("error");
%>
<center>
<h1>Layout Error!</h1>
<br>
<h3>There was an error while trying to update your layout.</h3>
<br>
<%=error%>
<br><br>
<a href="/warthog/editscheme.jsp">Layout</a>
</center>
</body>
</html>
