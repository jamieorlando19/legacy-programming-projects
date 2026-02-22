public class test
{
   public static void main(String[] args)
   {
      try
      {
         String[] a = {"g", "r", "a", "p", "e", "s"};
         printArray(a);
         printArray((String[])Assignment3.sort(a));

         Integer[] b = {new Integer(1), new Integer(4), new Integer(2)};
         printArray(b);
         printArray((Integer[])Assignment3.sort(b));

         Float[] c = {new Float(3.33), new Float (3.12), new Float (44),
                      new Float(5.201), new Float(1.001)};
         printArray(c);
         printArray((Float[])Assignment3.sort(c));

         //This segment will generate an exception and cause the program
         //to terminate.
         Bozo[] z = {new Bozo(41), new Bozo (11)};
         printArray((Bozo[])Assignment3.sort(z));
      }    
      catch (ObjectIsNotComparableException exception)
      {
         exception.printStackTrace();
      }
   }

   public static void printArray(Object[] a)
   {
      for(int i = 0; i < a.length; i++)
         System.out.println(a[i]);
      System.out.println();
   }
}
