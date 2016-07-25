#include <stdio.h>
int main(void)
{
	int arr[7]={0};
	int temp,i,sum=0,min=0;
	for (i=0; i<7; i++)
	{
		scanf("%d",&arr[i]);
	}
	for (i=0;i<7;i++)
	{
		if (arr[i]%2==1)
		{
			min=arr[i];
			break;
		}
	}
	for (i=0;i<7;i++)
	{
		if((arr[i]%2)==1)
		{
			sum+=arr[i];
			if (arr[i]<min)
				min=arr[i];
		}
	}
	if(sum==0)
		printf("-1");
	else
	{
		printf("%d\n",sum);
		printf("%d",min);
	}
	return 0;
}
