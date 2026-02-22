/*
Jamie Orlando
Project #1
CISC 361
sh.h
*/

#include "get_path.h"

int shellpid, pid;			/*PID of the shell and PID of the current process*/

int sh(int argc, char **argv, char **envp);                           /*Main method*/
void makeprompt();                                                    /*Creates the prompt*/
char **makearglist(char *commandline);                                /*Puts args into arv*/
int builtin(char **args, struct pathelement *pathlist, char **envp);  /*Checks if built-in and runs*/
char *which(char *command, struct pathelement *pathlist);             /*Which*/
void where(char *command, struct pathelement *pathlist);              /*Where*/
void list (char *path);                                               /*List*/
void printenv(char *param);                                           /*Printenv*/ 
void setenviron(char **args);                                         /*Setenv*/
void forkexec(char *path, char **argv, char **envp);                  /*Runs a new process*/
void abspath(char *commandline, char **arglist, char **envp);         /*Checks if absolute path and runs*/

#define PROMPTMAX 64             /*Max chars in a prompt*/
#define MAXARGS 10               /*Max arguments in a commandline*/
