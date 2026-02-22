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
  ShowDate test = new ShowDate();
  ArrayList showsarraylist = test.select(url);
  
  Object[] showslist = showsarraylist.toArray();
%> 

<center>
<h1>Band Show Dates</h1>
<br>
<h2>Add Show Date</h2>
<form name="showsform" method="post" action="/warthog/servlet/UpdateDate">
<input name="operation" type="hidden" value="add">
<table border="0" cellpadding="5">
  <tr>
    <td><h3>Year</h3></td>
    <td><h3>Month</h3></td>
    <td><h3>Day</h3></td>
  </tr>

  <tr>
    <td>
      <select name="year">
        <option value="2003">2003</option>
        <option value="2004">2004</option>
        <option value="2005">2005</option>
      </select>
    </td>
    <td>
      <select name="month">
        <option value="01">01</option>
        <option value="02">02</option>
        <option value="03">03</option>
        <option value="04">04</option>
        <option value="05">05</option>
        <option value="06">06</option>
        <option value="07">07</option>
        <option value="08">08</option>
        <option value="09">09</option>
        <option value="10">10</option>
        <option value="11">11</option>
        <option value="12">12</option>
      </select>
    </td>
    <td>
      <select name="day">
        <option value="01">01</option>
        <option value="02">02</option>
        <option value="03">03</option>
        <option value="04">04</option>
        <option value="05">05</option>
        <option value="06">06</option>
        <option value="07">07</option>
        <option value="08">08</option>
        <option value="09">09</option>
        <option value="10">10</option>
        <option value="11">11</option>
        <option value="12">12</option>
        <option value="13">13</option>
        <option value="14">14</option>
        <option value="15">15</option>
        <option value="16">16</option>
        <option value="17">17</option>
        <option value="18">18</option>
        <option value="19">19</option>
        <option value="20">20</option>
        <option value="21">21</option>
        <option value="22">22</option>
        <option value="23">23</option>
        <option value="24">24</option>
        <option value="25">25</option>
        <option value="26">26</option>
        <option value="27">27</option>
        <option value="28">28</option>
        <option value="29">29</option>
        <option value="30">30</option>
        <option value="31">31</option>
      </select>
    </td>
  </tr>
</table>
<br>


<b>Venue</b>
<br>
<input name="venue" type="text" size="35" maxlength="40" />
<br><br>
<b>City</b>
<br>
<input name = "city" type ="text" size="35" maxlength="40" />
<br><br>
<b>State</b>
<br>
<select name="state">
<%
  for(int a=0; a<states.length; a++) {
%>
    <option value="<%=states[a]%>"><%=states[a]%></option>
<%
  } //end for loop
%>
</select>
<br><br>
<b>Comments</b>
<br>
<textarea name="comments" rows="3" cols="70" wrap="virtual" onkeypress="return CheckLength(250)">
</textarea>
<br><br>
<input type="submit" value="Add Show" class="submit_button" />
</form>

<%
  if(showslist.length != 0) {
%>

<hr width="50%">
<br>

<h2>Edit Show Info</h2>
<form name="showslist" method="post">
  <select name="info">

<%
  for(int a = 0; a < showslist.length; a++) {
    String the_date = ((ShowDate)(showslist[a])).getDate();
    String the_venue = ((ShowDate)(showslist[a])).getVenue();
    String display = new String(the_date + " -- " + the_venue);
%>
   <option value="<%=display%>"><%=display%></option>
<%  
  } // end for loop
%>
  </select>
  <br><br>
  <input name="edit" type="button" class="submit_button" value="Edit"
    onClick=
    "
      this.form.action='/warthog/editdates2.jsp'
      this.form.submit()
    "
  >
  <input name="delete" type="button" class="submit_button" value="Delete"
    onClick=
    "
      this.form.action='/warthog/servlet/DeleteDate'
      if(confirm('Are you sure you want to delete this show date?'))
      {this.form.submit();}
    "
  >

</form>

<%
  } // end if
%>

<br><br>
<h3><a href="/warthog/admin.jsp">Back to Admin Page</a></h3> 
</center> 
</body> 
</html>
