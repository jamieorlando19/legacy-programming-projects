#ifndef ACCTARRAY_H
#define ACCTARRAY_H

#include"bankacct.h"

class acctarray
{
      friend istream& operator>>(istream &, acctarray &);       //Overloaded input operator
      friend ostream& operator<<(ostream &, const acctarray &); //Overloaded output operator
   public:
      acctarray(int = 10, double =  0.06);                      //Constructor
      ~acctarray();                                             //Destructor
      void processtransactionfile(char []);                     //Processes transaction file
      void calcmonthlyint();                                    //Adds monthly interest
      void sortbyname();                                        //Sorts by name
      int namecmp(Name , Name );                                //Name compare
      void sortbyacctno();                                      //Sorts by account number 
   private:
      bankacct *acct;
      int noaccts, currentarraysize;
      double annualinterestrate;
      void addacct(const bankacct &);                           //Account adder
};

#endif
