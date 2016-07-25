#include <stdio.h>
void bit_print(int a) {
	int i;
	int n=sizeof(int)*8;
	int mask=1<<(n-1);
	for (i=1; i<=n ; ++i) {
		putchar(((a&mask)==0)? '0' : '1');
		a<<=1;
		if (i%8==0 && i<n)
			putchar(' ');
	}
	printf("\n");
}
int circular_shift(int a, int b) {
	int mask=1 << (sizeof(int)*8-1);
	int last=1;
	int i;
	for (i=0; i<b; i++) {
		if ((a&mask)==mask) {
			a<<=1;
			a|=last;
		}
		else
			a<<=1;
	}
	bit_print(a);
}
int main(void) {
	int input, go;
	printf("순환이동할 숫자를 입력하세요 : ");
	scanf("%d", &input);
	printf("순환이동할 횟수를 입력하세요 : ");
	scanf("%d", &go);
	bit_print(input);
	circular_shift(input,go);
	return 0;
}
