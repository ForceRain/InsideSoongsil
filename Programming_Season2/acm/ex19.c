#include <stdio.h>
int main(void)
{
	int arr[9];
	int i,max,max_index;
	for (i=0;i<9;i++)
	{
		scanf("%d",&arr[i]);
	}
	max=arr[0];
	max_index=0;
	for (i=0;i<9;i++)
	{
		if (max<arr[i])
		{
			max=arr[i];
			max_index=i;
		}
	}
	printf("%d\n%d",max,max_index);
	return 0;
}
