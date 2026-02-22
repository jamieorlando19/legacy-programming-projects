/*
Movieline.cpp
Jamie Orlando
Assigned: 4/9/99
Due: 4/16/99
Worth: 85 points

Purpose: To simulate a line at a movie theater. First, the line gets initialized
         with two adults and three children. As each person goes in and pays,
         the user has the option of adding another person to the end of the line
         or declaring that the movie has sold out. If the line is empty, or the
         show has sold out, it is then printed how much money was made by the
         theater and how many people, if any, are still in line.
*/


#include<iostream.h>
#include<conio.h>
#include"apqueue.h"
#include"apstring.cpp"

const double adultcost = 4.00;   //Cost per adult ticket
const double childcost = 2.50;   //Cost per child ticket

void init(apqueue<bool> &people, double &money, char &sold);
void changeline(apqueue<bool> &people, double &money, char &sold);
void finalprint(apqueue<bool> people, double money, char &run);

int main()
{
	apqueue<bool> line;    //The line of people (either adult or child)
   char runagain;         //Indidates whether the program is to be run again
   char soldout;          //Indicates whether the movie is sold out
   double income;         //Total income that the theater gets

   do
   {
   	init(line, income, soldout);
      cout << "Orlando Theaters\n----------------\n\n";
      cout << "There are 5 people in line.\n";
      while(!line.isEmpty() && soldout != 'y')
      	changeline(line, income, soldout);
      finalprint(line, income, runagain);
      clrscr();
   }while(runagain == 'y' || runagain == 'Y');

   cout << "Thank you for doing business with 'Orlando Theaters'!";
   getch();
   return 0;
}

void init(apqueue<bool> &people, double &money, char &sold)
//Purpose: To initialize the line with three adults and two children, and make
//         the income 0, as well as declaring the show isn't sold out.
//Preconditions: none
//Postconditions: The three variables are returned.
{
   money = 0.00;
   sold = 'n';
   people.makeEmpty();

   for(int a = 0; a < 5; ++a)
   {
   	if((a % 2) == 0)
      	people.enqueue(1);
   	else
      	people.enqueue(0);
   }
}

void changeline(apqueue<bool> &people, double &money, char &sold)
//Purpose: To have the person at the front of the line pay, step out of line.
//         and ask the user if another person enters the line and if the movie
//         has sold out.
//Preconditions: The queue of booleans (line) and the real number (income) have
//               been initialized. The line must have at least one person in it.
//Postconditions: The three variables are returned.
{
	apstring person;    //Person at the front of the line
   char another;       //Indicates if another person joins the line
   char aorc;          //Indicates if the person who joins the line is an adult
                       //or child

	if(people.front())
   {
   	money += adultcost;
      person = "adult";
   }
   else
   {
   	money += childcost;
      person = "child";
   }
   people.dequeue();
   cout << "The " << person << " in the front of the line has paid.\n";
   cout << "There are " << people.length() << " people in line.\n";
   cout << "Do you wish to add a person to the end of the line? (y/n): ";
   cin >> another;
   if(another == 'y' || another == 'Y')
   {
      do
      {
   		cout << "Is the person an adult or child? (a/c): ";
         cin >> aorc;
         if(aorc != 'c' && aorc != 'C' && aorc != 'a' && aorc != 'A')
         	cout << "\nBad input!!!";
      }while(aorc != 'c' && aorc != 'C' && aorc != 'a' && aorc != 'A');
      if(aorc == 'c' || aorc == 'C')
      	people.enqueue(0);
      else
      	people.enqueue(1);
   }
   cout << "Has the movie sold out yet? (y/n): ";
   cin >> sold;
}

void finalprint(apqueue<bool> people, double money, char &run)
//Purpose: To print out the number of people still in line and the money made,
//         as well as to find out if the user wants to run the program again
//Preconditions: The real number (money made) is initialized. The movie sold
//               out, or the line is empty.
//Postconditions: Data will be outputted.
{
   if(people.length() != 0)
   {
   	cout << "\n\nThere are " << people.length();
      cout << " people remaining in the line.\n";
   }
   else
   	cout << "\n\nThere are no more people in line.\n";
   cout << "$" << money << " was made by 'Orlando Theaters'\n";
	cout << "Would you like to run this program again? (y/n): ";
   cin >> run;
}
