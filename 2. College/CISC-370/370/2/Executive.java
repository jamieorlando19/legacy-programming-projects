/** Executive class which is derived from the Manager class.  The
    difference is that Executive has a Health Plan and Stock Options.
    @author Jamie Orlando
    @version 1.0
    @since 1.0
    @see <a href="http://udel.edu/~orlandoj">Jamie's Page</a>
*/

public class Executive extends Manager
{
   /**
      @param n the executive's name
      @param s the salary
      @param year the hire year
      @param month the hire month
      @param day the hire day
      @param hp the health plan
      @param so the stock options
   */
   public Executive(String n, double s, int year, int month, int day, String hp, int so)
   {
      super(n, s, year, month, day);
      healthplan = hp;
      stockoptions = so;
   }

   /**
      @return health plan
   */
   public String getHealthPlan()
   {
      return healthplan;
   }
 
   /**
      @return stock options
   */
   public int getStockOptions()
   {
      return stockoptions;
   }

   private String healthplan;
   private int stockoptions;
}
