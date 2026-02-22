/**
   @version 1.20 1998-08-10
   @author Cay Horstmann
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class WelcomeApplet extends JApplet
{
   public void init()
   {
      Container contentPane = getContentPane();
      contentPane.setLayout(new BorderLayout());

      JLabel label = new JLabel(getParameter("greeting"),
         SwingConstants.CENTER);
      label.setFont(new Font("TimesRoman", Font.BOLD, 18));
      contentPane.add(label, BorderLayout.CENTER);

      JPanel panel = new JPanel();

      JButton cayButton = new JButton("Cay Horstmann");
      cayButton.addActionListener(getURLActionListener
         ("http://www.horstmann.com"));
      panel.add(cayButton);

      JButton garyButton = new JButton("Gary Cornell");
      garyButton.addActionListener(getURLActionListener
         ("mailto:gary@thecornells.com"));
      panel.add(garyButton);

      contentPane.add(panel, BorderLayout.SOUTH);
   }

   public ActionListener getURLActionListener(final String
      urlString)
   {
      return new
         ActionListener()
         {
            public void actionPerformed(ActionEvent evt)
            {
               try
               {
                  URL u = new URL(urlString);
                  getAppletContext().showDocument(u);
               }
               catch(Exception e) { e.printStackTrace(); }
            }
         };
   }
}

