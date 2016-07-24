#include <stdio.h>
int main(void) {
	int input;
	printf("정수값을 입력하세요 : ");
	scanf("%d", &input);
	printf("입력된 값은 8진수로 %#o입니다.", input);
	printf("입력된 값은 16진수로 %#x입니다.", input);
	return 0;
}
