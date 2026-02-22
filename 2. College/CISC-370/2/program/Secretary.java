/** Secretary class which is derived from the Employee class.  The only 
    difference is that Secretary has a healthplan variable.
    @author Jamie Orlando
    @version 1.0
    @since 1.0
    @see <a href="http://udel.edu/~orlandoj">Jamie's Page</a>
*/

public class Secretary extends Employee
{
   /**
      @param n the secretary's name
      @param s the salary
      @param year the hire year
      @param month the hire month
      @param day the hire day
      @param hp the health plan
   */
   public Secretary(String n, double s, int year, int month, int day, String hp)
   {
      super(n, s, year, month, day);
      healthplan = hp;
   }

   /**
      @return the Health Plan
   */
   public String getHealthPlan()
   {
      return healthplan;
   }

   private String healthplan;
}
