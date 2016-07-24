#include <stdio.h>
#include <stdlib.h>
unsigned long long factorial(int n) {
	unsigned long long f;
	int i;
	if(n<0) {
		printf("factorial() 함수 호출 오류 \n");
		exit(-1);
	}
	for (f=i=1; i<=n; i++)
		f*=i;
	return f;
}
int main(void) {
	unsigned long long c;
	int n;
	scanf("%lld", &n);
	c=factorial(n);
	printf("%d\n",c);
	return 0;
}
