//Jamie Orlando
//CISC-370-011
//Homework #6
//Client Server

import java.io.*;
import java.net.*;
import java.lang.*;

public class FileClient
{
   public static void main(String[] args)
   {
      try
      {
         BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
         //Reads strings from the keyboard

         System.out.println("Enter The IP");
         String IP = keyboard.readLine();
         System.out.println("Enter The Port");
         int port = Integer.parseInt(keyboard.readLine());
       
         Socket s = new Socket(IP, port);
         BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));         
         //Reads text from the server
         PrintWriter out = new PrintWriter(s.getOutputStream(), true);
         //Writes text to the server
         
         boolean done = false;
         String line, end = new String("EXIT");
         while(!done)
         {
            line = in.readLine();
            if(line.equals((Object)end))
               done = true;
             else
               System.out.println(line);
         }
         System.out.println();
         
         done = false;
         int b;
         while(!done)
         {
            line = keyboard.readLine();
            out.println(line);
            String exists = in.readLine();
            if(exists.equals((Object)(new String("YES"))))
            {
               File new_file = new File(line);
               if(new_file.exists())
                  new_file =  new File(line + "NEW");
               FileOutputStream fout = new FileOutputStream(new_file);
      
               while((in.readLine()).equals((Object)(new String(".")))) 
               { 
                  Integer a = new Integer(in.readLine());
                  fout.write(a.intValue()); 
               }

            }
            else if(exists.equals((Object)(new String("NO"))))
            {
               if(line.equals((Object)end))
                  done = true;
            }
            line = in.readLine();
            System.out.println(line);
         }
         System.out.println("Connection Closed");
      }
      catch(IOException e)
      { System.out.println(e); }
   }
}
