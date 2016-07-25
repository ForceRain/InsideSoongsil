#include <stdio.h>
int main(void) {
	int arr[5]={10,20,30,40,50};
	double arry[2]={10.1, 22.3};
	printf("%d %d %d\n", sizeof(arr), sizeof(arr[0]), sizeof(&arr[0]));
	printf("%d %d\n", sizeof(arry), sizeof(&arry[0]));
	return 0;
}
