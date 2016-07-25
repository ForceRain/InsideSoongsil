#include "header.h"
int main(void) {
	int x;
	printf("숫자를 입력하셍 : ");
	scanf("%d", &x);
	printf("원 둘레 : %f\n", circumstance(x));
	printf("원 넓이 : %f\n", area(x));
	printf("구의 표면적 : %f\n", surface(x));
	printf("구의 부피 : %f\n", volume(x));
	return 0;
}
