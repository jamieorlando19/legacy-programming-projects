<%
  //Rich Stahler & Jamie Orlando
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>Edit Band Biography</title>

<%@ page contentType="text/html" import="com.warthog.domain.core.*" %>
<%@ page contentType="text/html" import="com.warthog.domain.components.*" %>

<link type="text/css" rel="stylesheet" 
href="/warthog/css/warthogStyle.css">

<script type="text/javascript">

  function CheckLength(length) {
    if (document.bioform.bio.value.length >= length) {
      alert('You have reached your character limit of ' + length + ' characters!');
      return false;
    }
  }

</script>

</head>

<body>

<%
  String url = (String)session.getAttribute("url");
  Bio thebio = new Bio();
  thebio.select(url);
  String body = thebio.getBody();
%>



<center>
<h1>Band Biography</h1>
<br>
<h3>Edit your Biography</h3>
<br>

<form name="bioform" method="post" action="/warthog/servlet/EditBio">
  <textarea name="bio" rows="16" cols="70" wrap="virtual" onkeypress="return CheckLength(1000)"><%= body %></textarea>
  <br><br>
  <input type="submit" value="Submit Bio" class="submit_button">
</form> 

<h3><a href="/warthog/admin.jsp">Back to Admin Page</a></h3> 
</center> 
</body> 
</html>
