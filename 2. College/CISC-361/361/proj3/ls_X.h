/*
Jamie Orlando
Project #3
CISC 361
ls_X.h
*/



#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <dirent.h>
#include <time.h>
#include <pwd.h>
#include <grp.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mode.h> 
#include <sys/mkdev.h>



void ls_X(char *, int);              /*Produces output similar to ls -Llan on the directory passed*/
void fileinfo(struct stat, int);     /*Prints all the info for one file*/
void permissionsoffile(struct stat); /*Prints the permissions of the file*/



