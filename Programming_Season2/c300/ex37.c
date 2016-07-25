#include <stdio.h>
int main(void)
{
	int i,j;
	for (i=1;i<=100;i++)
	{
		if (i==10)
			break;
		for (j=1;j<=9;j++)
		{
			printf("%d * %d = %d\n",i,j,i*j);
		}
	}
	return 0;
}
