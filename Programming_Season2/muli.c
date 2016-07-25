#include <stdio.h>
#define G 9.81
int main(void) {
	float M,n,a;
	printf("M, m, a를 입력하세요 : ");
	scanf("%f%f%f", &M, &n, &a);
#if DEBUG==1
	printf("%f\n", M);
	printf("%f\n", n);
	printf("%f\n", a);
#endif
	printf("결과 값은 %f입니다.", (n*G-(M+n)*a)/(M*G));
	return 0;
}

