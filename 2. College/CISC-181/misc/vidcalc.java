/* Conference Calculator
    by James Orlando, Bristol Myers Squibb */

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;

public class d34 extends Applet
{
   public void init()
   { 
      setLayout(null);

      //Text Field Stuff
      t_length_h = new TextField("2", 7);
      t_length_m = new TextField("0", 7);
      t_attendees = new TextField("4", 7);
      t_traveling = new TextField("2", 7);
      t_length_h.setBounds(190, 5, 100, 25);
      t_length_m.setBounds(335, 5, 100, 25);
      t_attendees.setBounds(190, 30, 100, 25);
      t_traveling.setBounds(190, 55, 100, 25);
      add(t_length_h);
      add(t_length_m);
      add(t_attendees);
      add(t_traveling);

      //Button Stuff
      sendinfo = new Button("Calculate Totals");
      sendinfo.setBounds(15, 90, 470, 30);
      add(sendinfo);
      sendinfo.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent evt)
            { calculateAll(); }
         }                        );

     //Checkbox Stuff
     Dom_or_int = new CheckboxGroup();
     Checkbox domestic = new Checkbox("Domestic Flight", Dom_or_int, true);
     Checkbox international = new Checkbox("International Flight", Dom_or_int, false);
     domestic.setBounds(335, 33, 145, 25);
     international.setBounds(335, 58, 145, 25);
     add(domestic);
     add(international);



      declareLabels();      
      calculateAll(); 
   }

   public void calculateAll()
   {
      try
      {
         Double hours = Double.valueOf(t_length_h.getText());
         Double minutes = Double.valueOf(t_length_m.getText());
         Integer attendees = Integer.valueOf(t_attendees.getText());
         Integer traveling = Integer.valueOf(t_traveling.getText());
         Double planecost, videocost, planetime, videotime, 
                planesavings, videosavings, maniac;
         int planehours, airfare;

         if(((Dom_or_int.getSelectedCheckbox()).getLabel()).charAt(0) == 'D')
         {
            planehours = 4;
            airfare = 500;
         }  
         else
         {
            planehours = 7;
            airfare = 2000;
         }

         minutes = new Double(hours.doubleValue() * 60 + minutes.doubleValue());
         planetime = new Double(((2 + planehours + (minutes.doubleValue() / 60))
                                 * traveling.intValue()) +
                                (((minutes.doubleValue() / 60) + 0.5) 
                                 * (attendees.intValue() -
                                    traveling.intValue())));
         videotime = new Double(((minutes.doubleValue() / 60) + 0.5) 
                                 * (attendees.intValue()));   
         planecost = new Double(((270 + airfare) * (traveling.intValue())) + 
                                ((minutes.doubleValue() / 60) * 
                                (attendees.intValue()) * 90));
         videocost = new Double((30 * (attendees.intValue()) *
                                   (minutes.doubleValue() / 60)) + 
                                (90 * (minutes.doubleValue() / 60) *
                                   (attendees.intValue())));

         if((planecost.doubleValue() - videocost.doubleValue()) > 0)
         {
            videosavings = new Double(planecost.doubleValue() - videocost.doubleValue());
            planesavings = new Double(0.0);
         }
         else
         {
            planesavings = new Double(videocost.doubleValue() - planecost.doubleValue());
            videosavings = new Double(0.0);            
         }

         maniac = new Double(videosavings.doubleValue() * 52);

         t1_1.setText(format(minutes, false));
         t2_1.setText(format(minutes, false));
         t1_2.setText(format(planecost, true));
         t2_2.setText(format(videocost, true));
         t1_3.setText(format(planetime, false));
         t2_3.setText(format(videotime, false));
         t1_4.setText(format(planesavings, true));
         t2_4.setText(format(videosavings, true));
         t2_5.setText(format(maniac, true));
      }
      catch(NumberFormatException e)
      {
         t_length_h.setText("2");
         t_length_m.setText("0");
         t_attendees.setText("4");
         t_traveling.setText("2");
         calculateAll();
      }
   }

   //Formats a number by truncating decimals, adding commas, etc..
   public String format(Double d, boolean dollars)
      throws ArrayIndexOutOfBoundsException
   {
      Vector fin = new Vector();
      if(d.doubleValue() < 0)
         d = new Double(0 - d.doubleValue());
      String s_initial = d.toString();
      char[] initial = s_initial.toCharArray();
      int pointpos = -1;

      for(int a = initial.length - 1; a > -1 && pointpos == -1; a--)
      {
         if(initial[a] == '.') 
            pointpos = a;
         else if(initial[a] == 'E')
         {
            if(dollars)
               return "$".concat(s_initial);
            else
               return s_initial;
         }
      }
      fin.insertElementAt((Object)(new Character('.')), 0);
      if(pointpos == -1 || pointpos == initial.length - 1)
      {
         fin.insertElementAt((Object)(new Character('0')), fin.size());
         fin.insertElementAt((Object)(new Character('0')), fin.size());
         for(int q = initial.length - 1; q > -1; q--)
            fin.insertElementAt((Object)(new Character(initial[q])), 0);
      }
      else
      {
        fin.insertElementAt((Object)(new Character(initial[pointpos + 1])), fin.size());
        if(pointpos + 2 >= initial.length)
           fin.insertElementAt((Object)(new Character('0')), fin.size());
        else
           fin.insertElementAt((Object)(new Character(initial[pointpos + 2])), fin.size());
        if(pointpos - 1 < 0)
           fin.insertElementAt((Object)(new Character('0')), 0);
      }
      int comma = 0;
      if(pointpos == -1) pointpos = initial.length;
      for(int x = pointpos - 1; x > -1; x--)
      {
         if(comma == 3)
         {
            fin.insertElementAt((Object)(new Character(',')), 0);
            comma = 0;
         }
         fin.insertElementAt((Object)(new Character(initial[x])), 0);
         comma++;
      }
      if(dollars)
         fin.insertElementAt((Object)(new Character('$')), 0);

      String finstring = new String("");
      int the_size = fin.size();

      for(int a = 0; a < the_size; a++) 
      {
         finstring = (((Character)(fin.elementAt(fin.size() - 1))).toString()).concat(finstring);
         fin.removeElementAt(fin.size() - 1);
      } 
          
      return finstring;
   }

   public void paint(Graphics g)
   {
      drawTable(g);
      drawText(g);
   }

   private void drawText(Graphics g)
   {
      g.setColor(Color.black);
      g.setFont(new Font("Serif", Font.PLAIN, 13));
      g.drawString("Meeting Length", 15, 25);
      g.drawString("Number of Attendees", 15, 50);
      g.drawString("Number Traveling to Attend", 15, 75);
      g.drawString("Meeting Length in minutes", 15, 165);
      g.drawString("Total Meeting Cost", 15, 182);
      g.drawString("Total Time Spent in hours", 15, 199);
      g.setFont(new Font("Serif", Font.PLAIN, 11));
      g.drawString("hrs", 295, 28);
      g.drawString("min", 440, 28);
      g.drawString("A Meeting Maniac travels about once a week. At your savings, a meeting maniac would save: ", 15, 234);
      g.setFont(new Font("Serif", Font.BOLD, 13));
      g.drawString("Travel by Plane", 200, 148);
      g.drawString("Videoconference", 350, 148);
      g.drawString("Savings", 15, 216); 
      g.drawString("Yearly per Person Savings", 15, 252);
   }

   private void drawTable(Graphics g)
   {
      g.setColor(Color.black);
      
      //Box Border
      g.drawLine(5, 134, 495, 134);
      g.drawLine(5, 256, 495, 256);
      g.drawLine(5, 134, 5, 256);
      g.drawLine(495, 134, 495, 256);
      
      //2nd Horizontal Line from the bottom
      g.drawLine(5, 239, 495, 239);  
      
      //Horizontal Interior Lines To "Savings"
      for(int a = 1; a < 6; a++)
         g.drawLine(5, 134 + 17 * a, 495, 134 + 17 * a);
    
      //Vertical Interior Lines
      g.drawLine(345, 134, 345, 219);
      g.drawLine(345, 239, 345, 256);      
      g.drawLine(195, 134, 195, 219);
      g.drawLine(195, 239, 195, 256);
   }

   private void declareLabels()
   {
      t1_1 = new Label("0.00", Label.RIGHT);
      t2_1 = new Label("0.00", Label.RIGHT);
      t1_2 = new Label("$0.00", Label.RIGHT);
      t2_2 = new Label("$0.00", Label.RIGHT);
      t1_3 = new Label("0.00", Label.RIGHT);
      t2_3 = new Label("0.00", Label.RIGHT);
      t1_4 = new Label("$0.00", Label.RIGHT);
      t2_4 = new Label("$0.00", Label.RIGHT);
      t2_5= new Label("$0.00", Label.RIGHT);
         
      t1_1.setBounds(196, 153, 135, 10);
      t2_1.setBounds(346, 153, 135, 10);
      t1_2.setBounds(196, 170, 135, 10);
      t2_2.setBounds(346, 170, 135, 10);
      t1_3.setBounds(196, 187, 135, 10);
      t2_3.setBounds(346, 187, 135, 10);
      t1_4.setBounds(196, 204, 135, 10);
      t2_4.setBounds(346, 204, 135, 10);
      t2_5.setBounds(346, 242, 135, 10);

      add(t1_1);   
      add(t2_1);   
      add(t1_2);   
      add(t2_2);     
      add(t1_3);   
      add(t2_3);  
      add(t1_4);   
      add(t2_4);   
      add(t2_5); 
  }

   private TextField t_length_h;
   private TextField t_length_m;
   private TextField t_attendees;
   private TextField t_traveling;
   private Button sendinfo;
   private CheckboxGroup Dom_or_int;
   private Label t1_1, t2_1, t1_2, t2_2, t1_3, t2_3, t1_4, t2_4, t2_5;
}
