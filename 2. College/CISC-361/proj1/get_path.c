/*
Jamie Orlando
Project #1
CISC 361
get_path.c
*/

#include "get_path.h"

struct pathelement *get_path(char *path_list)
{
  /* path is a copy of the PATH and p is a temp pointer */
  char *path, *p;

  /* tmp is a temp point used to create a linked list and pathlist is a
     pointer to the head of the list */
  struct pathelement *tmp, *pathlist = calloc(1, sizeof(struct pathelement));

  p = path_list;          	/* get a pointer to the PATH env var.
			   make a copy of it, since strtok modifies the
			   string that it is working with... */
  path = malloc(strlen(p)*sizeof(char));	/* use malloc(3C) this time */
  strncpy(path, p, strlen(p));

  p = strtok(path, ":"); 	/* PATH is : delimited */
  tmp = pathlist;
  do				/* loop through the PATH */
  {				/* to build a linked list of dirs */
    tmp->element = p;		/* calloc(3C) again */
    tmp->next = calloc(1, sizeof(struct pathelement));
    tmp = tmp->next;		
  } while ( p = strtok(NULL, ":") );

  return pathlist;
} /* end get_path() */

