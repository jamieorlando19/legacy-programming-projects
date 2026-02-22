/**
   A <code>Rack</code> object represents the rack of seven
   letter tiles a player may use in a Scrabble game.
*/
public class Rack
{
   /**
      Constructs a Rack object
   */
   public Rack()
   {
      RackSpaces = new Letter[8];
      for(int a = 0; a < 8; a++)           
         RackSpaces[a] = null;
   }

   /**
      Adds a new Letter to an empty space in a Rack object.
      @param l the Letter to be added
   */
   public void add(Letter l)
   {
      int emptyspace = 0;
      for( ; RackSpaces[emptyspace] != null; emptyspace++);
      RackSpaces[emptyspace] = l;
   }

   /**
      Determines the current number of letters in a player's Rack.
      @return the number of letters
   */
   public int lettersinrack()
   {
      int num = 0;
      for(int a = 0; a < 8; a++)
      {
         if(RackSpaces[a] != null)
            num++;
      }   
      return num;
   }

   /**
      Accessor function for the array of Letters that are
      contained in a Rack object.
      @return the array of Letters
   */
   public Letter[] getRackLetters()
   { return RackSpaces; }
   
   private Letter[] RackSpaces;
}
