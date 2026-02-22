/**
   @version 1.20 2000-06-03
   @author Cay Horstmann
*/

import java.awt.*;
import javax.swing.*;

public class BuggyButtonFrame extends JFrame
{
   public BuggyButtonFrame()
   {
      setTitle("BuggyButtonTest");
      setSize(WIDTH, HEIGHT);

      // add panel to frame

      BuggyButtonPanel panel = new BuggyButtonPanel();
      Container contentPane = getContentPane();
      contentPane.add(panel);
   }

   public static final int WIDTH = 300;
   public static final int HEIGHT = 200;  
}
