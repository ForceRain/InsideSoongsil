#include <stdio.h>
int main(void)
{
	int start,to,ans,i=1,min=0,sum=0;
	scanf("%d",&start);
	scanf("%d",&to);
	for (;start<=to;start++)
	{
		i=1;
		while(1)
		{
			if ((start/i)==i &&(start%i)==0)
			{
				if (min==0)
					min=start;
				sum+=start;
				break;
			}
			if (start==i)
				break;
			i++;
		}
	}
	if (sum==0)
		sum=-1;
	printf("%d\n%d",sum,min);
	return 0;
}
