#include <stdio.h>
#define N 10
int main(void) {
	int data[N];
	int i;
	for (i=0; i<N; i++) {
		printf("%d번째 숫자를 입력하세요 : ", i+1);
		scanf("%d", &data[i]);
	}
	for (i=0; i<N; i++) {
		printf("%d번째 입력된 숫자 : %d\n", i+1, data[i]);
	}
	return 0;
}
