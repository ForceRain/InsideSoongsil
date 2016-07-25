#include <stdio.h>
int main(void) {
	char c='Z';
	char *cp=&c;
	int n=100;
	int *np= &n;

	printf("%d\n", sizeof(*cp));
	printf("%d\n", sizeof(*np));
	/*printf("%d\n", sizeof(cp));
	printf("%d\n", sizeof(np)); */

	return 0;
}
