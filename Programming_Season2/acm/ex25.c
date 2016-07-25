#include <stdio.h>
int main(void)
{
	int i,N,check=0;
	unsigned long long result=1,ruler=10;
	scanf("%d",&N);
	for (i=1;i<=N;i++)
	{
		result*=i;
	}
	while(1)
	{
		if ((result%ruler)==0)
		{
			check++;
			ruler*=10;
		}
		else
			break;
	}
	printf("%d",check);
	return 0;
}
