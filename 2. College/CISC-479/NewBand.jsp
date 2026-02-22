<!-- Jamie_Orlando -->
<!-- CISC 479 -->
<!-- Final Project -->
<!-- NewBand.jsp -->

<!-- Form for entering a new band into the database -->

<?xml version =	"1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%-- page settings --%>
<%@ page errorPage = "MuscLogErrorPage.jsp" %>
<%@ page import	= "java.util.*"	%>
<%@ page import	= "jsp.beans.*" %>

<jsp:useBean id = "band" scope = "page" class = "jsp.beans.BandsMusiciansBean" />
<jsp:useBean id = "bandData" scope = "request" class = "jsp.beans.BandsMusiciansDataBean" />

<html xmlns = "http://www.w3.org/1999/xhtml">

<%
   String username = request.getParameter("username");
   int refer = Integer.parseInt(request.getParameter("refer")),   //determines page to forward to
       year = Integer.parseInt(request.getParameter("year")),     //show year
       month = Integer.parseInt(request.getParameter("month")),   //show month
       day = Integer.parseInt(request.getParameter("day"));       //show day
%>

   <head>
      <title>Enter a new band, <%= username %></title>
   </head>

   <body bgcolor="white">
      <jsp:setProperty name = "band" property = "*" />
<%
      if(band.getbandname() == null)
      {
%>
         <form method = "post" action = "NewBand.jsp?username=<%= username %>&year=<%=year%>&month=<%=month%>&day=<%=day%>&refer=<%= refer %>">
            <table>
               <tr>
                  <td>
                     *Band Name
                  </td>
                  <td>
                     <INPUT TYPE = "text" name = "bandname" maxlength="25" size="26"/>
                  </td>
               </tr>

               <tr>
                  <td>
                     Band Bio
                  </td>
                  <td>
                     <TEXTAREA NAME="bandbio" COLS="70" ROWS="8" WRAP="virtual"></TEXTAREA>
                  </td>
               </tr>

               <tr>
                  <td>
                     Year Formed
                  </td>
                  <td>
                     <INPUT TYPE = "text" name = "yearformed" maxlength="4" size="5"/>
                  </td>
               </tr>

               <tr>     
                  <td colspan = "2">
                     <input type = "submit" value = "Submit"/>
                  </td>
               </tr>

            </table>
         </form>
         <br /><br />
         *Required field(s)
<%
       }
      else        
      {
         //Adds the new band         
         band.setusername(username);
         bandData.addBand(band);

         //Forwards to page which it was sent from
         if(refer == 1)
         {
%>
            <jsp:forward page = "ShowCreate.jsp">
               <jsp:param name ="username" value ="<%= username %>" />
            </jsp:forward>
<%
         }
         else if(refer == 2)
         {
%>
            <jsp:forward page = "BandView.jsp">
               <jsp:param name ="username" value ="<%= username%>" />
            </jsp:forward>
<%
         }
         else if(refer == 3)
         {
%>
            <jsp:forward page = "ShowEdit.jsp">
               <jsp:param name = "username" value ="<%= username %>" />
               <jsp:param name = "year" value = "<%= year %>" />
               <jsp:param name = "month" value = "<%= month %>" />
               <jsp:param name = "day" value = "<%= day %>" />
               <jsp:param name = "submitted" value = "0" />
            </jsp:forward>
<%
          }                 
      }
%>       
   </body>
</html>
