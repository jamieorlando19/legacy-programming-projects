/**
   @version 1.10 2000-06-03
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EventTracerTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

class EventTracerFrame extends JFrame
{
   public EventTracerFrame()
   {
      setTitle("EventTracerTest");
      setSize(WIDTH, HEIGHT);

      // add a slider and a button
      Container contentPane = getContentPane();

      contentPane.add(new JSlider(), BorderLayout.NORTH);
      contentPane.add(new JButton("Test"), BorderLayout.SOUTH);

      // trap all events of components inside the frame
      EventTracer tracer = new EventTracer();
      tracer.add(this);      
   }

   public static final int WIDTH = 400;
   public static final int HEIGHT = 400;  
}
