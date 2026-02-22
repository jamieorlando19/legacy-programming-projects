<%
  //Jamie Orlando
  //Edit News
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>Edit Band News</title>

<%@ page contentType="text/html" import="com.warthog.domain.core.*" %>
<%@ page contentType="text/html" import="com.warthog.domain.components.*" %>
<%@ page import="java.util.ArrayList"%> 

<link type="text/css" rel="stylesheet" href="/warthog/css/warthogStyle.css">

<script type="text/javascript">                                                

  function CheckLength(length) {
    if (document.newsform.news.value.length >= length) {
      alert('You have reached your character limit of ' + length + ' characters!');
      return false;
    }
  }

</script>

</head>           

<body>

<%
  String url = (String)session.getAttribute("url");
  NewsItem test = new NewsItem();
  ArrayList newsarraylist = test.select(url);

  Object[] newslist = newsarraylist.toArray();
%>

<center>
<h1>Band News</h1>
<br>
<h2>Add News</h2>
<form name="newsform" method="post" action="/warthog/servlet/UpdateNews">
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
<h3>News Title</h3>
<input name = "title" type ="text" size="35" maxlength="40" />
<br><br><br>
<h3>News Body</h3>
<textarea name="news" rows="8" cols="70" wrap="virtual"onkeypress="return CheckLength(300)"></textarea>
<br><br>
<input type="submit" value="Add News" class="submit_button" />
</form>


<%
  if(newslist.length != 0) {
%>

<hr width="50%">
<br>
<h2>Edit News</h2>
<form name="newslist" method="post">
  <select name="info">
  
<%
  for(int a = 0; a < newslist.length; a++) {
    String the_date = ((NewsItem)(newslist[a])).getDatePosted();
    String the_title = ((NewsItem)(newslist[a])).getTitle();
    String display = new String(the_date + " -- " + the_title);
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
      this.form.action='/warthog/editnews2.jsp'
      this.form.submit()
    "
  > 
  <input name="delete" type="button" class="submit_button" value="Delete" 
    onClick=
    "
      this.form.action='/warthog/servlet/DeleteNews'
      if(confirm('Are you sure you want to delete this news entry?')) 
      {this.form.submit();}
    "
  >       
</form>

<%
  } //end if
%>

<br><br>
<h3><a href="/warthog/admin.jsp">Back to Admin Page</a></h3>
</center>
</body>
</html>                                                                        
