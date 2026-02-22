/** Manager class which is derived from the Employee class. The only 
    difference is that Manager has a bonus that can be added to 
    the salary.
    @author Jamie Orlando
    @version 1.0
    @since 1.0
    @see <a href="http://udel.edu/~orlandoj">Jamie's Page</a>
*/
public class Manager extends Employee
{  
   /**
      @param n the manager's name
      @param s the salary
      @param year the hire year
      @param month the hire month
      @param day the hire day
   */
   public Manager(String n, double s, int year, int month, int day)
   {  
      super(n, s, year, month, day);
      bonus = 0;
   }

   /**
      @returns Salary + bonus
   */
   public double getSalary()
   { 
      double baseSalary = super.getSalary();
      return baseSalary + bonus;
   }

   /**
      @param b value to set the bonus to
   */
   public void setBonus(double b)
   {  
      bonus = b;
   }

   private double bonus;
}


