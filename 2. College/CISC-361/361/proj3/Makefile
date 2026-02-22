#Jamie Orlando

CC=cc

mysh: sh.o get_path.o ls_X.o main.c 
	$(CC) -g  -DHAVE_KSTAT -lkstat -lpthread *.c

clean:
	rm -rf sh.o get_path.o main.o ls_X.o a.out

