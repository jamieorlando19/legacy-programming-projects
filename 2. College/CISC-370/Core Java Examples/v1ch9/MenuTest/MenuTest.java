/**
   @version 1.20 2000-05-17
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MenuTest
{
   public static void main(String[] args)
   {  
      MenuFrame frame = new MenuFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   A frame with a sample menu bar.
*/
class MenuFrame extends JFrame
{  
   public MenuFrame()
   {  
      setTitle("MenuTest");
      setSize(WIDTH, HEIGHT);

      JMenu fileMenu = new JMenu("File");
      JMenuItem newItem = fileMenu.add(new TestAction("New"));

      // demonstrate accelerators

      JMenuItem openItem = fileMenu.add(new TestAction("Open"));
      openItem.setAccelerator(KeyStroke.getKeyStroke(
         KeyEvent.VK_O, InputEvent.CTRL_MASK));

      fileMenu.addSeparator();

      saveItem = fileMenu.add(new TestAction("Save"));
      saveItem.setAccelerator(KeyStroke.getKeyStroke(
         KeyEvent.VK_S, InputEvent.CTRL_MASK));

      saveAsItem = fileMenu.add(new TestAction("Save As"));
      fileMenu.addSeparator();

      fileMenu.add(new 
         AbstractAction("Exit")
         {
            public void actionPerformed(ActionEvent event)
            {
               System.exit(0);
            }
         });

      // demonstrate enabled/disabled items

      fileMenu.addMenuListener(new FileMenuListener());

      // demonstrate check box and radio button menus

      readonlyItem = new JCheckBoxMenuItem("Read-only");

      ButtonGroup group = new ButtonGroup();

      JRadioButtonMenuItem insertItem
         = new JRadioButtonMenuItem("Insert");
      insertItem.setSelected(true);
      JRadioButtonMenuItem overtypeItem
         = new JRadioButtonMenuItem("Overtype");

      group.add(insertItem);
      group.add(overtypeItem);

      // demonstrate icons

      Action cutAction = new TestAction("Cut"); 
      cutAction.putValue(Action.SMALL_ICON, 
         new ImageIcon("cut.gif"));
      Action copyAction = new TestAction("Copy");
      copyAction.putValue(Action.SMALL_ICON, 
         new ImageIcon("copy.gif"));
      Action pasteAction = new TestAction("Paste");
      pasteAction.putValue(Action.SMALL_ICON, 
         new ImageIcon("paste.gif"));

      JMenu editMenu = new JMenu("Edit");
      editMenu.add(cutAction);
      editMenu.add(copyAction);
      editMenu.add(pasteAction);

      // demonstrate nested menus

      JMenu optionMenu = new JMenu("Options");

      optionMenu.add(readonlyItem);
      optionMenu.addSeparator();
      optionMenu.add(insertItem);
      optionMenu.add(overtypeItem);

      editMenu.addSeparator();
      editMenu.add(optionMenu);

      // demonstrate mnemonics

      JMenu helpMenu = new JMenu("Help");
      helpMenu.setMnemonic('H');

      JMenuItem indexItem = new JMenuItem("Index");
      indexItem.setMnemonic('I');
      helpMenu.add(indexItem);

      // you can also add the mnemonic key to an action
      Action aboutAction = new TestAction("About");
      aboutAction.putValue(Action.MNEMONIC_KEY, 
         new Integer('A'));
      helpMenu.add(aboutAction);

      // add all top-level menus to menu bar

      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);

      menuBar.add(fileMenu);
      menuBar.add(editMenu);
      menuBar.add(helpMenu);

      // demonstrate pop-ups

      popup = new JPopupMenu();
      popup.add(cutAction);
      popup.add(copyAction);
      popup.add(pasteAction);

      getContentPane().addMouseListener(new 
         MouseAdapter()
         {  
            public void mouseReleased(MouseEvent event)         
            {  
               if (event.isPopupTrigger())
                  popup.show(event.getComponent(),
                     event.getX(), event.getY());
            }
         });
   }

   public static final int WIDTH = 300;
   public static final int HEIGHT = 200;  

   private JMenuItem saveItem;
   private JMenuItem saveAsItem;
   private JCheckBoxMenuItem readonlyItem;
   private JPopupMenu popup;

   /**
      updates the state of the file menu. The Save
      menu option is disabled if the document is read only
   */
   private class FileMenuListener implements MenuListener
   {
      public void menuSelected(MenuEvent evt)
      {  
         saveItem.setEnabled(!readonlyItem.isSelected());
         saveAsItem.setEnabled(!readonlyItem.isSelected());
      }

      public void menuDeselected(MenuEvent evt) {}
      
      public void menuCanceled(MenuEvent evt) {}
   }
}

/**
   A sample action that prints the action name to System.out
*/
class TestAction extends AbstractAction
{  
   public TestAction(String name) { super(name); }
   
   public void actionPerformed(ActionEvent event)
   {  
      System.out.println(getValue(Action.NAME)
         + " selected.");
   }
}








