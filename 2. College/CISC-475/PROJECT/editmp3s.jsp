<%
  //Jamie Orlando
  //Edit MP3s Page
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>Upload/Delete MP3s</title>

<%@ page contentType="text/html" import="com.warthog.domain.core.*" %>
<%@ page contentType="text/html" import="com.warthog.domain.components.*" %>

<link type="text/css" rel="stylesheet" href="css/warthogStyle.css">

<script type="text/javascript">

function verifyupload(formnum) {
  var mp3name = document.forms[formnum].mp3.value
  var mp3len=mp3name.length
  var songname = document.forms[formnum].song.value
  var songlen=songname.length
  var SubmitOK="True"

  if(mp3len>=5) {
    var mp3ext=mp3name.substring(mp3len - 4, mp3len)
    var mp3extformat=mp3ext.toLowerCase()
    if(mp3extformat!=".mp3") {
      SubmitOK="False"
      alert("Invalid MP3 file!")
    }
  }
  else {
    SubmitOK="False"
    alert("Invalid MP3 file!")
  }

  if(songlen<=0) {
    SubmitOK="False"
    alert("Please enter the name of the song!")
  }

      
  if(SubmitOK=="False") {
    return false
  }
  else { 
    alert("Click OK, and please be patient while your file uploads!")
    return true
  }
}

</script>
</head>

<body>
<%
  String url = (String)session.getAttribute("url");
  MP3File mp3s = new MP3File();
  mp3s.select(url);
  String songs[] = new String[5];
  String paths[] = new String[5];

  for(int x = 0; x < 5; x++)
    songs[x] = mp3s.getSongTitle(x);

  for(int y = 0; y < 5; y++)
    paths[y] = mp3s.getPath(y);
%>
<center>
<h1>Upload/Delete MP3s</h1>
<br>
<table border="1" bordercolor="#000000" cellpadding="15">

<%
  //Generates Upload/Delete Forms
 
  for(int i = 0; i < 5; i++) {
    boolean upload = true;
    
    if(paths[i].compareTo("") != 0)
      upload = false;
%>

  <tr>

    <%
      if(upload) {
      //Generate Upload Form
    %>

    <td align="left valign="middle"> 
      <form name="mp3form<%=i%>" method="post" action="/warthog/servlet/UploadMp3" enctype="multipart/form-data" onsubmit="return verifyupload(<%=i%>)">
      <input name="index" type="hidden" value="<%=i%>">
      <font size="5"><b><%=i+1%></b></font>
    </td>

    <td align="left" valign="middle">
      Enter the path of the file on your computer<br>
      <input name="mp3" type="file"><br><br>
      Enter the name of the song<br>
      <input name="song" type="text" maxlength="30" size="35">
    </td>

    <td align="left" valign="middle">
      <input name="upload" type="submit" value="Upload MP3" class="submit_button">
      </form>
    </td>

    <%
      }
      else {
      //Generate Delete Form
    %>

    <td align="left" valign="middle">
      <form name="mp3form<%=i%>" method="post" action="/warthog/servlet/DeleteMp3" onsubmit=
          "
            if(confirm('Are you sure you want to delete this mp3 (<%=songs[i]%>)??'))
              {return true} 
            else
              {return false}
          "
      >
        <input name="index" type="hidden" value="<%=i%>">
        <font size="5"><b><%=i+1%></b></font>
    </td>

    <td align="left" valign="middle">
      <font size="5"><%=songs[i]%></font>
    </td>

    <td align="left" valign="middle">
      <input name="delete" type="submit" value="Delete MP3" class="submit_button">
      </form>
    </td>

    <%
      } // end if
    %>

  </tr>

<%
  } //end for loop
%>

</table>
<br>
<i>Each MP3 file must be smaller than 5MB!</i>
<br>
<h3><a href="/warthog/admin.jsp">Back to Admin Page</a></h3>
</center>
</body>
</html>
