/** Employees have a name, salary and hire date
    @author Jamie Orlando
    @version 1.0
    @since 1.0
    @see <a href="http://udel.edu/~orlandoj">Jamie's Page</a>
*/

import java.util.*;

public class Employee
{  
   /**
      @param n the employee's name
      @param s the salary
      @param year the hire year
      @param month the hire month
      @param day the hire day
   */
   public Employee(String n, double s, int year, int month, int day)
   {  
      name = n;
      salary = s;
      GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
         // GregorianCalendar uses 0 for January
      hireDay = calendar.getTime();
   }

   /**
      @return the name
   */
   public String getName()
   {
      return name;
   }

   /**
      @return the salary
   */
   public double getSalary()
   {  
      return salary;
   }

   /**
      @return the hire day
   */
   public Date getHireDay()
   {  
      return hireDay;
   }

   /**
      @param byPecent the percentage to raise the salary
   */
   public void raiseSalary(double byPercent)
   {  
      double raise = salary * byPercent / 100;
      salary += raise;
   }

   private String name;
   private double salary;
   private Date hireDay;
}
