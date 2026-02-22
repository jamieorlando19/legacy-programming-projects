package com.warthog.application.controllers;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.warthog.domain.core.*;
import com.warthog.domain.components.*;
import com.warthog.services.utils.*;

/**
*  EditScheme edits a band's scheme and layout
*
*  @author Jamie Orlando
*  @version 1.0
*/
public class EditScheme extends HttpServlet {

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

    String backgroundcolor = request.getParameter("backgroundColor");
    String textcolor = request.getParameter("textColor");
    String layout = request.getParameter("layout");
    String forwardUrl = new String();


    try {
      WebSiteLayout the_layout = new WebSiteLayout();
      the_layout.select(url);
      the_layout.setBackgroundColor(backgroundcolor);
      the_layout.setTextColor(textcolor);
      the_layout.setLayoutType(layout);
      
      if((the_layout.getTextColor()).compareTo(the_layout.getBackgroundColor()) == 0)
        throw new IOException();

      if(the_layout.save()) {
        forwardUrl = "/layoutsuccess.jsp"; 
      }
      else {
        throw new SQLException();
      }
    }
    catch(IOException e) {
      session.setAttribute("error", "Text Color and Background Color Cannot Be The Same!");
      forwardUrl = "/layouterror.jsp";
    }
    catch(SQLException e) {
      session.setAttribute("error", "Database Error! Please try again later!");
      forwardUrl = "/layouterror.jsp";
    }

    ServletContext context = getServletContext();
    RequestDispatcher rd = context.getRequestDispatcher(forwardUrl);
    rd.forward(request, response);
  }
}
