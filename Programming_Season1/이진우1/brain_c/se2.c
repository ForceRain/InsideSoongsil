#include <stdio.h>
int main(void) {
	int ary[5]={10,20,30,40,50};
	int *ap=ary;
	int i;
	for (i=0; i<5; i++){
		printf("%d\n", *ap+i);
		ap++;
	}
	return 0;
}
