#include <stdio.h>
unsigned long long factorial(int n) {
	unsigned long long f;
	int i;
	if (n<0)
		return -1;
	for (f=i=1; i<=n; i++)
		f*=i;
	return f;
}
int main(void) {
	unsigned long long k;
	int input;
	printf("계승할 숫자를 입력하세염 : ");
	scanf("%d", &input);
	k = factorial(input);
	if (k==-1)
	printf("잘못된 숫자입니다");
		
		else printf("%d의 계승은 %llu입니다.", input, k);
	return 0;
}


