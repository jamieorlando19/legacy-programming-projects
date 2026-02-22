/*	Jamie Orlando
	11/24/98
	treasure.cpp
	90 points

	Purpose: To make a game in which you move through a dark cave and in each
   			room there are four doors.  Hazards and treasures are inserted
            randomly in the cave, and the player must retrieve all of the
            treasures before getting killed by pits or trapdoors.
   Variables:
      treasure_count - int counting the number of treasures obtained
		wins - counts the number of wins
      losses - counts the number of losses
      grid-a 2 dimensional array containing, objects from the objects data type
           which will be the cave the player will move through.
*/

#include<iostream.h>
#include<conio.h>
#include<ctype.h>
#include<stdlib.h>
#include<time.h>

enum objects{ gold, diamonds, silver, trapdoor, pit, blank };
//Data type containing the different possible things in the cave


//Player can optimize gameplay by changing the values here
const int MAX_NS = 10;    //Number of rows
const int MAX_EW = 10;    //Number of columns
const int HAZARD = 3;     //Number of hazards
const int TREASURE = 6;   //Number of treasures

void welcome();
void initialize(objects cave[MAX_NS][MAX_EW]);
void gameplay(objects cave[MAX_NS][MAX_EW], int &count);
void check(int r, int c, char &dir);
void contents(objects matrix[MAX_NS][MAX_EW], int &r, int &c, int &num,
              char dir);
void output(objects cave[MAX_NS][MAX_EW], int count, int &win, int &loss);
char repeat();

int main ()
{
   int treasure_count = 0, wins = 0, losses = 0;
	objects grid[MAX_NS][MAX_EW];

   time_t seconds;                 //
	time(&seconds);                 //Random numbers initialized
   srand((unsigned int) seconds);  //

   do
   {
   	welcome();
      initialize(grid);
      gameplay(grid, treasure_count);
      output(grid, treasure_count, wins, losses);
	}while (repeat() == 'y');

   cout << "Number of wins   - " << wins << endl;
   cout << "Number of losses - " << losses << "\n\n";
   cout << "Thank you for playing treasure hunt!";

	getch();
   return 0;
}

void welcome()
//Purpose: To display a title and give basic directions how to play
//Preconditions: none
//Postconditions: Info is outputted to the moniter
{
	cout << "Treasure Hunt\n";
   cout << "~~~~~~~~~~~~~\n\n";
   cout << "You enter a strange cave which is filled with dangers ";
   cout << "and treasures.\nMove your player north, south, east or ";
   cout << "west and get all " << TREASURE << " treasures to win!\n\n";
}

void initialize(objects cave[MAX_NS][MAX_EW])
//Purpose: To initialize the cave with randomly placed treasures and hazards
//Preconditions: The array must exist
//Postconditions: The array will be ready for gameplay
{
   int row, col, count = 0;

	for (int k = 0; k < MAX_NS; ++k)         //initializes every room as
   	for (int j = 0; j < MAX_EW; ++j)      //an empty room.
      	cave[k][j] = blank;                //

   while(count != HAZARD)                   //
   {                                        //
   	row = rand() % MAX_NS;                //
   	col = rand() % MAX_EW;                //inserts all hazards
      if (cave[row][col] == blank)          //
      {                                     //
      	cave[row][col] = (rand() % 2) + 3; //
         ++count;                           //
      }                                     //
   }                                        //

   count = 0;

   while(count != TREASURE)                 //
   {                                        //
   	row = rand() % MAX_NS;                //
   	col = rand() % MAX_EW;                //inserts all treasures
      if (cave[row][col] == blank)          //
      {                                     //
      	cave[row][col] = rand() % 3;       //
         ++count;                           //
   	}                                     //
   }                                        //
}

void gameplay(objects cave[MAX_NS][MAX_EW], int &count)
//Purpose: To get input from the user as to which direction they will move
//			  and say which room they are in, and terminate if they hit a hazard
//Preconditions: The array has to be initialized
//Postconditions: The player will either get all of the treasures or die
{
   int row = MAX_NS - 1, col = MAX_EW - 1;
   char direction;

   cout << "You start in an empty room. [" << row << "," << col << "]\n";

   do
   {

      check(row, col, direction);
   	if (direction == 'N')       //
   		--row;                   //
   	else if (direction == 'S')  //Gets the direction the player wishes to
   		++row;                   //move and advances them into the next room
   	else if (direction == 'E')  //of the direction of their choice
   		++col;                   //
   	else if (direction == 'W')  //
   		--col;                   //
		contents(cave, row, col, count, direction);
      getch();
   }while ((cave[row][col] != trapdoor) && (cave[row][col] != pit)
          && (count < TREASURE));

}

void check(int r, int c, char &dir)
//Purpose: To inform the player which directions he can move and input the
//         direction.
//Preconditions: There must be coordinates supplied to the function
//Postconditions: The variable north, south, east or west will be returned
{
   char north = 'N', south = 'S', east = 'E', west = 'W';

   if (r == 0)            //
   	north = 0;          //
   if (r == MAX_NS - 1)   //
   	south = 0;          //Finds which directions the player is allowed to move
   if (c == MAX_EW - 1)   //
   	east = 0;           //
   if (c == 0)            //
   	west = 0;           //
   cout << "\nYou have the choice of moving " << north << " " << south << " "
        << east << " " << west << ": ";
   cin >> dir;
   dir =  toupper (dir);
}

void contents(objects matrix[MAX_NS][MAX_EW], int &r, int &c, int &num,
				  char dir)
//Purpose: To report the conditions of the room and check if the move was legal
//Preconditions: The coordinates and direction must be supplied
//Postconditions: The contents of the room will be outputted
{
	if (r < 0)                                  //
   {                                           //
   	cout << "North is not a legal move.";    //
      r = 0;                                   //
   }                                           //
	else if (r > MAX_NS - 1)                    //
   {                                           //If a player makes an illegal
   	cout << "South is not a legal move.";    //move, they are told so and
      r = MAX_NS - 1;                          //their position is resetted
   }                                           //
	else if (c > MAX_EW - 1)                    //
   {                                           //
   	cout << "East is not a legal move.";     //
      c = MAX_EW - 1;                          //
   }                                           //
   else if (c < 0)                             //
   {                                           //
   	cout << "West is not a legal move.";     //
      c = 0;                                   //
   }
   else if ((dir != 'N') && (dir != 'S') && (dir != 'E') & (dir != 'W'))
   	cout << dir << " is not a valid direction.";
   else
   {
   	switch(matrix[r][c])
      {
      	case blank:   cout << "You are in an empty room. [" << r << "," << c
                       << "]";
         	break;
         case gold:    cout << "You have found GOLD! [" << r << "," << c << "]";
                       ++num;
                       matrix[r][c] = blank;
                       cout << "\nYou have gotten " << num << " out of "
                            << TREASURE << " treasures.";
         	break;
         case diamonds:cout << "You have found DIAMONDS! [" << r << "," << c
                       << "]";
                       ++num;
                       matrix[r][c] = blank;
                       cout << "\nYou have gotten " << num << " out of "
                            << TREASURE << " treasures.";
            break;
         case silver:  cout << "You have found SILVER! [" << r << "," << c
                       << "]";
                       ++num;
                       matrix[r][c] = blank;
                       cout << "\nYou have gotten " << num << " out of "
                            << TREASURE << " treasures.";
            break;
         case trapdoor:cout << "You hit a trapdoor! [" << r << "," << c << "]";
            break;
         case pit:     cout << "You fell in pit! [" << r << "," << c << "]";
            break;
      }
   }
}



void output(objects cave[MAX_NS][MAX_EW], int count, int &win, int &loss)
//Purpose: To output a picture of the cave once the gameplay is over
//Preconditions: The array must have data in it, and the player must have played
//               the game.
//Postconditions: Output will be sent to the moniter
{
   clrscr();
   if (count == TREASURE)
   {
   	cout << "Congratulations!!! You are a winner!!!  You obtained all "
           << count << " treasures!!!\n\n";
      ++win;
   }
   else
   {	cout << "You die having gotten " << count << " out of " << TREASURE
		     << " treasures.  You Lose!\n\n";
      ++loss;
   }
   for (int k = 0; k < MAX_NS; ++k)
   {
      cout << endl;
   	for (int j = 0; j < MAX_EW; ++j)
      {
      	switch(cave[k][j])
         {
         	case blank:    cout << ". ";
         		break;
            case gold:     cout << "G ";
         		break;
         	case diamonds: cout << "D ";
            	break;
            case silver:   cout << "S ";
            	break;
         	case trapdoor: cout << "T ";
            	break;
         	case pit:      cout << "P ";
            	break;
         }
		}
   }
	cout << endl << endl;
   cout << " -------------- \n";
   cout << "|. - empty room|\n";
   cout << "|G - gold      |\n";
   cout << "|D - diamonds  |\n";
   cout << "|S - silver    |\n";
   cout << "|T - trapdoor  |\n";
   cout << "|P - pit       |\n";
   cout << " -------------- ";

}

char repeat()
//Purpose: To see if the user would like to run the program again
//Preconditions: The program must have run at least once, and be at the very end
//Postconditions: The character will be returned
{
 	char decision;

	cout << "\n\nWould you like to play this game again? (y/n)";
   cin >> decision;
   clrscr();
   return decision;
}
