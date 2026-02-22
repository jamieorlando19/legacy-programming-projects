package com.warthog.application.controllers;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.warthog.domain.core.*;
import com.warthog.domain.components.*;
import com.warthog.services.utils.*;

/**
*  DeleteDate deletes a band's show date
*
*  @author Jamie Orlando
*  @version 1.0
*/
public class DeleteDate extends HttpServlet {

  /**
  *  Initializes the Servlet
  *  @param config the servlet configuration
  */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  

  /**
  *  Do Post Method:
  *    Deletes a ShowDate from the database
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
    String venue = info.substring(14, info.length());

    String forwardUrl = new String();
    try {
      boolean b = ShowDate.delete(url, fulldate, venue);
      if(b) {
        forwardUrl = "/showdeletesuccess.jsp";
      }
      else {
        throw new IOException("Show Date doesn't exist!");
      }
    }
    catch(IOException e) {
      session.setAttribute("error", e.toString());
      forwardUrl = "/showdeleteerror.jsp";
    }
    catch(SQLException e) {
      session.setAttribute("error", "Database error! " + e.toString());
      forwardUrl = "/showdeleteerror.jsp";
    }

    ServletContext context = getServletContext();
    RequestDispatcher rd = context.getRequestDispatcher(forwardUrl);
    rd.forward(request, response);

  }
}
