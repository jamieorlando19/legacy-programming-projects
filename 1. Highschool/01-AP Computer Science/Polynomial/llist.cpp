#include"llist.h"

template <class itemType>
llist<itemType>::llist()
: my_initial(0), my_previous(0), my_current(0)
//Create Operation
//Preconditions: The list is in an unknown state
//Postconditions: The list is empty
{
}

template <class itemType>
llist<itemType>::llist(const llist<itemType> &list)
//Copy Constructor
{
   node *probe;
   first();
   while(!null())
   {
      probe = my_current;
   	list.add(probe->data);
      next();
   }
}

template <class itemType>
llist<itemType>::~llist()
//Destructor
{
   first();
   while(!null())
		remove();
}

template <class itemType>
bool llist<itemType>::at_end() const
//At End Operation
//Preconditions: The list is initialized
//Postconditions: Returns true if the pointer is on the last node of the list
{
	return my_current->next == 0;
}

template <class itemType>
bool llist<itemType>::null() const
//Null Operation
//Preconditions: The list is initialized
//Postconditions: Returns true if the pointer has run off the end of the list
{
	return my_current == 0;
}

template <class itemType>
int llist<itemType>::length() const
//Length Operation
//Preconditions: none
//Postconditions: Returns the length of the list
{
   int len = 1;

   first();
   if(my_current == 0)
   	len = 0;
   else
   {
      while(!at_end())
      {
      	next();
         ++len;
      }
   }
   return len;
}


template <class itemType>
itemType llist<itemType>::access() const
//Access Operation
//Preconditions: The current pointer points to a node
//Postconditions: Returns the data element stored in the current node
{
        return my_current->data;
}

template <class itemType>
void llist<itemType>::first()
//First Operation
//Preconditions: The list is initialized
//Postconditions: Move the current pointer to the first node in the list
{
	if(my_current != my_initial)
   {
   	my_current = my_initial;
      my_previous = 0;
   }
}

template <class itemType>
void llist<itemType>::last()
//Last Operation
//Preconditions: The list is initialized
//Postconditions: Move the current pointer to the last node in the list
{
   first();
	while(!at_end())
   	next();
}

template <class itemType>
void llist<itemType>::next()
//Next Operation
//Preconditions: The current pointer points to a node
//Postconditions: Advances the current and previous pointers ahead by one node
{
	my_previous = my_current;
   my_current = my_current->next;
}

template <class itemType>
void llist<itemType>::modify(const itemType &item)
//Modify Operation
//Preconditions: The current pointer points to a node
//Postconditions: Sets the data in the current node to the new data
{
        my_current->data = item;
}

template <class itemType>
void llist<itemType>::add(const itemType &item)
//Add Operation
//Preconditions: The list is initialized
//Postconditions: If at_end is true then it inserts the new data into a new
//                node at the end of the list. Otherwise, inserts the new data
//                into a new node after the current one.  Makes the new node
//                the current one.
{
	node *newnode = new node;
   newnode->data = item;

   if(my_initial == 0)
   {
   	my_initial = newnode;
      my_initial->next = 0;
   }
   else
   {
   	my_previous = my_current;
      newnode->next = my_current->next;
      my_current->next = newnode;
   }
   my_current = newnode;
}

template <class itemType>
void llist<itemType>::before(const itemType &item)
//Before Operation
//Preconditions: The list is initialized
//Postconditions: If current points to the first node in the list, then it
//                inserts the new data into a new node at the beginning of the
//                list.  Otherwise, it inserts the new data into a new node
//                before the current one.  Makes the new node the current one.
{
	node *newnode = new node;
   newnode->data = item;

   if(my_initial == 0)
   {
        my_initial = newnode;
      my_initial->next = 0;
   }
   else
   {
        newnode->next = my_current;
      if(my_previous != 0)
        my_previous->next = newnode;
      else
        my_initial = newnode;
   }
   my_current = newnode;
}



template <class itemType>
void llist<itemType>::remove()
//Remove Operation
//Preconditions: The current pointer points to a node
//Postconditions: Removes the current node from the list, makes the node
//                following this node the current one.
{
   if(!null())
   {
   	node *garbage = my_current;
      if(my_current == my_initial)
      	my_initial = my_current->next;
      else
      	my_previous->next = my_current->next;
      my_current = my_current->next;
      delete garbage;
   }
}
