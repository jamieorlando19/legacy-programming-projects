/**
   @version 1.30 2000-05-04
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;

public class FontTest
{  
   public static void main(String[] args)
   {  
      FontFrame frame = new FontFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   A frame with a text message panel
*/
class FontFrame extends JFrame
{
   public FontFrame()
   {
      setTitle("FontTest");
      setSize(WIDTH, HEIGHT);

      // add panel to frame

      FontPanel panel = new FontPanel();
      Container contentPane = getContentPane();
      contentPane.add(panel);
   }

   public static final int WIDTH = 300;
   public static final int HEIGHT = 200;  
}

/**
   A panel that shows a centered message in a box.
*/
class FontPanel extends JPanel
{  
   public void paintComponent(Graphics g)
   {  
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;

      String message = "Hello, World!";

      Font f = new Font("Serif", Font.BOLD, 36);
      g2.setFont(f);

      // measure the size of the message

      FontRenderContext context = g2.getFontRenderContext();
      Rectangle2D bounds = f.getStringBounds(message, context);

      // set (x,y) = top left corner of text

      double x = (getWidth() - bounds.getWidth()) / 2;
      double y = (getHeight() - bounds.getHeight()) / 2;

      // add ascent to y to reach the baseline

      double ascent = -bounds.getY();
      double baseY = y + ascent;

      // draw the message

      g2.drawString(message, (int)x, (int)(baseY));

      g2.setPaint(Color.gray);

      // draw the baseline

      g2.draw(new Line2D.Double(x, baseY, 
         x + bounds.getWidth(), baseY));

      // draw the enclosing rectangle

      Rectangle2D rect = new Rectangle2D.Double(x, y, 
         bounds.getWidth(), 
         bounds.getHeight());
      g2.draw(rect);
   }
}
