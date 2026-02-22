package com.warthog.application.controllers;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.warthog.domain.core.*;
import com.warthog.domain.components.*;
import com.warthog.services.utils.*;

/**
*  UpdateNews updates a band's news
*
*  @author Jamie Orlando
*  @version 1.0
*/
public class UpdateNews extends HttpServlet {

  /**
  *  Initializes the Servlet
  *  @param config the servlet configuration
  */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  

  /**
  *  Do Post Method:
  *    Adds a new NewsItem to the database
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
    String title = request.getParameter("title");
    String body = request.getParameter("news");
    String op = request.getParameter("operation");

    body.replaceAll("&amp;", "&");
    body.replaceAll("&apos;", "\\'");

    String fulldate = new String(year + "-" + month + "-" + day);
    String formattitle = TextFormatter.filter(title);
    String formatnews = TextFormatter.filter(body);
    String forwardUrl = new String();

    NewsItem news = new NewsItem();
    news.setUrl(url);
    news.setDatePosted(fulldate);
    news.setTitle(formattitle);
    news.setBody(formatnews);    

    if(op.compareTo("add") == 0) {
    //ADD NEW NEWS

      try{
        if(!news.exists(url, fulldate, formattitle)) {
          news.save();

          session.setAttribute("fulldate", fulldate);
          session.setAttribute("title", formattitle);
          session.setAttribute("body", formatnews);

          forwardUrl = "/newsaddsuccess.jsp";
        }
        else {
          throw new IOException();
        }
      }
      catch(IOException e) {
        session.setAttribute("error", "This news item already exists!");
        forwardUrl = "/newsadderror.jsp";
      }
      catch(SQLException e) {
        session.setAttribute("error", "Database Error! Please try again later!");
        forwardUrl = "/newsadderror.jsp";
      }
    }
    else if(op.compareTo("update") == 0) {
    //UPDATE NEWS

      try{
        String olddate = (String)session.getAttribute("olddate");
        String oldtitle = (String)session.getAttribute("oldtitle");

        session.setAttribute("fulldate", fulldate);
        session.setAttribute("title", formattitle);
        session.setAttribute("body", formatnews);


        if(!news.delete(url, olddate, oldtitle))
          throw new IOException();

        if(!news.exists(url, fulldate, formattitle)) {
          news.save();
          forwardUrl = "/newsaddsuccess.jsp";
        }
        else {
          throw new IOException();
        }
      }
      catch(IOException e) {
        session.setAttribute("error", "News item doesn't exist to be editted!");
        forwardUrl = "/newsadderror.jsp";
      }
      catch(SQLException e) {
        session.setAttribute("error", "Database Error! Please try again later!");
        forwardUrl = "/newsadderror.jsp";
      }
    }

    ServletContext context = getServletContext();
    RequestDispatcher rd = context.getRequestDispatcher(forwardUrl);
    rd.forward(request, response);

  }
}
