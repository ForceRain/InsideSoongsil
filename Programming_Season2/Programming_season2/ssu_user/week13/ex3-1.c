#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
void control(int sig);
int main()
{
	signal(SIGFPE,control);
	int i,j;
	scanf("%d",&j);
	i=7/j;
	return 0;
}
void control(int sig)
{
	char ans[4];
	printf("부동 소수점 오류");
	printf("종료하시겠습니까?(y/n)\n");
	scanf("%s",ans);
	if ((ans[0]=='y')|| (ans[0]=='Y'))
		exit(1);
}
