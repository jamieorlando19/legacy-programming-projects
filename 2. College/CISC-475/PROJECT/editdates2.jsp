<%
  //Jamie Orlando               
  //Edit Dates
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>Edit Show Dates</title>

<%@ page contentType="text/html" import="com.warthog.domain.core.*" %>
<%@ page contentType="text/html" import="com.warthog.domain.components.*" %>
<%@ page import="java.util.ArrayList"%>

<link type="text/css" rel="stylesheet" href="/warthog/css/warthogStyle.css">

<%
   // define the states array:
   String states[] = {
   "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
   "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
   "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
   "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "PR", "RI",
   "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
%>

<script type="text/javascript">

  function CheckLength(length) {
    if (document.showsform.comments.value.length >= length) {
      alert('You have reached your character limit of ' + length + ' characters!');
      return false;
    }
  }

</script>

</head>
<body>

<%
  String url = (String)session.getAttribute("url");
  String info = request.getParameter("info");
  String fulldate = info.substring(0, 10);
  String venue = info.substring(14, info.length());
  String year = fulldate.substring(0, 4);
  String month = fulldate.substring(5, 7);
  String day = fulldate.substring(8, 10);
      
  session.setAttribute("olddate", fulldate);
  session.setAttribute("oldvenue", venue);

  ShowDate show = new ShowDate();
  show.select(url, fulldate, venue);
%>

<center>
<h1>Band Show Dates</h1>
<br>
<h2>Add Show Date</h2>
<form name="showsform" method="post" action="/warthog/servlet/UpdateDate">
<input name="operation" type="hidden" value="update">
<table border="0" cellpadding="5">
  <tr>
    <td><h3>Year</h3></td>
    <td><h3>Month</h3></td>
    <td><h3>Day</h3></td>
  </tr>

  <tr>
    <td>
      <select name="year">
        <option value="2003" <% if(year.compareTo("2003")==0) { %> selected <% }%> >2003</option>
        <option value="2004" <% if(year.compareTo("2004")==0) { %> selected <% }%> >2004</option>
        <option value="2005" <% if(year.compareTo("2005")==0) { %> selected <% }%> >2005</option>
      </select>
    </td>
    <td>
      <select name="month">
        <option value="01" <% if(month.compareTo("01")==0) { %> selected <% }%> >01</option>
        <option value="02" <% if(month.compareTo("02")==0) { %> selected <% }%> >02</option>
        <option value="03" <% if(month.compareTo("03")==0) { %> selected <% }%> >03</option>
        <option value="04" <% if(month.compareTo("04")==0) { %> selected <% }%> >04</option>
        <option value="05" <% if(month.compareTo("05")==0) { %> selected <% }%> >05</option>
        <option value="06" <% if(month.compareTo("06")==0) { %> selected <% }%> >06</option>
        <option value="07" <% if(month.compareTo("07")==0) { %> selected <% }%> >07</option>
        <option value="08" <% if(month.compareTo("08")==0) { %> selected <% }%> >08</option>
        <option value="09" <% if(month.compareTo("09")==0) { %> selected <% }%> >09</option>
        <option value="10" <% if(month.compareTo("10")==0) { %> selected <% }%> >10</option>
        <option value="11" <% if(month.compareTo("11")==0) { %> selected <% }%> >11</option>
        <option value="12" <% if(month.compareTo("12")==0) { %> selected <% }%> >12</option>
      </select>
    </td>
    <td>
      <select name="day">
        <option value="01" <% if(day.compareTo("01")==0) { %> selected <% }%> >01</option>
        <option value="02" <% if(day.compareTo("02")==0) { %> selected <% }%> >02</option>
        <option value="03" <% if(day.compareTo("03")==0) { %> selected <% }%> >03</option>
        <option value="04" <% if(day.compareTo("04")==0) { %> selected <% }%> >04</option>
        <option value="05" <% if(day.compareTo("05")==0) { %> selected <% }%> >05</option>
        <option value="06" <% if(day.compareTo("06")==0) { %> selected <% }%> >06</option>
        <option value="07" <% if(day.compareTo("07")==0) { %> selected <% }%> >07</option>
        <option value="08" <% if(day.compareTo("08")==0) { %> selected <% }%> >08</option>
        <option value="09" <% if(day.compareTo("09")==0) { %> selected <% }%> >09</option>
        <option value="10" <% if(day.compareTo("10")==0) { %> selected <% }%> >10</option>
        <option value="11" <% if(day.compareTo("11")==0) { %> selected <% }%> >11</option>  
        <option value="12" <% if(day.compareTo("12")==0) { %> selected <% }%> >12</option>  
        <option value="13" <% if(day.compareTo("13")==0) { %> selected <% }%> >13</option>  
        <option value="14" <% if(day.compareTo("14")==0) { %> selected <% }%> >14</option>  
        <option value="15" <% if(day.compareTo("15")==0) { %> selected <% }%> >15</option>  
        <option value="16" <% if(day.compareTo("16")==0) { %> selected <% }%> >16</option>  
        <option value="17" <% if(day.compareTo("17")==0) { %> selected <% }%> >17</option>  
        <option value="18" <% if(day.compareTo("18")==0) { %> selected <% }%> >18</option>  
        <option value="19" <% if(day.compareTo("19")==0) { %> selected <% }%> >19</option>  
        <option value="20" <% if(day.compareTo("20")==0) { %> selected <% }%> >20</option>  
        <option value="21" <% if(day.compareTo("21")==0) { %> selected <% }%> >21</option>  
        <option value="22" <% if(day.compareTo("22")==0) { %> selected <% }%> >22</option>  
        <option value="23" <% if(day.compareTo("23")==0) { %> selected <% }%> >23</option>
        <option value="24" <% if(day.compareTo("24")==0) { %> selected <% }%> >24</option>
        <option value="25" <% if(day.compareTo("25")==0) { %> selected <% }%> >25</option>
        <option value="26" <% if(day.compareTo("26")==0) { %> selected <% }%> >26</option>
        <option value="27" <% if(day.compareTo("27")==0) { %> selected <% }%> >27</option>
        <option value="28" <% if(day.compareTo("28")==0) { %> selected <% }%> >28</option>
        <option value="29" <% if(day.compareTo("29")==0) { %> selected <% }%> >29</option>
        <option value="30" <% if(day.compareTo("30")==0) { %> selected <% }%> >30</option>
        <option value="31" <% if(day.compareTo("31")==0) { %> selected <% }%> >31</option>
      </select>
    </td>
  </tr>
</table>
<br>


<b>Venue</b>
<br>
<input name="venue" type="text" size="35" maxlength="40" value="<%= show.getVenue() %>"/>
<br><br>
<b>City</b>
<br>
<input name = "city" type ="text" size="35" maxlength="40" value="<%= show.getCity() %>" />
<br><br>
<b>State</b>
<br>
<select name="state">
<%
  for(int a=0; a<states.length; a++) {
    if((states[a]).compareTo(show.getState()) == 0) {
%>
      <option value="<%=states[a]%>" selected><%=states[a]%></option>
<%
    }
    else {
%>
      <option value="<%=states[a]%>"><%=states[a]%></option>
<%
    }
  } //end for loop
%>
</select>
<br><br>
<b>Comments</b>
<br>
<textarea name="comments" rows="3" cols="70" wrap="virtual" onkeypress="return CheckLength(250)">
<%= show.getComments() %>
</textarea>
<br><br>
<input type="submit" value="Edit Show" class="submit_button" />
</form>
<br>
<h3><a href="/warthog/editdates.jsp">Back</a></h3> 
</center> 
</body> 
</html>
