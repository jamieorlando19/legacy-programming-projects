package com.warthog.services.utils;

//TODO:
//	write isValidEmailAddress method

/** Class contains static methods used to manipulate text, mostly
 * in the form of personal information within an application.  The
 * class was designed to handle the formatting of text when receiving
 * user's registration information from a form in a web app, but is
 * not limited to this purpose.
 *
 * 	@author Christopher R. Cali (CRC)
 * 	@version 1.0
 *
 * File: TextFormatter.java
 *
 * May 9, 2003: Version 1.0 completed.
 *
 * &#0169 2003 Christopher R. Cali.  May be freely distributed for 
 * non-commercial purposes only.  Proper attribution required.
 */
public class TextFormatter 
{	
	/** Method formats a string into the proper name format.
	 * All proper names must begin with a capital letter followed
	 * by all lower case letters.
	 * 
	 * 	@param name string to be converted to proper name
	 * 	@return proper name formatted string
	 */
	public static String properName(String s) {
		
		StringBuffer buffer = null; 
		
		if (Character.isLowerCase(s.charAt(0))) {
			buffer = new StringBuffer(s);
                        buffer.setCharAt(0, 
				Character.toUpperCase(s.charAt(0)));
			s = buffer.toString();
			}
                else {}
	
		for(int i = 1; i < s.length(); ++i) {
               		if (Character.isUpperCase(s.charAt(i))) {
				buffer = new StringBuffer(s);
				buffer.setCharAt(i, 
					Character.toLowerCase
						(s.charAt(i)));
				s = buffer.toString();
				}
			else {}
			}
		return s;
		}

	/** Method checks to see if string is a valid email address.  To
	 * be a valid email address, the string must pass the following
	 * test.  Note: This method does NOT test whether or not the 
	 * email is valid in the sense that it has a real owner.
	 * 	1.  The string contains the '@' character AND it's not 
	 * 	the first character.
	 * 	2.  The string contains at least 1 '.' and it's not the
	 * 	first, last, or the character before or after the '@'.
	 *
	 * 	@param email the email address to test
	 *  	@return true if in valid format, false otherwise
	 */
	public static boolean isValidEmailAddress(String email) {
		return (true);	
		}

	/** Method filters out special html characters and replaces
	 * them with the &___ equivalent.
	 *	@param input input string
	 *	@return the filtered string
	 *  
	 *  Taken from More Servlets and JavaServer Pages
 	 *  from Prentice Hall and Sun Microsystems Press,
 	 *  http://www.moreservlets.com/.
 	 *  &copy; 2002 Marty Hall; may be freely used or adapted.
	 *
	 *  Modified by Christopher R. Cali (CRC) 5/17/03:
 	 *	- added the code to filter out apostraphe
	 */

 	// Note that Javadoc is not used here due to the
  	// difficulty of making the special chars readable in
  	// both plain text and HTML.

 	// Given a string, this method replaces all occurrences of
  	//  '<' with '&lt;', all occurrences of '>' with
 	//  '&gt;', and (to handle cases that occur inside attribute
  	//  values), all occurrences of double quotes with
  	//  '&quot;' and all occurrences of '&' with '&amp;'.
  	//  Without such filtering, an arbitrary string
  	// could not safely be inserted in a Web page.

  public static String filter(String input) {
    StringBuffer filtered = new StringBuffer(input.length());
    char c;
    for(int i=0; i<input.length(); i++) {
      c = input.charAt(i);
      if (c == '<') {
        filtered.append("&lt;");
      } else if (c == '>') {
        filtered.append("&gt;");
      } else if (c == '"') {
	filtered.append("&quot;");
      } else if(c == '\'') {
	filtered.append("&#39;");
      } else if (c == '&') {
        filtered.append("&amp;");
      } else {
        filtered.append(c);
      }
    }
    return(filtered.toString());
  }

	}
