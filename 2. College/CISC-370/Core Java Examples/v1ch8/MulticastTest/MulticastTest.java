/**
   @version 1.30 2000-06-03
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MulticastTest
{
   public static void main(String[] args)
   {
      MulticastFrame frame = new MulticastFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   A frame with buttons to make and close secondary frames
*/
class MulticastFrame extends JFrame
{
   public MulticastFrame()
   {
      setTitle("MulticastTest");
      setSize(WIDTH, HEIGHT);

      // add panel to frame

      MulticastPanel panel = new MulticastPanel();
      Container contentPane = getContentPane();
      contentPane.add(panel);
   }

   public static final int WIDTH = 300;
   public static final int HEIGHT = 200;  
}

/**
   A panel with buttons to create and close sample frames.
*/
class MulticastPanel extends JPanel
{
   public MulticastPanel()
   {
      // add "New" button

      JButton newButton = new JButton("New");
      add(newButton);

      ActionListener newListener = new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               makeNewFrame();
            }
         };
      newButton.addActionListener(newListener);

      // add "Close all" button

      closeAllButton = new JButton("Close all");
      add(closeAllButton);
   }

   private void makeNewFrame()
   {
      // make new blank frame
      final BlankFrame frame = new BlankFrame();
      frame.show();

      // create action listener that disposes of this frame
      
      ActionListener closeAllListener = new 
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               frame.dispose();
            }
         };

      // add the listener to the "Close all" button of
      // the panel--note that this button has multiple
      // listeners added, one for each frame
      closeAllButton.addActionListener(closeAllListener);
   }

   private JButton closeAllButton;
}

class BlankFrame extends JFrame
{
   public BlankFrame()
   {
      counter++;
      setTitle("Frame " + counter);
      setSize(WIDTH, HEIGHT);
      setLocation(SPACING * counter, SPACING * counter);     
   }

   public static final int WIDTH = 200;
   public static final int HEIGHT = 150;  
   public static final int SPACING = 30;
   private static int counter = 0;
}
