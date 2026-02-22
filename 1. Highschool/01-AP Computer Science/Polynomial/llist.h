//Jamie Orlando
//Class Declaration File: LLIST.H

#ifndef LLIST_H

template <class itemType> class llist
{
        public:

   //Constructors/Destructor

   llist();
   llist(const llist<itemType> &list);
   ~llist();

   //Accessors

   bool at_end() const;
   bool null() const;
   int length() const;
   itemType access() const;

   //Modifiers

   void first();
   void last();
   void next();
   void modify(const itemType &item);
   void add(const itemType &item);
   void before(const itemType &item);
   void remove();

   private:

   //Data Members

   struct node
   {
   	itemType data;
      node *next;
   };
   node *my_initial, *my_previous, *my_current;
};

#define LLIST_H
#endif


