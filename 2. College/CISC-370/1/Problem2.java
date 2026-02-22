/**
Jamie Orlando
CISC 370-011
Assignment #1 Problem #2
*/

import java.io.*;

public class Problem2
{
    public static void main(String[] args)
    throws IOException
    {
        BufferedReader getline = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("How many names do you wish to enter?");
        int num = Integer.parseInt(getline.readLine());
        String[] Namelist = new String[num];
        
        for(int x = 0; x < num; x++){ Namelist[x] = getline.readLine();}
        System.out.println("Here are the names:");
        for(int y = 0; y < num; y++){ System.out.println(Namelist[y]);}
        
        System.out.println("All names displayed and accounted for.");
    }
}
