#include <stdio.h>
int main(void)
{
	int N,i;
	int max=0,up=0;
	scanf("%d",&N);
	int arr[N];
	for (i=0;i<N;i++)
	{
		scanf("%d",&arr[i]);
	}
	for (i=1;i<N;i++)
	{
		up++;
		if (arr[i-1]>arr[i])
		{
			if (max<up)
			{
				max=up;
				up=-1;
			}
			else
				up=-1;
		}
	}
	printf("%d",max);
	return 0;
}
