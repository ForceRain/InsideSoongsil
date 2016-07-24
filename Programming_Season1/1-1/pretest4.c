#include <stdio.h>
#include <string.h>
void set_to_zero(int a[],int size) {
	a[size]=0;
	return;
}

int main(void) {
	int a[10] = {1,2,3,4,5,6,7,8,9,0};
	int size;
	int i;
	for (i=0; i<10; i++) {
		printf("%d\n", a[i]);
	};
 	for (size=0; size<10; size++) {
		set_to_zero(a, size);
	};
	for (i=0; i<10; i++) {
		printf("%d\n", a[i]);
	};
	return 0;
}


		
