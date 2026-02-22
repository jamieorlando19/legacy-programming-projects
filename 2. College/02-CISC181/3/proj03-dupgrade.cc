//Project #3
//Reads a file of people's names and info, and puts in order by name; also erases duplicate records

#include<stream.h>
#include<string.h>
#include<iomanip.h>

const int MAX_RECORDS = 100;     //Maximum number of records in a file
const int MAX_NAME = 10;         //Maximum length of a first name or last name
const int PHONE_SIZE = 9;        //Length of a phone number (with dash)
const int MAX_EMAIL = 35;        //Maximum length of an email address
const int MAX_FILENAME = 20;     //Maximum length of a file name

struct Name
{
   char first[MAX_NAME];
   char last[MAX_NAME];
};

struct SupporterRecord
{
   Name name;
   char phone[PHONE_SIZE];
   char email[MAX_EMAIL];
};

int correct(ifstream &, ifstream &, char *, char *);
void sortRecords(SupporterRecord [], int &, ifstream &, ifstream &);
void readdata(ifstream &, SupporterRecord [], int &);
int namecmp(Name, Name);
void sort(SupporterRecord [], int);
void makeOutput(ofstream &, SupporterRecord [], int);

int main()
{
   SupporterRecord final[MAX_RECORDS * 2];
   int size = 0;                //Logical size of the record array
   char file1name[MAX_FILENAME];
   char file2name[MAX_FILENAME];
   char file3name[MAX_FILENAME];
   cout << "Enter the name of the first file: ";
   cin >> file1name;
   cout << "Enter the name of the second file: ";
   cin >> file2name;
   ifstream file1(file1name);
   ifstream file2(file2name);
   if(!file1 || !file2) 
   {
      int errorcheck;
      errorcheck = correct(file1, file2, file1name, file2name);
      if(errorcheck == 1)
      {
         cout << "Program aborted." << endl;
         return 1;   //If user quits  
      }
   }
   cout << "Enter a new file name to write to: ";
   cin >> file3name;
   ofstream out(file3name);
   
   sortRecords(final, size, file1, file2);
   makeOutput(out, final, size);

   cout << file3name << " created sucessfully!" << endl;
   return 0;
}

int correct(ifstream &f1, ifstream &f2, char *name1, char *name2)
{
   while(!f1 || !f2)
   {
      if(!f1)
      {
         cout << name1 << " does not exist.  Please type a new name, or 'q' to quit: ";
         cin >> name1;
         if(strcmp(name1, "q") == 0) return 1;
         f1.open(name1);
      }
      else
      {
         cout << name2 << " does not exist.  Please type a new name, or 'q' to quit: ";
         cin >> name2;
         if(strcmp(name2, "q") == 0) return 1; 
         f2.open(name2);
      }
   }
   return 0;
}

void sortRecords(SupporterRecord list [], int &n, ifstream &f1, ifstream &f2)
{
   SupporterRecord sub1[MAX_RECORDS], sub2[MAX_RECORDS];
   int a, b;   

   readdata(f1, sub1, a);
   readdata(f2, sub2, b);
   sort(sub1, a);
   sort(sub2, b);
   
   int x = 0, y = 0;
   while(x < a && y <b)
   {
      if(namecmp(sub1[x].name, sub2[y].name) < 0)
      {
         list[n] = sub1[x];  
         ++x; ++n;
      }
      else if(namecmp(sub2[y].name, sub1[x].name) < 0)
      {
         list[n] = sub2[y];
         ++y; ++n;
      }
      else
      {
         list[n] = sub1[x];
         ++x; ++y; ++n;
      }
   }
   for( ; x < a; ++x, ++n)
      list[n] = sub1[x];
   for( ; y < b; ++y, ++n)
      list[n] = sub2[y]; 
}

void readdata(ifstream &f, SupporterRecord list[], int &n)
{
   SupporterRecord temp;
   n = 0;
   if(f >> list[0].name.first >> list[0].name.last >> list[0].phone >> list[0].email) ++n;
   while((f >> temp.name.first >> temp.name.last >> temp.phone >> temp.email) && (n < MAX_RECORDS))
   {
      for(int x = 0; x < n && namecmp(temp.name, list[x].name) != 0; ++x);
      if(x == n)
      {
         list[n] = temp;
         ++n;
      }
   }
}

int namecmp(Name n1, Name n2)
{
   if(strcmp(n1.last, n2.last) == 0)
   {
      if(strcmp(n1.first, n2.first) == 0)
         return 0;
      else
         return strcmp(n1.first, n2.first);
   }
   else
      return strcmp(n1.last, n2.last);
}

inline void swap(SupporterRecord &a, SupporterRecord &b)
{ SupporterRecord t = a; a = b; b = t; }

void sort(SupporterRecord A[], int n)
{
   int i, j, min;
   for (i = 0; i < n; i++)
   {
      min = i;
      for (j = i + 1; j < n; ++j)
         if (namecmp(A[j].name, A[min].name) < 0) min = j;

       swap(A[i], A[min]);
    }                           
}

void makeOutput(ofstream &file, SupporterRecord list [], int n)
{
   char name[MAX_NAME * 2 + 1];
   file.setf(ios::left); 
   if(n == 0) file << "No data." << endl;
   for(int a = 0; a < n; ++a)
   {
      strcpy(name, list[a].name.first);
      strcat(name, " ");
      strcat(name, list[a].name.last);
      file << setw(MAX_NAME * 2 + 1) << name << setw(PHONE_SIZE) << list[a].phone << " " << list[a].email << endl;
   }
}
