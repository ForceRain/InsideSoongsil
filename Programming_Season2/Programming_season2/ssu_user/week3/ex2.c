#include <stdio.h>
int main(void) {
	int *p, i, j=20;
	void *v;

	v=&j;
	i=*(int *)v+10;
	printf("%d", i);
	return 0;
}
