#include <stdio.h>
void print_all(int (*p)[4],int i) {
	int k;
	int j;
	for (j=0; j<i; j++) {
		for (k=0; k<4;k++) {
			printf("%d", p[j][k]);
		}
		printf("\n");
	}
	return ;
}
int main(void) {
	int arr[2][4]={1,2,3,4,5,6,7,8};
	int i;
	i=sizeof(arr)/sizeof(*arr);
	print_all(arr,i);
	return 0;
}
