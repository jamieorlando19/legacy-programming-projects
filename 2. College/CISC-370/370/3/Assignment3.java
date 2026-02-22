/**  Assignment3 object has only one method which takes an array
     of Objects, and returns that array sorted (Objects must implement
     the Comparable interface, or a ObjectIsNotComparableException will
     be thrown.
     @author Jamie Orlando 
     @version 1.0
     @since 1.0
     @see <a href="http://udel.edu/~orlandoj">Jamie's Page</a>
*/

public class Assignment3
{
   /**
      Sorts a list of comparable objects
      @param initial_list, a list of Comparable Objects
      @return a list of sorted Comparable Objects
      @throws ObjectIsNotComparableException
   */
   public static Object[] sort(Object[] initial_list)
      throws ObjectIsNotComparableException
   {
      if(!(initial_list[0] instanceof Comparable))
         throw new ObjectIsNotComparableException();

      Object list[] = (Object[])initial_list.clone();
      
      //Selection Sort Begin
      int largest, position, current;
      for(position = list.length - 1; position > 0; position--)
      {
         largest = 0;
         for(current = 1; current <= position; current++)
         {
            if(((Comparable)list[largest]).compareTo(list[current]) < 0)      
                largest = current;
         } 
         Object temp = list[largest];
         list[largest] = list[position];
         list[position] = temp;
      }
      //Selection Sort End

      return list;
   }
}
