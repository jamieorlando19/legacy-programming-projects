/**  	WelcomePage servlet displays the main Warthog-bands website, 
	offers help, and presents login area
	
	@author	  Christopher Cali
	@version  1.0
	@since	1.0
*/

package warthogsite;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import utils.*;			// for ServletUtilities

public class WelcomePage extends HttpServlet {

	public void doGet(HttpServletRequest request, 
			HttpServletResponse response)
	throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		ServletUtilities.HTMLheadWithMeta(out, "Transitional", title, 
				styleSheet, metaAuthor, metaKeywords, 
				metaDescription);
	
		out.println("<body>");
		out.println("<h1>" + title + "</h1>");
		
		//setup basic table layout
		out.println("<table border=\"0\" width=\"95%\"" + 
				" cellspacing=\"2\" cellpadding=\"2\">");  
		out.println("<tr>");
		out.println("<td width=\"20%\" valign=\"top\">"); 

		//should call a method so that it's not hard coded
		//ie. showLogin() or something.
		showLogin(out);	
		out.println("</td>");
		out.println("<td rowspan=\"2\">");
		//call showNews() or showMainWindow() or something
		//out.println("Rubbish and Poppycock");
		out.println("</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td align=\"center\" valign=\"top\">");
		showLinks(out);
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");
		out.println("</p>");

		out.println("<p class=\"centered\">");
		out.println("<small>" + copyright+ "</small>");
		ServletUtilities.printTab(out); 
		out.println("<img src=\"" + resinLogo + "\">");
		out.println("</p>");
	
		ServletUtilities.footer(out);
	}

        public void doPost(HttpServletRequest request,
                        HttpServletResponse response)
        throws ServletException, IOException {

		doGet(request, response);
	}

	private void showLogin(PrintWriter out)
	{
		out.println("<table class=\"light_orange\" border=\"0\"" +  
				" cellspacing=\"0\" cellpadding=\"1\">");
		
		out.println("<tr>");
		out.println("<td align=\"right\">");
		// Don't forget to set maxlength in name and password
		// fields when agreed upon 
		out.println("<form action=\"something\">");
		out.println("<small><b>Login:</b></small>");
		out.println("</td>");
		out.println("<td>");
		out.println("<input type=\"text\" name=\"login\"" + 
				" class=\"input_box\" style=\"width:90\">"); 
		out.println("</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td align =\"right\">");
		out.println("<small><b>Password:</b></small>");
		out.println("</td>");
		out.println("<td>");
		out.println("<input type=\"password\" name=\"passwd\"" + 
				" class =\"input_box\" style=\"width:90\">");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		// netscape renders empty columns with no background
		// color, therefore you must fill in a space or transparent gif	
		out.println("<td>&nbsp;</td>");
		out.println("<td>");
		out.println("<center><input type=\"submit\"" +
				" class=\"submit_button\" value=\"Login\"></center>");
		out.println("</td>");
		out.println("</tr>");

		out.println("</form>");
		out.println("</table>");
	}

	private void showLinks(PrintWriter out)
	{
		out.println("<ul class=\"square\">");
		out.println("<li><a href=\"/warthog/servlet/SignUp\""
				+ " class=\"no_underline\">Sign Up!</a>");
		out.println("</ul>");
	}
	
	private String title = new String(":: Warthog-bands ::");
        private String styleSheet = new String("../css/warthogStyle.css");
        private String metaAuthor = new String("Team Warthog");
	private String metaKeywords = new String("CISC 475, band, bands, press kit, web sites");
	private String metaDescription = new String ("Warthog-bands offers a forum for musicians to construct an online press kit / web-site in order to promote their work.");
	private String copyright= new String ("&copy 2003 CISC475 Team Warthog");
	private String resinLogo = new String ("../images/resinLogo.gif");
}



