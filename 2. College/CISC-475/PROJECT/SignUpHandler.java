import java.io.*;
import java.sql.*;
import java.math.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.warthog.application.controllers.*;
import com.warthog.domain.core.*;
import com.warthog.domain.components.*;

public class SignUpHandler extends HttpServlet
{

 public void init(ServletConfig conf) throws ServletException
 {
   super.init(conf);
 }

 public void doPost(HttpServletRequest req, HttpServletResponse rsp)
  throws ServletException, IOException
 {
   HttpSession session = req.getSession(true);
   if( session == null )
     rsp.sendRedirect("/error.html");

   SignUpBean signup = new SignUpBean();

   StringBuffer complaints = new StringBuffer();
   String bandname = req.getParameter("bandname");
   String url = req.getParameter("url");
   String contname = req.getParameter("contname");
   String contaddr = req.getParameter("contaddr");
   String contcity = req.getParameter("contcity");
   String contstate = req.getParameter("contstate");
   String contzip = req.getParameter("contzip");
   String contphone = req.getParameter("contphone");
   String contemail = req.getParameter("contemail");
   String password = req.getParameter("password");
   String pwconfirm = req.getParameter("pwconfirm");
   String chatroom = req.getParameter("chatroom");
   String messboard = req.getParameter("messboard");
   String layout = req.getParameter("layout");
   String ccnumber = req.getParameter("ccnumber");
   String ccname = req.getParameter("ccname");
   String expmonth = req.getParameter("expmonth");
   String expyear = req.getParameter("expyear");
   String cardtype = req.getParameter("cardtype");
   float yearlyrate;


   if( bandname != null )
     bandname = bandname.trim();
   if( url != null )
     url = url.trim();
   if( contname != null )
     contname = contname.trim();
   if( contaddr != null )
     contaddr = contaddr.trim();
   if( contcity != null )
     contcity = contcity.trim();
   if( contstate != null )
     contstate = contstate.trim();
   if( contzip != null )
     contzip = contzip.trim();
   if( contphone != null )
     contphone = contphone.trim();
   if( contemail != null )
     contemail = contemail.trim();
   if( ccnumber != null )
     ccnumber = ccnumber.trim();
   if( ccname != null )
     ccname = ccname.trim();


   if( bandname == null || bandname.equals("") )
     complaints.append("Band Name cannot be blank.<br>\n");

   boolean urlValid = true;
   if( url != null )
     url = url.toLowerCase();
   if( url == null || url.equals("") ) {
     complaints.append("Website Address cannot be blank.<br>\n");
     urlValid=false;
   }
   else if( url.length() < 3 ) {
     complaints.append("Website Address cannot be less than 3 characters.<br>\n");
     urlValid=false;
   }
   if( !URLEncoder.encode(url, "UTF-8").equals(url) ) {
     complaints.append("Website Address contains invalid characters.<br>\n");
     urlValid=false;
   }

   // if the url didn't fail any of the previous tests, check the database to see if it's already taken
   if( urlValid == true )
   {
     try {
       Band band = new Band();
       if( band.url_taken( url ) )
         complaints.insert(0, "Sorry, that Website Address is already taken!  Please choose another.<br>\n<br>\n");
     }
     catch( SQLException e ) {
       // For now we don't do anything if there's a SQLException
       // If we can't connect to the database, then the later code will complain when it can't connect either
     }
   }


   if( contname == null || contname.equals("") )
     complaints.append("Contact Name cannot be blank.<br>\n");

   if( contaddr == null || contaddr.equals("") )
     complaints.append("Contact Address cannot be blank.<br>\n");

   if( contcity == null || contcity.equals("") )
     complaints.append("Contact City cannot be blank.<br>\n");

   if( contstate == null || contstate.equals("") )
     complaints.append("Contact State cannot be blank.<br>\n");

// don't need this stuff anymore since we're using a drop-down:
//   else
//   {
//     contstate = contstate.toUpperCase();
//     if( contstate.length() < 2 )
//       complaints.append("Contact State must be 2 characters.<br>\n");
//   }

   if( contzip == null || contzip.equals("") )
     complaints.append("Contact Zip Code cannot be blank.<br>\n");
   else
   {
     if( contzip.length() < 5 )
       complaints.append("Contact Zip Code must be 5 characters long.<br>\n");
     try {
       Integer.valueOf(contzip);
     }
     catch(NumberFormatException e) {
       complaints.append("Contact Zip Code must be numeric.<br>\n");
     }
   }

   if( contphone == null || contphone.equals("") )
     complaints.append("Contact Phone Number cannot be blank.<br>\n");
   else
   {
     if( contphone.length() < 10 )
       complaints.append("Contact Phone Number must be 10 characters long.<br>\n");
     try {
       long pnumber = Long.parseLong(contphone);
       if(pnumber < 0)
	 complaints.append("Contact Phone Number must be numeric.<br>\n");
     }
     catch(NumberFormatException e) {
       complaints.append("Contact Phone Number must be numeric.<br>\n");
     }
   }

   if( contemail == null || contemail.equals("") )
     complaints.append("Contact Email cannot be blank.<br>\n");
   else if( contemail.indexOf("@") < 1 ||                               // at least 1 '@'; can't start with @
            contemail.indexOf("@") != contemail.lastIndexOf("@") ||     // only 1 '@'
            contemail.indexOf(".") < 1 ||                               // at least 1 '.'; can't start with '.'
            contemail.lastIndexOf(".") < contemail.indexOf("@") + 2 ||  // at least 1 '.' after the '@' (with at least 1 char between)
            contemail.length() <= contemail.lastIndexOf(".") + 2 )      // at least 2 characters after the last '.'
   {
     complaints.append("Contact Email must be in the form of *@*.*<br>\n");
   }

   if( password == null || password.equals("") )
     complaints.append("Password cannot be blank.<br>\n");
   else if( password.length() < 4 )
     complaints.append("Password must be at least 4 characters.<br>\n");

   if( pwconfirm == null || pwconfirm.equals("") )
     complaints.append("Confirmation Password cannot be blank.<br>\n");
   else if( pwconfirm.length() < 4 )
     complaints.append("Confirmation Password must be at least 4 characters.<br>\n");

   if( !password.equals(pwconfirm) )
     complaints.append("Password and Confirmation Password do not match.<br>\n");
   else if( !password.equals("") && password.equals(url) )
       complaints.append("Password cannot be the same as Website Address.<br>\n");

   yearlyrate = BASE_RATE;
   if( chatroom != null && !chatroom.equals("") )
     yearlyrate += CHATROOM_FEE;
   if( messboard != null && !messboard.equals("") )
     yearlyrate += MESSBOARD_FEE;

   if( layout == null || layout.equals("") )
     complaints.append("You must choose a layout.<br>\n");

   if( ccnumber == null || ccnumber.equals("") )
     complaints.append("Credit Card Number cannot be blank.<br>\n");
   else
   {
     if( ccnumber.length() != 16 )
       complaints.append("Credit Card Number must be 16 digits long.<br>\n");
     try {
       long cn = Long.parseLong(ccnumber);
       if(cn < 0)
   	 complaints.append("Credit Card Number must be numeric.<br>\n");
     }
     catch(NumberFormatException e) {
       complaints.append("Credit Card Number must be numeric.<br>\n");
     }
   }

   if( ccname == null || ccname.equals("") )
     complaints.append("Cardholder Name cannot be blank.<br>\n");

   if( expmonth == null || expmonth.equals("") || expmonth.equals(" ") )
     complaints.append("Expiration Month cannot be blank.<br>\n");

   if( expyear == null || expyear.equals("") || expyear.equals(" ") )
     complaints.append("Expiration Year cannot be blank.<br>\n");


   if( cardtype == null || cardtype.equals("") || cardtype.equals(" ") )
     complaints.append("Credit Card Type cannot be blank.<br>\n");

   if( (new File("/usr/resin-2.1.9/webapps/warthog/" + url)).exists() ) 
     complaints.append("Url " + url + " is in use or is invalid.<br>\n");

   signup.setComplaints(complaints.toString());
   signup.setBandname(bandname);
   signup.setUrl(url);
   signup.setContname(contname);
   signup.setContaddr(contaddr);
   signup.setContcity(contcity);
   signup.setContstate(contstate);
   signup.setContzip(contzip);
   signup.setContphone(contphone);
   signup.setContemail(contemail);
   signup.setPassword(password);
   signup.setPwconfirm(pwconfirm);
   signup.setChatroom(chatroom);
   signup.setMessboard(messboard);
   signup.setLayout(layout);
   signup.setCcnumber(ccnumber);
   signup.setCcname(ccname);
   signup.setExpmonth(expmonth);
   signup.setExpyear(expyear);
   signup.setCardtype(cardtype);
   signup.setYearlyrate(yearlyrate);   

   session.setAttribute("signupbean", signup);


   if( complaints.length() == 0 )   // all fields were filled in correctly
   {
     try{
	Band band = new Band();
  	band.setUrl(url);
 	band.setBandName(bandname);
	band.save();

	StringTokenizer nameTokenize = new StringTokenizer(contname);
	String firstName = nameTokenize.nextToken();
	String lastName;
   	if(nameTokenize.hasMoreTokens())
	  lastName = nameTokenize.nextToken();
	else
	  lastName = " ";
        int zip = Integer.parseInt(contzip);
        

 	BandAdministrator bandAdmin = new BandAdministrator();
	bandAdmin.setUrl(url);
 	bandAdmin.setCity(contcity);
	bandAdmin.setState(contstate);
	bandAdmin.setStreet(contaddr);
	bandAdmin.setZip(contzip);
	bandAdmin.setPhoneNumber(contphone);
	bandAdmin.setEmail(contemail);
	bandAdmin.setFirstName(firstName);
	bandAdmin.setLastName(lastName);
	bandAdmin.save();

        java.sql.Date today = new java.sql.Date( System.currentTimeMillis() );
        java.sql.Date yearFromToday = new java.sql.Date( System.currentTimeMillis() + (long)365*24*60*60*1000 );
        Account account = new Account();
        account.setUrl(url);
        account.setPassword(password);
        account.setStartDate( today.toString() );
        account.setEndDate( yearFromToday.toString() );
        account.setYearlyRate( yearlyrate );
        account.save();

	CreditCard CC = new CreditCard();
	CC.setUrl(url);
	CC.setNumber(ccnumber);
	CC.setProvider(cardtype);
	CC.setCardholderName(ccname);
	CC.setExpDate( new String(expyear + "-" + expmonth + "-01") );
	CC.save();

        WebSiteLayout wsl = new WebSiteLayout();
        wsl.setUrl(url);
        wsl.setLayoutType(layout);
        if( layout.equals("1") ) {
          wsl.setBackgroundColor( WebSiteLayout.LAYOUT1_DEFAULTBGCOLOR );
          wsl.setTextColor( WebSiteLayout.LAYOUT1_DEFAULTTEXTCOLOR );
        }
        else if( layout.equals("2") ) {
          wsl.setBackgroundColor( WebSiteLayout.LAYOUT2_DEFAULTBGCOLOR );
          wsl.setTextColor( WebSiteLayout.LAYOUT2_DEFAULTTEXTCOLOR );
        }
        else {
          wsl.setBackgroundColor( WebSiteLayout.LAYOUT3_DEFAULTBGCOLOR );
          wsl.setTextColor( WebSiteLayout.LAYOUT3_DEFAULTTEXTCOLOR );
        }

        if( chatroom != null && !chatroom.equals("") )
          wsl.setHasChatRoom(true);
        else
          wsl.setHasChatRoom(false);

        wsl.save();


        WebSite ws = new WebSite();
        ws.setUrl(url);
        ws.setTitle(bandname);
        ws.save();

        Bio bio = new Bio();
        bio.setUrl(url);
        bio.setBody("");
        bio.save();

        ImageFile imagefile = new ImageFile();
        imagefile.setUrl(url);
        for( int x=0; x<5; x++ ) {
          imagefile.setCaption(x, "");
          imagefile.setPath(x, "");
        }
        imagefile.save();

        MP3File mp3file = new MP3File();
        mp3file.setUrl(url);
        for( int x=0; x<5; x++ ) {
          mp3file.setSongTitle(x, "");
          mp3file.setPath(x, "");
        }
        mp3file.save();


        File newdir = new File("/usr/resin-2.1.9/webapps/warthog/" + url + "/");
        if( !newdir.exists() )
          newdir.mkdir();
        File newfile = new File("/usr/resin-2.1.9/webapps/warthog/" + url + "/index.jsp");
        if( !newfile.exists() )
          newfile.createNewFile();
        PrintWriter outNewFile = new PrintWriter( new FileOutputStream(newfile) );
        outNewFile.println("<jsp:forward page=\"/servlet/LoadSiteHandler?url=" + url + "\" />");
        outNewFile.flush();
        outNewFile.close();

     }
     catch(SQLException e){
	ServletContext sc = getServletContext();
	RequestDispatcher rd = sc.getRequestDispatcher("/error.html");
	System.out.println("EXCEPTION");
     	rd.forward(req, rsp);
     }

     String passwordAsterisks = new String("");
     for( int x=0; x<password.length(); x++ )
       passwordAsterisks = passwordAsterisks.concat("*");
     signup.setPassword( passwordAsterisks );

     if( chatroom == null || chatroom.equals("") )
        signup.setChatroom("No");
     else
        signup.setChatroom("Yes");

     if( messboard == null || messboard.equals("") )
        signup.setMessboard("No");
     else
        signup.setMessboard("Yes");

     signup.setLayout( layout.toUpperCase() );

     signup.setCcnumber( "************" + ccnumber.substring( ccnumber.length()-4 ) );

     ServletContext sc = getServletContext();
     RequestDispatcher rd = sc.getRequestDispatcher(successUrl);
     rd.forward(req, rsp);
   }
   else
   {
     ServletContext sc = getServletContext();
     RequestDispatcher rd = sc.getRequestDispatcher(failUrl);
     rd.forward(req, rsp);
   }
 }

 String successUrl = "/success.jsp";
 String failUrl = "/signup.jsp";

 final float BASE_RATE = (float)30.00;
 final float CHATROOM_FEE = (float)5.00;
 final float MESSBOARD_FEE = (float)10.00;
}
