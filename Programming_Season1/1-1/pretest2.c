#include <stdio.h>
void func(int a, int b, int c[]) {
	c[0] = a/b;
	c[1] = a%b;
	return;
}

int main(void) {
	static int c[2];
	static int a, b;
	printf("피젯수와 젯수의 값을 입력하세요 : ");
	scanf("%d%d",&a, &b);
	func(a,b,c);
	printf("몫 : %d, 나머지 : %d", c[0], c[1]);
	return 0;
}

