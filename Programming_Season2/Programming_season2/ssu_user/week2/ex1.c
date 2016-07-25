#include <stdio.h>
int main(void) {
	int i=10, j=20;
	int *p;
	p=&i;
	*p=40;
	p=&j;
	*p=60;
	printf("i : %d, j: %d\n", i,j);
	return 0;
}
