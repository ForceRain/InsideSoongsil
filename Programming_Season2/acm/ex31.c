#include <stdio.h>
int main(void)
{
	int N;
	scanf("%d",&N);
	int input[N][7];
	int result[N];
	int min[N];
	int i,j;
	for (i=0;i<N;i++)
	{
		result[i]=0;
		min[i]=101;
		for (j=0;j<7;j++)
		{
			scanf("%d", &input[i][j]);
		}
	}
	for (i=0;i<N;i++)
	{
		for (j=0;j<7;j++)
		{
			if (input[i][j]%2==0)
			{
				result[i]+=input[i][j];
				if (min[i]>input[i][j])
					min[i]=input[i][j];
			}
		}
		printf("%d %d\n",result[i],min[i]);
	}
	return 0;
}
