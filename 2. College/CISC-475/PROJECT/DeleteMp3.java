package com.warthog.application.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import com.warthog.domain.core.*;
import com.warthog.domain.components.*;

/**
*  DeleteMp3 allows a user to delete an mp3
*
*  @author Jamie Orlando
*  @version 1.0
*/
public class DeleteMp3 extends HttpServlet {
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
  *    Deletes the mp3 file from the uploads folder, and erases the song title
  *  and path out of the database.  Forwards to a success or error page depending
  *  on the final condition.
  *
  *  @param request the request
  *  @param response the response
  */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException{      
    try {
      HttpSession session = request.getSession(true);  
      String url = (String)session.getAttribute("url");
      int index = Integer.parseInt(request.getParameter("index"));

      MP3File mp3s = new MP3File();
      mp3s.select(url);

      File the_mp3 = new File(dirName + "/" + mp3s.getPath(index));
      the_mp3.delete();

      mp3s.setPath(index, "");
      mp3s.setSongTitle(index, "");
      mp3s.save();

      ServletContext context = getServletContext();
      RequestDispatcher rd = context.getRequestDispatcher("/deletemp3success.jsp");
      rd.forward(request, response);
    } 
    catch (IOException i) {
      ServletContext context = getServletContext();
      RequestDispatcher rd = context.getRequestDispatcher("/deletemp3error.jsp");
      rd.forward(request, response);
    }
    catch(SQLException s) {
      ServletContext context = getServletContext();
      RequestDispatcher rd = context.getRequestDispatcher("/deletemp3error.jsp");
      rd.forward(request, response);
    }
  }
}
