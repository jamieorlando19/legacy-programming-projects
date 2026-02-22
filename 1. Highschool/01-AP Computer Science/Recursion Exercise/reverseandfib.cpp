#include<iostream.h>
#include<conio.h>

int reversedigits(int num, int &c);
int fibonacci(int nterm);

int main()
{
	int n2init, n2final, count = 1;
   int n4init, n4final;
   cout << "#2 Input in an integer to be reversed: ";
   cin >> n2init;
	n2final = reversedigits(n2init, count);
   cout << "The number reversed is: " << n2final << endl << endl;
   cout << "#4 Type in the nth term of the Fibonacci sequence to calculate: ";
   cin >> n4init;
   n4final = fibonacci(n4init);
   cout << "The #" << n4init << " term is: " << n4final;

   getch();
   return 0;
}


int reversedigits(int num, int &c)
{
   if(num != 0)
   {
      cout << num % 10;
      c *= 10;
      return reversedigits(num/10, c)*c;
   }
}

int fibonacci(int nterm)
{
   if(nterm == 1 || nterm == 2)
   	return 1;
   else
   	return fibonacci(nterm - 1) + fibonacci(nterm - 2);
}


