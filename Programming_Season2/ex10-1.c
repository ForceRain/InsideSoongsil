#include <stdio.h>
int main(void) {
	int i=10;
	typedef struct employee{
		unsigned a : 1, : 0, b : 1, : 0, c : 1;
	}em;
	printf("%d", sizeof(em));
	return 0;
}
