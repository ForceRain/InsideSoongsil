#include <stdio.h>
int main(void)
{
	int i,j;
	int arr[5][4];
	int ans[5]={0};
	int max,max_index;
	for (i=0;i<5;i++)
	{
		for (j=0;j<4;j++)
		{
			scanf("%d",&arr[i][j]);
			ans[i]+=arr[i][j];
		}
	}
	max=ans[0];
	max_index=0;
	for (i=0;i<5;i++)
	{
		if (max<ans[i])
		{
			max=ans[i];
			max_index=i;
		}
	}
	printf("%d %d",max_index,max);
	return 0;
}
