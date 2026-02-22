package com.warthog.application.controllers;

import java.io.*;
import java.sql.*;
import java.math.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.warthog.application.controllers.*;
import com.warthog.domain.core.*;

public class LoadSiteHandler extends HttpServlet
{

   public void init(ServletConfig conf) throws ServletException
   {
      super.init(conf);
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
   {
      String destPage; 
      HttpSession session = request.getSession(true);
      if( session == null )
         response.sendRedirect("/error.html");
	
      try { 
	 String url = request.getParameter("url");       
	 WebSiteLayout siteLayout = new WebSiteLayout(); 
	 
         if( !siteLayout.select(url) ) 
            destPage = errorPage;
	 session.setAttribute("url", url);

         System.out.println( "url is " + url );
         // System.out.println( "website url is " + website.getUrl() );
         // System.out.println( "website title is " + website.getTitle() );

	 String layoutType = siteLayout.getLayoutType();
         System.out.println( "website layout is " + layoutType );
         
         if( layoutType.equals("1") ) 
	    destPage = "/layout1.jsp";
	 else if( layoutType.equals("2") )
	    destPage = "/layout2.jsp";
	 else if( layoutType.equals("3") )
	    destPage = "/layout3.jsp"; 
	 else destPage = errorPage;
         System.out.println( "dest page is " + destPage );
      }
      catch( SQLException e ) { destPage = errorPage; }

      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(
	 destPage );
      dispatcher.forward( request, response );
   }
   String errorPage = "/LoadSiteError.jsp";
}
