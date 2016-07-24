#include <stdio.h>
int main(void) {
	int i = 100;
	int *p = NULL;

	p = &i;
	printf("i 주소 : %p\n", &i);
	printf("i값 : %d\n", i);
	printf("p값 : %p\n", p);
	printf("*p값 : %d\n", *p);

	return 0;
}
