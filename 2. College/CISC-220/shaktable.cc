shaktable.h
//Class Implementation File for type "shaktable"

#include"shaktable.h"

//***Constructor (for type "fieldentry")***
//Postcondition:  filled, collision, bptr and fptr are assigned defaults
fieldentry::fieldentry()
{
   filled = 0;
   collision = bptr = fptr = NULL;
} 

//***Constructor (for type "binnode")***
//Postcondition: field_ptr, left, right and repeat are assigned to NULL
binnode::binnode()
{ 
   field_ptr = NULL;
   left = right = repeat = NULL; 
}

//***Constructor***
//Postconditions: Values for table_name and fieldnames are passed to the
//                function.  table_index is NULL.  Space is allocated for
//                table_data** so it can be accessed like a 2d array.
//                "TABLE CREATED" is outputted.
shaktable::shaktable(str &name, list<fname> &fnames)
{
   strcpy(table_name.ptr, name.ptr);
   fieldnames = fnames;   
   table_index = NULL;
   table_data =  (fieldentry**)malloc(sizeof(fieldentry*)*fieldnames.size()); 
   for(int x = 0; x < fieldnames.size(); x++)
      table_data[x] = (fieldentry*)malloc(sizeof(fieldentry)*max_column);
   cout << "TABLE CREATED" << endl;
}

//***Table Name Accessor***
//Preconditions: table_name has a value
//Postconditions: table_name is returned
str shaktable::get_name()
{ return table_name; }

//***Field Names Accessor***
//Preconditions: fieldnames has a value
//Postconditions: fieldnames is returned
list<fname> shaktable::get_fields()
{ return fieldnames; }

//***Table Insert***
//Preconditions: Constructor must have been called, data in row must
//               correspond with the types in fieldnames
//Postconditions: A row is inserted into table_data, and indexed with a 
//                new entry in table_index.  "ROW INSERTED" is outputted
void shaktable::insert(list<fieldentry> &row)
{
   list<fname>::iterator cur_field = fieldnames.begin();
   list<fieldentry>::iterator cur_entry = row.begin();
   fieldentry *previous = NULL, *current;
   fieldentry data;
   int index, dummy = 0;

   for(int count = 0; count < fieldnames.size(); count++, cur_field++, cur_entry++)
   {
      if(count != 0) data.bptr = previous;
         
      switch(cur_field->flag)
      {
         case 0: data.int_entry = cur_entry->int_entry;
            break;
         case 1: data.char_entry = cur_entry->char_entry;
            break;
         case 2: data.float_entry = cur_entry->float_entry;
      }
      index = hash_convert(data, cur_field->flag);     //Finds an index for the hashtable
      data.filled = 1;
      current = &table_data[count][index];
      if(table_data[count][index].filled)
      {
         while(current->collision != NULL)
            current = current->collision;
         current->collision = new fieldentry;
         current = current->collision;
      }
      *current = data;
      if(count == 0)
         tree_insert(table_index, current, cur_field->flag, dummy);
      if(count != 0) previous->fptr = current;
      previous = current;
   }
   cout << "ROW INSERTED" << endl;
}

//***Print All***
//Postconditions: All data in the table is outputted.  If there is no
//                data, "NO SUCH RECORD" is printed
void shaktable::print_all()
{
   if(table_index == NULL)
      cout << "NO SUCH RECORD" << endl;
   else
      recursive_print_all(table_index);
}

//***Print Where***
//Preconditions: fld is a str in the list fieldnames
//Postconditions: If the corresponding value in key is found in
//                table_data, the rows are printed.  If not, "NO SUCH
//                RECORD" is outputted.
void shaktable::print_where(str &fld, fieldentry &key)
{
   list<fname>::iterator cur_name = fieldnames.begin();

   int pos = 0;
   for( ; (strcmp((cur_name->name).ptr, fld.ptr) != 0); cur_name++, pos++);

   if(!table_data[pos][hash_convert(key, cur_name->flag)].filled)
      cout << "NO SUCH RECORD" << endl;
   else
   {
      fieldentry *current = &table_data[pos][hash_convert(key, cur_name->flag)];
      while(current != NULL)
      {
         print_row(*current, fieldnames);
         cout << endl;
         current = current->collision;
      }
   }
}

//***Recursive Print All***
//Postconditions:  An inorder traversal of the tree pointed to by
//                *sub_root is completed, outputting the corresponding rows 
void shaktable::recursive_print_all(binnode *sub_root)
{
   if(sub_root != NULL)
   {
      recursive_print_all(sub_root->left); 
      print_row(*sub_root->field_ptr, fieldnames);
      cout << endl;     
      recursive_print_all(sub_root->repeat);
      recursive_print_all(sub_root->right);
   }
}

//***Join All***
//Postconditions: Every row of the table is printed; for every row of
//                table printed, all of table2 is printed. If table
//                or table2 is empty, "NO SUCH RECORD" is printed.
void shaktable::join_all(shaktable &table2)
{
   if(table_index == NULL || table2.table_index == NULL)
      cout << "NO SUCH RECORD" << endl;
   else
      recursive_join_all(table_index, table2.table_index, table2.fieldnames);
}

//***Join Where***
//Preconditions: fld1 and fld3 are fields in table. fld2 is a field in table2
//Postconditions: All values are joined where fld1 = fld2 and fld3 = key,
//                as in the join all function. If values don't exist, "NO
//                SUCH RECORDS" is printed.
void shaktable::join_where(shaktable &table2, str &fld1, str &fld2, str &fld3, fieldentry &key)
{
   list<fname>::iterator cur_name;
   int pos1 = 0, pos2 = 0, pos3 = 0, pos1_flag, pos3_flag;

   cur_name = fieldnames.begin();
   for( ; (strcmp((cur_name->name).ptr, fld1.ptr) != 0); cur_name++, pos1++);
   pos1_flag = cur_name->flag;
   cur_name = (table2.fieldnames).begin();
   for( ; (strcmp((cur_name->name).ptr, fld2.ptr) != 0); cur_name++, pos2++);
   cur_name = fieldnames.begin();
   for( ; (strcmp((cur_name->name).ptr, fld3.ptr) != 0); cur_name++, pos3++); 
   pos3_flag = cur_name->flag;

   if(!table_data[pos3][hash_convert(key, pos3_flag)].filled)
      cout << "NO SUCH RECORD" << endl;
   else
   {
      list<fieldentry*> found_ptr;
      list<fieldentry*>::iterator cur_found_ptr;
      fieldentry *current = &table_data[pos3][hash_convert(key, cur_name->flag)], *current2;
      while(current != NULL)
      {
         found_ptr.push_back(current);
         current = current->collision;
      }
      int data_exists = 0;
      cur_found_ptr = found_ptr.begin();
    
      while(cur_found_ptr != found_ptr.end())
      {
         current = *cur_found_ptr;
         while(current->bptr != NULL) current = current->bptr;
         for(int a = 0; a < pos1; a++) current = current->fptr;
         if((table2.table_data)[pos2][hash_convert(*current, pos1_flag)].filled)
         {
            
            current2 = &(table2.table_data)[pos2][hash_convert(*current, pos1_flag)];
            while(current2 != NULL)
            {
               data_exists = 1;
               print_row(*current, fieldnames);
               cout << ", ";
               print_row(*current2, table2.fieldnames);
               cout << endl;
               current2 = current2->collision;
            }
         }
         cur_found_ptr++;
      }
      if(!data_exists) cout << "NO SUCH RECORD" << endl;
   }
}

//***Recursive Join All pt1***
//Postconditions: An inorder traversal of the tree pointed to by
//                *sub_root1 is completed, outputting the corresponding
//                rows. For each row printed, every record from the tree
//                pointed to by *sub_root2 is joined to it. 
void shaktable::recursive_join_all(binnode *sub_root1, binnode *sub_root2, list<fname> &fname2)
{
   if(sub_root1 != NULL)
   {
      recursive_join_all(sub_root1->left, sub_root2, fname2);
      recursive_join_all2(sub_root2, *sub_root1->field_ptr, fname2);      
      recursive_join_all(sub_root1->repeat, sub_root2, fname2);
      recursive_join_all(sub_root1->right, sub_root2, fname2);
   }  
}

//***Recursive Join All pt2***
//Postconditions: An inorder traversal of the tree pointed to by *sub_root
//                is completed, outputting one joined row passed to the
//                function for every entry printed.
void shaktable::recursive_join_all2(binnode *sub_root, fieldentry &joined, list<fname> &fname2)
{
   if(sub_root != NULL)
   {
      recursive_join_all2(sub_root->left, joined, fname2);
      print_row(joined, fieldnames);
      cout << ", ";
      print_row(*sub_root->field_ptr, fname2);
      cout << endl;
      recursive_join_all2(sub_root->repeat, joined, fname2);
      recursive_join_all2(sub_root->right, joined, fname2);
   }
}

//***Order By***
//Postconditions: All entries of the table are printed in order by the
//                field, fld and key comparisons are printed. If there is
//                no data in the table, "NO SUCH RECORD" is printed.
void shaktable::order_by(str &fld)
{
   if(table_index == NULL)
      cout << "NO SUCH RECORD" << endl;
   else
   {
      binnode *temp_sort_tree = NULL;
      int comparisons = 0, pos = 0, flag, dummy;
      fieldentry *fdummy;
      list<fname>::iterator cur_name = fieldnames.begin();

      for( ; (strcmp((cur_name->name).ptr, fld.ptr) != 0); cur_name++, pos++);
      flag = cur_name->flag;
      if(pos != 0)
      {
         recursive_order_by(table_index, temp_sort_tree, fdummy, comparisons, dummy, pos, flag);
         recursive_print_all(temp_sort_tree);
      }
      else
         print_all();
      cout << "KEY COMPARISONS: " << comparisons << endl;
   }
}

//***Recursive Order By***
//Postconditions: Creates an ordered index tree, rooted on *new_root, doing
//                a traversal of the tree pointed to by *sub_root and
//                using the column position, pos.
void shaktable::recursive_order_by(binnode *sub_root, binnode *& new_root, fieldentry *current, int &comparisons, int addon, int pos, int flag)
{
   if(sub_root != NULL)
   {
      recursive_order_by(sub_root->left, new_root, current, comparisons, addon, pos, flag);
      current = sub_root->field_ptr;
      for(int a = 0; a < pos; a++) current = current->fptr;
      addon = 0;
      tree_insert(new_root, current, flag, addon);   
      comparisons += addon;
      recursive_order_by(sub_root->repeat, new_root, current, comparisons, addon, pos, flag);
      recursive_order_by(sub_root->right, new_root, current, comparisons, addon, pos, flag);
   }
}

//***Print row***
//Preconditions: list_fields corresponds with cur_field (and the entire row)
//Postconditions: Each field of the row which cur_field is in is printed
void shaktable::print_row(fieldentry &cur_field, list<fname> &list_fields)
{
   fieldentry *current = &cur_field;
   list<fname>::iterator cur_name = list_fields.begin();

   while(current->bptr != NULL) current = current->bptr;
   while(current != NULL)
   {
      switch(cur_name->flag)
      {
         case 0:  cout << current->int_entry;
            break;
         case 1:  cout << (current->char_entry).ptr;
            break;
         case 2:  cout << current->float_entry;
      }
      if(current->fptr != NULL) cout << ", ";
      cur_name++;
      current = current->fptr;
   }
}


//***Tree Insert***
//Postconditions: A pointer to current is placed in an ordered binary tree
//                pointed to by *sub_root.
void shaktable::tree_insert(binnode *&sub_root, fieldentry *&current, int flag, int &comparison)
{
   if(sub_root == NULL)
   {
      sub_root = new binnode;
      sub_root->field_ptr = current;
   }
   else
   {
      if(flag == 0)
      {
         if(current->int_entry < (sub_root->field_ptr)->int_entry) 
            tree_insert(sub_root->left, current, flag, ++comparison);
         else if(current->int_entry > (sub_root->field_ptr)->int_entry)
            tree_insert(sub_root->right, current, flag, ++comparison);
         else
            tree_insert(sub_root->repeat, current, flag, ++comparison);
      }
      else if(flag == 1)
      {
         if(strcmp((current->char_entry).ptr, ((sub_root->field_ptr)->char_entry).ptr) < 0)
            tree_insert(sub_root->left, current, flag, ++comparison);
         else if(strcmp((current->char_entry).ptr, ((sub_root->field_ptr)->char_entry).ptr) > 0)
            tree_insert(sub_root->right, current, flag, ++comparison);
         else
            tree_insert(sub_root->repeat, current, flag, ++comparison);
      }
      else
      {
         if(current->float_entry < (sub_root->field_ptr)->float_entry)
            tree_insert(sub_root->left, current, flag, ++comparison);
         else if(current->float_entry > (sub_root->field_ptr)->float_entry)   
            tree_insert(sub_root->right, current, flag, ++comparison);
         else
            tree_insert(sub_root->repeat, current, flag, ++comparison);
      }
   }
}

//***Hash Convert***
//Postconditions: The coded hash-index of field is returned.
int shaktable::hash_convert(fieldentry &field, int flag)
{
   if(flag == 0)
      return abs((field.int_entry * 2013) % max_column);
   else if(flag == 1)
   {
      int index = 0;
      for(int a = 5; a < strlen((field.char_entry).ptr) + 5; a++)
         index += (((field.char_entry).ptr)[a - 5] * a);
      return index %= max_column;
   }
   else
      return abs(int((floor(field.float_entry * 2013))) % max_column);
}
