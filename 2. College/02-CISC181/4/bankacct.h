#ifndef BANKACCT_H
#define BANKACCT_H

#include<iostream.h>

const int NAME_LEN = 20;
struct Name
{
   char first[NAME_LEN + 1];
   char last[NAME_LEN + 1];
};

class bankacct
{
      friend istream& operator>>(istream &, bankacct &);                  //Overloaded input operator   
      friend ostream& operator<<(ostream &, const bankacct &);            //Overloaded output operator
   public:
      bankacct(const char * = "", const char * = "", int = 0, double = 0);//Constructor
      Name getname() const;                                               //Name getter
      int getacctno() const;                                              //Acct # getter
      double getcurrentbal() const;                                       //Balance getter
      void deposit(double);                                               //Deposits amount
      double withdraw(double);                                            //Withdraw amount
      void monthlyint(double);                                            //Calculates monthly interest
   private:
      Name name;
      int acctno;
      double balance;
};

#endif
