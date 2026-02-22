//Jamie Orlando
//CISC 181
//Project #1
//9/14/99

//Desc: Computes the date of Easter from 1900 to 2099

#include<iostream.h>

int main()
{
   int first_year, last_year, day, a, b, c, d, e;
   int march = 1;  //Boolean variable; indicates whether march or april
   char run_again;

   do
   {
      cout << "EASTER PROGRAM" << endl << "==============" << endl << endl
           << "Enter the first year for which you desire to know the date of Easter (1900-2099): ";
      cin >> first_year;
      cout << "Enter the last year: ";
      cin >> last_year;
      if((first_year < 1900) || (first_year > 2099) || (last_year > 2099) || (last_year < 1900) || (first_year > last_year))
      {
        cerr << "Bad Input";
      	return 0;
      }
      for(int year = first_year; year <= last_year; year++)
      {
         a = year % 19;
         b = year % 4;
         c = year % 7;
         d = (19 * a + 24) % 30;
         e = (2 * b + 4 * c + 6 * d + 5) % 7;
         day = (22 + d + e);
         if((year == 1954) || (year == 1981) || (year == 2049) || (year == 2076))
            day -= 7;
         if(day > 31)
         {
            day -= 31;
            march = 0;
         }
         cout << "Easter in " << year << " is, Sunday, ";
         if(march == 1)
            cout << "March ";
         else
            cout << "April ";
         cout << day << endl;
            march = 1;
      }
      cout << "Would you like to run the program again? (y/n): ";
      cin >> run_again;
      cout << "\n\n\n\n";
   }while((run_again == 'Y') || (run_again == 'y'));
   cout << "by Jamie Orlando";

   return 0;
}
