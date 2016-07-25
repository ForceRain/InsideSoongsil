#include <stdio.h>
int main(void) 
{
	char a,b,*p,*q;
	p=&a;
	q=&b;
	printf("%p %p %p %p", p, q, &p, &q);
	return 0;
}
