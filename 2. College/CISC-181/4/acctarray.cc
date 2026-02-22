#include<stream.h>
#include<string.h>
#include"bankacct.h"
#include"acctarray.h"

//Constructor-------------------------------------------------

acctarray::acctarray(int size, double annintrt)
{
   currentarraysize = size;
   annualinterestrate = annintrt;
   acct = new bankacct[size];
   noaccts = 0;
}

//Destructor-------------------------------------------------

acctarray::~acctarray() {delete [] acct;}

//Processes Transaction File---------------------------------

void acctarray::processtransactionfile(char filename[])
{
   ifstream fin(filename);
   char choice[9];
   int acno;
   double amt;

   while(fin >> choice >> acno >> amt)
   {
      for(int a = 0; acct[a].getacctno() != acno; ++a);

      if(strcmp(choice, "deposit") == 0)
         acct[a].deposit(amt);
      else
         acct[a].withdraw(amt);
   }
}

//Adds monthly interest--------------------------------------

void acctarray::calcmonthlyint()
{
   for(int q = 0; q < noaccts; ++q)
      acct[q].monthlyint(annualinterestrate);
}

//Swap function (independent)--------------------------------

void swap(bankacct &a, bankacct &b) {bankacct t = a; a = b; b = t;}

//Sort by name-----------------------------------------------

void acctarray::sortbyname()
{
   int i, j, min;
   for(i = 0; i < noaccts; ++i)
   {
      min = i;
      for(j = i + 1; j < noaccts; ++j)
         if(namecmp(acct[j].getname(), acct[min].getname()) < 0) min = j;

      swap(acct[i], acct[min]);
   }
}

//Name compare------------------------------------------------

int acctarray::namecmp(Name name1, Name name2)
{
   if(strcmp(name1.last, name2.last) == 0)
   {
      if(strcmp(name1.first, name2.first) == 0)
         return 0;
      else
         return strcmp(name1.first, name2.first); 
   }
   else
      return strcmp(name1.last, name2.last);
}

//Sort by account number-------------------------------------

void acctarray::sortbyacctno()
{
   int i, j, min;
   for(i = 0; i <noaccts; ++i)
   {
      min = i;
      for(j = i + 1; j < noaccts; ++j)
         if(acct[j].getacctno() < acct[min].getacctno()) min = j;
 
      swap(acct[i], acct[min]);
   }   
}

//Account Adder----------------------------------------------

void acctarray::addacct(const bankacct &a)
{
   if(noaccts == currentarraysize)
   {
      bankacct *newacct;
      newacct = new bankacct[currentarraysize + 10];
     
      for(int p = 0; p < currentarraysize; ++p)
         newacct[p] = acct[p];

      delete [] acct;
      acct = newacct;
      currentarraysize += 10;
   }

   acct[noaccts] = a;
   ++noaccts;
}

//Overloaded input operator----------------------------------

istream& operator>>(istream &input, acctarray &a)
{
   char dummy[25];
   input >> dummy >> dummy;
   input >> a.annualinterestrate;
   input >> dummy;
   a.annualinterestrate *= .01;
   
   bankacct nextacct;

   while(input >> nextacct)
      a.addacct(nextacct);

   return input;
}

//Overloaded output operator---------------------------------

ostream& operator<<(ostream &output, const acctarray &a)
{
   for(int w = 0; w < a.noaccts; ++w)
      output << a.acct[w] << endl;

   return output;
}
