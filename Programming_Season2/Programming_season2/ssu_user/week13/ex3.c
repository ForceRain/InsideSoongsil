#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
void control(int sig);
int main()
{
	char *p=NULL;
	signal(SIGSEGV,control);
	scanf("%s",p);
	return 0;
}
void control(int sig)
{
	char ans[4];
	printf("segmentation fault 발생");
	printf("종료하시겠습니까?(y/n)\n");
	scanf("%c",ans);
	if ((ans[0]=='y')||(ans[0]=='Y'))
		exit(1);
}
