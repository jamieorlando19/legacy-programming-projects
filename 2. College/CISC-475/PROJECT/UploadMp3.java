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
*  UploadMp3 allows a user to upload an mp3
*
*  @author Jamie Orlando
*  @version 1.0
*/
public class UploadMp3 extends HttpServlet {
  private String dirName;
  
  /**
  *  Initializes the Servlet
  *  @param config the servlet configuration
  */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    dirName = new String("/usr/resin-2.1.9/webapps/warthog/uploads/mp3s");
  }
  
  /**
  *  Do Post Method:
  *    Stores the mp3 file in the appropriate uploads folder, and updates
  *  the database with a songtitle and path.  Forwards to a success or
  *  error page depending on the final condition.
  *
  *  @param request the request
  *  @param response the response
  */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException{      
    HttpSession session = request.getSession(true);  
    String forwardUrl = new String();

    try {
      String url = (String)session.getAttribute("url");

      MultipartRequest multi = new MultipartRequest(request, dirName, 5*1024*1024, "ISO-8859-1", new DefaultFileRenamePolicy());
      Enumeration files = multi.getFileNames();
      String name = (String)files.nextElement();
      String filename = multi.getFilesystemName(name);
      File f = multi.getFile(name);

      double sizeinBytes = (new Long(f.length())).doubleValue();
      if(sizeinBytes == 0) throw new Exception();

      String song = (multi.getParameter("song")).trim();
      String formatsong = TextFormatter.filter(song);
      String formatfilename = TextFormatter.filter(filename);

      int index = Integer.parseInt(multi.getParameter("index"));

      double oneMeg = 1048576;
      double sizeinMegs = sizeinBytes/oneMeg;
      DecimalFormat megFormat = new DecimalFormat("#.#####");
      String formatlength = megFormat.format(sizeinMegs);

      try {
        if(sizeinBytes == 0) throw new Exception();
 
        MP3File mp3s = new MP3File();
        mp3s.select(url);
        mp3s.setSongTitle(index, formatsong);
        mp3s.setPath(index, formatfilename);
        mp3s.save();

        session.setAttribute("filelength", formatlength);
        session.setAttribute("filename", formatfilename);
        session.setAttribute("songtitle", formatsong);

        forwardUrl = "/uploadmp3success.jsp";
      }
      catch(SQLException s) {
        session.setAttribute("error", "Database Error! Please try again later!");
        forwardUrl = "/uploadmp3error.jsp";
      }
    }
    catch (IOException i) {
      session.setAttribute("error", "File is larger than 5 MB!");
      forwardUrl = "/uploadmp3error.jsp";
    }
    catch (Exception e) {
      session.setAttribute("error", "File doesn't exist!");
      forwardUrl = "/uploadmp3error.jsp";
    }

    ServletContext context = getServletContext();
    RequestDispatcher rd = context.getRequestDispatcher(forwardUrl);
    rd.forward(request, response);
  }
}
