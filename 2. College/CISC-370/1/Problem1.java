/**
Jamie Orlando
CISC 370-011
Assignment #1 Problem #1
*/

import java.io.*;

public class Problem1
{
    public static void main(String[] args)
    throws IOException
    {
        System.out.println("Please Enter Your Name:");
        BufferedReader name = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Hello " + name.readLine() + ". I am very glad to meet you!");
        System.out.println("Byebye! :)");
    }
}
