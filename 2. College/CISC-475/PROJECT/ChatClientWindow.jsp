<html>
<head>
<title><%=request.getParameter("bandname")%> Chat</title>
</head>
<body bgcolor="black">
<center>
<applet code="ChatClient.class" codebase="chatclasses" width="250" height="100">
  <param name="BandName" value="<%=request.getParameter("bandname")%>">
</applet>
</center>
</body>
</html>
