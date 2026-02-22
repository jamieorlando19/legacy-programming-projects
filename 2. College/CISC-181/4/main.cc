#include<stream.h>
#include"acctarray.h"

main()
{
   ifstream fin1("atm-acct-data"), fin2("passbook-acct-data");
   acctarray atm, passbook;

   cout << "ATM" << endl;
   fin1 >> atm;
   cout << atm << endl;
   atm.processtransactionfile("atm-transactions");
   atm.calcmonthlyint();
   atm.sortbyname();
   cout << atm << endl;
   atm.sortbyacctno();
   cout << atm << endl << endl;
   cout << "PASSBOOK" << endl;
   fin2 >> passbook;
   cout << passbook << endl;
   passbook.processtransactionfile("passbook-transactions");
   passbook.calcmonthlyint();
   passbook.sortbyname();
   cout << passbook << endl;
   passbook.sortbyacctno();
   cout << passbook;
}
