package com.warthog.application.controllers;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.warthog.domain.core.*;
import com.warthog.domain.components.*;
import com.warthog.services.utils.*;

/**
*  EditBio edits a band's bio
*
*  @author Jamie Orlando
*  @version 1.0
*/
public class EditBio extends HttpServlet {

  /**
  *  Initializes the Servlet
  *  @param config the servlet configuration
  */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  
  /**
  *  Do Post Method:
  *    Gets the new bio from a form and updates this in the database
  *    Forwards to either a success or error page.
  *  
  *  @param request the request
  *  @param response the response
  */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    String url = (String)session.getAttribute("url");
    String body = request.getParameter("bio");
    String formatbody = TextFormatter.filter(body);
    
    Bio thebio = new Bio();
    thebio.setUrl(url);
    thebio.setBody(formatbody);

    String forwardUrl = new String();

    try{
      if(thebio.save()) {
        forwardUrl = "/biosuccess.jsp"; 
      }
      else {
        throw new SQLException();
      }
    }
    catch(SQLException e) {
      session.setAttribute("error", "Database Error! Please try again later!");      
      forwardUrl = "/bioerror.jsp";
    }

    ServletContext context = getServletContext();
    RequestDispatcher rd = context.getRequestDispatcher(forwardUrl);
    rd.forward(request, response);

  }
}
