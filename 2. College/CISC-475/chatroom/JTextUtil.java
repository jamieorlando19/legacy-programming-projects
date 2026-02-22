//package com.domain.chat;

import javax.swing.text.*;

/** 
  A simple utility for imposing a MAXIMUM length for a JTextField

  @author Team Warthog
  @version 1.0
*/
public class JTextUtil extends PlainDocument
{   
  private int maxChars;   

  /**
    Initializes the JTextUtil object
    @param max the maximum amount of characters allowed in a JTextField
  */
  public JTextUtil(int max)   
  { maxChars = max; }   

  /**
    Insert String Method
    @param offset
    @param s
    @param a
  */
  public void insertString (int offset, String s, AttributeSet a) throws BadLocationException   
  {
    if (getLength() + s.length() > maxChars)      
    { return; }
      
    super.insertString (offset, s, a);   
   }
}