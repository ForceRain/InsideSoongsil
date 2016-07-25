#include <stdio.h>
int main(void)
{
	int N;
	int i,j,count=0;
	scanf("%d", &N);
	int arr[N];
	int check[N];
	for (i=0;i<N;i++)
	{
		scanf("%d",&arr[i]);
		check[i]=0;
	}
	for (i=0;i<N;i++)
	{
		for (j=1;j<=arr[i];j++)
		{
			if ((arr[i]%j)==0)
			{
				check[i]++;
			}
		}
		if (check[i]==2)
			count++;
	}
	printf("%d",count);
	return 0;
}
