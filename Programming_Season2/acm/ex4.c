#include <stdio.h>
int main(void)
{
	int input;
	scanf("%d",&input);
	int ary[input];
	int i,sum=0;
	for (i=0;i<input;i++)
	{
		scanf("%d",&ary[i]);
	}
	for (i=0;i<input;i++)
	{
		sum+=ary[i];
	}
	printf("%d",sum-(input-1));
	return 0;
}
