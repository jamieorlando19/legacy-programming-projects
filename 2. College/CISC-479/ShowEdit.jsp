<!-- Jamie Orlando -->
<!-- CISC 479 -->
<!-- Final Project -->
<!-- ShowEdit.jsp -->

<!-- Allows user to edit an existing show -->

<?xml version =	"1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%-- page settings --%>
<%@ page errorPage = "MuscLogErrorPage.jsp" %>
<%@ page import	= "java.util.*"	%>
<%@ page import = "java.sql.*" %>
<%@ page import	= "jsp.beans.*" %>

<jsp:useBean id = "bandData" scope = "request" class = "jsp.beans.BandsMusiciansDataBean" />
<jsp:useBean id = "show" scope = "page" class = "jsp.beans.ShowBean" />
<jsp:useBean id = "showData" scope = "request" class = "jsp.beans.ShowDataBean" />

<html xmlns = "http://www.w3.org/1999/xhtml">

<%
   String username = request.getParameter("username");
   int year = Integer.parseInt(request.getParameter("year")),
       month = Integer.parseInt(request.getParameter("month")),
       day = Integer.parseInt(request.getParameter("day")), 
       submitted = Integer.parseInt(request.getParameter("submitted"));  //If form was submitted
   ShowBean curshow = showData.getTheShow(username, year, month, day);   //Info about the show
%>

   <head>
      <title>Edit the show</title>
   </head>

   <body bgcolor="white">
      <jsp:setProperty name = "show" property = "*" />

<%    
      if(submitted == 0)
      {
%>
         <form method = "post" action = "ShowEdit.jsp?username=<%= username %>&year=<%= year%>&month=<%= month%>&day=<%= day%>&submitted=1">        
            <table>
               <tr>
                  <td>
                     *Band Name
                  </td>  
                  <td>
                    <SELECT name="bandname">
                       <OPTION value = "<%= curshow.getbandname() %>" SELECTED/> <%= curshow.getbandname() %>
<%                     
                       List bandlist = bandData.getList(username);
                       Iterator bandit = bandlist.iterator();
                       BandsMusiciansBean curband;

                       while (bandit.hasNext()) 
                       {
                          curband = (BandsMusiciansBean)bandit.next();
                          if((curband.getbandname()).compareTo(curshow.getbandname()) != 0)
                          {
%>
            <OPTION value = "<%=curband.getbandname()%>" />  <%=curband.getbandname()%> 
<%
                          }
                       }
%>
                    </SELECT><br />
     <a href="NewBand.jsp?username=<%= username %>&year=<%= year%>&month=<%= month%>&day=<%= day%>&refer=3">Enter A New Band</a>
                 </td>
               </tr>

               <tr>
                  <td>
                     *Show Year
                  </td>
                  <td>
                     <INPUT TYPE = "text" name = "showyear" maxlength="4" size="5" value = "<%= curshow.getshowyear()%>"/>
                  </td>
               </tr>

               <tr>
                  <td>
                     Show Month
                  </td>
                  <td>
<%
                     if(curshow.getshowmonth() != 0)
                     {
%>
                        <INPUT TYPE = "text" name = "showmonth" maxlength="2" size="3" value = "<%= curshow.getshowmonth() %>"/>
<%
                     }
                     else
                     {
%>
                        <INPUT TYPE = "text" name = "showmonth" maxlength="2" size="3"/>
<%
                     }
%>
                  </td>
               </tr>

               <tr>
                  <td>
                     Show Day
                  </td>
                  <td>
<%
                     if(curshow.getshowday() != 0)
                     {  
%>                      
                        <INPUT TYPE = "text" name = "showday" maxlength="2" size="3" value = "<%= curshow.getshowday() %>" />
<%                
                     }
                     else
                     {
%>                
                        <INPUT TYPE = "text" name = "showday" maxlength="2" size="3" />
<%                
                     }
%>    
                  </td>
               </tr>

               <tr>
                  <td>
                     **Setlist
                  </td>
                  <td>
<%
                     if(curshow.getsetlist() == null)
                     {  
%>                      
                        <TEXTAREA NAME="setlist" COLS="70" ROWS="8" WRAP="virtual"></TEXTAREA>
<%                
                     }
                     else
                     {
%>                
                        <TEXTAREA NAME="setlist" COLS="70" ROWS="8" WRAP="virtual"><%= curshow.getsetlist() %></TEXTAREA>
<%                
                     }
%>    
                  </td>
               </tr>

               <tr>
                  <td>
                     Journal
                  </td>
                  <td>
<%
                     if(curshow.getjournal() == null)
                     {
%>
                        <TEXTAREA NAME="journal" COLS="70" ROWS="8" WRAP="virtual"></TEXTAREA>
<%
                     }
                     else
                     {   
%>
                        <TEXTAREA NAME="journal" COLS="70" ROWS="8" WRAP="virtual"><%= curshow.getjournal() %></TEXTAREA>
<%
                     }
%>
                  </td>
               </tr>

               <tr>
                  <td>
                     **Band Member(s)
                  </td>
                  <td>
<%
                     if(curshow.getbandmembers() == null)
                     {
%>
                        <TEXTAREA NAME="bandmembers" COLS="70" ROWS="8" WRAP="virtual"></TEXTAREA>
<%
                     }
                     else
                     {   
%>                       
                        <TEXTAREA NAME="bandmembers" COLS="70" ROWS="8" WRAP="virtual"><%= curshow.getbandmembers() %></TEXTAREA>
<%                
                     }
%> 
                  </td>
               </tr>

               <tr>
                  <td>
                     *State
                  </td>
                  <td>
                     <SELECT name="showstate">
                        <OPTION value = "<%= curshow.getshowstate() %>" SELECTED/> <%= curshow.getshowstate() %>
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
                     <INPUT TYPE = "text" name = "showcity" maxlength="30" size="15" value = "<%= curshow.getshowcity() %>"/>
                  </td>
               </tr>

               <tr>
                  <td>
                     Venue
                  </td>
                  <td>
<%
                     if(curshow.getshowvenue() != null)   
                     {
%>
                        <INPUT TYPE = "text" name = "showvenue" maxlength="30" size="15" value = "<%= curshow.getshowvenue() %>"/>
<%
                     }
                     else
                     {
%>
                        <INPUT TYPE = "text" name = "showvenue" maxlength="30" size="15" />
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
      //If form was submitted
      else
      {
         show.setusername(username);

         //Deletes the show from the database and re-adds with new data  
         showData.deleteTheShow(username, year, month, day);
         showData.addShow(show);
%>
         <jsp:forward page = "ShowNiceView.jsp">
            <jsp:param name ="username" value ="<%= username %>" />
            <jsp:param name ="year" value ="<%= show.getshowyear() %>" />
            <jsp:param name ="month" value="<%= show.getshowmonth() %>" />
            <jsp:param name ="day" value="<%= show.getshowday() %>" />
         </jsp:forward>
<%
      }
%>
   </body>
</html>
