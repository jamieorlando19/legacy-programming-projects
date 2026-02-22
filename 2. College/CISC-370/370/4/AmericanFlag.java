import javax.swing.*;
import java.awt.Container;

public class AmericanFlag extends JFrame
{
   public AmericanFlag()
   {
      setSize(1040, 520);
      setTitle("American Flag");
      setLocation(0, 0);

      AmericanFlagGraphics panel = new AmericanFlagGraphics();
      Container contentPane = getContentPane();
      contentPane.add(panel);
   }
}
