<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<jsp:useBean id="signupbean" class="com.warthog.application.controllers.SignUpBean" scope="session" />

<html>
<head>
<title>Sign Up Successful</title>
<link type="text/css" rel="stylesheet" href="http://warthog.bounceme.net:1111/warthog/css/warthogStyle.css">
</head>
<body>
<p>
Sign up successful!  Thank you for signing up for warthogbands.com!<br>
<br>
Your website address is: <a href="http://warthog.bounceme.net:1111/warthog/<jsp:getProperty name="signupbean" property="url" 
/>/">http://warthog.bounceme.net:1111/warthog/<jsp:getProperty name="signupbean" property="url" />/</a>.<br>
<br>
To edit your page, please log in at <a href="http://warthog.bounceme.net:1111/warthog/">http://warthog.bounceme.net:1111/warthog/</a>.  
Your login name is <jsp:getProperty name="signupbean" property="url" />.
</p>

<br>
<br>
<table>
<tr><td>Band name:</td><td>&nbsp;&nbsp;&nbsp;</td><td><jsp:getProperty name="signupbean" property="bandname" /></td></tr>
<tr><td>Website address:</td><td></td><td>http://warthog.bounceme.net:1111/warthog/<jsp:getProperty name="signupbean" property="url" /></td></tr>
<tr><td>Contact name:</td><td></td><td><jsp:getProperty name="signupbean" property="contname" /></td></tr>
<tr><td valign="top">Contact address:</td><td></td><td><jsp:getProperty name="signupbean" property="contaddr" /><br>
                                                       <jsp:getProperty name="signupbean" property="contcity" />,
                                                       <jsp:getProperty name="signupbean" property="contstate" />
                                                       <jsp:getProperty name="signupbean" property="contzip" /></td></tr>
<tr><td>Contact phone number:</td><td></td><td><jsp:getProperty name="signupbean" property="contphone" /></td></tr>
<tr><td>Contact email:</td><td></td><td><jsp:getProperty name="signupbean" property="contemail" /></td></tr>
<tr><td>Administrative password:</td><td></td><td><jsp:getProperty name="signupbean" property="password" /></td></tr>
<tr><td>Chatroom:</td><td></td><td><jsp:getProperty name="signupbean" property="chatroom" /></td></tr>
<tr><td>Message board:</td><td></td><td><jsp:getProperty name="signupbean" property="messboard" /></td></tr>
<tr><td>Layout:</td><td></td><td><jsp:getProperty name="signupbean" property="layout" /></td></tr>
<tr><td>Credit card number:</td><td></td><td><jsp:getProperty name="signupbean" property="ccnumber" /></td></tr>
<tr><td>Cardholder name:</td><td></td><td><jsp:getProperty name="signupbean" property="ccname" /></td></tr>
<tr><td>Expiration date:</td><td></td><td><jsp:getProperty name="signupbean" property="expmonth" /> / <jsp:getProperty name="signupbean" property="expyear" /></td></tr>
<tr><td>Card type:</td><td></td><td><jsp:getProperty name="signupbean" property="cardtype" /></td></tr>
<tr><td>Yearly rate:</td><td></td><td>$<jsp:getProperty name="signupbean" property="yearlyrate" /></td></tr>
</table>
</body>
</html>
