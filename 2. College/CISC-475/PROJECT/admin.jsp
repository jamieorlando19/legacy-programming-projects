<%
  //Jamie Orlando & Rich Stahler
  //Admin site for the band members
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>Admin Page</title>

<%@ page contentType="text/html" import="com.warthog.domain.core.*" %>
<%@ page contentType="text/html" import="com.warthog.domain.components.*" %>

<link type="text/css" rel="stylesheet" 
href="/warthog/css/warthogStyle.css">

<script type="text/javascript">
</script>

</head>

<body>

<%
  String url = (String)session.getAttribute("url");
%>

<center>
<h1>Welcome to your Admin Page</h1>
<h3>This is the page for editting the content of your band's site.</h3>

<a href="/warthog/<%=url%>" target="_blank">http://warthog.bounceme.net:1111/warthog/<%= url %></a><br>
<hr width="50%">
<br><br>

<table border="0">
<tr>
<td align="left">

<h2>
<a href="/warthog/editscheme.jsp">Edit Site-Layout & Color-Scheme</a>
</h2>
Change your current background color, text color, or website layout
<br><br>

<h2>
<a href="/warthog/editbio.jsp">Edit Bio</a>
</h2>
Change your current biography
<br><br>

<h2>
<a href="/warthog/editnews.jsp">Edit News</a>
</h2>
Add, edit or remove your band's news
<br><br>

<h2>
<a href="/warthog/editdates.jsp">Edit Show Dates</a>
</h2>
Add, edit or remove your band's show dates
<br><br>

<h2>
<a href="/warthog/editmp3s.jsp">Edit MP3 Files</a>
</h2>
Add, edit or remove five MP3 files for your music page
<br><br>

<h2>
<a href="/warthog/editimages.jsp">Edit Band Images</a>
</h2>
Add, edit or remove five image files for your photos page

</td>
</tr>
</table>

<form name="logout" method="post" action="/warthog/servlet/ValidateLogout" 
onsubmit=
"
  if(confirm('Are you sure you want to logout?')) 
    {return true} 
  else 
    {return false}
"
>
  <input type="submit" class="submit_button" value="Logout">
</form>

<br><br>
<small>Copyright Team Warthog 2003</small>
</center>
</body>
</html>
