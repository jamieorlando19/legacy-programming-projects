/**
   @version 1.30 2000-05-12
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ResourceTest extends JApplet
{  
   public void init()
   {  
      Container contentPane = getContentPane();
      contentPane.add(new AboutPanel());
   }
}

/**
   A panel with a text area and an "About" button. Pressing
   the button fills the text area with text from a resource.
*/
class AboutPanel extends JTextArea
{  
   public AboutPanel()
   {  
      setLayout(new BorderLayout());

      // add text area
      textArea = new JTextArea();
      add(new JScrollPane(textArea), BorderLayout.CENTER);

      // add About button
      URL aboutURL = AboutPanel.class.getResource("about.gif");
      JButton aboutButton = new JButton("About", 
         new ImageIcon(aboutURL));
      aboutButton.addActionListener(new AboutAction());
      add(aboutButton, BorderLayout.SOUTH);
   }

   private JTextArea textArea;

   private class AboutAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         try
         {  
            // read text from resource into text area
            InputStream in = AboutPanel.class.
               getResourceAsStream("about.txt");
            BufferedReader reader = new BufferedReader(new
               InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null)
               textArea.append(line + "\n");
         } 
         catch(IOException exception) 
         { 
            exception.printStackTrace(); 
         }
      }
   }
}

