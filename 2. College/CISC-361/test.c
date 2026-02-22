#include<stdio.h>
#include<unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/stat.h>

int main()
{
int fd, fd2;

struct stat filestats;
stat("/dev/tty", &filestats);

printf("1hello!\n");

/*Sets up to print in file*/
fd = open("jamie" , O_CREAT|O_WRONLY, filestats.st_mode);
dup2(fd, 1);
close(fd);
printf("2hello!\n");

/*Sets up to print on screen*/
close(1);
fd = open("/dev/tty", O_WRONLY);
printf("3hello!\n");
printf("4hello!\n");

/*Print in file*/
fd = open("jamie" , O_APPEND|O_WRONLY, filestats.st_mode);
fd2 = open("jamie", O_APPEND|O_WRONLY, filestats.st_mode);
dup2(fd, 1);
dup2(fd2, 2);
close(fd);
close(fd2);
printf("5hello!\n");
fprintf(stderr, "YO!\n");

/*Back to screen*/
close(1);
close(2);
fd = open("/dev/tty", O_WRONLY);
fd2 = open("/dev/tty", O_WRONLY);
printf("6hello!\n");
fprintf(stderr, "YO2!\n");


return 0;
}
