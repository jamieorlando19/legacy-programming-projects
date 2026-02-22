import java.rmi.*;
import java.lang.*;

public interface HashServer extends Remote
{
   public void remote_put(String s, Integer i)
      throws RemoteException;
   public String remote_get(Integer i)
      throws RemoteException;
}
