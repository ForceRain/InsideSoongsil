#include <stdio.h>
int main(void) {
	int a[]={0,2,4,6,8}, *p=a+3;

	printf("a[3]    = %d\n", *p);
	printf("a[3+1]  = %d\n", *(p+1));
	return 0;
}
