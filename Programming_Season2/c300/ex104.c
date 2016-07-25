#include <stdio.h>
#include <string.h>
struct tag
{
	int x;
	int y;
	char buffer[30];
};

void *my_memcpy(void *,const void *,unsigned int);

int main(void)
{
	struct tag x1,x2;
	x1.x=5;
	x1.y=10;
	strcpy(x1.buffer,"memory copy");

	my_memcpy(&x2,&x1,sizeof(x1));

	puts(x2.buffer);
	return 0;
}

void *my_memcpy(void *x2,const void *x1, unsigned int count)
{
	void *ret=x2;

	while(count--)
	{
		*(char *)x2=*(char *)x1;
		(char *)x2++;
		(char *)x1++;
	}
	return ret;
}
