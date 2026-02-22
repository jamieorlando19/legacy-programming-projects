<html>
<head>
<title><%=request.getParameter("bandname")%> Chat</title>
</head>
<body bgcolor="black">
<center>
<applet code="ChatClient.class" codebase="http://warthog-bands.no-ip.com:81/warthog/chatclasses" width="250" height="100">
  <param name="BandName" value="<%=request.getParameter("bandname")%>">
</applet>
</center>
</body>
</html>
