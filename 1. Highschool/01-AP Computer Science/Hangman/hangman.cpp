/*
hangman.cpp
Jamie Orlando
Assigned: 5/25/99
Due:
Worth: points

Purpose: A fun game for the whole family! Hangman! =)
*/

#include<conio.h>
#include"apstring.cpp"
#include"apvector.h"
#include<fstream.h>
#include<time.h>
#include<stdlib.h>
#include<ctype.h>

apstring initialize();
void initscreen(apstring word);
void gameplay(apstring word, bool &done);
void draw(int num, ifstream &pic);
void final(apstring word, char &run, bool done);

int main()
{
   apstring selectedword;          //A word randomly selected from a file
   bool worddone;                  //Indicates if the word was solved
   char runagain;                  //Indicates if the user want to play again
	time_t seconds;                 //
	time(&seconds);                 //Random numbers initialized
   srand((unsigned int) seconds);  //

   do
   {
   	selectedword = initialize();
      initscreen(selectedword);
		gameplay(selectedword, worddone);
      final(selectedword, runagain, worddone);
      clrscr();
   }while(runagain == 'Y');
   cout << char(2) << "!BYE!" << char(2);

	getch();
   return 0;
}

apstring initialize()
//Purpose: To pick a random string from a file and return it
//Preconditions: An input file with words must exist in your directory
//Postconditions: The string will be returned
{
   apstring temp;                  //A string in the file to be put in a vector
   apvector<apstring> wordlist;    //The vector which has the data of the file
   int size = 0;                   //The vector's size
   ifstream wordfile;              //The file containing words
   wordfile.open("wordfile.dat");
   if(!wordfile)
   {
   	cerr << "Wordfile.dat could not be found in your directory!";
      getch();
      abort();
   }

   while(!wordfile.eof())
   {
      ++size;
      wordlist.resize(size);
   	wordfile >> temp;
      wordlist[size - 1] = temp;
	}
   return wordlist[rand() % (wordlist.length() - 1)];;
}

void initscreen(apstring word)
//Purpose: To setup the screen for gameplay
//Preconditions: A word must have been selected from the file
//Postconditions: Data will be outputted
{
	gotoxy(20, 1);
   cprintf("H A N G M A N");
   gotoxy(20, 2);
   cprintf("xxxxxxxxxxxxx");
   gotoxy(20, 5);
   for(int a = 0; a < word.length(); ++a)
   {
   	if(word[a] != '_')
			cprintf("-");
      else
      	cprintf(" ");
	}
   gotoxy(20,7);
   cprintf("Guess a letter: ");
   gotoxy(20, 10);
   cprintf("Used Letters");
   gotoxy(20, 11);
   cprintf("------------");
}

void gameplay(apstring word, bool &done)
//Purpose: To have the user type characters and if they are right, output them
//         and if they are wrong put it in the used letters list.  After six
//         wrong characters, the game will end. If the user is able to figure
//         out the word, then they win.
//Preconditions: Hangman.dat must be in the directory and the word must have
//               been selected
//Postconditions: A bool will be returned indicating if the user won or lost
{
   apvector<bool> meter(word.length());    //Determines if the user has won
   int number = 0;         //How many parts have been drawn
   int usedcharpos = 0;    //Determines where the used letter will be printed
   bool inword;            //Indicates if the character selected was in the word
   char temp;              //Character entered by user
   ifstream hangman;       //File containing data how to draw the hangman
	hangman.open("hangman.dat");
   if(!hangman)
   {
      clrscr();
   	cerr << "Hangman.dat could not be found in your directory!";
      getch();
      abort();
   }
   for(int b = 0; b < meter.length(); ++b)    //The meter corresponds to the
   {                                          //string. It is initialized to be
   	if(word[b] != '_')                      //all false except for spaces and
      	meter[b] = 0;                        //then as the user enters correct
      else                                    //characters, the corresponding
      	meter[b] = 1;                        //fields become true. Once all
   }                                          //fields are true the user wins.

   do
   {
      gotoxy(36, 7);
      temp = getch();
      temp = toupper(temp);
      inword = 0;
      for(int a = 0; a < word.length(); ++a)
      	if(temp == word[a])
         	inword = 1;
      if(inword)
      {
      	for(int a = 0; a < word.length(); ++a)
         {
         	if(temp == word[a])
            {
               meter[a] = 1;
            	gotoxy(20 + a, 5);
               putch(temp);
            }
         }

      }
      else
      {
      	++number;
         draw(number, hangman);
         gotoxy(20 + usedcharpos * 2, 13);
         putch(temp);
         ++usedcharpos;
      }
      done = 1;
      for(int q = 0; q < meter.length(); ++q)
      	if(meter[q] == 0)
         	done = 0;
   }while(number != 6 && !done);
   hangman.close();
}

void draw(int num, ifstream &pic)
//Purpose: To draw hangman progressively
//Preconditions: The number sent to the function must be between 1 and 6.  A
//               file must be properly opened.
//Postconditions: Parts of the man will be outputted
{
   apstring c;    //A temporary character (it is in string form, because the
                  //backslash in character form would cause an error)
   int x, y;      //Coordinates for the string (character) to be placed
   int todraw;    //Indicates how characters to print per time

   switch(num)
   {
   	case 1:   todraw = 10;     //Head
      	break;
      case 2:   todraw = 4;      //Face
      	break;
      case 3:   todraw = 4;      //Body
      	break;
      case 4:   todraw = 3;      //Arms
      	break;
      case 5:   todraw = 2;      //Legs
      	break;
      case 6:   todraw = 35;     //Gallows
   }
	for(int a = 1; a <= todraw; ++a)
	{
		pic >> x;
		pic >> y;
		pic >> c;
		gotoxy(x, y);
		cprintf(c.c_str());
   }
}

void final(apstring word, char &run, bool done)
//Purpose: To indicate if the person won or lost and to ask them to play again
//Preconditions: The string and the bool are sent to the function
//Postconditions: A char is returned indicating if the user wants to play again
{
	gotoxy(1, 17);
	if(done)
      cprintf("YOU WIN!!! YOU ARE A HANGMAN MASTER!");
   else
   {
   	cprintf("You Lose! The word was: ");
   	for(int a = 0; a < word.length(); ++a)
      {
      	if(word[a] != '_')
         	putch(word[a]);
         else
         	putch(' ');
		}
      cprintf("!!! You could always try again...");
   }
	gotoxy(1, 19);
   cprintf("Do you want to try again? (y/n)");
 	run = getch();
   run = toupper(run);
}
