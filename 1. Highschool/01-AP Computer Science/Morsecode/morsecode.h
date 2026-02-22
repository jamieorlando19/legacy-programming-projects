//Jamie Orlando
//Class Declaration File: MORSECODE.H

#ifndef MORSECODE_H
#define MORSECODE_H
#include<stdlib.h>
#include<conio.h>
#include<fstream.h>
#include"apstring.cpp"

class morsecode
{
	public:

   morsecode();                              //Constructor
	char convert(const apstring &code);       //Accessor

   private:

   //Data Members

   struct node
   {
		char letter;
      node *dot, *dash;
   };
   node *initial, *current;
   ifstream morseletters;
   void build(int h, node *root);            //Private recursive function which
                                             //coincides with the constructor
};

#include"morsecode.cpp"
#endif
