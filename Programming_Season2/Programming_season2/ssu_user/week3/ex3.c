#include <stdio.h>
int main(void) {
	int ary[5]={10,15,20,25,30};
	int *ap=ary;

	printf("%d\n", ary);
	printf("%d\n", &ary[2]);
	printf("%d\n", &ap[3]);
	printf("%d\n", ap+2);
	printf("%d\n", *ap);
	printf("%d\n", ap[3]+1);
	printf("%d\n", ary+2);
	printf("%d\n", *ary+2);
	printf("%d\n", &ary[0]+2);
	printf("%d\n", &*ary+2);
	printf("%d\n", &*(ary+2));
	return 0;
}
