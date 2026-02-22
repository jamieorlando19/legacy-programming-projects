package com.warthog.application.controllers;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.warthog.domain.core.*;
import com.warthog.domain.components.*;
import com.warthog.services.utils.*;

/**
*  UpdateDate updates a show date
*
*  @author Jamie Orlando
*  @version 1.0
*/
public class UpdateDate extends HttpServlet {

  /**
  *  Initializes the Servlet
  *  @param config the servlet configuration
  */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  
  /**
  *  Do Post Method:
  *    Adds a new ShowDate to the database
  *  
  *  @param request the request
  *  @param response the response
  */
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    String url = (String)session.getAttribute("url");

    String year = request.getParameter("year");
    String month = request.getParameter("month");
    String day = request.getParameter("day");
    String venue = request.getParameter("venue").trim();
    String city = request.getParameter("city").trim();
    String state = request.getParameter("state");
    String comments = request.getParameter("comments").trim();
    String op = request.getParameter("operation");

    String fulldate = new String(year + "-" + month + "-" + day);
    String formatvenue = TextFormatter.filter(venue);
    String formatcity = TextFormatter.filter(city);
    String formatcomments = TextFormatter.filter(comments);

    String forwardUrl = new String();

    ShowDate show = new ShowDate();
    show.setUrl(url);
    show.setDate(fulldate);
    show.setVenue(formatvenue);
    show.setCity(formatcity);
    show.setState(state);
    show.setComments(formatcomments);

    session.setAttribute("fulldate", fulldate);
    session.setAttribute("venue", formatvenue);
    session.setAttribute("city", formatcity);
    session.setAttribute("state", state);
    session.setAttribute("comments", formatcomments);

    if(op.compareTo("add") == 0) {
    //ADD NEW NEWS

      try{
        if(!show.exists(url, fulldate, formatvenue)) {
          show.save();
          forwardUrl = "/showaddsuccess.jsp";
        }
        else {
          throw new IOException();
        }
      }
      catch(IOException e) {
        session.setAttribute("error", "The show date already exists!");
        forwardUrl = "/showadderror.jsp";
      }
      catch(SQLException e) {
        session.setAttribute("error", "Database Error! Please try again later!");
        forwardUrl = "/showadderror.jsp";
      }
    }
    else if(op.compareTo("update") == 0) {
    //UPDATE NEWS

      try{
        String olddate = (String)session.getAttribute("olddate");
        String oldvenue = (String)session.getAttribute("oldvenue");

        if(!show.delete(url, olddate, oldvenue))
          throw new IOException();

        if(!show.exists(url, fulldate, formatvenue)) {
          show.save();

        forwardUrl = "/showaddsuccess.jsp";
        }
        else {
          throw new IOException();
        }
      }
      catch(IOException e) {
        session.setAttribute("error", "Show Date doesn't exist, so it can't be editted!");
        forwardUrl = "/showadderror.jsp";
      }
      catch(SQLException e) {
        session.setAttribute("error", "Database Error! Please try again later!");
        forwardUrl = "/showadderror.jsp";
      }
    }

    ServletContext context = getServletContext();
    RequestDispatcher rd = context.getRequestDispatcher(forwardUrl);
    rd.forward(request, response);

  }
}
