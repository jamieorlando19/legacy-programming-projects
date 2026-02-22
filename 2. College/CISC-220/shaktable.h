//Jamie Orlando
//Class Header File for type "shaktable"

#ifndef SHAKTABLE_H

#include<iostream>
#include<list>
#include<string.h>
#include<math.h>
using namespace std;

//Special String Type For List STL
typedef struct Cstring
{ char ptr[32]; } str;

//Field Name
struct fname
{
   str name;
   int flag;
};

//Field Entry
struct fieldentry
{
   int int_entry;
   str char_entry;
   float float_entry;
   int filled;
   fieldentry *collision, *bptr, *fptr;
   fieldentry();
}

//Binary Node
struct binnode
{
   fieldentry *field_ptr;
   binnode *left, *right, *repeat;
   binnode();
}

const int max_column = 101;

class shaktable
{
   public:
      shaktable(str &, list<fname> &);

      str get_name();
      list<fname> get_fields();
      void insert(list<fieldentry> &);
      void print_all();
      void print_where(str &, fieldentry &);
      void join_all(shaktable &);
      void join_where(shaktable &, str &, str &, str &, fieldentry &);
      void order_by(str &);
   private:
      void recursive_print_all(binnode *);
      void recursive_join_all(binnode *, binnode *, list<fname> &);
      void recursive_join_all2(binnode *, fieldentry &, list<fname> &);
      void recursive_order_by(binnode *, binnode *&, fieldentry *, int &, int, int, int);
      void print_row(fieldentry &, list<fname> &);
      void tree_insert(binnode *&, fieldentry *&, int, int &);
      int hash_convert(fieldentry &, int);

      str table_name;
      list<fname> fieldnames;
      fieldentry **table_data;
      binnode **table_index;
};
