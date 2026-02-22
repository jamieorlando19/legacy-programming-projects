<%
  //Jamie Orlando and Rich Stahler
  //Edit Schemes and Layout
%>

<html>
<head>
<title>Edit Layout & Scheme</title>
<link type="text/css" rel="stylesheet" 
href="/warthog/css/warthogStyle.css">

<script type="text/javascript">
</script>

</head>

<%@ page contentType="text/html" import="com.warthog.domain.core.*" %>
<%@ page contentType="text/html" import="com.warthog.domain.components.*" %>


<body>

<%
  String url = (String)session.getAttribute("url");
  WebSiteLayout web = new WebSiteLayout();  
  web.select(url);
%>


<center>
<h1>Edit Website Layout & Color Scheme</h1>

<form name="schemeform" method="post" action="/warthog/servlet/EditScheme">

<br>
<b>Background Color</b>
<br><br>
Select the color you wish to use for your background color.
<br><br>
<select name="backgroundColor">
  <option value="#000000" <% if((web.getBackgroundColor()).compareTo("#000000") == 0) { %> selected <% } %> >Black</option>
  <option value="#ffffff" <% if((web.getBackgroundColor()).compareTo("#ffffff") == 0) { %> selected <% } %> >White</option>
  <option value="#3333cc" <% if((web.getBackgroundColor()).compareTo("#3333cc") == 0) { %> selected <% } %> >Blue</option>
  <option value="#ff0000" <% if((web.getBackgroundColor()).compareTo("#ff0000") == 0) { %> selected <% } %> >Red</option>
  <option value="#336633" <% if((web.getBackgroundColor()).compareTo("#336633") == 0) { %> selected <% } %> >Green</option>
  <option value="#ffff33" <% if((web.getBackgroundColor()).compareTo("#ffff33") == 0) { %> selected <% } %> >Yellow</option>
</select>
<br><br>
<hr width="50%">

<br>
<b>Text Color</b>
<br><br>
Select the color you wish to use for your text color.
<br><br>
<select name="textColor">
  <option value="#000000" <% if((web.getTextColor()).compareTo("#000000") == 0) { %> selected <% } %> >Black</option>
  <option value="#ffffff" <% if((web.getTextColor()).compareTo("#ffffff") == 0) { %> selected <% } %> >White</option>
  <option value="#3333cc" <% if((web.getTextColor()).compareTo("#3333cc") == 0) { %> selected <% } %> >Blue</option>
  <option value="#ff0000" <% if((web.getTextColor()).compareTo("#ff0000") == 0) { %> selected <% } %> >Red</option>
  <option value="#336633" <% if((web.getTextColor()).compareTo("#336633") == 0) { %> selected <% } %> >Green</option>
  <option value="#ffff33" <% if((web.getTextColor()).compareTo("#ffff33") == 0) { %> selected <% } %> >Yellow</option>
</select>
<br><br>
<hr width="50%">

<br>
<b>Website Layout</b>
<br><br>
<img src="/warthog/images/format1.jpg">
<input type="radio" name="layout" value="1" <% if((web.getLayoutType()).compareTo("1") == 0) { %> checked <% } %> >
One 
<img src="/warthog/images/format2.jpg">
<input type="radio" name="layout" value="2" <% if((web.getLayoutType()).compareTo("2") == 0) { %> checked <% } %> >
Two
<img src="/warthog/images/format3.jpg">
<input type="radio" name="layout" value="3" <% if((web.getLayoutType()).compareTo("3") == 0) { %> checked <% } %> >
Three 
<br><br><br>
<input type="submit" value="Update Scheme & Layout" class="submit_button">
</form>
<br>

<h3><a href="/warthog/admin.jsp">Back to Admin Page</a></h3>
</center>
</body>
</html>
