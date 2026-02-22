/** Senior Engineer class which is derived from the Manager class.
    The only difference is that Senior Engineer returns a name with 
    "P.E." appended to it.
    @author Jamie Orlando
    @version 1.0
    @since 1.0
    @see <a href="http://udel.edu/~orlandoj/">Jamie's Page</a>
*/

public class SrEngineer extends Manager
{
   /**
      @param n the senior engineer's name
      @param s the salary
      @param year the hire year
      @param month the hire month
      @param day the hire day
   */
   public SrEngineer(String n, double s, int year, int month, int day)
   {
      super(n, s, year, month, day);
   }

   /**
      @return the name with "P.E." appended to it
   */
   public String getName()
   {
      return super.getName() + " P.E.";
   }
}
