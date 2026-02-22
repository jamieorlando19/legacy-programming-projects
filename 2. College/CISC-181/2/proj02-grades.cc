//Project #2
//Program reads grades from a file containing names and ID numbers and calculates the class
//average, standard deviation, percentage of grades in certain ranges and a histogram

#include<iostream.h>
#include<iomanip.h>
#include<math.h>

const int MAX_STUDENTS = 100;        //Maximum Students in the class
const int GRADE_RANGE = 5;           //Number of ranges of grades to store

int readgrades(int[]);
float average(const int[]);
float stdeviation(const int[], float, int);
void ranges(const int[], int[]);
void printresults(const int[], const int[], int, float, float);

int main()
{
   int gradelist[MAX_STUDENTS];     //The list of the students grades
   int rangelist[GRADE_RANGE] = {0};//Array to store the range of grades
   int numstud;                     //The number of students in the class
   float classavg;                  //The class average   
   float stddev;                    //The standard deviation

   numstud = readgrades(gradelist);
   classavg = average(gradelist);
   stddev = stdeviation(gradelist, classavg, numstud);
   ranges(gradelist, rangelist); 
   printresults(gradelist, rangelist, numstud, classavg, stddev);
 
   cout << endl << endl << "Programmed by Jamie Orlando" << endl;
   return 0;
}

int readgrades(int list[])
//Reads grades from a file into the array, and returns the number of grades
{
   int grade;                      //Temporary variable to store the grade before it is added to the array
   char dummy[40];                 //Temporary string to store superflous file data
   
   for(int a = 1; a <=4; ++a)      //Reads in the superflous data at the beginning of the file 
      cin >> dummy; 

   for(int b = 0; cin >> dummy >> dummy >> grade; ++b)  //Reads grades into the array
      list[b] = grade;
   list[b] = '\0';
   return b;
}

float average(const int list[])
//Computes the class average
{
   float total = 0;
   for(int num = 0; list[num]; ++num) 
      total += list[num];
   
   return total/num;
}

float stdeviation(const int list[], float avg, int n)
//Computes the standard deviation
{
   if(n == 1) return 0;
   float numer = 0;                  //Numerator in the equation for standard deviation

   for(int q = 0; list[q]; ++q)
       numer += pow((list[q] - avg), 2);

   return sqrt(numer/(n - 1));
}

void ranges(const int list[], int rng[])
//Organizes each grade into categories
{
   for(int z = 0; list[z]; ++z)
   {
      if(list[z] >= 90)
         ++rng[0];
      else if(list[z] < 90 && list[z] >= 80)
         ++rng[1];
      else if(list[z] < 80 && list[z] >= 70)
         ++rng[2];
      else if(list[z] < 70 && list[z] >= 60)
         ++rng[3];
      else
         ++rng[4];
   }
}

void printresults(const int list[], const int rng[], int n, float avg, float stdev)
//Prints average, standard deviation, percentage of various grades along with a histogram display
{
   cout << setprecision(2) << setiosflags(ios::fixed);
   cout << " ------------------------- " << endl;
   cout << "|Class Average:" << setw(11) << avg << "|" << endl;
   cout << "|Standard Deviation:" << setw(6) << stdev << "|" << endl;
   cout << " ------------------------- " << endl << endl;
   cout << " ------------------------------------------------- " << endl;
   cout << "|                Range Percentages                |" << endl;
   cout << "|-------------------------------------------------|" << endl;
   cout << "|  0 - 59" << setw(10) << "| 60 - 69" << setw(10) << "| 70 - 79" << setw(10) << "| 80 - 89" << setw(12) << "| 90 - 100|" << endl;
   cout << "|-------------------------------------------------|" << endl;
   for(int d = 4; d >= 0; --d)             //Prints corresponding percentages 
      cout << "|" << setw(7) << (float(rng[d])/n) * 100 << "%" << setw(2);
   cout << "|" <<  endl;
   cout << " ------------------------------------------------- " << endl << endl;
   cout << "Grade Range" << setw(9) << "Freq   " << "Histogram" << endl;
   cout << "-----------" << setw(9) << "----   " << "---------" << endl;
   for(int a = 0; a < 5; ++ a)             //Prints the histogram
   {
      switch(a)
      {
         case 0: cout << " 90 - 100";
            break;
         case 1: cout << " 80 -  89";
            break;
         case 2: cout << " 70 -  79";
            break;
         case 3: cout << " 60 -  69";
            break;
         case 4: cout << "  0 -  59";
      }
      cout << setw(8) << rng[a] << "   ";
      for(int b = 0; b < rng[a]; ++b) cout << "*";
      cout << endl;
   } 
}
