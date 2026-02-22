package com.warthog.application.controllers;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.warthog.domain.core.*;


/**
*  ValidateLogin allows a user to log out of the site
*
*  @author Jamie Orlando
*  @version 1.0
*/

public class ValidateLogout extends HttpServlet {

  /**
  *  Initializes the Servlet
  *  @param config the servlet configuration
  */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  

  /**
  *  Do Post Method:
  *    Clears out the HttpSession and forwards to index.jsp
  *
  *  @param request the request
  *  @param response the response
  */

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    (request.getSession(true)).invalidate();
    ServletContext context = getServletContext();
    RequestDispatcher rd = context.getRequestDispatcher("/index.jsp");
    rd.forward(request, response);
  }
}
