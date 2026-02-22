<!-- Jamie_Orlando -->
<!-- CISC 479 -->
<!-- Final Project -->
<!-- ShowCreate.jsp -->

<!-- Form for entering a new band into the database -->

<?xml version =	"1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%-- page settings --%>
<%@ page errorPage = "MuscLogErrorPage.jsp" %>
<%@ page import	= "java.util.*"	%>
<%@ page import	= "jsp.beans.*" %>

<jsp:useBean id = "bandData" scope = "request" class = "jsp.beans.BandsMusiciansDataBean" />
<jsp:useBean id = "show" scope = "page" class = "jsp.beans.ShowBean" />
<jsp:useBean id = "showData" scope = "request" class = "jsp.beans.ShowDataBean" />

<html xmlns = "http://www.w3.org/1999/xhtml">

<%
   String username = request.getParameter("username");
%>

   <head>
      <title>Enter the info for the show, <%= username %></title>
   </head>

   <body bgcolor="white">
      <jsp:setProperty name = "show" property = "*" />

<%    
      //If the form hasn't been submitted yet
      if(show.getbandname() == null || show.getshowstate() == null || show.getshowcity() == null)
      {
%>
         <form method = "post" action = "ShowCreate.jsp?username=<%= username %>&refer=1">        
            <table>
               <tr>
                  <td>
                     *Band Name
                  </td>  
                  <td>
                    <SELECT name="bandname">
<%                     
                       //Lists user's corresponding bands
                       List bandlist = bandData.getList(username);

                       //If there are no bands, forward to NewBand.jsp
                       if(bandlist.size() == 0)
                       {
%>
                          <jsp:forward page = "NewBand.jsp">
                             <jsp:param name ="username" value ="<%= username %>" />
                             <jsp:param name ="refer" value = "1" />
                             <jsp:param name ="year" value = "0" />
                             <jsp:param name ="month" value = "0" />
                             <jsp:param name ="day" value = "0" />
                          </jsp:forward>
<%
                       }

                       Iterator bandit = bandlist.iterator();
                       BandsMusiciansBean curband;
                       
                       //Places fields in combo box
                       while (bandit.hasNext()) 
                       {
                          curband = (BandsMusiciansBean)bandit.next();
%>
            <OPTION value = "<%=curband.getbandname()%>" />  <%=curband.getbandname()%> 
<%
                       }
                       
%>
                    </SELECT><br />
   <a href="NewBand.jsp?username=<%= username %>&refer=1&year=0&month=0&day=0">Enter A New Band</a>
                 </td>
               </tr>

               <tr>
                  <td>
                     *Show Year
                  </td>
                  <td>
                     <INPUT TYPE = "text" name = "showyear" maxlength="4" size="5"/>
                  </td>
               </tr>

               <tr>
                  <td>
                     Show Month
                  </td>
                  <td>
                     <INPUT TYPE = "text" name = "showmonth" maxlength="2" size="3"/>
                  </td>
               </tr>

               <tr>
                  <td>
                     Show Day
                  </td>
                  <td>
                     <INPUT TYPE = "text" name = "showday" maxlength="2" size="3"/>
                  </td>
               </tr>

               <tr>
                  <td>
                     **Setlist
                  </td>
                  <td>
                     <TEXTAREA NAME="setlist" COLS="70" ROWS="8" WRAP="virtual"></TEXTAREA>
                  </td>
               </tr>

               <tr>
                  <td>
                     Journal
                  </td>
                  <td>
                     <TEXTAREA NAME="journal" COLS="70" ROWS="8" WRAP="virtual"></TEXTAREA>
                  </td>
               </tr>

               <tr>
                  <td>
                     **Band Member(s)
                  </td>
                  <td>
                     <TEXTAREA NAME="bandmembers" COLS="70" ROWS="8" WRAP="virtual"></TEXTAREA>
                  </td>
               </tr>

               <tr>
                  <td>
                     *State
                  </td>
                  <td>
                     <SELECT name="showstate">
                        <OPTION value = "AK" />AK
                        <OPTION value = "AL" />AL
                        <OPTION value = "AR" />AR
                        <OPTION value = "AZ" />AZ
                        <OPTION value = "CA" />CA
                        <OPTION value = "CO" />CO
                        <OPTION value = "CT" />CT
                        <OPTION value = "DE" />DE 
                        <OPTION value = "FL" />FL 
                        <OPTION value = "GA" />GA 
                        <OPTION value = "HI" />HI
                        <OPTION value = "IA" />IA
                        <OPTION value = "ID" />ID
                        <OPTION value = "IL" />IL
                        <OPTION value = "IN" />IN
                        <OPTION value = "KS" />KS
                        <OPTION value = "KY" />KY
                        <OPTION value = "LA" />LA
                        <OPTION value = "MA" />MA
                        <OPTION value = "MD" />MD
                        <OPTION value = "ME" />ME
                        <OPTION value = "MI" />MI
                        <OPTION value = "MN" />MN
                        <OPTION value = "MO" />MO
                        <OPTION value = "MS" />MS
                        <OPTION value = "MT" />MT
                        <OPTION value = "NC" />NC
                        <OPTION value = "ND" />ND
                        <OPTION value = "NE" />NE
                        <OPTION value = "NH" />NH
                        <OPTION value = "NJ" />NJ
                        <OPTION value = "NM" />NM
                        <OPTION value = "NV" />NV
                        <OPTION value = "NY" />NY
                        <OPTION value = "OH" />OH
                        <OPTION value = "OK" />OK
                        <OPTION value = "OR" />OR
                        <OPTION value = "PA" />PA
                        <OPTION value = "RI" />RI
                        <OPTION value = "SC" />SC
                        <OPTION value = "SD" />SD
                        <OPTION value = "TN" />TN
                        <OPTION value = "TX" />TX
                        <OPTION value = "UT" />UT
                        <OPTION value = "VA" />VA
                        <OPTION value = "VT" />VT
                        <OPTION value = "WA" />WA
                        <OPTION value = "WI" />WI
                        <OPTION value = "WV" />WV
                        <OPTION value = "WY" />WY                                       
                    </SELECT><br />
                  </td>
               </tr>

               <tr>
                  <td>
                     *City
                  </td>
                  <td>
                     <INPUT TYPE = "text" name = "showcity" />
                  </td>
               </tr>

               <tr>
                  <td>
                     Venue
                  </td>
                  <td>
                     <INPUT TYPE = "text" name = "showvenue" />
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
         *Required field(s)<br />
         **Comma delimited
<%
      }
      //If form was submitted
      else
      {
         //Creates the new show
         show.setusername(username);
         showData.addShow(show);
%>
            <jsp:forward page = "ShowView.jsp">
               <jsp:param name ="username" value ="<%= username %>" />
               <jsp:param name ="arg" value ="_" />
               <jsp:param name ="type" value="0" />
            </jsp:forward>

<%
      }
%>
   </body>
</html>
