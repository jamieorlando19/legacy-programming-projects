#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>



struct mailthreads
{
  char *element;
  struct mailthreads *next;
};



int main()
{
  struct mailthreads *yo = calloc(1, sizeof(struct mailthreads)), *tmp;
  char *stringy, *tmpstring;
  int a = 0;  

  tmp = yo;

  while(a < 10)
  {
    fgets(stringy, 1000, stdin);
    tmpstring = stringy;

    memtmp->element = tmpstring;
    tmp->next = calloc(1, sizeof(struct mailthreads));
    tmp = tmp->next;
    a++;
  }
  tmp = yo;

  while(tmp != NULL)
  {
    printf("%s\n", tmp->element);
    tmp=tmp->next; 
   
  }



return 0;

}
