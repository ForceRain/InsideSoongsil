#include <stdio.h>
#include <string.h>
struct tag
{
	int x;
	int y;
	char buffer[30];
};
int main(void)
{
	struct tag x1,x2;

	x1.x=5;
	x1.y=10;
	strcpy(x1.buffer,"memory copy");
	memcpy(&x2,&x1,sizeof(x1));
	
	printf("%d %d %s",x2.x, x2.y, x2.buffer);
	return 0;
}
