#include <stdio.h>
int main(void) {
	int ary[5]={1,2,3,4,5};
	void *vp=ary;
	printf("%d", *((int *)vp+2));
	return 0;
}
