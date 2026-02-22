/*
Polynomial.cpp
Jamie Orlando
Assigned: 2/17/99
Due: 3/3/99
Worth: 80 points

Purpose: To read in as many polynomials as the user desires, and then to add,
         subtract or multiply them.  It then echos all input and outputs the
         resultant polynomial.
*/

#include<iostream.h>
#include<conio.h>
#include"apvector.h"
#include"llist.cpp"

struct exponent
{
	int number;     //Coefficient of variable
   int degree;     //Degree of variable
};

void resetvars(apvector<llist<exponent>> &pols, llist<exponent> &result);
void initinput(apvector<llist<exponent>> &pols);
void addseq(llist<exponent> &list, exponent data);
void addsub(apvector<llist<exponent>> &pols, llist<exponent> &result, char dec);
void multiply(apvector<llist<exponent>> &pols, llist<exponent> &result);
void print(apvector<llist<exponent>> &pols, llist<exponent> &result, char dec);
void printpoly(llist<exponent> &terms);

int main()
{
   char choice;            //Whether to add, subtract, or multiply
   char runagain = 'N';    //Whether the user wants to run the program again
   int size = 0;           //The amount of polynomials the user wishes to enter
   llist<exponent> answer; //Resultant polynomial
   /*Apvector which stores polynomials is declared below*/

	do
   {
		cout << "Jamie Orlando's Polynomial Utility\n";
      cout << "----------------------------------\n\n";
      cout << "How many polynomials do you wish to enter? ";
      do
      {
      	cin >> size;
         if(size < 2)
         	cout << "The amount must be two or more! Enter again: ";
      }while(size < 2);
      apvector<llist<exponent> > polynomials(size);   //List of polynomials
      if(runagain == 'y' || runagain == 'Y')
      	resetvars(polynomials, answer);
      initinput(polynomials);
      if(size == 2)
      {
			do
      	{
      		cout << "Do you wish to (A)dd, (S)ubtract or (M)ultiply the entered polynomials? ";
      		cin >> choice;
      	}while(choice != 'A' && choice != 'a' && choice != 'S' && choice != 's' && choice != 'M' && choice != 'm');
      }
      else
      {
      	do
      	{
         	cout << "Do you wish to (A)dd or (S)ubtract the entered polynomials? ";
      		cin >> choice;
      	}while(choice != 'A' && choice != 'a' && choice != 'S' && choice != 's');
      }
      if(choice == 'A' || choice == 'a' || choice == 'S' || choice == 's')
         addsub(polynomials, answer, choice);
      else
      	multiply(polynomials, answer);
      clrscr();
      print(polynomials, answer, choice);
      cout << "\n\nDo you want to run this again? (y/n)";
      cin >> runagain;
      clrscr();
   }while(runagain == 'y' || runagain == 'Y');
   cout << "Jamie Orlando's Polynomial Utility...TERMINATED!";
   getch();
   return 0;
}

void resetvars(apvector<llist<exponent> > &pols, llist<exponent> &result)
//Purpose: To erase all current data stored in the linked lists so that the
//         program can run again.
//Preconditions: A vector of linked lists of exponents and a linked list of
//               exponents must exist.
//Postconditions: Both variables will be returned with unneccessary data erased
{
	for(int a = 0; a <= (pols.length() - 1); ++a)
   {
   	pols[a].first();
      while(!pols[a].null())
      	pols[a].remove();
   }
   result.first();
   while(!result.null())
   	result.remove();
}

void initinput(apvector<llist<exponent> > &pols)
//Purpose: To input the polynomials
//Preconditions: The variable must be reset
//Postconditions: The vector will now contain two or more polynomials stored in
//                linked lists
{
   exponent read;     //Exponent currently entered by the user

   for(int count = 1; count <= pols.length(); ++count)
   {
      cout << "Polynomial #" << count << " (Enter a term [ex. 3x^5 should be inputted as 3 5]): ";
      do
      {
      	cin >> read.number >> read.degree;
         if(read.number == 0)
         	cout << "You can't have zero as a value! Enter again: ";
      }while(read.number == 0);
      while(read.number != 0)
      {
      	addseq(pols[count - 1], read);
         cout << "Enter another term (0 0 to stop): ";
      	cin >> read.number >> read.degree;
      }
   }
}

void addseq(llist<exponent> &list, exponent data)
//Purpose: To take an exponent and add it in its proper place to a linked list
//Preconditions: The data to be inserted must be initialized
//Postconditions: The data will be put in the linked list in order by degree
{
	exponent temp;     //Data to be added to the list

   if(list.length() == 0)
   	list.add(data);
   else
   {
   	list.first();
      temp = list.access();
      while(temp.degree > data.degree && !list.at_end())
      {
         list.next();
      	temp = list.access();
      }
      if(temp.degree == data.degree)
      {
      	data.number += temp.number;
         list.modify(data);
      }
      else if(temp.degree > data.degree)
      	list.add(data);
      else
      	list.before(data);
   }
}

void addsub(apvector<llist<exponent> > &pols, llist<exponent> &result, char dec)
//Purpose: If the use wants to add, it adds the polynomials together into a new
//         polynomial.  If they want to subtract, it negates all polynomial's
//         terms except for the first one entered, and then proceeds to add.
//Preconditions: At least two polynomials must be initialized
//Postconditions: The new polynomial will have the data of the sum or difference
//                of the polynomials into a new linked list.
{
	exponent temp;     //Temporary exponent read in

   if((dec == 's') || (dec == 'S'))
   {
   	for(int a = 1; a <= (pols.length() - 1); ++a)
      {
      	pols[a].first();
         while(!pols[a].null())
         {
         	temp = pols[a].access();
            temp.number = -temp.number;
            pols[a].modify(temp);
            pols[a].next();
         }
		}
   }
   for(int b = 0; b <= (pols.length() - 1); ++b)
   {
      pols[b].first();
      while(!pols[b].null())
      {
      	temp = pols[b].access();
         addseq(result, temp);
         pols[b].next();
      }
   }
}

void multiply(apvector<llist<exponent> >& pols, llist<exponent> &result)
//Purpose: If only two polynomials have been entered, it will multiply the two,
//         and store the product in a new polynomial.
//Preconditions: Only two polynomials may exist.
//Postconditions: The new polynomial will have the data of the product of the
//                two polynomials.
{
	exponent temp1;       //Temporary variable for terms of the first polynomial
   exponent temp2;       //Temporary variable for terms of the second polynomial
   exponent product;     //Temporary variable for the product of two terms

   pols[0].first();
   while(!pols[0].null())
   {
   	temp1 = pols[0].access();
      pols[1].first();
      while(!pols[1].null())
      {
      	temp2 = pols[1].access();
         product.number = temp1.number * temp2.number;
         product.degree = temp1.degree + temp2.degree;
         addseq(result, product);
         pols[1].next();
      }
      pols[0].next();
   }
}

void print(apvector<llist<exponent> > &pols, llist<exponent> &result, char dec)
//Purpose: To echo all user input and print out the resultant polynomial
//Preconditions: The polynomials must have been inputted and then been either
//               added, subtracted or multiplied into a new polynomial.
//Postconditions: Data will be outputted to the screen.
{
   cout << "Results\n-------\n\n";
   cout << "You chose to ";
   switch(dec)
   {
   	case 'm':
      case 'M':  cout << "multiply";
        break;
      case 's':
      case 'S':
      case 'a':
      case 'A':  cout << "add";
   }
   cout << " the following polynomials: \n";
   for(int b = 0; b <= (pols.length() - 1); ++b)
   {
      printpoly(pols[b]);
      cout << endl;
   }
   cout << "\nThe answer comes out to be: \n";
	printpoly(result);
}

void printpoly(llist<exponent> &terms)
//Purpose: To take a linked list of exponents and print it out polynomial style
//Preconditions: The list must be initialized
//Postconditions: Data will be printed to the moniter
{
   exponent temp;        //Term to be printed
   bool zeroterm = 1;    //Keeps track if there is more than one non-zero term
   char del = 8;         //Deletes a trailing plus sign on the if the previous
                         //term, if the end term is 0

	terms.first();
   while(!terms.null())
   {
   	temp = terms.access();
      if(temp.number != 0)
      {
         zeroterm = 0;
      	cout << temp.number;
      	switch(temp.degree)
      	{
      		case 1: cout << "x";
         		break;
         	case 0:
         		break;
         	default: cout << "x^" << temp.degree;;
      	}

      	if(!terms.at_end())
      		cout << " + ";
      }
      terms.next();
   }
   terms.last();
   temp = terms.access();
   if(temp.number == 0 && !zeroterm)
   	cout << del << del << " ";
   if(zeroterm)
   	cout << "0";
}

