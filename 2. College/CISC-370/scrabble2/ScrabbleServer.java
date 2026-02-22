import java.io.*;
import java.net.*;

/**
   A <code>ScrabbleServer</code> object is a server which interacts with 2
   different clients (ScrabblePanel) which connect to it.
*/

public class ScrabbleServer
{  
   /**
      Main method which starts the server, and runs the game until the end
      when it automatically ends.
   */
   public static void main(String args[])
   {  
      try
      {  
         boolean gameover = false;
         LetterList pile = new LetterList();
         //Used to end the game if the pile is empty and players reject their turns
         int numreject = 0;
         System.out.println("Server Running on " + ScrabbleIP.IPADDY + ":" + ScrabbleIP.PORTNUM);
         ServerSocket serversocket = new ServerSocket(ScrabbleIP.PORTNUM);
         Socket socket1 = serversocket.accept();
         System.out.println("Player 1 Connected");
         //Writes and reads to and from player 1
         BufferedReader fromplayer1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
         PrintWriter toplayer1 = new PrintWriter(socket1.getOutputStream(), true);
         
         //Writes and reads to and from player 2
         Socket socket2 = serversocket.accept();
         System.out.println("Player 2 Connected");
         BufferedReader fromplayer2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
         PrintWriter toplayer2 = new PrintWriter(socket2.getOutputStream(), true);
         System.out.println("Starting Game");
         
         toplayer1.println("p");        //
         toplayer1.println("1");        //Tells player's computer
         toplayer2.println("p");        //
         toplayer2.println("2");        //which number player they are.
   
         //Fills both player's racks and updates the number of letters remaining
         ScrabbleServer.updaterack(toplayer1, toplayer2, fromplayer1, fromplayer2, pile);
         ScrabbleServer.updaterack(toplayer2, toplayer1, fromplayer2, fromplayer1, pile);
         ScrabbleServer.updateremaining(toplayer1, toplayer2, pile);

         //Makes player 1 the current player and player 2 the other player
         BufferedReader currentread = fromplayer1;
         BufferedReader otherread = fromplayer2;
         PrintWriter currentwrite = toplayer1;
         PrintWriter otherwrite = toplayer2;
         
         while(!gameover)
         {
            switch((currentread.readLine()).charAt(0))
            {
               case 'r':
                  ScrabbleServer.rejectturn(currentread, currentwrite, otherwrite, pile);
                  if(pile.get_size() == 0)
                  {
                     numreject++;
                     //If both players reject their turn when the pile is empty
                     if(numreject == 2)
                     {
                        ScrabbleServer.gameisover(currentwrite, otherwrite, currentread, otherread, false);   
                        gameover = true;
                     }
                  }
                  break;
               case 'i':
                  ScrabbleServer.invalidturn(currentread, otherwrite);
                  numreject = 0;
                  break;
               case 'v':
                  ScrabbleServer.validturn(currentread, otherread, currentwrite, otherwrite);
                  numreject = 0;
                  break;
               default:
                  System.out.println("error");
            }

            if(numreject < 2)
            {            
               gameover = ScrabbleServer.updaterack(currentwrite, otherwrite, currentread, otherread, pile);
               ScrabbleServer.updateremaining(currentwrite, otherwrite, pile);
            }

            //If player 1 is the current player, player 2 is current etc...
            if(((Object)currentread).equals((Object)fromplayer1))
            {
               currentread = fromplayer2;
               otherread = fromplayer1;
               currentwrite = toplayer2;
               otherwrite = toplayer1;
            }
            else
            {
               currentread = fromplayer1;
               otherread = fromplayer2;
               currentwrite = toplayer1;
               otherwrite = toplayer2;
            }
         }
      System.out.println("Game ended successfully!");
      }
      catch(SocketException e)
      { System.out.println("Game has been exited prematurely!"); } 
      catch(Exception e)
      { System.out.println(e); }
   }

   /**
      Updates the current player's rack and if the game ends, runs the
      gameisover() method.
      @param to writer to the current player
      @param to2 writer to the other player
      @param from reader from the current player
      @param from2 reader from the second player
      @param pile the pile of letters
      @returns true if the game is over, false otherwise
   */
   public static boolean updaterack(PrintWriter to, PrintWriter to2, BufferedReader from, BufferedReader from2, LetterList pile)
      throws IOException
   {
      to.println("r");
      int put = Integer.parseInt(from.readLine()), lettersput = 0;
      //Updates player's rack as long they need letters and there exists letters
      for(int a = 0; pile.get_size() > 0 && a < (7 - put); a++)
      {
         to.println((pile.get_letter()).get_letter());
         lettersput++;
      }
      to.println("?");

      //If rack is empty and the letterlist is empty
      if(put == 0 && lettersput == 0 && pile.get_size() == 0)
      {
         ScrabbleServer.gameisover(to, to2, from, from2, true);
         return true;
      }
      return false;
   }

   /**
      Updates the remaining letters on both player's screens
      @param to writer to the current player
      @param to2 writer to the other player
      @param pile the pile of letters
   */
   public static void updateremaining(PrintWriter to, PrintWriter to2, LetterList pile)
   {
      to.println("m");
      to2.println("m");
      to.println(pile.get_size());
      to2.println(pile.get_size());
   }

   /**
      Handles when current player passes their turn
      @param from reader from current player
      @param to writer to current player 
      @param to2 writer to other player
      @param pile the pile of letters
   */
   public static void rejectturn(BufferedReader from, PrintWriter to, PrintWriter to2, LetterList pile)
      throws IOException
   {
      char current;
      char firstturn = (from.readLine()).charAt(0);
      while((current = (from.readLine()).charAt(0)) != '?')
         pile.add(new Letter(current));
      to2.println("j");
      to2.println(firstturn);
   }

   /**
      Handles when current player makes an invalid move
      @param from reader from current player
      @param to writer to other player
   */
   public static void invalidturn(BufferedReader from, PrintWriter to)
      throws IOException
   {
      to.println("i");
      to.println(from.readLine());
   }

   /**
      Handles when current player submits a valid move
      @param from reader from current player
      @param from2 reader from other player
      @param to writer to current player
      @param to2 writer to other player
   */
   public static void validturn(BufferedReader from, BufferedReader from2, PrintWriter to, PrintWriter to2)
      throws IOException
   {
      to2.println("v");
      to2.println(from.readLine());
      to2.println(from.readLine());
      char current;
      while((current = (from.readLine()).charAt(0)) != '?')
      {
         to2.println(current);
         to2.println(from.readLine());
         to2.println(from.readLine());
      }
      to2.println("?");
      to.println("a");
      to.println(from2.readLine());
   }

   /**
      Handles when current player ends the game
      @param to writer to current player
      @param to2 writer to other player
      @param from reader from current player
      @param from2 reader from other player
      @param usedletters indicates if all the current player's letters were used
   */
   public static void gameisover(PrintWriter to, PrintWriter to2, BufferedReader from, BufferedReader from2, boolean usedletters)
      throws IOException
   {
      to.println("g");
      to2.println("g");
      if(usedletters)
      {
         to.println("1");
         to2.println("2");
         to.println(from2.readLine());
      }
      else
      {
         to.println("3");
         to2.println("3");
         to.println(from2.readLine());
         to2.println(from.readLine());
      } 
   }
}
