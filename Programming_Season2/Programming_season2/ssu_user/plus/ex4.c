#include <stdio.h>
int main(void) {
	int a=256, b=255, c=16, d=15;
	printf("%d\n", (a&0xFF));
	printf("%d\n", (b&0xFF));
	printf("%d\n", (c&0xF));
	printf("%d\n", (d&0xF));
	return 0;
}
