<!-- Jamie Orlando -->
<!-- CISC 479 -->
<!-- Final Project -->
<!-- MuscLogLoginError.jsp -->

<!-- This is an error page for MuscLogLogin.jsp, which catches username/password errors-->

<?xml version =	"1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns = "http://www.w3.org/1999/xhtml">

<%
   int arg = Integer.parseInt(request.getParameter("arg"));  //determines the error
%>

<head>
   <title>Error!</title>
</head>

<body bgcolor="white">
   <center>
      <font size="5">
<%
         if(arg == 1) 
         {
%>
            NO SUCH USER!            
<%            
         }
         else if(arg == 2)
         {
%>
            INVALID PASSWORD!
<%
         }
         else if(arg == 3)
         {
%>
            USERNAME IS TAKEN.  PLEASE TRY ANOTHER!
<%
         }
%>

      <br /><br />
      </font>

      <a href="MuscLogLogin.jsp">Go Back</a>
   </center>
</body>
</html>
