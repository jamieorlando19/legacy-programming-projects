/** ObjectIsNotComparableException is called if an Object is not
    comparable and the compareTo function is called on it.
    @author Jamie Orlando
    @version 1.0
    @since 1.0
    @see <a href="http://udel.edu/~orlandoj">Jamie's Page</a>
*/
public class ObjectIsNotComparableException extends RuntimeException
{
   /** Prints "whoops!" if this exception is called */
   public ObjectIsNotComparableException()
   {
      System.out.println("whoops!");
   }
}
