import java.rmi.*;
import java.rmi.server.*;
import sun.applet.*;
import java.util.*;

public class HashServerImpl extends UnicastRemoteObject implements HashServer
{
   public static void main(String args[])
   {
      try
      {
         HashServerImpl temp = new HashServerImpl();
         String serverObjName = "//localhost/HashServer";
         Naming.rebind(serverObjName, temp);
      }
      catch(Exception e)
      { System.out.println("Error: " + e); }

   }

   public HashServerImpl()
      throws Exception
   { main_hash = new Hashtable(); }

   public void remote_put(String s, Integer i)
      throws RemoteException
   { main_hash.put((Object)s, (Object)i); }

   public String remote_get(Integer i)
      throws RemoteException
   { return (String)main_hash.get((Object)i); }
 
   private Hashtable main_hash;
}
