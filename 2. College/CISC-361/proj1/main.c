/*
Jamie Orlando
Project #1
CISC 361
main.c
*/

#include "sh.h"
#include <signal.h>
#include <stdio.h>

void sig_handler(int signal); 

int main( int argc, char **argv, char **envp )
{
  sigignore(SIGHUP);
  sigignore(SIGTSTP);  
  sigignore(SIGTERM);  
  
  sigset(SIGINT, sig_handler);   /*For control C*/

  return sh(argc, argv, envp);
}

void sig_handler(int signal)
{
  if(pid == 0 && (getpid() != shellpid))   /*Check if a child and terminate process*/
    kill(getpid(), 9);
  printf("\n");    
}

