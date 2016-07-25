#include <stdio.h>
int main(void)
{
	int arr[10][2];
	int i,j,remain,max;
	for (i=0;i<10;i++)
	{
		for (j=0;j<2;j++)
		{
			scanf("%d",&arr[i][j]);
		}
	}
	remain=0;
	max=remain;
	for (i=0;i<10;i++)
	{
		remain=arr[i][1]+remain-arr[i][0];
		if (max<remain)
			max=remain;
	}
	printf("%d",max);
	return 0;
}
