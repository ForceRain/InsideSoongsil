#include <stdio.h>
int main(void) {
	char str[]="1234567890";
	int a,b,c,d;
	sscanf(str,"%2d%2d", &a, &b);
	sscanf(str+4,"%2d%2d", &c, &d);
	printf("%d%d%d%d", a,b,c,d);
	return 0;
}
