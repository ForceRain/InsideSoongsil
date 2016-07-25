#include <stdio.h>
int main(void) {
	int c=0xf0000000;
	printf("%d\n",(c>>1));
	printf("%d\n",(c>>8));
	printf("%d\n",(c>>30));
	return 0;
}
