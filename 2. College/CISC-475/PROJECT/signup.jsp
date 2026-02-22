<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<jsp:useBean id="signupbean" class="com.warthog.application.controllers.SignUpBean" scope="session" />

<html>
<head>
<title>Warthog Bands Signup</title>
<link type="text/css" rel="stylesheet" 
	href="http://warthog.bounceme.net:1111/warthog/css/warthogStyle.css">

<% // define the states array

                String states[] = {"AL", "AK", "AZ", "AR", "CA", "CO",
                        "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN",
                        "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI",
                        "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
                        "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA",
                        "PR", "RI", "SC", "SD", "TN", "TX", "UT", "VT",
                        "VA", "WA", "WV", "WI", "WY"};
%>

</head>

<body>
<center><h1>Warthog Bands Signup</h1></center>

<font class="error">
<jsp:getProperty name="signupbean" property="complaints" /></font>

<br>
<br>
<form action="http://warthog.bounceme.net:1111/warthog/servlet/SignUpHandler" method="post">
<table>

  <tr>
    <td colspan=3><h2>Band Information:</h2></td>
  </tr>
  <tr>
    <td colspan=2>Band Name:</td>
    <td><input type="text" name="bandname" class="input_box" maxlength="30" size="25" value="<jsp:getProperty name="signupbean" property="bandname" />"></td>
  </tr>
  <tr>
    <td colspan=2><br>Website Address:</td>
    <td><br>http://warthog.bounceme.net:1111/warthog/ <input type="text" name="url" class="input_box" maxlength="15" size="15" value="<jsp:getProperty name="signupbean" property="url" />"></td>
  </tr>

  <tr>
    <td colspan=3><br><br><h2>Contact Information:</h2></td>
  </tr>
  <tr>
    <td colspan=2>Contact Name:</td>
    <td><input type="text" name="contname" class="input_box" maxlength="40" size="25" value="<jsp:getProperty name="signupbean" property="contname" />"></td>
  </tr>
  <tr>
    <td colspan=2><br>Contact Address:</td>
    <td><br><input type="text" name="contaddr" class="input_box" maxlength="50" size="50" value="<jsp:getProperty name="signupbean" property="contaddr" />"></td>
  <tr>
  <tr>
    <td colspan=2><br>Contact City:</td>
    <td>
      <br><input type="text" name="contcity" class="input_box" maxlength="20" size="20" value="<jsp:getProperty name="signupbean" property="contcity" />">
      &nbsp;&nbsp;&nbsp;&nbsp;Contact State: 
	<select name="contstate" class="input_box">
        <%
        	for(int i=0; i<states.length; ++i) {
         %>

        	<option value="<%= states[i] %>">
               	<%= states[i] %>
               	</option>
        <%
        	}
         %>
        </select>
      &nbsp;&nbsp;&nbsp;&nbsp;Contact Zip Code: <input type="text" name="contzip" class="input_box" maxlength="5" size="7" value="<jsp:getProperty name="signupbean" property="contzip" />">
    </td>
  <tr>
  <tr>
    <td><br>Contact Phone Number:</td>
    <td>&nbsp;&nbsp;&nbsp;</td>
    <td><br><input type="text" name="contphone" class="input_box" maxlength="10" size="10" value="<jsp:getProperty name="signupbean" property="contphone" />">(ex. 1115558888)</td>
  <tr>
  <tr>
    <td colspan=2><br>Contact Email:</td>
    <td><br><input type="text" name="contemail" class="input_box" maxlength="30" size="25" value="<jsp:getProperty name="signupbean" property="contemail" />"></td>
  <tr>
  <tr>
    <td colspan=2><br>Administrative Password:</td>
    <td><br><input type="password" name="password" class="input_box" maxlength="10" size="10" value="<jsp:getProperty name="signupbean" property="password" />"></td>
  <tr>
  <tr>
    <td colspan=2><br>Confirm Password:</td>
    <td><br><input type="password" name="pwconfirm" class="input_box" maxlength="10" size="10" value="<jsp:getProperty name="signupbean" property="pwconfirm" />"></td>
  <tr>

  <tr>
    <td colspan=3><br><br><h2>Website Features:</h2></td>
  </tr>
  <tr>
    <td colspan=3><input type="checkbox" class="checkbox" name="chatroom" <% if( signupbean.getChatroom() != null && !signupbean.getChatroom().equals("") ) { %> checked <% } %>>Chatroom ($5.00/year extra)</td>
  </tr>
<!-- Commented out CRC 5/20/03  
  <tr>
    <td colspan=3><input type="checkbox" class="checkbox" name="messboard" <% if( signupbean.getMessboard() != null && !signupbean.getMessboard().equals("") ) { %> checked <% } %>>Message Board ($10/year extra)</td>
  </tr>
 -->
  <tr>
    <td colspan=3><br><input type="radio" class="radiobutton" name="layout" value="1" <% if( signupbean.getLayout() != null && signupbean.getLayout().equals("1") ) { %> checked <% } %>><a href="layout1.jpg" target="_blank">Layout 1</a>
: No frames.</td>
  </tr>
  <tr>
    <td colspan=3><input type="radio" class="radiobutton" name="layout" value="2" <% if( signupbean.getLayout() != null && signupbean.getLayout().equals("2") ) { %> checked <% } %>><a href="layout2.jpg" target="_blank">Layout 2</a>
: Pages with menubar frame along the left hand side.</td>
  </tr>
  <tr>
    <td colspan=3><input type="radio" class="radiobutton" name="layout" value="3" <% if( signupbean.getLayout() != null && signupbean.getLayout().equals("3") ) { %> checked <% } %>><a href="layout3.jpg" target="_blank">Layout 3</a></td>
  </tr>

  <tr>
    <td colspan=3><br><br><h2>Credit Card Information:</h2></td>
  </tr>
  <tr>
    <td>Card Number:</td>
    <td>&nbsp;&nbsp;&nbsp;</td>
    <td><input type="text" name="ccnumber" class="input_box" maxlength="16" size="25" value="<jsp:getProperty name="signupbean" property="ccnumber" />"></td>
  </tr>
  <tr>
    <td><br>Cardholder Name:</td>
    <td>&nbsp;&nbsp;&nbsp;</td>
    <td><br><input type="text" name="ccname" class="input_box" maxlength="40" size="25" value="<jsp:getProperty name="signupbean" property="ccname" />"></td>
  </tr>
  <tr>
    <td><br>Expiration Date:</td>
    <td>&nbsp;&nbsp;&nbsp;</td>
    <td><br><select name="expmonth" class="dropdownmenu">
              <option> </option>
              <option value="01" <% if( signupbean.getExpmonth() != null && signupbean.getExpmonth().equals("01") ) { %> selected <% } %>>01</option>
              <option value="02" <% if( signupbean.getExpmonth() != null && signupbean.getExpmonth().equals("02") ) { %> selected <% } %>>02</option>
              <option value="03" <% if( signupbean.getExpmonth() != null && signupbean.getExpmonth().equals("03") ) { %> selected <% } %>>03</option>
              <option value="04" <% if( signupbean.getExpmonth() != null && signupbean.getExpmonth().equals("04") ) { %> selected <% } %>>04</option>
              <option value="05" <% if( signupbean.getExpmonth() != null && signupbean.getExpmonth().equals("05") ) { %> selected <% } %>>05</option>
              <option value="06" <% if( signupbean.getExpmonth() != null && signupbean.getExpmonth().equals("06") ) { %> selected <% } %>>06</option>
              <option value="07" <% if( signupbean.getExpmonth() != null && signupbean.getExpmonth().equals("07") ) { %> selected <% } %>>07</option>
              <option value="08" <% if( signupbean.getExpmonth() != null && signupbean.getExpmonth().equals("08") ) { %> selected <% } %>>08</option>
              <option value="09" <% if( signupbean.getExpmonth() != null && signupbean.getExpmonth().equals("09") ) { %> selected <% } %>>09</option>
              <option value="10" <% if( signupbean.getExpmonth() != null && signupbean.getExpmonth().equals("10") ) { %> selected <% } %>>10</option>
              <option value="11" <% if( signupbean.getExpmonth() != null && signupbean.getExpmonth().equals("11") ) { %> selected <% } %>>11</option>
              <option value="12" <% if( signupbean.getExpmonth() != null && signupbean.getExpmonth().equals("12") ) { %> selected <% } %>>12</option>
            </select> / 
            <select name="expyear" class="dropdownmenu">
              <option> </option>
              <option value="2003" <% if( signupbean.getExpyear() != null && signupbean.getExpyear().equals("2003") ) { %> selected <% } %>>2003</option>
              <option value="2004" <% if( signupbean.getExpyear() != null && signupbean.getExpyear().equals("2004") ) { %> selected <% } %>>2004</option>
              <option value="2005" <% if( signupbean.getExpyear() != null && signupbean.getExpyear().equals("2005") ) { %> selected <% } %>>2005</option>
              <option value="2006" <% if( signupbean.getExpyear() != null && signupbean.getExpyear().equals("2006") ) { %> selected <% } %>>2006</option>
              <option value="2007" <% if( signupbean.getExpyear() != null && signupbean.getExpyear().equals("2007") ) { %> selected <% } %>>2007</option>
              <option value="2008" <% if( signupbean.getExpyear() != null && signupbean.getExpyear().equals("2008") ) { %> selected <% } %>>2008</option>
              <option value="2009" <% if( signupbean.getExpyear() != null && signupbean.getExpyear().equals("2009") ) { %> selected <% } %>>2009</option>
              <option value="2010" <% if( signupbean.getExpyear() != null && signupbean.getExpyear().equals("2010") ) { %> selected <% } %>>2010</option>
              <option value="2011" <% if( signupbean.getExpyear() != null && signupbean.getExpyear().equals("2011") ) { %> selected <% } %>>2011</option>
              <option value="2012" <% if( signupbean.getExpyear() != null && signupbean.getExpyear().equals("2012") ) { %> selected <% } %>>2012</option>
            </select>
    </td>
  </tr>
  <tr>
    <td><br>Card type:</td>
    <td>&nbsp;&nbsp;&nbsp;</td>
    <td><br><select name="cardtype" class="dropdownmenu">
              <option> </option>
              <option value="American Express" <% if( signupbean.getCardtype() != null && signupbean.getCardtype().equals("American Express") ) { %> selected <% } %>>American Express</option>
              <option value="Discover" <% if( signupbean.getCardtype() != null && signupbean.getCardtype().equals("Discover") ) { %> selected <% } %>>Discover</option>
              <option value="MasterCard" <% if( signupbean.getCardtype() != null && signupbean.getCardtype().equals("MasterCard") ) { %> selected <% } %>>MasterCard</option>
              <option value="Visa" <% if( signupbean.getCardtype() != null && signupbean.getCardtype().equals("Visa") ) { %> selected <% } %>>Visa</option>
            </select>
    </td>
  </tr>
</table>

<br>
<br>
After hitting "Submit" below, your credit card will be charged $30 (plus chat room and message board fees, if applicable) for one year 
of web site hosting on Warthog Pages.
<br>
<br>
<input type="submit" class="submit_button" value="Submit">&nbsp;&nbsp;<input type="reset" class="submit_button" value="Reset">

</form>
</body>
</html>
