import javax.swing.*;

/**
   A Scrabble object is an applet that contains a
   ScrabblePanel.
*/
public class Scrabble extends JApplet
{
   /**
      Retrieves the content pane of the applet and
      adds the ScrabblePanel.
   */
   public void init()
   {
      ScrabblePanel scrabblepanel = new ScrabblePanel();
      getContentPane().add(scrabblepanel);
   }
}

