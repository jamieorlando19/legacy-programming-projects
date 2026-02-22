/** Engineer class which is derived from the employee class.  The only
    difference is that Engineer adds P.E. to the end of the name.
    @author Jamie Orlando
    @version 1.0
    @since 1.0
    @see <a href="http://udel.edu/~orlandoj">Jamie's Page</a>
*/
public class Engineer extends Employee
{
   /**
      @param n the engineer's name
      @param s the salary
      @param year the hire year
      @param month the hire year
      @param day the hire day
   */
   public Engineer(String n, double s, int year, int month, int day)
   {
      super(n, s, year, month, day);
   }

   /**
      @return the name with P.E. appended to it
   */
   public String getName()
   {
      return super.getName() + " P.E."; 
   }
}
