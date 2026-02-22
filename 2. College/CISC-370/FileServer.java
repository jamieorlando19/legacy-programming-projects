//Jamie Orlando
//CISC-370-011
//Homework #6
//File Server


import java.io.*;
import java.net.*;

public class FileServer
{  public static void main(String[] args )
   {  
      try
      {  
         System.out.println("Server Running");
         ServerSocket s = new ServerSocket(8000);

         while(true)
         {  
            Socket incoming = s.accept();
            System.out.println("Connection Made");
            new FileServerHandler(incoming).start();
         }
      }
      catch (Exception e)
      { System.out.println(e); }
   }
}

class FileServerHandler extends Thread
{  
   public FileServerHandler(Socket i)
   { incoming = i; }

   public void run()
   {  
      try
      {  
         BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
         //Reads text from the client
         PrintWriter out = new PrintWriter(incoming.getOutputStream(), true);
         //Sends text to the client       

         File directory = new File("C:\\Jamie\\Server");
         String files[] = directory.list();
         
         for(int x = 0; x < files.length; x++)
            out.println(files[x]);
         out.println("EXIT");

        
         int b;
         boolean done = false;
         String exit = new String("EXIT");
         while (!done)
         { 
            String str = in.readLine();
            File inputted_file_name = new File("C:\\Jamie\\Server", str);
            if (inputted_file_name.exists())
            {
               out.println("YES");
               FileInputStream fin = new FileInputStream(inputted_file_name);                 
                               
               while((b = fin.read()) != -1) 
               { 
                  out.println(".");
                  out.println(b);
               }
               out.println("done");              
               out.println("File Sent.");
            }
            else
            {  
               out.println("NO");
               if (str.equals((Object)exit))
               {
                  done = true;
                  out.println("Session Terminated");
               }
               else
                  out.println("Invalid Filename");
            }
         }
         incoming.close();
         System.out.println("Connection Closed");
      }
      catch (Exception e)
      { System.out.println(e); }
   }

   private Socket incoming;
}

