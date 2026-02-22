package com.warthog.application.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import com.warthog.domain.core.*;
import com.warthog.domain.components.*;

/**
*  DeleteImage allows a user to delete an image
*
*  @author Jamie Orlando
*  @version 1.0
*/
public class DeleteImage extends HttpServlet {
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
  *    Deletes the image file from the uploads folder, and erases the caption
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

      ImageFile images = new ImageFile();
      images.select(url);

      File the_image = new File(dirName + "/" + images.getPath(index));
      the_image.delete();

      images.setCaption(index, "");
      images.setPath(index, "");
      images.save();

      ServletContext context = getServletContext();
      RequestDispatcher rd = context.getRequestDispatcher("/deleteimagesuccess.jsp");
      rd.forward(request, response);
    } 
    catch (IOException i) {
      ServletContext context = getServletContext();
      RequestDispatcher rd = context.getRequestDispatcher("/deleteimageerror.jsp");
      rd.forward(request, response);
    }
    catch(SQLException s) {
      ServletContext context = getServletContext();
      RequestDispatcher rd = context.getRequestDispatcher("/deleteimageerror.jsp");
      rd.forward(request, response);
    }
  }
}
