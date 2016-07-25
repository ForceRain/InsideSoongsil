#include <stdio.h>
#include <time.h>
int main(void)
{
	int i,j,sum,temp,tmp;
	int arr[9];
	int ans[7];
	srand(time(NULL));
	for (i=0;i<9;i++)
	{
		scanf("%d",&arr[i]);
	}
	while(1)
	{
		int check[9]={0};
		int sum=0;
		for (i=0;i<7;i++)
		{
			ans[i]=0;
		}
		for (i=0;i<7;i++)
		{
			while (1)
			{
				temp=rand()%9;
				if (check[temp]==0)
				{
					sum+=arr[temp];
					check[temp]=-1;
					ans[i]=arr[temp];
					break;
				}
			}
		}
		if (sum==100)
			break;
	}
	for (i=0;i<7-1;i++)
	{
		for (j=7-1;j>i;j--)
		{
			if (ans[j-1]>ans[j])
			{
				tmp=ans[j-1];
				ans[j-1]=ans[j];
				ans[j]=tmp;
			}
		}
	}
	for (i=0;i<7;i++)
	{
		printf("%d\n",ans[i]);
	}
	return 0;
}
