//SHAKTI v2.0 (written by Jamie Orlando)
//
//Shakti takes the following commands:
//CREATE TABLE x ( y AS TYPE {, y AS TYPE});
//INSERT INTO x VALUES (y {,y});
//SELECT * FROM x {WHERE y = Value};
//SELECT * FROM x,y {WHERE x.field1 = x.field2 AND x.field3 = Value};
//SELECT * FROM x ORDER BY z;
//
//TYPE implies INT, CHAR or FLOAT
//
//*Note: A common postcondition of all int functions is that a "status"
//      integer is returned indicating if it ran successfully.


#include"shaktable.h"

int create_table(list<shaktable> &, char []);
int insert_into(list<shaktable> &, char []);
int select_from(list<shaktable> &, char []);
int table_join(list<shaktable> &, char []);
int order_by(shaktable &, char []);
int int_converter(const char [], int &);
int char_converter(char []);
int float_converter(const char [], float &);
void erase_line(char []);

int main()
{
   cout << "SHAKTI 2.0: written by Jamie Orlando" << endl
        << "*TO QUIT, enter an EOF character or press <ctrl-c>*" << endl << endl;
   list<shaktable> table_list;     //List of tables
   char command[32];
   int correct_syntax;

   while(cin >> command)
   {
      correct_syntax = 0;
      if(strcmp(command, "CREATE") == 0)
         correct_syntax = create_table(table_list, command);      
      else if(strcmp(command, "INSERT") == 0 && table_list.size() != 0)
         correct_syntax = insert_into(table_list, command);
      else if(strcmp(command, "SELECT") == 0 && table_list.size() != 0)
         correct_syntax = select_from(table_list, command);
      
      if(!correct_syntax) erase_line(command);
   }

   return 0;
}

//***Create Table***
//Postconditions: table_list has a shaktable added on with a name and fieldnames
int create_table(list<shaktable> &table_list, char piece[])
{
   str name;
   list<fname> fields;
   fname complete_fname;
   str field_name;
   int field_flag, count = 1, at_end = 0;
   while(!at_end && cin >> piece)
   {
      if(count < 3)
      {
         if(count == 1)
         {
            if(strcmp(piece, "TABLE") != 0)
               return 0;
         }
         else if(count == 2)
         {
            strcpy(name.ptr, piece);
            char paren;
            cin >> paren;
            if(paren != '(') return 0;
         }
         count++;
      }        
      else
      {
         strcpy(field_name.ptr, piece);
         
         cin >> piece;
         if(strcmp(piece, "AS") != 0) return 0;
         
         cin >> piece;
         if(piece[strlen(piece) - 1] == ',') 
            piece[strlen(piece) - 1] = '\0';
         else if(piece[strlen(piece) - 1] == ';' && piece[strlen(piece) - 2] == ')')
         {
            piece[strlen(piece) - 2] = '\0';
            at_end = 1;
         }

         if(strcmp(piece, "INT") == 0) field_flag = 0;
         else if(strcmp(piece, "CHAR") == 0) field_flag = 1;
         else if(strcmp(piece, "FLOAT") == 0) field_flag = 2;
         else return 0;

         complete_fname.name = field_name;
         complete_fname.flag = field_flag;
         fields.push_back(complete_fname);
      }
   }
   shaktable new_table(name, fields);
   table_list.push_back(new_table);

   return 1;
}

//***INSERT INTO***
//Preconditions: table_list must have at least one shaktable in it
//Postconditions: A row is inserted into the table that the user specifies
int insert_into(list<shaktable> &table_list, char piece[])
{
   int count = 1, at_end = 0;
   list<shaktable>::iterator cur_table = table_list.begin();
   list<fieldentry> entry_list;
   fieldentry entry;
   list<fname> fields;
   list<fname>::iterator cur_field;
   int convert_success;
 
   while(!at_end && cin >> piece)
   {
      if(count < 4)
      {
         if(count == 1)
         {
            if(strcmp(piece, "INTO") != 0)
               return 0;
         }
         else if(count == 2)
         {
            int found_record = 0;
            while(!found_record && cur_table != table_list.end())
            {
               if(strcmp(piece, (cur_table->get_name()).ptr) == 0)
                  found_record = 1;
               cur_table++;
            }
            if(found_record == 0) return 0;

            cur_table--;
            fields = cur_table->get_fields();
            cur_field = fields.begin();
         }
         if(count == 3)
         {
            if(strcmp(piece, "VALUES") != 0)
               return 0;
            char paren;
            cin >> paren;
            if (paren != '(') return 0;
         }
      }
      else
      {
         if(cur_field->flag == 1)
         {
            convert_success = char_converter(piece);
            if(!convert_success) return 0; 
         }
                 
         if(piece[strlen(piece) - 1] == ',' && (fields.size() + 3) != count)
            piece[strlen(piece) - 1] = '\0';
         else if(piece[strlen(piece) - 1] == ';' && piece[strlen(piece) - 2] == ')' && (fields.size() + 3) == count)
         {
            piece[strlen(piece) - 2] = '\0';
            at_end = 1;
         }
         else return 0;
            
            
         if(cur_field->flag == 0)
            convert_success = int_converter(piece, entry.int_entry);
         else if(cur_field->flag == 1)
            strcpy((entry.char_entry).ptr, piece);
         else
            convert_success = float_converter(piece, entry.float_entry);

         if(convert_success == 0) return 0;
        
         entry_list.push_back(entry);
         cur_field++;
      }
      count++;
   }
   cur_table->insert(entry_list);

   return 1;
}

//***SELECT FROM***
//Preconditions: create_table and insert_into must have been called at
//               least once successfully
//Postconditions: Certain or all values of a specific table will be
//                printed, depending on the commands entered by the user
int select_from(list<shaktable> &table_list, char piece[])
{
   int at_end = 0, count = 1;
   list<shaktable>::iterator cur_table = table_list.begin();
   list<fname> fields;  
   list<fname>::iterator cur_field;  
   fieldentry search_entry;
   int convert_success;

   while(!at_end && cin >> piece)  
   {
      if(count < 4)
      {
         if(count == 1)
         {
            if(strcmp(piece, "*") != 0)
               return 0;
         }
         else if(count == 2)
         {
            if(strcmp(piece, "FROM") != 0)
               return 0;
         }
         else if(count == 3)
         {
            int found_record = 0;
            if(piece[strlen(piece) - 1] == ';')
            {
               piece[strlen(piece) - 1] = '\0';
               at_end = 1;
            }
            else if(piece[strlen(piece) - 1] == ',')
            {
               piece[strlen(piece) - 1] = '\0';
               return table_join(table_list, piece); 
            }
            while(!found_record && cur_table != table_list.end())
            {
               if(strcmp(piece, (cur_table->get_name()).ptr) == 0)
                  found_record = 1;
               cur_table++;
            }
            if(found_record == 0)
            {
               piece[strlen(piece)] = ';';     //So erase_line recognizes the end of line
               return 0;
            }
            cur_table--;
         }
      }
      else
      {
         if(count == 4)
         {
            if(strcmp(piece, "WHERE") != 0)
            {
               if(strcmp(piece, "ORDER") == 0)
                  return order_by(*cur_table, piece);
               return 0; 
            }
         }
         else if(count == 5)
         {
            fields = cur_table->get_fields();
            int found_field = 0;
            cur_field = fields.begin();
            while(!found_field && cur_field != fields.end())
            {
               if(strcmp(piece, (cur_field->name).ptr) == 0)
                  found_field = 1;
               cur_field++;
            }
            if(found_field == 0) return 0;
            cur_field--;  
         }
         else if(count == 6)
         {
            if(strcmp(piece, "=") != 0)
               return 0;
         }
         else
         {
            if(cur_field->flag == 1)
            {
               convert_success = char_converter(piece);
               if(!convert_success) return 0; 
            }
               
            if(piece[strlen(piece) - 1] != ';') return 0;   
            piece[strlen(piece) -1] = '\0';
            

            if(cur_field->flag == 0)
               convert_success = int_converter(piece, search_entry.int_entry);
            else if(cur_field->flag == 1)
               strcpy((search_entry.char_entry).ptr, piece);
            else
               convert_success = float_converter(piece, search_entry.float_entry);

            if(!convert_success) return 0;
      
            at_end = 1;         
         }
               
      }
   count++;
   }

   if(count == 4)
      cur_table->print_all();
   else
      cur_table->print_where(cur_field->name, search_entry);

   return 1;
}

//***TABLE JOIN***
//Preconditions:
//Postconditions:
int table_join(list<shaktable> &table_list, char piece[])
{
   int at_end = 0, count = 1;
   list<shaktable>::iterator cur_table1 = table_list.begin(), 
                             cur_table2 = table_list.begin();
   list<fname>::iterator cur_fields;
   char table1[32], table2[32];
   str fld1, fld2, fld3;
   int fldloc1, fldloc2, fldloc3, fldflag;
   fieldentry entry;
   strcpy(table1, piece);   

   while(!at_end && cin >> piece)
   {
      if(count == 1)
      {
         strcpy(table2, piece);
         if(table2[strlen(table2) - 1] == ';')
         {
            at_end = 1;
            table2[strlen(table2) - 1] = '\0';
         }
         int found_table1 = 0, found_table2 = 0;        
   
         while((!found_table1 || !found_table2) && cur_table1 != table_list.end() && cur_table2 != table_list.end())
         {
            if(!found_table1)
            {    
               if(strcmp(table1, (cur_table1->get_name()).ptr) == 0)
                  found_table1 = 1;
               cur_table1++;
            } 
            if(!found_table2)
            {
               if(strcmp(table2, (cur_table2->get_name()).ptr) == 0)
                  found_table2 = 1;
               cur_table2++;
            }
         }
         if(!found_table1 || !found_table2) return 0;
         cur_table1--; cur_table2--;
         if(cur_table1 == cur_table2) return 0;
      }
      else if(count == 2)
      {
         if(strcmp(piece, "WHERE") != 0)
            return 0;
      }
      else if(count == 3 || count == 5 || count == 7)
      {
         list<fname> fields;
         char *token;
         int found_field = 0;
         token = strtok(piece, ".");
         if(strcmp(token, table1) == 0)
         {
            fields = cur_table1->get_fields();
            switch(count)
            {
               case 3: fldloc1 = 1;
                  break;
               case 5: fldloc2 = 1;
                  break;
               case 7: fldloc3 = 1;
            }
         }
         else if(strcmp(token, table2) == 0)
         {
            fields = cur_table2->get_fields();
            switch(count)
            {
               case 3: fldloc1 = 2;
                  break;
               case 5: fldloc2 = 2;
                  break;
               case 7: fldloc3 = 2;
            }
         }
         else
            return 0;
  
         cur_fields = fields.begin();
        
         token = strtok(NULL, ".");

         while(!found_field && cur_fields != fields.end())
         {
            if(strcmp((cur_fields->name).ptr, token) == 0)
               found_field = 1;
            cur_fields++;
         }
         if(!found_field) return 0;
         cur_fields--;
         
         if(count == 7)
            fldflag = cur_fields->flag;

         switch(count)
         {
            case 3: strcpy(fld1.ptr, token);
               break;
            case 5: strcpy(fld2.ptr, token);
               break;
            case 7: strcpy(fld3.ptr, token);
         }    
         if(count == 5)
         {
            if(fldloc1 == fldloc2)
               return 0;
         }

      }
      else if(count == 4 || count == 8)
      {
         if(strcmp(piece, "=") != 0)
            return 0;
      }
      else if(count == 6)
      {
         if(strcmp(piece, "AND") != 0)
            return 0;
      }
      else
      {
         int convert_success = 0;
         if(fldflag == 1)
         {
            convert_success = char_converter(piece);
            if(!convert_success) return 0;
         }

         if(piece[strlen(piece) - 1] != ';') return 0;
         piece[strlen(piece) -1] = '\0';

         if(fldflag == 0)
            convert_success = int_converter(piece, entry.int_entry);
         else if(fldflag == 1)
            strcpy((entry.char_entry).ptr, piece);   
         else
            convert_success = float_converter(piece, entry.float_entry);

         if(!convert_success) return 0;

         at_end = 1;
      }
      count++;  
   }
 
   if(count == 2)
      cur_table1->join_all(*cur_table2);
   else if(count == 10)
   {
      str swap;
      if(fldloc3 == 1)
      {
         if(fldloc2 == 1)
         {
            swap = fld2;
            fld2 = fld1;
            fld1 = swap;
         }
         cur_table1->join_where(*cur_table2, fld1, fld2, fld3, entry);
      }
      else
      {
         if(fldloc2 == 2)
         {
            swap = fld2;
            fld2 = fld1;
            fld1 = swap;
         }
         cur_table2->join_where(*cur_table1, fld1, fld2, fld3, entry);
      }
   }
  
   return 1;
}   

//***ORDER BY***
//Preconditions:
//Postconditions:
int order_by(shaktable &table, char piece[])   
{
   list<fname>::iterator cur_field = (table.get_fields()).begin();

   cin >> piece;
   if(strcmp(piece, "BY") != 0)
      return 0;

   cin >> piece;
   if(piece[strlen(piece) - 1] == ';')
      piece[strlen(piece) - 1] = '\0';
   else
      return 0;

   int found_record = 0;
   while(!found_record && cur_field != (table.get_fields()).end())
   {
      if(strcmp(piece, (cur_field->name).ptr) == 0)
         found_record = 1;
      cur_field++;
   }
   if(!found_record) return 0;
   cur_field--;

   table.order_by(cur_field->name); 

   return 1;
}

//***Integer Converter***
//Preconditions: an integer in the form of a string is passed
//Postconditions: the string is converted to an integer
int int_converter(const char value[], int &convert)
{
   int temp;
   convert = 0;
   for(int a = strlen(value) - 1, b = 0; a >= 0; a--, b++)
   {
      if(value[a] >= '0' && value[a] <= '9')
      {
         temp = int(value[a]) - 48;
         temp *= pow(10, b);
         convert += temp;
      }
      else if(value[a] == '-' && a == 0)
         convert = -convert;
      else return 0;
   }

   return 1;
}

//***Character Converter (Formatter)***
//Precondions: A string starting with " is passed
//Postconditions: If input is correct, either x; x, or x); will be returned by reference
int char_converter(char value[])
{
   if(value[0] != '"') return 0;

   int end_char = 0, a;
   char temp[32];

   for(a = 0; a < strlen(value); a++)
   {
      value[a] = value[a + 1];
      if(value[a + 1] == '"') end_char = 1;
   }
   while(!end_char)
   {
      cin >> temp;
      a = 0;
      while(!end_char && a < strlen(temp))
      {
         if(temp[a] == '"') end_char = 1;
         else if(temp[a] == ';') return 0;
         a++;
      }
      strcat(value, " ");
      strcat(value, temp);
   }
   if(value[strlen(value) - 2] == '"')
      value[strlen(value) - 2] = value[strlen(value) - 1];
   else if(value[strlen(value) - 3] == '"')
   {
      value[strlen(value) - 3] = value[strlen(value) - 2];
      value[strlen(value) - 2] = value[strlen(value) - 1];
   }
   else return 0;

   value[strlen(value) - 1] = '\0';
}

//***Float Converter***
//Preconditions: A float in the form of a string is passed
//Postconditions: The string is converted to a float
int float_converter(const char value[], float &convert) 
{
   char temp[32];
   int convert_success, pos, int_value, point;
   for(pos = 0; pos < strlen(value) && value[pos] != '.'; pos++)
      temp[pos] = value[pos];
   temp[pos] = '\0';
   
   convert_success = int_converter(temp, int_value);
   if(!convert_success) return 0;
   convert = float(int_value);
   if(pos < strlen(value) - 1)
   {
      pos++;
      for(pos, point = 0; pos < strlen(value); pos++, point++)
         temp[point] = value[pos];
      temp[point] = '\0';
      convert_success = int_converter(temp, int_value);
      if(!convert_success) return 0;
      if(convert < 0)
         int_value = -int_value;
      int dec_length = -strlen(temp);
      convert += (int_value * pow(10, dec_length));
   }

   return 1;
}

//***Erase Line***
//Postconditions: The line will be erased until a semicolon is hit
void erase_line(char recent[])
{
   int not_read;

   if(recent[strlen(recent) - 1] == ';') not_read = 0;
   else not_read = 1;

   while(not_read)
   {
      cin >> recent;
      if(recent[strlen(recent) - 1] == ';') not_read = 0;
   }
   cout << "SYNTAX ERROR" << endl;
}
