/*
Jamie Orlando
Project #2
CISC 361
sh.c
*/

#include <stdio.h>
#include <string.h>
#include <strings.h>
#include <limits.h>
#include <unistd.h>
#include <stdlib.h>
#include <pwd.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/param.h>
#include <sys/stat.h>
#include <sys/time.h>
#include <signal.h>
#include <pthread.h>
#include <kstat.h>
#include <time.h>
#include <utmpx.h>
#include "sh.h"

extern char **environ;
char *prompt;
char *commandline;              
char *prefix;                   /*Prompt prefix*/
char *homedir;        		/*Home directory*/
char *prevdir;			/*Previous directory*/
struct pathelement *pathlist;
struct mailthreads *mailheadpointer;
struct watchnames *namesheadpointer;

int sh( int argc, char **argv, char **envp )
{  

  int go = 1, ampersand = 0;
  char **args;
 
  mailheadpointer = calloc(1, sizeof(struct mailthreads*));
  namesheadpointer = calloc(1, sizeof(struct watchnames*));
  userload = 0.0;

  shellpid = getpid();           /*Sets the pid of the shell*/
  commandline = calloc(MAX_CANON, sizeof(char));
  pathlist = get_path(getenv("PATH"));  /*Makes a pathlist from the enviroment variables*/
  homedir = getenv("HOME");	        /*Sets home directory*/  
  
   
  while ( go )
  {
    makeprompt();
    fgets(commandline, MAX_CANON, stdin);

    if(strlen(commandline) > 1)                         /*Removes carriage return*/
    {
      commandline[strlen(commandline) - 1] = '\0';
      
      if(commandline[strlen(commandline) - 1] == '&')   /*If the commandline ends in ampersand*/
      {
        ampersand = 1;
        commandline[strlen(commandline) - 1] = '\0';
        printf("AMPERSAND FOUND!\n");
      }
      args = makearglist(commandline);    
    }
    else                                                /*If nothing*/
      commandline[0] = ' ';


    if(strcmp(commandline, "exit") == 0)                      /*EXIT*/
    {
      printf("Connection closed.\n\n"); 
      go = 0;      
    }
    else if(commandline[0] == ' '){ }                         /*Nothing is typed*/
    else if(strcmp(args[0], "prompt") == 0)                   /*Prompt is typed*/
    {
      printf("Running built-in %s\n", args[0]);
      if(args[1] == NULL)
      {
        printf("Enter what you want the new prompt prefix to be: ");
        fgets(prefix, MAX_CANON, stdin);
        if(strlen(prefix) > 1)
          prefix[strlen(prefix) - 1] = '\0';
      } 
      else
      {
        prefix = calloc(strlen(args[1]), sizeof(char));
        memcpy(prefix, args[1], strlen(args[1]));
      }  
      if(strlen(prefix) < 1)
        prefix = "";
    }
    else if(builtin(args, pathlist, envp)){ }                 /*Built-in command*/
    else if(which(args[0], pathlist) != NULL)                 /*System command*/
      forkexec(which(args[0], pathlist), args, envp, ampersand);
    else if((commandline[0] == '/') || (commandline[0] == '.'))   /*Absolute path*/
      abspath(commandline, args, envp, ampersand);
    else                                                      /*Non-valid command*/
      fprintf(stderr, "%s: Command not found.\n", args[0]); 

    ampersand = 0;
    printf("\n");
  }

  return 0;
} /* sh() */

void makeprompt()
{
  /*Current working directory and the prompt*/
  char *pwd, *prompt = calloc(PROMPTMAX + PROMPTMAX, sizeof(char));;

  if ((pwd = getcwd(NULL, 100)) == NULL )
  {
    perror("getcwd");
    exit(2);
  }
  if(prefix == NULL)
    prefix = "";
  
  memcpy(prompt, prefix, strlen(prefix));
  strcat(prompt, "  [");
  strcat(prompt, pwd);
  strcat(prompt, "]> ");

  printf("%s", prompt);
} /* printprompt() */

char **makearglist(char *commandline)
{
  char **arglist = calloc(MAXARGS, sizeof(char*));
  char *temp;
  int count = 0;

  /*Tokenizes string by spaces and inserts into a char** */

  temp = strtok(commandline, " ");
  do
  {
    arglist[count] = temp;
    count++;
  }while(temp = strtok(NULL, " "));


  if(arglist[0] == NULL)
    arglist[0] = " ";

  return arglist;
}

int builtin(char **args, struct pathelement *pathlist, char **envp)
{
  char *result, *buffer;
  int status = 0, count = 1;
  pthread_t loadthread, watchthread;
  struct mailthreads *tempmailptr;
  struct watchnames *tempnameptr;
  struct utmpx *up;

  if(strcmp(args[0], "which") == 0)                            /*Which*/
  {
    printf("Running built-in %s\n", args[0]);
    while(args[count] != NULL)
    {
      result = which(args[count], pathlist);
      if(result == NULL)
        printf("%s: Command not found.\n", args[count]);
      else
        printf("%s\n", result);
      count++;
    }

    if(args[1] == NULL) 
      status = 1;   
  }
  else if(strcmp(args[0], "where") == 0)                       /*Where*/
  {
    printf("Running built-in %s\n", args[0]);     
    while(args[count] != NULL)
    {
      where(args[count], pathlist);     
      count++;
    }

    if(args[1] == NULL) 
      status = 1;
  }
  else if(strcmp(args[0], "cd") == 0)                          /*cd*/
  {
    printf("Running built-in %s\n", args[0]);
    if(args[1] == NULL)
    {
      prevdir = getcwd(NULL, 100);
      chdir(homedir);
    }
    else if(args[2] != NULL)
      status = 2;
    else if(strcmp(args[1], "-") == 0)
    {
      buffer = getcwd(NULL, 100);
      chdir(prevdir);
      prevdir = buffer;
    }
    else
    {
      if(chdir(args[1]) != 0)
        printf("%s: No such file or directory\n", args[1]); 
    }
  }    
  else if(strcmp(args[0], "pwd") == 0)                         /*pwd*/
  {
    printf("Running built-in %s\n", args[0]);
    printf("%s\n", getcwd(NULL, 100));    
  }
  else if(strcmp(args[0], "list") == 0)			       /*list*/
  {
    printf("Running built-in %s\n", args[0]);
    if(args[1] == NULL)
      list(".");
    else
    {
      while(args[count] != NULL)
      {
        list(args[count]);
        printf("\n");
        count++;
      }
    }
  }
  else if(strcmp(args[0], "pid") == 0)			       /*pid*/
  {
    printf("Running built-in %s\n", args[0]);
    printf("%d\n", getpid());    
  }
  else if(strcmp(args[0], "kill") == 0)			       /*kill*/
  {
    printf("Running built-in %s\n", args[0]);
    if(args[1] == NULL)
      printf("Usage: kill [-SIG] pid\nWhere SIG is a signal number.\n");
    else if(args[1][0] == '-')
    {
      if(args[2] != NULL)
      {
        buffer = calloc(1, sizeof(char));
        buffer[0] = args[1][1];
        if((kill(atoi(args[2]), atoi(buffer))) != 0)
          printf("%s: Bad syntax\n", args[0]);
      }
      else printf("%s: Bad syntax\n", args[0]);
    }
    else if((kill(atoi(args[1]), SIGTERM)) != 0)
      printf("%s: Bad syntax\n", args[0]);
  }
  else if(strcmp(args[0], "printenv") == 0)                    /*printenv*/
  {
    printf("Running built-in %s\n", args[0]);
    if(args[1] == NULL)
      printenv("all");
    else if(args[2] != NULL)
      status = 2;
    else
      printenv(args[1]);
  }
  else if(strcmp(args[0], "setenv") == 0)	               /*setenv*/
  {
    printf("Running built-in %s\n", args[0]);
    if(args[1] == NULL) 
      printenv("all");
    else if(args[3] != NULL)
      status = 2;
    else
      setenviron(args); 
  }
  else if(strcmp(args[0], "warnload") == 0)                   /*warnload*/
  {
    printf("Running built-in %s\n", args[0]);
    if(args[1] != NULL && args[2] == NULL)
    {
      if(atof(args[1]) == 0)                     /*shutting off*/
      {
        printf("Warnload terminating.\n");
        userload = 0.0;
      }
      else
      {
        userload = atof(args[1]);
        printf("\nWarning threshold set to %f\n", userload);
        pthread_create(&loadthread, NULL, warnloadloop, NULL);
      }
    }
    else if(args[2] != NULL)
      status = 2;     /*Too many arguments*/
    else
      status = 1;     /*Too few arguments*/
  }
  else if(strcmp(args[0], "watchmail") == 0)                  /*watchmail*/
  {
    printf("Running built-in %s\n", args[0]);
    tempmailptr = mailheadpointer;


    if(args[1] == NULL)          /*if there is no argument*/
      status = 1;
    else if(args[2] == NULL)     /*if there is an argument*/
    {
      if(access(args[1], F_OK) == 0)
      {
        while(tempmailptr->thefilename != NULL && strcmp(args[1], tempmailptr->thefilename) != 0)
        { tempmailptr = tempmailptr->next; }

        if(tempmailptr->thefilename == NULL)    /*if thread isn't running yet*/
        {
          tempmailptr->thefilename = calloc(1, sizeof(char*));
          memcpy(tempmailptr->thefilename, args[1], strlen(args[1]));
          tempmailptr->next = calloc(1, sizeof(struct mailthreads*));
          
          pthread_create(&(tempmailptr->threadforfile), NULL, watchmail, (void *)tempmailptr->thefilename); 
        }
        else
          printf("%s: Watchmail is monitering this file already", args[1]);        
      }
      else
        printf("%s: Not a valid filename.\n", args[1]);
    }
    else if(args[3] == NULL && strcmp(args[2], "off") == 0) /*if the second argument is off*/
    {
      while(tempmailptr->thefilename != NULL && strcmp(args[1], tempmailptr->thefilename) != 0)
      { tempmailptr = tempmailptr->next; }

      if(tempmailptr->thefilename == NULL)
        printf("%s is not currently being watched.", tempmailptr->thefilename);
      else
      {
        printf("Watchmail no longer watching %s", tempmailptr->thefilename);
        tempmailptr->thefilename = "dead";
        pthread_cancel(tempmailptr->threadforfile);
      }
    }
    else                         /*if there are too many arguments*/
      status = 2;

  }                                                                                  
  else if(strcmp(args[0], "watchuser") == 0)                  /*watchuser*/     
  {
    tempnameptr = namesheadpointer; 
    printf("Running built-in %s\n", args[0]);

    if(args[1] == NULL)
      status = 1;
    else
    {
      printf("Watching user: %s\n", args[1]);
      if(tempnameptr->login == NULL)
        pthread_create(&watchthread, NULL, watchloop, NULL);

      while(tempnameptr->login != NULL)
      { tempnameptr = tempnameptr->next; }

      tempnameptr->login = calloc(1, sizeof(char *));
      memcpy(tempnameptr->login, args[1], strlen(args[1]));
      tempnameptr->next = calloc(1, sizeof(struct watchnames*));
 
      setutxent();
 
      while(up = getutxent())
      {
        if(up->ut_type == USER_PROCESS && strcmp(args[1], up->ut_user) == 0)
        {

        }
        
      }
      tempnameptr = namesheadpointer; 
    }
  }
  else
    return 0;

  if(status == 1)
    fprintf(stderr, "%s: Too few arguments.\n", args[0]);
  if(status == 2)
    fprintf(stderr, "%s: Too many arguments.\n", args[0]);

  return 1;
} /* builtin() */

char *which(char *command, struct pathelement *pathlist)
{
  /*Traverses through pathlist and returns the first occurances of the command*/

  char *path;
  struct pathelement *tmp = pathlist;
  
  while(tmp->element != NULL)
  {
    path = calloc(strlen(tmp->element) + strlen(command), sizeof(char));
    memcpy(path, tmp->element, strlen(tmp->element));
    strcat(path, "/");
    strcat(path, command);
    if(access(path, F_OK) == 0 && access(path, X_OK) == 0)
      return path;
    tmp = tmp ->next;  
  }

  return NULL;
} /* which() */

void where(char *command, struct pathelement *pathlist)
{
  /*Traverses pathlist and returns all occurances of the command*/

  int flag = 0;
  char *path;
  struct pathelement *tmp = pathlist;
  
  while(tmp->element != NULL)
  {
    path = calloc(strlen(tmp->element) + strlen(command), sizeof(char));
    memcpy(path, tmp->element, strlen(tmp->element));
    strcat(path, "/");
    strcat(path, command);
    if(access(path, F_OK) == 0 && access(path, X_OK) == 0)
    {
      printf("%s\n", path);
      flag = 1;
    }
    tmp = tmp ->next;  
  }

  if(flag == 0)
    printf("%s: Command not found.\n", command);
} /* where() */

void list (char *path)
{
  /*Lists all files in the directory*/

  DIR *dirp;
  struct dirent *direntp;

  printf("%s:\n", path);
  
  if(access(path, F_OK) == 0)
  {
    dirp = opendir(path);

    while((direntp = readdir(dirp)) != NULL)
      printf("%s\n", direntp->d_name);
  }
  else
    printf("%s: Invalid path.\n", path);

  
} /* list() */

void printenv(char *param)
{
  /*Prints environment variables according to param sent*/

  int i;

  /*Prints all env vars if "all" sent*/
  if(strcmp(param, "all") == 0)
  {
    for (i = 0; environ[i] != NULL; i++)
      printf("%s\n", environ[i]);
  }
  else if(getenv(param) == NULL)
    printf("%s: Invalid environment.\n", param);
  else
    printf("%s=%s\n", param, getenv(param));  

} /* printenv() */

void setenviron(char **args)
{
  /*Sets environment variables*/
 
  char *param;
  if(args[2] == NULL) param = "";
  else
  {
    param = calloc(strlen(args[1]) + strlen(args[2]), sizeof(char));
    memcpy(param, args[1], strlen(args[1]));
    strcat(param, "=");
    strcat(param, args[2]);
  }

  if(strcmp(args[1], "HOME") == 0)
  {
    if(access(args[2], F_OK) == 0)
    {
      putenv(param);
      homedir = getenv("HOME");
    }
    else
      printf("%s: Invalid %s variable.\n", args[0], args[1]);
  }
  else if(strcmp(args[1], "PATH") == 0)
  {
    putenv(param);
    pathlist = get_path(getenv("PATH"));
  }
  else
  {
    if(putenv(param) == -1)
      printf("%s: Bad syntax\n", args[0]);
  }
} /* setenviron() */

void forkexec(char *path, char **argv, char **envp, int ampersand)
{
  /*fork, execve & waitpid the new process*/

  int rv, status;
  pid = fork();

  if(pid == -1)
  {
    fprintf(stderr, "FORK FAILED!\n");
    perror("fork");    
  }
  else if(pid == 0) /*CHILD FUNCTION*/
  {
    printf("Executing %s\n", path);
    rv = execve(path, argv, envp);
    if(rv == -1)
      printf("%s: Permission denied.\n", path);
    kill(getpid(), 9);
  }
  else              /*PARENT FUNCTION*/
  {
    if(ampersand == 0)
      rv = waitpid(pid, &status, 0);
    else
      rv = waitpid(pid, &status, 0);

    if(rv == -1)
      printf("Exited.\n");
    else if(status != 0)
      printf("Exit %d\n", WEXITSTATUS(status));
  }

} /* forkexec() */

void abspath(char *commandline, char **arglist, char **envp, int ampersand)
{
  /*Executes absolute paths w/ error checking*/

  char *temp, *actual;

  if(access(arglist[0], F_OK) == 0)
  {
    if(access(arglist[0], X_OK) == 0)
    {
      actual = calloc(strlen(commandline), sizeof(char));
      memcpy(actual, commandline, strlen(commandline));
      temp = strtok(arglist[0], "/");
      do
      {
        arglist[0] = temp;
      }while(temp = strtok(NULL, "/"));

      forkexec(actual, arglist, envp, ampersand);   
    }
    else
      printf("exec: Permission denied\n");
  }
  else
    printf("%s: Command not found.\n", arglist[0]);
} /* abspath() */


void *warnloadloop(void *arg)
{
  double newload;

  while(1 == 1)
  {
    sleep(30);
    newload = (loadpermin() / 100);

    if(userload == 0)
    {
      pthread_exit(NULL);
      return (NULL);
    }

    if(newload > userload)
      printf("\nWarning load level is %f\n", newload);
  }
}

double loadpermin()
{
  kstat_ctl_t *kc;
  kstat_t *ksp;
  kstat_named_t *kn;

  kc = kstat_open();
  if (kc == 0)
  {
    perror("kstat_open");
    exit(1);
  }

  ksp = kstat_lookup(kc, "unix", 0, "system_misc");
  
  if (ksp == 0)
  {
    perror("kstat_lookup");
    exit(1);
  }
  if (kstat_read(kc, ksp,0) == -1) 
  {
    perror("kstat_read");
    exit(1);
  }

  kn = kstat_data_lookup(ksp, "avenrun_1min");
  if (kn == 0) 
  {
    fprintf(stderr,"not found\n");
    exit(1);
  }

  return kn->value.ul / (FSCALE / 100);
}


void *watchmail(void *arg)
{
  off_t current_size, new_size;
  struct stat filestats;
  struct timeval timeofday;
  struct timezone dummy;
  long lotsofseconds; 

  
  printf("Watchmail now watching: %s\n", (char *)arg);
 
  stat(arg, &filestats);
  current_size = filestats.st_size;
  new_size = current_size;

  while(1 == 1)
  {
    stat(arg, &filestats);
    new_size = filestats.st_size;  
    if(new_size > current_size)
    {
/*      gettimeofday(&timeofday, &dummy);*/
/*      lotsofseconds = timeofday.tv_sec;*/
/*      printf("LOTS OF SECONDS: %l jamie\n", lotsofseconds);*/
/*      fflush(stdout);*/
      printf("\n\aYou have new mail in %s!\n", arg);
      fflush(stdout);
      current_size = new_size;
    }   
    sleep(1);
  }

}

void printusers()
{
  struct utmpx *up;
  setutxent();
  while(up = getutxent() )
  {
    if(up->ut_type == USER_PROCESS)
    {
      if(strcmp(up->ut_user, "orlandoj") == 0)
        printf("%s logged on %s from %s\n", up->ut_user, up->ut_line, up->ut_host);
    }
  }
}

void *watchloop(void *args)
{
  struct watchnames *tempnameptr;
  struct utmpx *up;
  

  while(1 == 1)
  {
    sleep(5);
    setutxent();

    while(up = getutxent() )
    {

      if(up->ut_type == USER_PROCESS)
      {
        tempnameptr = namesheadpointer;
        while(tempnameptr->login != NULL)
        {
          if(strcmp(up->ut_user, tempnameptr->login) == 0)
          {
            printf("in if\n"); 
          }
          tempnameptr = tempnameptr->next;
        }         
      }      
    }

  }
}
