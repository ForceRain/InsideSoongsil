#include <stdio.h>
int is_prime(int n) {
	int d, r, q;
	for(d=2;d<n;d++) {
		if (n%d==0)
			{d=n;
			return 0;}
		;
	};
	return 1;
}

int main(void) {
	int input, gap;
	printf("숫자를 입력하세요 : ");
	scanf("%d", &input);
	gap = is_prime(input);
	if (gap==1)
		printf("입력된 숫자는 소수입니다.\n");
	else
		printf("입력된 숫자는 합성수입니다.\n");
	return 0;
}

