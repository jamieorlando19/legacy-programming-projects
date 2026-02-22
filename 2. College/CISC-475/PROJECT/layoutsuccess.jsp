<%
  //Jamie Orlando
  //Layout Success Page
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>Update Layout A Success!</title>

<%@ page contentType="text/html" import="com.warthog.domain.core.*" %>
<%@ page contentType="text/html" import="com.warthog.domain.components.*" %>

<link type="text/css" rel="stylesheet" href="/warthog/css/warthogStyle.css">

<script type="text/javascript">
</script>

</head>
<body>


<%
   String url = (String)session.getAttribute("url");     
   WebSiteLayout the_layout = new WebSiteLayout();
   the_layout.select(url);
   String tcolor = the_layout.getTextColor();
   String bcolor = the_layout.getBackgroundColor();
%>

<center>
<h1>Update Layout A Success!</h1>
<br>
<h2>Your site colors will look like this</h2>
<table border="3" bordercolor="#000000" width="75%">
  <tr>
    <td bgcolor="<%=bcolor %>" valign="middle" align="center">
      <br><br><br>
      <h1><font color="<%= tcolor%>">HERE IS YOUR TEXTCOLOR</font></h1>
      <br><br><br>
    </td>
  </tr>
</table>
<br>
<a href="/warthog/editscheme.jsp">Change Layout</a>
</center>
</body>
</html>
