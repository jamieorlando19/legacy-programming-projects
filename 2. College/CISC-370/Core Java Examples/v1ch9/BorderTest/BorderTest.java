/**
   @version 1.30 2000-06-03
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
 
public class BorderTest
{
   public static void main(String[] args)
   {  
      BorderFrame frame = new BorderFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   A frame with radio buttons to pick a border style. 
*/
class BorderFrame extends JFrame 
{  
   public BorderFrame()
   {  
      setTitle("BorderTest");
      setSize(WIDTH, HEIGHT);

      demoPanel = new JPanel();
      buttonPanel = new JPanel();
      group = new ButtonGroup();

      addRadioButton("Lowered bevel", 
         BorderFactory.createLoweredBevelBorder());    
      addRadioButton("Raised bevel", 
         BorderFactory.createRaisedBevelBorder());    
      addRadioButton("Etched", 
         BorderFactory.createEtchedBorder());
      addRadioButton("Line", 
         BorderFactory.createLineBorder(Color.blue));
      addRadioButton("Matte",
         BorderFactory.createMatteBorder(
            10, 10, 10, 10, Color.blue));
      addRadioButton("Empty", 
         BorderFactory.createEmptyBorder());
         
      Border etched = BorderFactory.createEtchedBorder();
      Border titled = BorderFactory.createTitledBorder
         (etched, "Border types");
      buttonPanel.setBorder(titled);
      
      Container contentPane = getContentPane();
      
      contentPane.setLayout(new GridLayout(2, 1));
      contentPane.add(buttonPanel);
      contentPane.add(demoPanel);
   }

   public void addRadioButton(String buttonName, final Border b)
   {  
      JRadioButton button = new JRadioButton(buttonName);
      button.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {  
               demoPanel.setBorder(b);
               validate();
            }
         });
      group.add(button);
      buttonPanel.add(button);
   }

   public static final int WIDTH = 600;
   public static final int HEIGHT = 200;  
   
   private JPanel demoPanel;
   private JPanel buttonPanel;
   private ButtonGroup group;
}
