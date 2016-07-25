#include <stdio.h>
int main(void)
{
	int i=0;
	int sum=0;
	do
	{
		i++;
		sum+=i;
	}
	while(i!=10);
	printf("%d\n",sum);
	return 0;
}
