<%
  //Jamie Orlando
  //Delete MP3 Success Page

   String filename = (String)session.getAttribute("filename");
   String filelength = (String)session.getAttribute("filelength");
   String songtitle = (String)session.getAttribute("songtitle");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>Delete A Success!</title>
<link type="text/css" rel="stylesheet" href="/warthog/css/warthogStyle.css">

<script type="text/javascript">
</script>

</head>

<body>
<center>
<h1>Delete A Success!</h1>
<br>
<a href="/warthog/editmp3s.jsp">MP3s</a>
</center>
</body>
</html>
