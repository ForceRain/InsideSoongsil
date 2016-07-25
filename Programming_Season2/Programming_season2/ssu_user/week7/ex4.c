#include <stdio.h>
union a{
	int a;
	char b;
	float c;
}a,b,c;

int main(void) {
	a.a=1;
	printf("%d %c %f\n", a,a,a);
	b.b='a';
	printf("%d %c %f\n", b,b,b);
	c.c=1.1;
	printf("%d  %f\n", c,c.c);
	return 0;
}
