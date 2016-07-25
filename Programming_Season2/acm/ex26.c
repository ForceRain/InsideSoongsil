#include <stdio.h>
int main(void)
{
	int i,j,input,temp;
	int arr[10];
	int ruler=1000000000;
	scanf("%d",&input);
	for (i=0;i<10;i++)
	{
		arr[i]=-1;
	}
	for (i=0;i<10;i++)
	{
		arr[i]=input/ruler;
		input=input%ruler;
		ruler/=10;
	}
	for (i=0;i<10-1;i++)
	{
		for (j=10-1;j>i;j--)
		{
			if (arr[j-1]<arr[j])
			{
				temp=arr[j-1];
				arr[j-1]=arr[j];
				arr[j]=temp;
			}
		}
	}
	for (i=0;i<10;i++)
	{
		if (arr[i]!=-1)
			printf("%d",arr[i]);
	}
	return 0;
}
