/*
Inventory.cpp
Jamie Orlando
Assigned: 12/1/98
Due: 12/18/98
Worth: 90 points

Purpose:
	To keep a log of inventory records in a file, and allowing the user to create
a list, make changes to the amount on hand, and print the list.
*/

#include<iostream.h>
#include<conio.h>
#include<iomanip.h>
#include<fstream.h>
#include<stdlib.h>
#include<ctype.h>

const int MAX_FILE = 100; //The max number of entries a file can hold
const int MAX_LINES = 20; //The max amount of lines that can fit on the screen
const int MAX_SIZE = 15;  //The max amount of characters for the description

struct invData
{
	int item_num;               //The item number
   int num_item;               //The number of items in stock
   char description[MAX_SIZE]; //A 15 character description of the item
};

char mainmenu();
void create(fstream &inv);
void print(fstream &inv);
void change(fstream &inv);


int main()
{
	fstream inventory("Inventory.dat", ios::in | ios::out);
   //Random Access File that stores all of the information.
   char choice; //Character deciding what the user would like to do.
	if(!inventory)
   {
   	cerr << "The file could not be opened." << endl;
      cerr << "Make sure the file Inventory.dat is in this directory.";
      getch();
      exit(1);
   }
   //Program will terminate if Inventory.dat cannot be opened.

   while((choice = mainmenu()) != 'Q')
   {
      switch (choice)
      {
      	case 'N':
            create(inventory);
            break;
         case 'P':
         	print(inventory);
            break;
         case 'C':
            change(inventory);
            break;
         case 'Q':
            break;
         default:
         	cerr << "\nInvalid Command.";
            getch();
      }
		inventory.clear();
   }
   cout << "Thank you for using this product by 'Orlando Inc'!";
   getch();
   return 0;
}

char mainmenu()
//Purpose: The user decides what to do (create, print, change, quit)
//Preconditions: The variable choice in the main function must exist
//Postconditions: The function will return a character value to choice
{
	char temp; //Variable which will be returned

   clrscr();
   cout << "Main Menu\n" << "*********\n\n";
   cout << "Would you like to start a (N)ew log, (P)rint the log,\n(C)hange ";
   cout << "the attributes of the log, or (Q)uit? ";
   cin >> temp;
   temp = toupper(temp);

   return temp;
}

void create(fstream &inv)
//Purpose: To initialize a new file, and have the user input values into it
//Preconditions: The file which will store the data must exist
//Postconditions: Data will be entered into the file and be returned
{
	invData entry = {0, 0, ""}; //temporary entry
   char choice;                //user decides whether to erase or not

   clrscr();
   cout << "Warning! Creating a new log erases any existing one.\nDo you wish";
   cout << " to proceed? (y/n): ";
   cin >> choice;
   choice = toupper(choice);

   if (choice == 'Y')
   {
      clrscr();
      inv.seekg(0);
   	for (int i = 1; i <= MAX_FILE; ++i)          //Initializes the
   		inv.write((char *)&entry, sizeof(entry)); //new file.
		cout << "Create a New Log\n" << "****************\n\n";
   	do
   	{
   		cout << "Please type an item number (1 - " << MAX_FILE << "): ";
   		cin >> entry.item_num;
   	}while((entry.item_num > MAX_FILE) || (entry.item_num < 0));
   	while(entry.item_num != 0)
   	{
   		cout << "Enter 15 character description: ";
      	cin >> entry.description;
      	cout << "Enter the number of items in stock: ";
      	cin >> entry.num_item;
      	inv.seekp((entry.item_num - 1) * sizeof(invData));
      	inv.write((char *)&entry, sizeof(invData));
      	cout << "Item Number " << entry.item_num << "; " << entry.description;
      	cout << " has been entered successfully.\n";
      	do
      	{
   			cout << "Item Number (1 - " << MAX_FILE << ") [0 to quit]: ";
   			cin >> entry.item_num;
   		}while((entry.item_num > MAX_FILE) || (entry.item_num < 0));
   	}
	}
}


void print(fstream &inv)
//Purpose: To output the contents of the file
//Preconditions: The file must be setup for output
//Postconditions: The contents will be outputted to the screen
{
   int count = 0;                  //counts each line as it is outputted
	invData entry;                  //temporary entry

	cout << setiosflags(ios::left);
	clrscr();
   cout << setw(25) << "Item Number" << setw(30) << "Description" << setw(15);
   cout << "Number of Items\n";
   cout << setw(25) << "***********" << setw(30) << "***********" << setw(15);
   cout << "***************\n\n";

   inv.seekg(0);
   inv.read((char *)&entry, sizeof(entry));
	while(!inv.eof())
	{
		if(entry.item_num != 0)
      {
      	cout << setw(25) << entry.item_num << setw(30) << entry.description;
         cout << setw(15) << entry.num_item << endl;
      	++count;
      	if (count == MAX_LINES)
     	   {
         	cout << "...continued\n";
      		getch();
         	clrscr();
   			cout << "Item Number" << setw(16) << "Description" << setw(33);
         	cout << "Number of Items\n\n";
         	count = 0;
      	}
      }
      inv.read((char *)&entry, sizeof(entry));
   }
	cout << "\nHit any key to continue...";
   getch();
}


void change(fstream &inv)
//Purpose: To change the number of items for a user given item number
//Preconditions: The inventory should have data, and the file should be setup
//               for input and output.
//Postconditions: The new inventory will be returned
{
   int num;       //item number to change
   int cngnum;    //How much to change the number of items by
   invData entry; //temporary entry
   char run;      //Determines if the function is to run again

   clrscr();
   cout << "Change Amounts\n" << "**************\n\n";

   do
   {
   	cout << "Type in the item to change the values of: ";
      cin >> num;
      inv.seekg((num - 1) * sizeof(entry));

      inv.read((char *)&entry, sizeof(entry));
      if(entry.item_num != 0)
      {
      	cout << "Item Number: " << entry.item_num;
         cout << "\nDescription: " << entry.description;
         cout << "\nNumber of Items: " << entry.num_item;
         cout << "\n\nHow many items do you wish to add to ";
         cout << entry.description << "? \n(To decrease, just type a negative";
         cout << " sign before the number): ";
         cin >> cngnum;
         if ((entry.num_item + cngnum) >= 0)
         {
         	entry.num_item += cngnum;
            inv.seekp((num - 1) * sizeof(entry));
      		inv.write((char *)&entry, sizeof(entry));
         	cout << "\nItem Number: " << entry.item_num;
         	cout << "\nDescription: " << entry.description;
         	cout << "\nNumber of Items: " << entry.num_item;
         }
         else
         	cout << "You cannot remove more items than you have!";
         cout << "\nWould you like to change more values? (y/n): ";
         cin >> run;
         run = toupper(run);
         cout << endl;
      }
      else
      {
      	cout << "Item " << num << " has not been defined! ";
         cout << "Would you like to try again? (y/n): ";
         cin >> run;
         run = toupper(run);
      }
	}while(run == 'Y');
}


