/**
   @version 1.10 2000-05-28
   @author Cay Horstmann
*/

/**
   A class for assertion checking, similar to the C assert macro
*/
public class Assertion
{
   /**
      Check an assertion
      @param b the condition to check
      @param s a string describing the check
   */
   public static void check(boolean b, String s)
   {
      if (!ndebug && !b)
      {
         System.err.print("Assertion failed. ");
         if (s != null) System.err.print(s);
         System.err.println();
         Thread.dumpStack();
         System.exit(1);
      }
   }

   /**
      Check an assertion
      @param b the condition to check
   */
   public static void check(boolean b)
   {
      check(b, null);
   }

   /**
      Check an assertion
      @param obj an object that should not be null
      @param s a string describing the check
   */
   public static void check(Object obj, String s)
   {
      check (obj != null, s);
   }

   /**
      Check an assertion
      @param obj an object that should not be null
   */
   public static void check(Object obj)
   {
      check (obj != null, null);
   }

   /**
      Check an assertion
      @param x a number that should not be 0
      @param s a string describing the check
   */
   public static void check(double x, String s)
   {
      check (x != 0, s);
   }

   /**
      Check an assertion
      @param x a number that should not be 0
   */
   public static void check(double x)
   {
      check (x != 0, null);
   }

   /**
      Check an assertion
      @param x a number that should not be 0
      @param s a string describing the check
   */
   public static void check(long x, String s)
   {
      check (x != 0, s);
   }

   /**
      Check an assertion
      @param x a number that should not be 0
   */
   public static void check(long x)
   {
      check (x != 0, null);
   }

   /**
      Turn checking on or off
      @param b true to turn checking off,
      false to turn checking on
   */
   public static void setNdebug(boolean b)
   {
      ndebug = b;
   }

   private static boolean ndebug = false;

   /**
      test stub
   */
   public static void main(String[] args)
   {
      Assertion.check(args);
      Assertion.check(args.length, "No command line arguments");
   }
}

