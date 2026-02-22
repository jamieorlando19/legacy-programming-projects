<html>
<head>
<title>::Warthog-Bands::</title>
<link type="text/css" rel="stylesheet" href="/css/warthogStyle.css">

<script type="text/javascript">

function allowlogin() {
  var login = document.loginform.login.value
  var pw = document.loginform.pw.value

  if(login == "" || pw == "") {
    return false;
  }
  else {
    return true;
  }
}

</script>
</head>

<body>
<center>
<img src="/images/warthogbanner.jpg">
<br>
<table border="0" cellspacing="10" cellpadding="10">
  <tr>
    <td bgcolor="#CC9933">
      <table border="0" cellspacing="0" cellpadding="1">
      <form name="loginform" method="post" action="/servlet/ValidateLogin" onsubmit="return allowlogin()">
        <tr>
          <td colspan="2" align="center">
            <h2>Registered Bands</h2>
          </td>
        </tr>
        <tr>
          <td align="right">
            <b>Login:</b>
          </td>
          <td>
            <input type="text" name="login" size="15" maxlength="15"> 
          </td>
        </tr>
        <tr>
          <td align="right">
            <b>Password:</b>
          </td>
          <td>
            <input type="password" name="pw" size="15" maxlength="10">
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <center><input type="submit" class="submit_button" value="Login"></center>
          </td>
        </tr>
      </form>   
      <form name="register" method="post" action="SignUp.jsp">
        <tr>
          <td colspan="2" align="center">
            <br><br><h2>New Bands</h2>
          </td>
        </tr>  
          <td colspan="2"> 
            <center><input type="submit" class="submit_button" value="Click Here To Register"></center>
          </td>
        </tr>  
      </form> 
      </table>
    </td>
    <td bgcolor="#CC9966" valign="top">
    <h3>
      <br>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      ::insert team mission here::<br>
<% 
  String url = request.getParameter("url"); 
%>
<%=url%><br><br>
blah blah blah 
blah blah blah 
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
blah blah blah
    </h3>
    </td>
  </tr>
</table>
<small>Copyright Team Warthog 2003</small>
</center>
</body>
</html>
