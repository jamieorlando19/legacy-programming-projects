package com.warthog.application.controllers;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.warthog.domain.core.*;

/**
*  ValidateLogin allows a user to login into the site
*  
*  @author Jamie Orlando
*  @version 1.0
*/
public class ValidateLogin extends HttpServlet {

  /**
  *  Initializes the Servlet
  *  @param config the servlet configuration
  */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  
  /**
  *  Do Post Method:
  *    Takes the username and password parameter and checks it against the database.
  *    Forwards the user to the admin page if successful, back to the login page if not.
  *
  *  @param request the request
  *  @param response the response
  */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String url = request.getParameter("login");
    HttpSession session = request.getSession(true);

    Account account = new Account();
    String forwardUrl = new String();
    session.setAttribute("url", url);

    try{
      if(account.select(url) && (account.getPassword()).compareTo(request.getParameter("pw")) == 0) {
        forwardUrl = "/admin.jsp";
      }
      else {
        forwardUrl = "/index.jsp";
      }
    }
    catch(SQLException e) {
      forwardUrl = "/index.jsp";
    }

    ServletContext context = getServletContext();
    RequestDispatcher rd = context.getRequestDispatcher(forwardUrl);
    rd.forward(request, response);

  }
}
