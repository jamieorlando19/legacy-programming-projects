/**
   A <code>Board</code> object represents the Scrabble game
   board, which is composed of a grid of 15 by 15.  It has a ColorMap 
   to indicate the color of each square, and a LetterMap to indicate
   the placement of Scrabble letter tiles on the board.
*/

public class Board
{
   /**
      Constructs a Board object
   */
   public Board()
   {
      ColorMap = new int[15][15];
      LetterMap = new Letter[15][15];
      setColors();
   }
 
   /**
      Accessor function for the board's ColorMap.
      @return a two-dimensional int array representing the ColorMap
   */ 
   public int[][] getColorMap()
   { return ColorMap; }
 
   /**
      Accessor function for the board's LetterMap.
      @return a two-dimensional Letter array representing the LetterMap
   */
   public Letter[][] getLetterMap()
   { return LetterMap; }

   private void setColors()
   {
      for(int a = 0; a < 15; a++)      //Sets Each Tile Blank
         for(int b = 0; b < 15; b++)
            ColorMap[a][b] = 0;
        
      ColorMap[7][7] = 5;              //Sets Middle Tile
      setDoubleLetter();
      setDoubleWord();
      setTripleLetter();
      setTripleWord();
   }

   private void setDoubleLetter()     // Sets the cyan squares
   {
      ColorMap[0][3] = 1;
      ColorMap[0][11] = 1;
      ColorMap[2][6] = 1;
      ColorMap[2][8] = 1;
      ColorMap[3][0] = 1;
      ColorMap[3][7] = 1;
      ColorMap[3][14] = 1;
      ColorMap[6][2] = 1;
      ColorMap[6][6] = 1;
      ColorMap[6][8] = 1;
      ColorMap[6][12] = 1;
      ColorMap[7][3] = 1;
      ColorMap[7][11] = 1;
      ColorMap[8][2] = 1;
      ColorMap[8][6] = 1;
      ColorMap[8][8] = 1;
      ColorMap[8][12] = 1;
      ColorMap[11][0] = 1;
      ColorMap[11][7] = 1;
      ColorMap[11][14] = 1;
      ColorMap[12][6] = 1;
      ColorMap[12][8] = 1;
      ColorMap[14][3] = 1;
      ColorMap[14][11] = 1;
   }
  
   private void setDoubleWord()       // Sets the pink squares
   {
      ColorMap[1][1] = 2;
      ColorMap[1][13] = 2;
      ColorMap[2][2] = 2;
      ColorMap[2][12] = 2;
      ColorMap[3][3] = 2;
      ColorMap[3][11] = 2;
      ColorMap[4][4] = 2;
      ColorMap[4][10] = 2;
      ColorMap[10][4] = 2;
      ColorMap[10][10] = 2;
      ColorMap[11][3] = 2;
      ColorMap[11][11] = 2;
      ColorMap[12][2] = 2;
      ColorMap[12][12] = 2;
      ColorMap[13][1] = 2;
      ColorMap[13][13] = 2;
   }

   private void setTripleLetter()     // Sets the blue squares
   {
      ColorMap[1][5] = 3;
      ColorMap[1][9] = 3;
      ColorMap[5][1] = 3;
      ColorMap[5][5] = 3;
      ColorMap[5][9] = 3;
      ColorMap[5][13] = 3;
      ColorMap[9][1] = 3;
      ColorMap[9][5] = 3;
      ColorMap[9][9] = 3;
      ColorMap[9][13] = 3;
      ColorMap[13][5] = 3;
      ColorMap[13][9] = 3;
   }
 
   private void setTripleWord()     // Sets the red squares
   {
      ColorMap[0][0] = 4;
      ColorMap[0][7] = 4;
      ColorMap[0][14] = 4;
      ColorMap[7][0] = 4;
      ColorMap[7][14] = 4;
      ColorMap[14][0] = 4;
      ColorMap[14][7] = 4;
      ColorMap[14][14] = 4;
   }

   // Determines how many tiles were used in the turn
   // represented by turn
   private int tiles_used( Letter[][] turn ) 
   {                                       
      int num = 0;                           
      for (int i = 0; i < 15; i++)
         for (int j = 0; j < 15; j++)
            if (turn[i][j] != null)
               ++num;
      return num;
   }

   // used in turn_is_valid to calculate the score for a single turn
   // represented by turn.  x and y are the coordinates of the first
   // tile of a turn, and vertical is used to determine the type of move
   private int computeScore( Letter[][] turn, int x, int y, boolean
                             vertical )
   {
      int score = 0;
      boolean t = true;
      boolean f = false;
      int j = 0;    // for loop counter

      if (vertical)
      {
         // for each letter in the turn
         for (int i = y; (i < 15) && ((turn[x][i] != null) ||
              (LetterMap[x][i] != null)); i++)

            // if there is an existing tile
            if (turn[x][i] != null)
            {

               // if there is a horizontal word to the left, add in its
               // score
               if ((x > 0) && LetterMap[x-1][i] != null)
               {
                  // find where the word begins
                  for (j = x-1; (j >= 0) && LetterMap[j][i] != null; j--);
                  score += (tileScore( turn, (j+1), i, f));
               }

               // if there is a horizontal word to the right, add in its
               // score
               else if ((x < 14) && LetterMap[x+1][i] != null)
                  score += (tileScore(turn, x, i, f));
            }
         
         // find where the turn begins
         int r;
         for (r = y; ( r >= 0) && (turn[x][r] != null || LetterMap[x][r]
              != null); r--);
         ++r;

         // now add in the vertical word
         score += (tileScore( turn, x, r, vertical));
      }
     
      else  // the turn is horizontal
      {
         // for each letter in the turn
         for (int i = x; (i < 15) && ((turn[i][y] != null) ||
              (LetterMap[i][y] != null)); i++)
         {
            // if there is an existing letter
            if (turn[i][y] != null)
            {
               // if there is a word going up, add in its score
               if ((y > 0) && LetterMap[i][y-1] != null)
               { 
        
                  // find where the word begins
                  for (j = y-1; (j >= 0) && LetterMap[i][j] != null; j--);
                  score += (tileScore( turn, i, (j+1), t));
               }
             
               // if there is a word going down, add in its score
               else if ((y < 14) && LetterMap[i][y+1] != null)
                  score += (tileScore( turn, i, y, t));
            }
         }

         // find where the turn begins
         int c;
         for (c = x; (c >= 0) && (turn[c][y] != null || LetterMap[c][y] !=
                     null); c--);
         ++c;                                 

         // now add in the horizontal word
         score += (tileScore( turn, c, y, vertical)); 
      }

      // add a 50 point bonus if all 7 tiles were used 
      if (tiles_used(turn) == 7)
         score += 50;

      return score;
   }

   // used in computeScore to calculate the score of a single word
   private int tileScore( Letter[][] turn, int x, int y, boolean
                          vertical)
   {                           
      int tscore = 0;
      int bonus = 1;

      // for each tile of the word
      while ((x < 15) && (y < 15) && ((turn[x][y] != null) ||
             (LetterMap[x][y] != null)) ) 
      {
         // if its an existing tile, it only counts for face value
         if (turn[x][y] == null)                  
            tscore += LetterMap[x][y].get_value();
         else 
         {
            // determine what color square the tile lies on
            switch(ColorMap[x][y])
            {
               case 1:
                  tscore += (2 * turn[x][y].get_value());
                  break;

               case 2:
               case 5:
                  tscore += turn[x][y].get_value();
                  bonus *= 2;
                  break;

               case 3:
                  tscore += (3 * turn[x][y].get_value());
                  break;

               case 4:
                  tscore += turn[x][y].get_value();
                  bonus *= 3;
                  break;
  
               default:
                  tscore += turn[x][y].get_value(); 
            }
         }
         if (vertical)
            ++y;
         else
            ++x;
      }

      tscore *= bonus;
      return tscore;
   }

   /**
      Determines whether or not a turn is valid in that it occupies only
      a single row or column, and it connects to at least one existing 
      tile.  
      @param turn the tiles placed by a player on one turn
      @param first_turn whether or not this is the first turn of the game
      @return an int that is 0 if the turn is invalid and the score of 
              the turn if it is valid
   */
   public int turn_is_valid( Letter[][] turn, boolean first_turn ) 
   {
      boolean horizontal = false;
      boolean vertical = false;
      boolean continuous = false;
      boolean center = false;
      boolean single = false;  
      boolean t = true;
      boolean f = false;
      int firstX = 0, firstY = 0;   // coordinates of the first letter
      
      // find the first letter of the move
      for (int a = 14; a >= 0; a--) 
         for (int b = 14; b >= 0; b--)
            if(turn[a][b] != null)
            { firstX = a; firstY = b;}      
      
      // determine whether the move is horizontal or vertical or a single
      if ((null_up(turn, firstX, firstY)) && 
          (rest_of_column_null(turn, firstX, firstY)) &&
          (null_left(turn, firstX, firstY)) && 
          (rest_of_row_null(turn, firstX, firstY))) 
         single = true;

      else if ((null_up(turn, firstX, firstY)) &&
               (rest_of_column_null(turn, firstX, firstY)))
         horizontal = true;

      else if ((null_left(turn, firstX, firstY)) &&
               (rest_of_row_null(turn, firstX, firstY)))
         vertical = true; 

      else
         return 0;

      if (single)
      {
         int s_score = 0;

         // check that there are no other tiles in the move
         for (int i = 0; i < 15; i++)
           for (int j = 0; j < 15; j++)
              if ((turn[i][j] != null) && (i != firstX) && (j != firstY))
                 return 0; 
 
         if (first_turn)
         {
            // first turn must include center square
            if ((firstX != 7) && (firstY != 7))
               return 0;
            else
               return (2 * turn[firstX][firstY].get_value());
         }

         // add in the score for any word to the left
         if ((firstX > 0) && LetterMap[firstX-1][firstY] != null)
         {
            int h;
            // find the beginning of the word
            for (h = firstX-1; (h >= 0) && LetterMap[h][firstY] != null;
                 h--);
            s_score += (tileScore( turn, (h+1), firstY, f));
         }

         // add in the score for any word going down
         if ((firstY < 14) && LetterMap[firstX][firstY+1] != null)
            s_score += (tileScore( turn, firstX, firstY, t));

         // add in the score for any word to the right
         if ((firstX < 14) && LetterMap[firstX+1][firstY] != null)
            s_score += (tileScore( turn, firstX, firstY, f));

         // add in the score for any word going up
         if ((firstY > 0) && LetterMap[firstX][firstY-1] != null)
         {
            int p;
            // find the start of the word
            for (p = firstY-1; (p >= 0) && LetterMap[firstX][p] != null;
                 p--);
            s_score += (tileScore( turn, firstX, (p+1), t));
         }

         return s_score;  
       }
         
      else if (vertical)
      {
         // check for stray tiles by making sure all remaining tiles of 
         // the move are in the same column
         for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
               if ((turn[i][j] != null) && (i != firstX))
                  return 0;

         // search for spaces in the turn
         for (int w = firstY; w < 15; w++)
         {
            if (turn[firstX][w] == null)
            {
               // check if that space is occupied by an existing tile
               if (LetterMap[firstX][w] != null)
                  continuous = true;
               
               // condition for first move of the game; one tile 
               // must be on the center square
               else if (first_turn)
               {
                  continuous = true;
                  if (!(rest_of_column_null( turn, firstX, w )))
                     return 0;

                  center = false;
                  for (int u = firstY; (u > 15) && turn[firstX][u] != null; u++)
                     if ((u == 7) && (firstX == 7))
                        center = true;
                  if (center == false)
                     return 0;
               
                  // determine score for first turn
                  int seven = 0;
                  if (tiles_used(turn) == 7)
                     seven = 50;
                  return (seven + tileScore(turn, firstX, firstY, t));
               } 

               // otherwise the rest of the column has to be empty
               else
                  if (!(rest_of_column_null( turn, firstX, w )))
                     return 0;
            }
         }
         
         // look for tiles above, below, to the left, or to the right of 
         // the turn
         if (!continuous)
         {
            for (int z = firstY; (z < 15) && (turn[firstX][z] != null);z++)
               if ( ((firstX > 0) && (LetterMap[firstX-1][z] != null)) ||
                    ((z < 14) && (LetterMap[firstX][z+1] != null))||
                    ((firstX < 14) && (LetterMap[firstX+1][z] != null))||
                    ((z > 0) && (LetterMap[firstX][z-1] != null)) )
                  continuous = true;
         }
      }

      else if (horizontal)
      {
         // check for stray tiles by making sure all remaining tiles of
         // the move are in the same row
         for(int l = 0; l < 15; l++)
           for (int m = 0; m < 15; m++)
              if ((turn[l][m] != null) && (m != firstY))
                 return 0;

         // search for spaces in the turn
         for (int d = firstX; d < 15; d++)
         {
            if (turn[d][firstY] == null)
            {
               // check if a space if occupied by an existing letter
               if (LetterMap[d][firstY] != null)
                  continuous = true; 

               // condition for first move of the game; one tile
               // must be on the center square 
               else if (first_turn)
               {
                  continuous = true;
                  if (!(rest_of_row_null( turn, d, firstY )))
                     return 0;

                  center = false;
                  for (int u = firstX; (u < 15) && turn[u][firstY] !=
                       null; u++)
                     if ((u == 7) && (firstY == 7))
                        center = true;
                  if (center == false)
                     return 0;

                  // determine the score for the first turn
                  int seven = 0;
                  if (tiles_used(turn) == 7)
                     seven = 50;
                  return (seven + tileScore(turn, firstX, firstY, f));
               }
    
               // otherwise the rest of the row must be empty
               else 
               {
                  if (!(rest_of_row_null( turn, d, firstY )))
                     return 0;
               }
            }  
         }
  
         // look for existing tiles above, below, to the left, or to the
         // right of the turn
         if (!continuous)
         {
            for (int z = firstX; (z < 15) && (turn[z][firstY] !=null);z++)
               if ( ((z > 0) && (LetterMap[z-1][firstY] != null)) ||
                    ((firstY < 14) && (LetterMap[z][firstY+1] != null))||
                    ((z < 14) && (LetterMap[z+1][firstY] != null))||
                    ((firstY > 0) && (LetterMap[z][firstY-1] != null)) )
               continuous = true; 
         }         

      }     

      // if the turn is valid, return the score, otherwise return 0
      if (continuous)
         return (computeScore( turn, firstX, firstY, vertical));
      else
         return 0;
   }

   // determines if the remainder of a column is empty 
   private boolean rest_of_column_null( Letter[][] turn, int x, int y )
   {
      if (y == 14)
         return true;
      int i;
      for (i = y+1; (i < 15) && (turn[x][i] == null); i++);
      return (i == 15);
   }

   // determines if the remainder of a row is empty
   private boolean rest_of_row_null( Letter[][] turn, int x, int y )
   {
      if (x == 14)
         return true;
      int i;
      for (i = x+1; (i < 15) && (turn[i][y] == null); i++);
      return (i == 15);
   }

   // determines if the row to the left is empty
   private boolean null_left( Letter[][] turn, int x, int y )
   {
      if (x == 0)
         return true;
      int i;
      for (i = x-1; (i >= 0) && (turn[i][y] == null); i--);
      return (i == -1);
   }

   // determines if a column is empty upwards
   private boolean null_up( Letter[][] turn, int x, int y )
   {
      if (y == 0)
         return true;
      int i;
      for (i = y-1; (i >= 0) && (turn[x][i] == null); i--);
      return (i == -1);
   }

   private int[][]ColorMap;
   private Letter[][] LetterMap;
}
