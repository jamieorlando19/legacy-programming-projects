<html>
<head>
<title><%=request.getParameter("bandname")%>'s Sample Page</title>
</head>
<center>
<h1><%=request.getParameter("bandname")%>'s Sample Page</h1><br><br>

<a href="ChatClientWindow.jsp?bandname=<%=request.getParameter("bandname")%>" target="_blank" onclick="window.open(this.href, this.target,'width=305,height=140,scrollbars,top=0,left=0'); return false;">
  Click here to go to <%=request.getParameter("bandname")%>'s Chat
</a>
<br><br>
</center>
</html>