#include <stdio.h>
int main(void)
{
	int days=0,sum=0;
	int up,down,length;
	scanf("%d %d %d",&up,&down,&length);
	while(1)
	{
		days++;
		sum+=up;
		if (sum==length)
			break;
		sum-=down;
	}
	printf("%d",days);
	return 0;
}
