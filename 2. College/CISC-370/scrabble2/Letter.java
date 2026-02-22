/**
   A <code>Letter</code> object represents a letter tile in a 
   Scrabble game.  It has a char for the letter and an int for
   that letter's respective point value.
*/
public class Letter
{
   /**
      Constructs a Letter object.
      @param x the letter that the object will represent.
   */ 
   public Letter(char x)
   {
      letter = x;
      set_letter_value(x);
   }

   /**
      Accessor function for the letter of a Letter object.
      @return the letter
   */
   public char get_letter() { return letter; }

   /** 
      Accessor function for the corresponding point value of 
      a letter.
      @return the point value
   */
   public int get_value() { return value; }

   // Sets the value field to the appropriate point value 
   // for the letter c
   private void set_letter_value(char c)
   {
      switch(c)
      {
         case 'A':
         case 'E':
         case 'I':
         case 'L':
         case 'N':
         case 'O':
         case 'R':
         case 'S':
         case 'T':
         case 'U':
            value = 1;
            break;  
         case 'B':
         case 'C':
         case 'M':
         case 'P':
            value = 3;
            break;
         case 'D':
         case 'G':
            value = 2;
            break;
         case 'F':
         case 'H':
         case 'V':
         case 'W':
         case 'Y':
            value = 4;
            break;
         case 'J':
         case 'X':
            value = 8;
            break;
         case 'K':
            value = 5;
            break;
         case 'Q':
         case 'Z':
            value = 10;
            break;
         case ' ':         //blank tile
            value = 0;
      }
   } 

   private char letter;
   private int value;
}
