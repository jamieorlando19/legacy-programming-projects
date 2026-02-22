<!-- Jamie_Orlando -->
<!-- CISC 479 -->
<!-- Final Project -->
<!-- BandEdit.jsp -->

<!-- Allows user to edit an existing band -->

<?xml version =	"1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%-- page settings --%>
<%@ page errorPage = "MuscLogErrorPage.jsp" %>
<%@ page import	= "java.util.*"	%>
<%@ page import	= "jsp.beans.*" %>
<%@ page import = "java.sql.*" %>

<jsp:useBean id = "band" scope = "page" class = "jsp.beans.BandsMusiciansBean" />
<jsp:useBean id = "bandData" scope = "request" class = "jsp.beans.BandsMusiciansDataBean" />

<html xmlns = "http://www.w3.org/1999/xhtml">

<%
   String username = request.getParameter("username"),
          bandname = request.getParameter("bandname"),    //Bandname with underscores
          bandname2 = bandname.replace('_', ' ');         //Bandname with spaces
   int submitted = Integer.parseInt(request.getParameter("submitted"));    //If page was submitted
   BandsMusiciansBean curband = bandData.getTheBand(username, bandname2);  //Info about the show     
%>

   <head>
      <title>Edit <%= bandname2 %></title>
   </head>

   <body bgcolor="white">
      <jsp:setProperty name = "band" property = "*" />
<%
      if(submitted == 0)
      {
%>
         <form method = "post" action = "BandEdit.jsp?username=<%= username %>&bandname=<%= bandname %>&submitted=1">
            <table>
               <tr>
                  <td>
                     Band Name
                  </td>
                  <td>
                     <b><%= bandname2 %></b>
                  </td>
               </tr>

               <tr>
                  <td>
                     Band Bio
                  </td>
                  <td>
<%
                     if(curband.getbandbio() != null)
                     {
%>
                        <TEXTAREA NAME="bandbio" COLS="70" ROWS="8" WRAP="virtual"><%= curband.getbandbio() %></TEXTAREA>
<%
                     }
                     else
                     {
%>
                        <TEXTAREA NAME="bandbio" COLS="70" ROWS="8" WRAP="virtual"></TEXTAREA>
<%
                     }
%>
                  </td>
               </tr>

               <tr>
                  <td>
                     Year Formed
                  </td>
                  <td>
<%
                     if(curband.getyearformed() != 0)
                     {   
%>
                        <INPUT TYPE = "text" name = "yearformed" value = "<%= curband.getyearformed() maxlenth="4" size="5"%>"/>

<%
                     }
                     else
                     {
%>
                        <INPUT TYPE = "text" name = "yearformed" maxlength="4" size="5" />
<%
                     }
%>
                  </td>
               </tr>

               <tr>     
                  <td colspan = "2">
                     <input type = "submit" value = "Submit"/>
                  </td>
               </tr>
            </table>
         </form>
<%
       }
      //If form data was submitted
      else        
      {
         band.setusername(username);
         band.setbandname(bandname2);
           
         //Deletes the band from the database and re-adds with new data
         bandData.deleteTheBand(username, bandname2);
         bandData.addBand(band);
%>

         <jsp:forward page = "BandNiceView.jsp">
            <jsp:param name ="username" value ="<%= username %>" />
            <jsp:param name ="bandname" value ="<%= bandname %>" />
         </jsp:forward>
<%                   
      }
%>       
   </body>
</html>
