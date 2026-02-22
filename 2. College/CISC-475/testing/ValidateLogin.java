import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.warthog.domain.core.*;

public class ValidateLogin extends HttpServlet {
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
//    PrintWriter out = response.getWriter();
//    response.setContentType("text/plain");
    HttpSession session = request.getSession(true);
    ServletContext context = getServletContext();

//    String url = request.getParameter("login");
//    Account account = new Account();
    session.setAttribute("url", "testing123");
//    String forwardUrl = new String();

/*
    try{
      if(account.select(url) && (account.getPassword()).compareTo(request.getParameter("pw")) == 0) {
        forwardUrl = "/admin.jsp";        
      }
      else {
        forwardUrl = "/index.jsp";
      }
    }
    catch(SQLException e) {
      forwardUrl = "index.jsp";
    }
*/
    RequestDispatcher rd = context.getRequestDispatcher("/index.jsp");
    rd.forward(request, response);

  }
}
