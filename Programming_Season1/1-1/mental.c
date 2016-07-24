#include <stdio.h>
int main(void) {
	int a =2, b=4;
	printf("A : a = %d, b = %d\n", a, b);
	{
		int a;
		a=5;
		b++;
		printf("B : a = %d, b = %d\n", a, b);
	}
	printf("A: a = %d, b = %d\n", a, b);
return 0;
}

