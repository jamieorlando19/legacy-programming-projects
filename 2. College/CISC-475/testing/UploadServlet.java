import java.util.Enumeration;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;

public class UploadServlet extends HttpServlet {
  private String dirName;
  
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    // read the uploadDir from the servlet parameters
    dirName = config.getInitParameter("uploadDir");
    if (dirName == null) {
      throw new ServletException("Please supply uploadDir parameter");
    }
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    response.setContentType("text/plain");
      
    try {
      MultipartRequest multi = new MultipartRequest(request, dirName, 5*1024*1024, "ISO-8859-1", new DefaultFileRenamePolicy());
      Enumeration files = multi.getFileNames();
      String name = (String)files.nextElement();
      String filename = multi.getFilesystemName(name);
      File f = multi.getFile(name);

      double sizeinBytes = (new Long(f.length())).doubleValue();
      double oneMeg = 1048576;
      double sizeinMegs = sizeinBytes/oneMeg;
      DecimalFormat megFormat = new DecimalFormat("#.#####");
      String formatlength = megFormat.format(sizeinMegs);

      out.println("<html>");
      out.println("<head>");
      out.println("<title>");
      out.println("File Uploaded Succesfully!");
      out.println("</title>");
      out.println("<center>");
      out.println("<h1>File Uploaded Successfully!</h1><br>");
      out.println("File Name<br><b>" + filename + "</b><br>");
      out.println("File Size<br><b>" + formatlength + "MB</b><br><br>");
      out.println("<a href=/testing/fileupload.html>Upload Another File</a>");
      out.println("</center>");
      out.println("</html>");
    } 
    catch (IOException i) {
      out.println("ERROR: File is larger than 5MB!<BR>Please upload a smaller file!");
    }
  }
}
