//Jamie Orlando
//Class Implemenation File: MORSECODE.CPP

#include"morsecode.h"

morsecode::morsecode()
/*Create Operation*/
//Purpose: To create a binary tree with a data fields which hold letters,
//         corresponding to a certain place in the tree which represents its
//         morsecode equivalent.
{
	int h = 4;    //Height of the tree to be created, not counting the root
   initial = new node;
   current = initial;
   morseletters.open("morsecode.dat");
   if(!morseletters)
   {
   	cerr << "The file could not be opened.\n";
      cerr << "Make sure the file 'morsecode.dat' is in this directory.";
      getch();
      abort();
   }
	build(h, current);
   morseletters.close();
}

char morsecode::convert(const apstring &code)
/*Convert Operation*/
//Purpose: To take a string parameter which holds a morsecode value and to
//         convert it to an english letter.

{
   current = initial;
   if(code.length() > 4)
   {
   	cerr << endl << code << " is not a valid uppercase morsecode letter.";
      getch();
      abort();
   }
	for(int c = 0; c <= code.length() - 1; ++c)
   {
   	if(code[c] == '.')
      	current = current->dot;
      else if(code[c] == '-')
      	current = current->dash;
      else
      {
      	cerr << endl << code << " contains invalid characters.";
         getch();
         abort();
      }
	}
   if(current->letter != '?')
   	return current->letter;
   else
   {
   	cerr << endl << code << " is not a valid uppercase morsecode letter.";
      getch();
      abort();
   }
}

void morsecode::build(int h, node *root)
/*Build Operation*/
//Purpose: (see Create Operation)
{
   morseletters.get(root->letter);
	if(h > 0)
   {
   	node *newleft = new node;
   	root->dot = newleft;
   	node *newright = new node;
   	root->dash = newright;
      build(h - 1, root->dot);
      build(h - 1, root->dash);
   }
   else
   {
   	root->dot = 0;
      root->dash = 0;
   }
}

