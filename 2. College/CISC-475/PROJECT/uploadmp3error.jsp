<%
  //Jamie Orlando
  //Upload MP3 Error Page
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>Upload Error!</title>
<link type="text/css" rel="stylesheet" href="/warthog/css/warthogStyle.css">

<script type="text/javascript">
</script>

</head>

<body>
<%
  String error = (String)session.getAttribute("error");
%>

<center>
<h1>Upload Error!</h1>
<br>
<h3>The MP3 upload has encountered an error!</h3>
<br>
<%= error %>
<br><br>
<a href="/warthog/editmp3s.jsp">MP3s</a>
</center>
</body>
</html>
