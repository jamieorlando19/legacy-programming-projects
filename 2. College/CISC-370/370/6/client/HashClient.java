import java.rmi.*;
import java.rmi.server.*;

public class HashClient
{
   //public void process()
   public static void main(String[] args)
   {
      try
      {
         String serverObjName = "rmi://mariokart.dhs.org/HashServer";
         HashServer mytemp = (HashServer)Naming.lookup(serverObjName);

         String current_get;
         mytemp.remote_put("This is in key 1", new Integer(1));
         current_get = mytemp.remote_get(new Integer(1));
         System.out.println(current_get);
      }
      catch(java.rmi.ConnectException ce)
      { System.out.println("Connection to server failed."); }
      catch(Exception e)
      { System.out.println("Error " + e); }
      System.exit(0);
   }
}
