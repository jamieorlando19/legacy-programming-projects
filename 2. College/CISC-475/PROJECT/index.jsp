<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<!--Jamie Orlando-->
<!--index.jsp-->

<html>
<head>
<title>::Warthog-Bands::</title>
<link type="text/css" rel="stylesheet" href="/warthog/css/warthogStyle.css">

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

<%
  //Do not display an error message
  boolean success = true;
  
  //If a parameter is being returned to this page, return an error
  if(session.getAttribute("url") != null) {
    success = false;
  }
%>

<center>
<img src="/warthog/images/warthogbanner.jpg">
<br>
<table border="0" cellspacing="10" cellpadding="10">
  <tr>
    <td bgcolor="#CC9933" align="center" valign="top">
      <br><br>
      <table border="0" cellspacing="0" cellpadding="1">
      <form name="loginform" method="post" action="/warthog/servlet/ValidateLogin" onsubmit="return allowlogin()">
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
            <input type="text" name="login" class="input_box" size="15" maxlength="15"> 
          </td>
        </tr>
        <tr>
          <td align="right">
            <b>Password:</b>
          </td>
          <td>
            <input type="password" name="pw" class="input_box" size="15" maxlength="10">
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <center><input type="submit" class="submit_button" value="Login"></center>
          </td>
        </tr>
      </form>   
      <form name="register" method="post" action="/warthog/signup.jsp">
        <tr>
          <td colspan="2" align="center">
<%
  if(!success) {
%>
    <h3><font color="red">
    INVALID LOGIN!<br>
    PLEASE TRY AGAIN!
    </font></h3>
<%
  }
%>
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
    <h2>
      <br>Bands! Need a low-cost website? Warthog-Bands can help!!!<br><br>
    </h2>
    <h3>
      Warthog-Bands is your solution to create and maintain a professional-grade website!
      We make the site for YOU! By simply filling out a few online forms, you can customize your
      band's site to your liking.  Your site will be sure to impress booking agents and club owners
      and is a surefire way to increase your flow of gigs!
    </h3>
    Your site can include:<br>
    <table border="0"><tr><td align="left" valign="top">
    <ul>
      <li>MP3 Files</li>
      <li>Image Files</li>
      <li>A Band Biography</li>
      <li>Band News</li>
      <li>Tour Dates</li>
      <li>Chat Room</li>
      <li>A Customizable Layout</li>
    </ul>
    </td></tr></table>
    <h3>So, what are you waiting for?  Click the REGISTER button to join, today!</h3>
    </td>
  </tr>
</table>
<small>Copyright Team Warthog 2003</small>
</center>

	<%
		session.invalidate();
	%>
</body>
</html>
