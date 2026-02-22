/*
morseconvert.cpp
Jamie Orlando
Assigned: 5/12/99
Due: 5/24/99
Worth: 60 points

Purpose: To input morse code from the user and give the english translation.
*/


#include<iostream.h>
#include<conio.h>
#include"morsecode.h"

void instructions();
void inputconvert(apstring &initial, apstring &final);
void print(apstring initial, apstring final, char &run);

int main()
{
	apstring unconverted;    //The initial morsecode input
   apstring converted;      //The resulting english conversion
   char directions;         //Decides if the user views the directions
   char runagain;           //Decides if the user runs the program again
   do
   {
      converted = "";
      cout << "Jamie Orlando's Morsecode Decoder";
      cout << "\n---------------------------------\n\n";
		cout << "Do you want directions? (y/n) ";
      cin >> directions;
      if(directions == 'y' || directions == 'Y')
      	instructions();
   	inputconvert(unconverted, converted);
   	print(unconverted, converted, runagain);
   }while(runagain == 'y' || runagain == 'Y');

   clrscr();
   cout << char(2) <<"!Have a nice day!" << char(2);
	getch();
   return 0;
}

void instructions()
//Purpose: To print directions if the user needs them
//Preconditions: none
//Postconditions: Data will be printed
{
	cout << "\n\nThis program only accepts morse code for uppercase letters,\n";
   cout << "however if you pay me $100, you can register this program \n";
   cout << "and take advantage of all the special features!\n\n";
   cout << "To input the morse code, indicate a dot using a '.' and a dash,\n";
   cout << "using a '-'. Use a space to indicate a new word and a '/' to\n";
   cout << "indicate a new letter.\n";
}

void inputconvert(apstring &initial, apstring &final)
//Purpose: To input the morsecode and convert it to english
//Preconditions: Initial and final are two strings which have been declared
//Postconditions: Initial and final will be returned to the main function
{
   apstring tempstring;    //Morsecode of one letter to be sent to be converted
   apstring dummy;         //Dummy variable for getline
   morsecode m;            //Binary tree which converts the morsecode

   cout << "\nType in morsecode that you wish to convert: ";
   getline(cin, dummy);
   getline(cin, initial);
   for(int c = 0; c <= initial.length() - 1; ++c)
   {
      tempstring = "";
   	if(initial[c] != ' ' && initial[c] != '/')
      {
   		while(c <= initial.length() - 1 && initial[c] != ' ' && initial[c] != '/')
         {
         	tempstring += initial[c];
         	++c;
         }
         final += m.convert(tempstring);
		}
      if(c > initial.length() - 1)
      	--c;
      if(initial[c] == ' ')
      	final += ' ';
	}
}

void print(apstring initial, apstring final, char &run)
//Purpose: To nicely print out input and final results
//Preconditions: Initial and final should be storing values
//Postconditions: Data will be printed
{
	cout << "\n\n\nInitial input: " << initial;
   cout << "\nEnglish tranlation: " << final;
   cout << "\nDo you want to run this program again? (y/n) ";
   cin >> run;
   clrscr();
}
