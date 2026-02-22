package com.warthog.application.controllers;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.warthog.domain.core.*;
import com.warthog.domain.components.*;
import com.warthog.services.utils.*;

/**
*  DeleteNews deletes a band's news
*
*  @author Jamie Orlando
*  @version 1.0
*/
public class DeleteNews extends HttpServlet {

  /**
  *  Initializes the Servlet
  *  @param config the servlet configuration
  */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  

  /**
  *  Do Post Method:
  *    Deletes a NewsItem from the database
  *  
  *  @param request the request
  *  @param response the response
  */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    String url = (String)session.getAttribute("url");
    String info = request.getParameter("info");

    String fulldate = info.substring(0, 10);  
    String title = info.substring(14, info.length());

    String forwardUrl = new String();
    try {
      boolean b = NewsItem.delete(url, fulldate, title);
      if(b) {
        forwardUrl = "/newsdeletesuccess.jsp";
      }
      else {
        throw new IOException("News Item doesn't exist!");
      }
    }
    catch(IOException e) {
      session.setAttribute("error", e.toString());
      forwardUrl = "/newsdeleteerror.jsp";
    }
    catch(SQLException e) {
      session.setAttribute("error", "Database error! " + e.toString());
      forwardUrl = "/newsdeleteerror.jsp";
    }

    ServletContext context = getServletContext();
    RequestDispatcher rd = context.getRequestDispatcher(forwardUrl);
    rd.forward(request, response);

  }
}
