/**
   @version 1.00 1998-04-11
   @author Cay Horstmann
*/

import java.lang.reflect.*;
import java.util.*;

public class ObjectAnalyzerTest
{  
   public static void main(String[] args)
   { 
      // test toString method of Employee
      Employee harry  = new Employee("Harry Hacker", 35000,
         1996, 12, 1);
      System.out.println("harry=" + harry);

      // test equals method of Employee
      Employee coder  = new Employee("Harry Hacker", 35000,
         1996, 12, 1);
      System.out.println(
         "Before raise, harry.equals(coder) returns "
         + harry.equals(coder));
      harry.raiseSalary(5);
      System.out.println(
         "After raise, harry.equals(coder) returns "
         + harry.equals(coder));

      Manager carl = new Manager("Carl Cracker", 80000,
         1987, 12, 15);
      Manager boss = new Manager("Carl Cracker", 80000,
         1987, 12, 15);
      boss.setBonus(5000);
      System.out.println("boss=" + boss);
      System.out.println(
         "carl.equals(boss) returns " + carl.equals(boss));
   }
}

class ObjectAnalyzer
{
   /**
      Converts an object to a string representation that lists 
      all fields. 
      @param obj an object
      @return a string with the object's class name and all
      field names and values
   */
   public static String toString(Object obj)
   {  
      Class cl = obj.getClass();
      String r = cl.getName();

      // inspect the fields of this class and all superclasses
      do
      {
         r += "[";
         Field[] fields = cl.getDeclaredFields();
         AccessibleObject.setAccessible(fields, true);

         // get the names and values of all fields
         for (int i = 0; i < fields.length; i++)
         {  
            Field f = fields[i];
            r += f.getName() + "=";
            try
            {  
               Object val = f.get(obj);
               r += val.toString();
            } 
            catch (Exception e) { e.printStackTrace(); }
            if (i < fields.length - 1)
               r += ",";
         }
         r += "]";
         cl = cl.getSuperclass();
      }
      while (cl != Object.class);

      return r;
   }

   /**
      Tests whether two objects are equal by checking if all
      field values are equal
      @param a an object
      @param b another object
      @return true if a and b are equal
   */
   public static boolean equals(Object a, Object b)
   {  
      if (a == b) return true;
      if (a == null || b == null) return false;
      Class cl = a.getClass();
      if (cl != b.getClass()) return false;

      // inspect the fields of this class and all superclasses
      do
      {
         Field[] fields = cl.getDeclaredFields();
         AccessibleObject.setAccessible(fields, true);
         for (int i = 0; i < fields.length; i++)
         {  
            Field f = fields[i];
            // if field values don't match, objects aren't equal
            try
            {  
               if (!f.get(a).equals(f.get(b)))
                  return false;
            }
            catch (Exception e) { e.printStackTrace(); }
         }
         cl = cl.getSuperclass();
      }
      while (cl != Object.class);

      return true;
   }
}

class Employee
{  
   public Employee(String n, double s,
      int year, int month, int day)
   {  
      name = n;
      salary = s;
      GregorianCalendar calendar
         = new GregorianCalendar(year, month - 1, day);
         // GregorianCalendar uses 0 for January
      hireDay = calendar.getTime();
   }

   public String getName()
   {  
      return name;
   }

   public double getSalary()
   {  
      return salary;
   }

   public Date getHireDay()
   {  
      return hireDay;
   }

   public void raiseSalary(double byPercent)
   {  
      double raise = salary * byPercent / 100;
      salary += raise;
   }

   public String toString()
   {  
      return ObjectAnalyzer.toString(this);
   }

   public boolean equals(Object b)
   {  
      return ObjectAnalyzer.equals(this, b);
   }

   private String name;
   private double salary;
   private Date hireDay;
}

class Manager extends Employee
{  
   public Manager(String n, double s,
      int year, int month, int day)
   {  
      super(n, s, year, month, day);
      bonus = 0;
   }

   public double getSalary()
   { 
      double baseSalary = super.getSalary();
      return baseSalary + bonus;
   }

   public void setBonus(double b)
   {  
      bonus = b;
   }

   private double bonus;
}
