#include <stdio.h>
int main(void)
{
	int i;
	int arr[2][2];
	for (i=0;i<2;i++)
	{
		scanf("%d %d",&arr[i][0],&arr[i][1]);
	}
	printf("%d %d",(arr[0][1]*arr[1][0]+arr[0][0]*arr[1][1]), arr[0][1]*arr[1][1]);
	return 0;
}
