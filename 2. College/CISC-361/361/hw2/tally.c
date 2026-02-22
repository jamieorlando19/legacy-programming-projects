#include<stdio.h>
#include<pthread.h>

const int n = 50;
int tally;

void *total(void *);

int main()
{
  pthread_t tid1, tid2;
  tally = 0;
  pthread_create(&tid1, NULL, total, "Thread1");
  pthread_create(&tid2, NULL, total, "Thread2");
  sleep(2);
 

  printf("Tally = %d \n", tally);

  return 0;
}

void *total(void *msg)
{
  int count;
  for(count = 1; count <= n; count++)
  { tally++; }
}

