#include <stdio.h>
void func(int a, int b, int c[]) {
	c[0]=a/b;
	c[1]=a%b;
	return;
}
int main(void) {
	int a,b;
	int c[2]={0};
	printf("두 수를 입력하세요 : ");
	scanf("%d%d", &a, &b);
	func(a,b,c);
	printf("몫 = %d\n",c[0]);
	printf("나머지 = %d\n",c[1]);
	return 0;
}
