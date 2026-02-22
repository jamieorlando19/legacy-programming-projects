package com.warthog.application.controllers;

import java.util.Enumeration;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;
import com.warthog.domain.core.*;
import com.warthog.domain.components.*;
import com.warthog.services.utils.*;

/**
*  UploadImage allows a user to upload an image
*
*  @author Jamie Orlando
*  @version 1.0
*/

public class UploadImage extends HttpServlet {
  private String dirName;
  
  /**
  *  Initializes the Servlet
  *  @param config the servlet configuration
  */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    dirName = new String("/usr/resin-2.1.9/webapps/warthog/uploads/images");
  }
  
  /**
  *  Do Post Method:
  *    Stores the image file in the appropriate uploads folder, and updates
  *  the database with a caption and path.  Forwards to a success or
  *  error page depending on the final condition.
  *
  *  @param request the request
  *  @param response the response
  */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    String forwardUrl = new String();

    try {
      String url = (String)session.getAttribute("url");
  
      MultipartRequest multi = new MultipartRequest(request, dirName, 150*1024, "ISO-8859-1", new DefaultFileRenamePolicy());
      Enumeration files = multi.getFileNames();
      String name = (String)files.nextElement();
      String filename = multi.getFilesystemName(name);
      File f = multi.getFile(name);

      double sizeinBytes = (new Long(f.length())).doubleValue();
      if(sizeinBytes == 0) throw new Exception();

      String caption = (multi.getParameter("caption")).trim();
      String formatcaption = TextFormatter.filter(caption);
      String formatfilename = TextFormatter.filter(filename);

      int index = Integer.parseInt(multi.getParameter("index"));

      double oneKilo = 1024;
      double sizeinKilos = sizeinBytes/oneKilo;
      DecimalFormat kiloFormat = new DecimalFormat("###.##");
      String formatlength = kiloFormat.format(sizeinKilos);

      try {

        ImageFile images = new ImageFile();
        images.select(url);
        images.setCaption(index, formatcaption);
        images.setPath(index, formatfilename);
        images.save();

        session.setAttribute("filename", formatfilename);
        session.setAttribute("filelength", formatlength);
        session.setAttribute("caption", formatcaption);      

        forwardUrl = "/uploadimagesuccess.jsp";
      } 
      catch(SQLException s) {
        session.setAttribute("error", "Database Error! Please try again later!");
        forwardUrl = "/uploadimageerror.jsp";
      }
    }
    catch (IOException i) {
      session.setAttribute("error", "File is larger than 150 KB!");
      forwardUrl = "/uploadimageerror.jsp";
    }
    catch (Exception e) {
      session.setAttribute("error", "File doesn't exist!");
      forwardUrl = "/uploadimageerror.jsp";
    }


    ServletContext context = getServletContext();
    RequestDispatcher rd = context.getRequestDispatcher(forwardUrl);
    rd.forward(request, response);
  }
}
