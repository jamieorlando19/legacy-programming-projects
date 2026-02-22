#include<iostream.h>
#include<iomanip.h>
#include<string.h>
#include"bankacct.h"

//Constructor----------------------------------------------------------

bankacct::bankacct(const char *fn, const char *ln, int an, double bal)
{
   strcpy(name.first, fn);
   strcpy(name.last, ln);
   acctno = an;
   balance = bal;
}

//Name getter----------------------------------------------------------

Name bankacct::getname() const
{return name;}

//Acct # getter--------------------------------------------------------

int bankacct::getacctno() const
{return acctno;}

//Balance getter------------------------------------------------------

double bankacct::getcurrentbal() const
{return balance;}

//Deposit amount------------------------------------------------------

void bankacct::deposit(double amt)
{
   if(amt >= 0)
      balance += amt;
}

//Withdraw amount-----------------------------------------------------

double bankacct::withdraw(double amt)
{
   if(amt >= 0 && amt <= balance)
      balance -= amt;
   else
      amt = 0;

   return amt;
}

//Calculates monthly interest-----------------------------------------

void bankacct::monthlyint(double annualrate)
{
   double interest = (balance * annualrate) / 12;
   balance += interest;   
}

//Overloaded input operator-------------------------------------------

istream& operator>>(istream &input, bankacct &a)
{
   input >> a.name.first >> a.name.last >> a.acctno >> a.balance;
   return input;
}

//Overloaded output operator------------------------------------------

ostream& operator<<(ostream &output, const bankacct &a)
{
   output << setprecision(2) <<setiosflags(ios::fixed);
   output << a.name.first << " " << a.name.last << " " << a.acctno << " " << a.balance;
   return output;
}
