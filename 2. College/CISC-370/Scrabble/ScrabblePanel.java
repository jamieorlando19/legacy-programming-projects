import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
   A <code>ScrabblePanel</code> is a JPanel which contains a Board, Rack,
   various buttons for playing the game of Scrabble, and a ScrabbleServerListener 
   thread, which listens for any data coming from the ScrabbleServer. 
*/

public class ScrabblePanel extends JPanel
{
   /**
      Constructs and initializes a new ScrabblePanel
   */
   public ScrabblePanel()
   {
      try
      {
         setLayout(null);                        //No layout manager
         TheRack = new Rack();
         RackSpaces = TheRack.getRackLetters();
         TheBoard = new Board();
         ColorMap = TheBoard.getColorMap();
         BoardSpaces = TheBoard.getLetterMap();
         CurrentBoardMoves = new Letter[15][15];
         CurrentPileMoves = new LinkedList();
         currentLetterinRack = -1;
         currentLetterinBoard = new int[] {-1, -1};
         setTurn(false);     
         accepting = false;
         gameover = false;
         myScore = 0;
         otherScore = 0;
         currentScore = 0;
         firstturn = true;
         lettersleft = 100;
         message = "Waiting for other player to connect.";

         //Button Stuff
         JButton sendbutton = new JButton("Send");
         JButton undobutton = new JButton("Undo");
         JButton acceptbutton = new JButton("Accept");
         JButton rejectbutton = new JButton("Reject");
         sendbutton.addActionListener(
            new ActionListener()
            {
               public void actionPerformed(ActionEvent evt)
               {
                  if(!gameover && !accepting && myTurn)
                  {
                     HandleSendPress();
                     repaint();
                  }
               }
            }                          );
         undobutton.addActionListener(
            new ActionListener()
            {
               public void actionPerformed(ActionEvent evt)
               {
                  if(!gameover && !accepting && myTurn)
                  {
                     HandleUndoPress();
                     repaint();
                  }
               }
            }                          );  
         acceptbutton.addActionListener(
            new ActionListener()
            {
               public void actionPerformed(ActionEvent evt)
               {
                  if(!gameover && accepting && !myTurn)
                  { 
                     HandleAcceptPress();
                     repaint();
                  }
               }
            }                            );      
         rejectbutton.addActionListener(
            new ActionListener()
            {
               public void actionPerformed(ActionEvent evt)
               {
                  if(!gameover && accepting && !myTurn)
                  {
                     HandleRejectPress();
                     repaint();
                  }
               }
            }                            );
         sendbutton.setBounds(459, 10, 80, 58);
         undobutton.setBounds(459, 78, 80, 58);
         acceptbutton.setBounds(459, 146, 80, 58);
         rejectbutton.setBounds(459, 214, 80, 58);
         add(sendbutton); 
         add(undobutton);
         add(acceptbutton);
         add(rejectbutton);

         //Mouse Stuff
         addMouseListener(
            new MouseAdapter()
            {
               public void mousePressed(MouseEvent e)
               {
                  if(!gameover && !accepting)
                  {
                     HandleMousePress(e.getX(), e.getY());
                     repaint();
                  }
               }
            }              );
         
         //Server Stuff
         //Change the port # if port is already being used on copland.udel.edu
         Socket theSocket = new Socket("copland.udel.edu", 58624);
         //reads from server
         fromserver = new BufferedReader(new InputStreamReader(theSocket.getInputStream()));
         //writes to server
         toserver = new PrintWriter(theSocket.getOutputStream(), true);
         //always listens for incoming commands from the server
         new ScrabbleServerListener(this, fromserver, toserver).start();
      }
      catch(UnknownHostException e)
      { setMessage("Unknown Host.  Server may be down."); }
      catch(ConnectException e)
      { setMessage("Scrabble Server is not running!  Try Later."); }
      catch(Exception e)
      { setMessage(e.toString()); }
   }

   //Invoked by the Send button listener
   private void HandleSendPress()
   {
      myTurn = false;
 
      //If the player is passing their turn
      if(!BoardMoves())
      {
         toserver.println("r");
         if(firstturn)
            toserver.println("1");
         else
            toserver.println("0");

         while(CurrentPileMoves.size() != 0)
            toserver.println(((Letter)(CurrentPileMoves.remove(0))).get_letter());
         toserver.println("?");
         setMessage("Turn passed. It's the other player's turn.");
      }
      //If letters have been placed on the board
      else
      {
         currentScore = TheBoard.turn_is_valid(CurrentBoardMoves, firstturn);
         //If an invalid sequence of letters has been placed on the board
         if(currentScore == 0)
         {
            toserver.println("i");
            if(firstturn)
               toserver.println("1");
            else
               toserver.println("0");
            //puts letters back in the rack
            HandleUndoPress();
            setMessage("Invalid move. It's the other player's turn."); 
         }
         //If a valid sequence of letters has been placed on the board
         else
         {
            toserver.println("v");
            if(firstturn)
               toserver.println("1");
            else
               toserver.println("0");
            toserver.println(currentScore);
            //Sends all CurrentBoardMoves to server
            for(int x = 0; x < 15; x++)
            {
               for(int y = 0; y < 15; y++)
               {
                  if(CurrentBoardMoves[x][y] != null)
                  {
                     toserver.println(CurrentBoardMoves[x][y].get_letter());
                     toserver.println(x);
                     toserver.println(y);
                  }
               }
            }
            toserver.println("?");

            setMessage("Waiting for other player to accept move.");
         }
      }
      if(firstturn) firstturn = false;
      currentLetterinBoard = new int[] {-1, -1};
      currentLetterinRack = -1;
   }

   /**
      Invoked by the Undo button listener and also used to take all of the
      current letters out of the board or pile and put them back on the rack.
   */
   public void HandleUndoPress()
   {
      int p = 0;

      //If moves have been made to the pile
      if(CurrentPileMoves.size() != 0)
      {
         while(CurrentPileMoves.size() != 0)
         {
            for( ; RackSpaces[p] != null; p++);   //Finds first open space
            RackSpaces[p] = (Letter)(CurrentPileMoves.remove(0)); 
         }
      }
      //If moves have been made to the board
      else
      {
         for(int a = 0; a < 15; a++)
         {
            for(int b = 0; b < 15; b++)
            {
               if(CurrentBoardMoves[a][b] != null)
               {
                  for( ; RackSpaces[p] != null; p++);
                  RackSpaces[p] = CurrentBoardMoves[a][b];
                  CurrentBoardMoves[a][b] = null;
               }
            }
         }
      }
      currentLetterinBoard = new int[] {-1, -1};
      currentLetterinRack = -1;
   }

   //Invoked by the Accept button listener
   private void HandleAcceptPress()
   {
      toserver.println("a");
      addCurrentWord();
      if(firstturn) firstturn = false;
      setMessage("Move accepted. Your Turn.");
   }

   //Invoked by the Reject button listener
   private void HandleRejectPress()
   {
      toserver.println("r");
      CurrentBoardMoves = new Letter[15][15];
      currentScore = 0;
      accepting = false;
      myTurn = true;
      setMessage("You have rejected other player's move. Your turn.");
   }
 
   /**
      Adds every Letter in CurrentBoardMoves to the corresponding position
      in BoardSpaces and remove all bonus tiles on these positions. If
      player is in accepting state, add the score to the other score and
      make it their turn. If the player isn't in accepting state, add the
      score to their score, and make it not their turn.
   */
   public void addCurrentWord()
   {
      if(accepting)
      {
         otherScore += currentScore;
         myTurn = true;
         accepting = false;
      }
      else
      {
         myScore += currentScore;
         myTurn = false;
      }
      for(int x = 0; x < 15; x++)
      {
         for(int y = 0; y < 15; y++)
         {
            if(CurrentBoardMoves[x][y] != null)
            {
               BoardSpaces[x][y] = CurrentBoardMoves[x][y];
               ColorMap[x][y] = 0;
            }       
         }
      }

      CurrentBoardMoves = new Letter[15][15]; 
      currentScore = 0;
   }

   //Invoked by the mouse listener
   private void HandleMousePress(int x, int y)
   {
      int position;          //Position if in the rack
      int positions[];       //Position if in the board

      //If mouse is clicked in one of the rack spaces
      if((position = isInRack(x, y)) != -1)
      {
         //If no current letter is selected and a letter in the rack is selected
         if(currentLetterinRack == -1 && RackSpaces[position] != null)
         {
            currentLetterinRack = position;
            currentLetterinBoard = new int[] {-1, -1};
         }
         //If current letter selected isn't the letter clicked on in the rack
         else if(currentLetterinRack != -1 && currentLetterinRack != position)
         {
            Letter temp = RackSpaces[position];
            RackSpaces[position] = RackSpaces[currentLetterinRack];
            RackSpaces[currentLetterinRack] = temp;
            currentLetterinRack = -1;            
         }
         //If there is current board letter and the clicked space is empty in the rack
         else if(currentLetterinBoard[0] != -1 && RackSpaces[position] == null)
         {
            RackSpaces[position] = CurrentBoardMoves[currentLetterinBoard[0]][currentLetterinBoard[1]];
            CurrentBoardMoves[currentLetterinBoard[0]][currentLetterinBoard[1]] = null;
            currentLetterinBoard = new int[] {-1, -1};
         }
      }
      //If its myTurn and letter clicked is in the board and there is no current pile moves
      else if(myTurn && (positions = isInBoard(x, y))[0] != -1 && CurrentPileMoves.size() == 0)
      {
         //If something is currently selected in the board
         if(CurrentBoardMoves[positions[0]][positions[1]] != null)
            currentLetterinBoard = positions;
         //If something is selected in the rack and nothing in the position on the board
         else if(currentLetterinRack != -1 && CurrentBoardMoves[positions[0]][positions[1]] == null && BoardSpaces[positions[0]][positions[1]] == null)
         {
            CurrentBoardMoves[positions[0]][positions[1]] = RackSpaces[currentLetterinRack];
            RackSpaces[currentLetterinRack] = null;
            currentLetterinRack = -1;
         }
         //If something is selected on the board and nothing in clicked space on the board
         else if(currentLetterinBoard[0] != -1 && CurrentBoardMoves[positions[0]][positions[1]] == null && BoardSpaces[positions[0]][positions[1]] == null)
         {
            CurrentBoardMoves[positions[0]][positions[1]] = CurrentBoardMoves[currentLetterinBoard[0]][currentLetterinBoard[1]];
            CurrentBoardMoves[currentLetterinBoard[0]][currentLetterinBoard[1]] = null;
            currentLetterinBoard = new int[] {-1, -1};
         }
      }
      //If its myTurn, click is on the pile and no current board moves
      else if(myTurn && isInPile(x, y) != -1 && !BoardMoves())
      {
         //If something is selected on the rack
         if(currentLetterinRack != -1)
         {
            CurrentPileMoves.add((Object)RackSpaces[currentLetterinRack]);
            RackSpaces[currentLetterinRack] = null;
            currentLetterinRack = -1;
         }
      }
   }

   /**
      Sets a new message at the bottom of the screen
      @param m the new message to be displayed
   */
   public void setMessage(String m)
   {
      message = m;
      repaint();
   }

   //Returns the position of the rackspace given by the coordinates
   private int isInRack(int x, int y)
   {
      for(int pos = 0; pos < 8; pos++)
      {
         if(x >= (pos * 40 + 70) && x <= (pos * 40 + 99))
            if(y >= 459 && y <= 488)
               return pos;
      } 
  
      return -1;
   }

   //Returns 1 if coordinates are in the pile, -1 if not
   private int isInPile(int x, int y)
   {
      if(x >= 459 && x < 540 && y >= 282 && y < 399)
         return 1;
      return -1;
   }

   //Returns the position of the boardspace given by the coordinates
   private int[] isInBoard(int x, int y)
   {
      for(int a = 0; a < 15; a++)
      {
         for(int b = 0; b < 15; b++)
         {
            if(x >= (a * 30 - 1) && x <= (a * 30 + 29))
               if(y >= (b * 30 - 1) && y <= (b * 30 + 29))
                  return new int[] {a, b};
         }
      }

      return new int[] {-1, -1};
   }

   //Returns true if no moves have been made to the board yet
   private boolean BoardMoves()
   {
      for(int a = 0; a < 15; a++)
      {
         for(int b = 0; b < 15; b++)
         {
            if(CurrentBoardMoves[a][b] != null)
               return true;
         }
      }

      return false;
   }

   /**
      Sets the turn status
      @param b flag determining if it will be the player's turn
   */
   public void setTurn(boolean b)
   { myTurn = b; }

   /**
      Sets the number of letters remaining in the pile
      @param l number of letters remaining in the pile
   */
   public void setRemaining(int l)
   {
      lettersleft = l;
      repaint();
   }

   /**
      Draws background, rack, board, current positions of the letters,
      pile, score sheet and message.
      @param g Graphics argument
   */
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      BoardGraphics = (Graphics2D)g;
      BoardGraphics.setPaint(Color.black);
      BoardGraphics.fillRect(0, 0, 550, 530);
      drawRack();
      drawBoard();
      drawRackLetters();
      drawBoardLetters(BoardSpaces);
      drawBoardLetters(CurrentBoardMoves);
      drawLetterPile();
      drawScoreSheet();
      drawMessage();
   }

   //Draws an empty rack
   private void drawRack()
   {
      BoardGraphics.setPaint(Color.black);
      BoardGraphics.drawRect(0, 449, 449, 49);
      BoardGraphics.setPaint(Color.red.darker());
      BoardGraphics.fillRect(1, 450, 448, 48);
      BoardGraphics.setPaint(Color.black);
      for(int a = 0; a < 8; a++)
         BoardGraphics.drawRect(a * 40 + 70, 459, 30, 30);
   }
 
   //Draws letters on the rack
   private void drawRackLetters()
   {
      for(int a = 0; a < 8; a++)
      {
         if(RackSpaces[a] != null)
            drawLetter(RackSpaces[a], a * 40 + 70, 459);
      }
   }

   //Draws letters in the array into their corresponding position on the board
   private void drawBoardLetters(Letter[][] Tile)
   {
      for(int a = 0; a < 15; a++)
      {
         for(int b = 0; b < 15; b++)
         {
            if(Tile[a][b] != null)
               drawLetter(Tile[a][b], a * 30, b * 30 - 1);
         }
      }
   }

   //Draws empty board
   private void drawBoard()
   {
      for(int a = 0; a < 15; a++)
      {
         for(int b = 0; b < 15; b++)
         {
            BoardGraphics.setPaint(Color.black);
            BoardGraphics.drawRect(a * 30, b * 30 - 1, 29, 29);
            switch(ColorMap[a][b])
            {
               case 0:   
                  BoardGraphics.setPaint(Color.orange);
                  break;
               case 1:
                  BoardGraphics.setPaint(Color.cyan);
                  break;
               case 2:
                  BoardGraphics.setPaint(Color.pink);
                  break;
               case 3:
                  BoardGraphics.setPaint(Color.blue);
                  break;
               case 4:
                  BoardGraphics.setPaint(Color.red);
                  break;
               case 5:
                  BoardGraphics.setPaint(Color.magenta);
            }
            BoardGraphics.fillRect(a * 30 + 1, b * 30, 28, 28);
         }
      }
   }

   //Draws letter pile
   private void drawLetterPile()
   {
      String left = Integer.toString(lettersleft);
      BoardGraphics.setPaint(Color.white);
      BoardGraphics.drawRect(459, 282, 80, 116);
      BoardGraphics.setPaint(Color.orange);
      BoardGraphics.fillRect(460, 283, 79, 115);
      BoardGraphics.setFont(new Font("Serif", Font.BOLD, 45)); 
      BoardGraphics.setPaint(Color.black);
      if(lettersleft == 100)
         BoardGraphics.drawString(left, 465, 333);
      else if(lettersleft >= 10)
         BoardGraphics.drawString(left, 480, 333);   
      else
      {
         BoardGraphics.setPaint(Color.red);
         BoardGraphics.drawString("0" + left, 480, 333);
      }
      BoardGraphics.setFont(new Font("Serif", Font.BOLD, 16));
      BoardGraphics.drawString("Letters", 476, 370);
      BoardGraphics.drawString("Remaining", 462, 390);     
   }

   //Draws the score sheet
   private void drawScoreSheet()
   {
      BoardGraphics.setPaint(Color.white);
      BoardGraphics.drawRect(459, 408, 80, 89);
      BoardGraphics.setPaint(Color.orange);
      BoardGraphics.fillRect(460, 409, 79, 88);
      BoardGraphics.setPaint(Color.black);
      BoardGraphics.setFont(new Font("Serif", Font.BOLD, 16));
      BoardGraphics.drawString("You   : " + myScore, 465, 445);
      BoardGraphics.drawString("Them: " + otherScore, 465, 475);
   }

   //Draws the message
   private void drawMessage()
   {
      BoardGraphics.setPaint(Color.green.darker());
      if(gameover)
         BoardGraphics.setPaint(Color.green);
      BoardGraphics.setFont(new Font("Serif", Font.BOLD, 18));
      BoardGraphics.drawString(message, 70, 522);   
   }

   //Draws a single letter at the given coordinates
   private void drawLetter(Letter x, int w, int h)
   {
      BoardGraphics.setPaint(Color.black);
      BoardGraphics.drawRect(w, h, 29, 29);
      BoardGraphics.setPaint(Color.yellow.darker());
      BoardGraphics.fillRect(w + 1, h + 1, 28, 28);

      if(x.get_letter() != ' ')
      {
         Character a = new Character(x.get_letter());
         String the_letter = new String(a.toString());
         Integer b = new Integer(x.get_value());
         String the_value = new String (b.toString()); 
         BoardGraphics.setFont(new Font("Serif", Font.BOLD, 25));
         BoardGraphics.setColor(Color.black);
         if(x.get_letter() == 'I')
            BoardGraphics.drawString(the_letter, w + 5, h + 22);
         else
            BoardGraphics.drawString(the_letter, w + 2, h + 22);
         BoardGraphics.setFont(new Font("Serif", Font.PLAIN, 10));
         if(x.get_letter() == 'K' || x.get_letter() == 'W')
            BoardGraphics.drawString(the_value, w + 23, h + 25);
         else if(x.get_letter() == 'M')
            BoardGraphics.drawString(the_value, w + 25, h + 25);
         else 
            BoardGraphics.drawString(the_value, w + 20, h + 25); 
      }
   }

   /** If the person is in accepting state */
   public boolean accepting;
   /** Player's rack */
   public Rack TheRack;
   /** Letters in the rack */
   public Letter[] RackSpaces;
   /** Moves to the board before the turn is sent */
   public Letter[][] CurrentBoardMoves;
   /** Player's score */
   public int myScore;
   /** Other player's score */
   public int otherScore;
   /** Score just from the current turn */
   public int currentScore;
   /** First turn flag */
   public boolean firstturn;
   /** Game over flag */
   public boolean gameover;
   //Graphics on the board
   private Graphics2D BoardGraphics;
   //Flag for player's turn
   private boolean myTurn;
   //Position of current letter selected in the rack
   private int currentLetterinRack;      
   //Position of current letter selected in the board
   private int[] currentLetterinBoard;
   //The board
   private Board TheBoard;
   //Bonus tiles on the board
   private int[][] ColorMap;
   //Letters on the board already
   private Letter[][] BoardSpaces;
   //Letters moved to the pile before the turn is sent
   private LinkedList CurrentPileMoves;
   //Message displayed at the bottom of the screen
   private String message;
   //Reader from the server
   private BufferedReader fromserver;
   //Writer to the server
   private PrintWriter toserver;
   //Letters remaining in the pile
   private int lettersleft;
}

//Listens for input from the ScrabbleServer
class ScrabbleServerListener extends Thread
{
   //Constructor which takes the ScrabblePanel, reader and writer
   public ScrabbleServerListener(ScrabblePanel s, BufferedReader r, PrintWriter p)
   {
      scrabpan = s;
      bufread = r;
      printwriter = p;
   }

   //Invoked automatically when the thread is started
   public void run()
   {
      try
      {
         while(!scrabpan.gameover)
         {
            switch((bufread.readLine()).charAt(0))
            {
               case 'p':
                  playerset();
                  break;
               case 'r':
                  updaterack();
                  break;
               case 'm':
                  updateremaining();
                  break;
               case 'j':
                  handlerejectturn();
                  break;
               case 'i':
                  handleinvalidturn();
                  break;
               case 'v':
                  handlevalidturn(); 
                  break;
               case 'a':
                  handleacceptorreject();
                  break;
               case 'g':
                  handlegameover();
                  break;
               default:
                  scrabpan.setMessage("Error");
            }
         }
      }
      catch(NullPointerException e)
      { 
         scrabpan.setMessage("Scrabble Server has shut down!"); 
         scrabpan.gameover = true;
      }
      catch(Exception e)
      { 
         scrabpan.setMessage(e.toString()); 
         scrabpan.gameover = true;
      }
   }

   //Determines if you are first or second player
   public void playerset()
      throws IOException
   {
      if((bufread.readLine()).charAt(0) == '1')
      {
         scrabpan.setTurn(true);
         scrabpan.setMessage("Welcome! It's your turn.");
         scrabpan.firstturn = true;
      }
      else
      {
        scrabpan.setTurn(false);
        scrabpan.setMessage("Welcome! It's the other player's turn.");
        scrabpan.firstturn = false;
      }
   }

   //Updates the rack
   public void updaterack()
      throws IOException
   {
      printwriter.println((scrabpan.TheRack).lettersinrack());           
      char curlet;
      while((curlet = (bufread.readLine()).charAt(0)) != '?')
         (scrabpan.TheRack).add(new Letter(curlet));
      scrabpan.repaint();
   }

   //Updates the remaining letters in the pile
   public void updateremaining()
      throws IOException
   { scrabpan.setRemaining(Integer.parseInt(bufread.readLine())); }

   //Handles when the other player passes on their turn
   public void handlerejectturn()
      throws IOException
   {
      scrabpan.setTurn(true);
      scrabpan.setMessage("Player has passed their turn. It's your turn.");
      if((bufread.readLine()).charAt(0) == '1')
         scrabpan.firstturn = true;
      else
         scrabpan.firstturn = false;
   }

   //Handles when the other player makes an invalid move 
   public void handleinvalidturn()
      throws IOException
   {
      scrabpan.setTurn(true);
      scrabpan.setMessage("Player has made an invalid turn. It's your turn.");
      if((bufread.readLine()).charAt(0) == '1')
         scrabpan.firstturn = true;
      else
         scrabpan.firstturn = false;
   }

   //Handles when the other player makes a valid move
   public void handlevalidturn()
      throws IOException
   {
      char current;
      int x, y;

      if((bufread.readLine()).charAt(0) == '1')
         scrabpan.firstturn = true;
      else
         scrabpan.firstturn = false;  
      scrabpan.currentScore = Integer.parseInt(bufread.readLine());
      scrabpan.CurrentBoardMoves = new Letter[15][15];
      //Reads in characters and positions and adds them to CurrentBoardMoves of the ScrabblePanel
      while((current = (bufread.readLine()).charAt(0)) != '?')
      {
         x = Integer.parseInt(bufread.readLine());
         y = Integer.parseInt(bufread.readLine());
         scrabpan.CurrentBoardMoves[x][y] = new Letter(current);
      }
      scrabpan.accepting = true;
      scrabpan.setMessage("Player has made move. Accept or reject this.");
   }

   //Handles player accepting or rejecting the current move
   public void handleacceptorreject()
      throws IOException
   {
      char decision = (bufread.readLine()).charAt(0);
      if(decision == 'a')
      {
         scrabpan.addCurrentWord();
         scrabpan.setMessage("Move accepted. Other player's turn.");
         if(scrabpan.firstturn) scrabpan.firstturn = false;
      }
      else if(decision == 'r')
      {
         scrabpan.HandleUndoPress();
         scrabpan.currentScore = 0;
         scrabpan.setMessage("Player has rejected your move! Other player's turn.");
      }
   }

   //Handles the game ending
   public void handlegameover()
      throws IOException
   {
      scrabpan.gameover = true;
      char status = (bufread.readLine()).charAt(0);
      //If player used all their letters
      if(status == '1')
      {
         int addsub = Integer.parseInt(bufread.readLine());
         scrabpan.myScore += addsub;
         scrabpan.otherScore -= addsub;
      }
      //If other player used all their letters
      else if(status == '2')
      {
         int addsub = 0;
         for(int a = 0; a < 8; a++)
         {
            if(scrabpan.RackSpaces[a] != null)
            {
               addsub += (scrabpan.RackSpaces[a]).get_value();
               scrabpan.RackSpaces[a] = null;
            }
         }
         printwriter.println(addsub);
         scrabpan.otherScore += addsub;
         scrabpan.myScore -= addsub;
      }
      //If both players couldn't use up all their letters
      else
      {
         int sub = 0;
         for(int a = 0; a < 8; a++)
         {
            if(scrabpan.RackSpaces[a] != null)
            {
               sub += (scrabpan.RackSpaces[a]).get_value();
               scrabpan.RackSpaces[a] = null;
            }
         }
         scrabpan.myScore -=  sub;
         printwriter.println(scrabpan.myScore);
         scrabpan.otherScore = Integer.parseInt(bufread.readLine());  
      }

      if(scrabpan.myScore > scrabpan.otherScore)
         scrabpan.setMessage("GAME OVER! YOU WIN!");
      else if(scrabpan.myScore < scrabpan.otherScore)
         scrabpan.setMessage("GAME OVER! YOU LOSE!");
      else
         scrabpan.setMessage("GAME OVER! TIE GAME!");
   }

   //The ScrabblePanel to send commands to
   private ScrabblePanel scrabpan;
   //The Reader to read commands from
   private BufferedReader bufread;
   //The Writer to write out commands
   private PrintWriter printwriter;
}
