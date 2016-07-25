#include <stdio.h>
#define N 10
int main(void) {
	int a[N];
	int b[N][N];

	printf("sizeof(a[N-1])     : %d\n", sizeof(a[N-1]));
	printf("sizeof(a)          : %d\n", sizeof(a));
	printf("sizeof(b[N-1][N-1]): %d\n", sizeof(b[N-1][N-1]));
	printf("sizeof(b[N-1])     : %d\n", sizeof(b[N-1]));
	printf("sizeof(b)          : %d\n", sizeof(b));
	return 0;
}
