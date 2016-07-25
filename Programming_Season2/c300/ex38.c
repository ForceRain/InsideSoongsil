#include <stdio.h>
int main(void)
{
	unsigned int i;
	for (i=0;i<256;i++)
	{
		printf("%u의 ASCII코드는 : '%c'\n",i,i);
	}
	return 0;
}
