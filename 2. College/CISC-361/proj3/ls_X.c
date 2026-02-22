/*
Jamie Orlando
Project #3
CISC 361
ls_X.c
*/

#include "ls_X.h"

void ls_X(char *filetolist, int mode)
{
  struct stat filestats;
  DIR *dirp;
  struct dirent *direntp;
  char *cur;

  stat(filetolist, &filestats);

  if(S_ISDIR(filestats.st_mode))     /*If it is a directory*/
  {
    cur = getcwd(NULL, 100);
    chdir(filetolist);
    printf("%s:\n", filetolist);
    dirp = opendir(".");
    while((direntp = readdir(dirp)) != NULL)
    {
      stat(direntp->d_name, &filestats);      /*get stats*/
      fileinfo(filestats, mode);              /*print info*/  
      printf("  %s\n", direntp->d_name);      /*print filename*/
    }
    chdir(cur);
  }
  else                              /*Any other type of file*/
  {
    fileinfo(filestats, mode);
    printf("    %s", filetolist);
    printf("\n");
  }

} /* ls_X() */

void fileinfo(struct stat filestats, int mode)
{
  struct passwd *p;            /*to determine user name*/
  struct group *g;             /*to determine group name*/

  char *pname, *gname;
  char *printedtime1 = calloc(1, sizeof(char *));
  char *printedtime2 = calloc(1, sizeof(char *));

  permissionsoffile(filestats);
  
  printf(" %5d", filestats.st_nlink);
  if(mode == 1)                /*if running ls-X2, prints the actual names rather than numerics*/
  {
    p = getpwuid(filestats.st_uid);
    g = getgrgid(filestats.st_gid);
    pname = p->pw_name;
    gname = g->gr_name;
    printf(" %7s", pname);
    printf(" %7s", gname);
  }
  else if(mode == 0)
  {
    printf(" %7d", filestats.st_uid);
    printf(" %7d", filestats.st_gid);
  }

  /*Print devices rather than file size for character or block files*/

  if(!S_ISCHR(filestats.st_mode) && !S_ISBLK(filestats.st_mode))         
  {
    printf(" %10d", filestats.st_size);
  }
  else
  {
    printf("   %3d, %3d", major(filestats.st_rdev), minor(filestats.st_rdev)); 

  }

  /*print the time*/

  cftime(printedtime1, "%b %d", &filestats.st_mtime);
  cftime(printedtime2, " %k:%M:%S %Y", &filestats.st_mtime);

  printf("    %s %s", printedtime1, printedtime2);

} /* fileinfo() */

void permissionsoffile(struct stat filestats)
{
  int owner = 0, group = 0, others = 0, special = 0;  /*Octal bits*/

  if(S_ISLNK(filestats.st_mode))              /*Determines filetype*/
    printf("l");
  else if(S_ISREG(filestats.st_mode))
    printf("-");
  else if(S_ISDIR(filestats.st_mode))
    printf("d");
  else if(S_ISCHR(filestats.st_mode))
    printf("c");
  else if(S_ISBLK(filestats.st_mode))
    printf("b");
  else if(S_ISFIFO(filestats.st_mode))
    printf("p");
  else if(S_ISSOCK(filestats.st_mode))
    printf("s");
  else if(S_ISDOOR(filestats.st_mode))
    printf("D");
  else
    printf("?");

  if(filestats.st_mode & S_IRUSR)       /*User read*/
  {
    printf("r");
    owner += 4;
  }
  else
    printf("-");
  if(filestats.st_mode & S_IWUSR)       /*User write*/
  {
    printf("w");
    owner += 2;
  }
  else
    printf("-");
  if(filestats.st_mode & S_ISUID)       /*SUID bit */
  {
    printf("s");
    special += 4;
  }
  if(filestats.st_mode & S_IXUSR)       /*User execute*/
  {
    if(!(filestats.st_mode & S_ISUID))
      printf("x");
    owner += 1;
  }
  else
    printf("-");
  if(filestats.st_mode & S_IRGRP)       /*Group read*/
  {
    printf("r");
    group += 4;
  }
  else
    printf("-");
  if(filestats.st_mode & S_IWGRP)       /*Group write*/
  {
    printf("w");
    group += 2;
  }
  else
    printf("-");
  if(filestats.st_mode & S_ISGID)       /*SGID bit */
  {
    printf("s");
    special += 2;
  }
  if(filestats.st_mode & S_IXGRP)      /*Group execute*/
  {
    if(!(filestats.st_mode & S_ISGID))
      printf("x");
    group += 1;
  }
  else
    printf("-");
  if(filestats.st_mode & S_IROTH)       /*Other read*/
  {
    printf("r");
    others += 4;
  }
  else
    printf("-");
  if(filestats.st_mode & S_IWOTH)       /*Other write*/
  {
    printf("w");
    others += 2;
  }
  else
    printf("-");
  if(filestats.st_mode & S_ISVTX)       /*Sticky bit*/
  {
    printf("t");
    special += 1;
  }
  if(filestats.st_mode & S_IXOTH)  /*Other execute*/
  {
    if(!(filestats.st_mode & S_ISVTX))
      printf("x");
    others += 1;
  }
  else
    printf("-");
  
  if(special == 0)
    printf("(%d%d%d)", owner, group, others);
  else
    printf("(%d%d%d%d)", special, owner, group, others);

} /* permissionsoffile() */

