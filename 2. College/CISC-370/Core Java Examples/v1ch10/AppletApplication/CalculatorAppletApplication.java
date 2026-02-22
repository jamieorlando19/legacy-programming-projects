/**
   @version 1.30 2000-05-12
   @author Cay Horstmann
*/

/*
  The applet viewer reads the tags below if you call it with
      appletviewer CalculatorAppletApplication.java (!)
  No separate HTML file is required.
  <APPLET CODE="CalculatorAppletApplication.class"
     WIDTH=200 HEIGHT=200>
  </APPLET>
*/

import javax.swing.*;

public class CalculatorAppletApplication 
   extends CalculatorApplet
// It's an applet. It's an application. It's BOTH!
{  
   public static void main(String[] args)
   {  
      AppletFrame frame 
         = new AppletFrame(new CalculatorApplet());
      frame.setTitle("CalculatorAppletApplication");
      frame.setSize(WIDTH, HEIGHT);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }

   public static final int WIDTH = 200;
   public static final int HEIGHT = 200;
}

