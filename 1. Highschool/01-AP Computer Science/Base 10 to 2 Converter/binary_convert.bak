#include<iostream.h>
#include<conio.h>
#include<fstream.h>

const int MAX_SIZE = 32;

int input();
void convert(int base10, int algorithm[]);
void output(int base10, int algorithm[], ofstream &output_file);

int main()
{
	int num, digits[MAX_SIZE];
   char run;
   ofstream out_file;
   out_file.open("binconvert.dat");

   do
   {
   	for(int k = 0; k < MAX_SIZE; ++k)
      	digits[k] = -1;

      clrscr();

      num = input();
      convert(num, digits);
      output(num, digits, out_file);

      cout << "\nDo you wish to run this program again? (y/n): ";
      cin >> run;
   }while (run != 'n');

   out_file.close();
   clrscr();
   cout << "Thank You!";

   getch();
   return 0;
}

int input()
{
	int temp;

   cout << "Base 10 to Base 2 Converter\n";
   cout << "===========================\n\n";
   cout << "Type in a value for the base 10 number you wish to convert: ";
   cin >> temp;
   return temp;
}

void convert(int base10, int algorithm[])
{
	int temp = base10 * 2, count = 0;

	while (temp > 0)
   {
   	temp = temp / 2;
      algorithm[count] = temp % 2;
      ++count;
   }

   algorithm[count - 1] = -1;
}

void output(int base10, int algorithm[], ofstream &output_file)
{
	cout << "\nYour initial number was: " << base10 << endl;
   cout << "The number converted is: ";

   output_file << base10 << " = ";
   for (int p = (MAX_SIZE - 1); p >= 0; --p)
   {
   	if(algorithm[p] >= 0)
      {
      	cout << algorithm[p];
         output_file <<algorithm[p];
      }

   }
   output_file << endl;
}
