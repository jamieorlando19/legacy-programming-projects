import java.util.*;

/**
   A <code>LetterList</code> object represents the pile of letter
   tiles in a Scrabble game.  It contains a linked list of the 
   remaining tiles throughout a game.
*/
public class LetterList
{
   /**
      Constructs a LetterList object.
   */
   public LetterList()
   {
      letter_pile = new LinkedList();
      create_list();  
   }

   /**
      Selects a letter at random from a LetterList object
      and removes that letter from the pile.
      @return the selected letter
   */
   public Letter get_letter()
   {
      Random get_index = new Random();
      int index = get_index.nextInt(letter_pile.size());
      Letter a_letter = (Letter)letter_pile.get(index);
      letter_pile.remove(index);
         
      return a_letter;
   }

   /**
      Accessor function for the current size of a
      LetterList object.
      @return the size of the list
   */
   public int get_size()
   { return letter_pile.size(); }

   /**
      Adds a new Letter to the LetterList.
      @param l the letter to be added
   */
   public void add(Letter l)
   { letter_pile.add((Object)l); }

   // creates the letter pile by adding the appropriate
   // number of each letter
   private void create_list()
   {
      add_letter('A', 9);
      add_letter('B', 2);
      add_letter('C', 2);
      add_letter('D', 4);
      add_letter('E', 12);
      add_letter('F', 2);
      add_letter('G', 3);
      add_letter('H', 2);
      add_letter('I', 9);
      add_letter('J', 1);
      add_letter('K', 1);
      add_letter('L', 4);
      add_letter('M', 2);
      add_letter('N', 6);
      add_letter('O', 8);
      add_letter('P', 2);
      add_letter('Q', 1);
      add_letter('R', 6);
      add_letter('S', 4);
      add_letter('T', 6);
      add_letter('U', 4);
      add_letter('V', 2);
      add_letter('W', 2);
      add_letter('X', 1);
      add_letter('Y', 2);
      add_letter('Z', 1);
      add_letter(' ', 2);
   }
   // adds a certain number, indicated by num, of the 
   // Letter indicated by let to the letter pile
   private void add_letter(char let, int num)
   {
      Letter new_let = new Letter(let);
      for(int a = 0; a < num; a++)
         letter_pile.add((Object)new_let);
   }

   private LinkedList letter_pile;
}
