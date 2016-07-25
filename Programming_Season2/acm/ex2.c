#include <stdio.h>
int main(void)
{
	int time,min,sec;
	int input_sec;
	scanf("%d %d %d", &time,&min,&sec);
	scanf("%d",&input_sec);
	time+=input_sec/3600;
	input_sec=input_sec%3600;
	min+=input_sec/60;
	input_sec=input_sec%60;
	sec+=input_sec;
	if (sec>=60)
	{
		sec=sec%60;
		min++;
	}
	if (min>=60)
	{
		min=min%60;
		time++;
	}
	if (time>=24)
	{
		time=time%24;
	}
	printf("%d %d %d",time,min,sec);
	return 0;
}
