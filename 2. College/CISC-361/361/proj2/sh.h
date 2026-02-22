/*
Jamie Orlando
Project #2
CISC 361
sh.h
*/

#include "get_path.h"

int shellpid, pid;			/*PID of the shell and PID of the current process*/
double userload;                        /*One minute load average*/

struct mailthreads                      /*A struct in order to create a linked list*/
{                                       /*which holds a filename and a thread*/
  char *thefilename;
  pthread_t threadforfile; 		
  struct mailthreads *next;
};


int sh(int argc, char **, char **);                 /*Main method*/
void makeprompt();                                  /*Creates the prompt*/
char **makearglist(char *);                         /*Puts args into arv*/
int builtin(char **, struct pathelement *, char **);/*Checks if built-in and runs*/
char *which(char *, struct pathelement *);          /*Which*/
void where(char *, struct pathelement *);           /*Where*/
void list (char *);                                 /*List*/
void printenv(char *);                              /*Printenv*/ 
void setenviron(char **);                           /*Setenv*/
void forkexec(char *, char **, char **, int);       /*Runs a new process*/
void abspath(char *, char **, char **, int);        /*Checks if absolute path and runs*/
void *warnloadloop(void *);                         /*Continuously checks load*/
double loadpermin();                                /*Returns the load avg/min*/
void *watchmail(void *);                            /*Continously watch a file*/
void watchuser(char *);                             /*Determine which TTY(s) user is on*/


#define PROMPTMAX 64             /*Max chars in a prompt*/
#define MAXARGS 10               /*Max arguments in a commandline*/
