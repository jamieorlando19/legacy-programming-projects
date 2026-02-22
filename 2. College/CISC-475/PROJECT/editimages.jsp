<%
  //Jamie Orlando
  //Edit Images Page
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset //EN"
"http://www.w3.org/TR/REC-html40/frameset.dtd">

<html>
<head>
<title>Upload/Delete Images!</title>

<%@ page contentType="text/html" import="com.warthog.domain.core.*" %>
<%@ page contentType="text/html" import="com.warthog.domain.components.*" %>

<link type="text/css" rel="stylesheet" href="css/warthogStyle.css">
<script type="text/javascript">

function verifyupload(formnum) {
  var imagename = document.forms[formnum].image.value
  var imagelen=imagename.length
  var SubmitOK="True"

  if(imagelen>=6) {
    var imageext4=imagename.substring(imagelen - 5, imagelen)
    var imageformat4=imageext4.toLowerCase()

    if(imageformat4!=".jpeg") {
      var imageext3=imagename.substring(imagelen - 4, imagelen)
      var imageformat3=imageext3.toLowerCase()
      if(imageformat3!=".jpg" && imageformat3!=".gif" && imageformat3!=".bmp") { 
        SubmitOK="False"
      }
    }
  }
  else {
    SubmitOK="False"
  }
      
  if(SubmitOK=="False") {
    alert("Invalid Image File!")
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
  ImageFile images = new ImageFile();
  images.select(url);

  String captions[] = new String[5];
  String paths[] = new String[5];

  for(int x = 0; x < 5; x++)
    captions[x] = images.getCaption(x);
    
  for(int y = 0; y < 5; y++)
    paths[y] = images.getPath(y);
%>


<center>
<h1>Upload/Delete Images</h1>
<br>

<table border="1" bordercolor="#000000" cellpadding="15">
      
<%
  //Generates Upload/Delete Forms
    
  for(int i = 0; i < 5; i++) {
    boolean upload = true;
      
    if((images.getPath(i)).compareTo("") != 0)
      upload = false;
%>
       
  <tr>
      
    <%
      if(upload) {
      //Generate Upload Form
    %>
           
    <td align="left valign="middle">
      <form name="imageform<%=i%>" method="post" action="/warthog/servlet/UploadImage" enctype="multipart/form-data" onsubmit="return verifyupload(<%=i%>)">
      <input name="index" type="hidden" value="<%=i%>">
      <font size="5"><b><%=i+1%></b></font>
    </td>  
   <td align="left" valign="middle">
      Enter the path of the file on your computer<br>
      <input name="image" type="file"><br><br>
      Enter a caption for the file<br>
      <input name="caption" type="text" maxlength="30" size="35">
    </td>
    
    <td align="left" valign="middle">
      <input name="upload" type="submit" value="Upload Image" class="submit_button">
      </form>
    </td>
    
    <% 
      }
      else {
      //Generate Delete Form
    %>
  
    <td align="left" valign="middle">
      <form name="imageform<%=i%>" method="post" action="/warthog/servlet/DeleteImage" onsubmit=
          "
            if(confirm('Are you sure you want to delete this image??'))
              {return true}
            else
              {return false}
          "
      >
        <input name="index" type="hidden" value="<%=i%>">
        <font size="5"><b><%=i+1%></b></font>
    </td>
      
    <td align="left" valign="middle">
      <font size="5"><%=images.getCaption(i)%></font>
    </td>
      
    <td align="left" valign="middle">
      <input name="delete" type="submit" value="Delete Image" class="submit_button">
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

  <i>Each image file must be smaller than 150KB!</i><br>
  <small>Accepted File types: jpg, jpeg, gif, bmp</small>
<br>
<h3><a href="/warthog/admin.jsp">Back to Admin Page</a></h3>
</center>
</body>
</html>
