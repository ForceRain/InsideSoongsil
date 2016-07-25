#include <stdio.h>
int main(void) {
	char *p[2][3]={"abc", "defg", "hi", "jklmno", "pqrstuvw", "xyz"};
	printf("%c\n", ***p);
	printf("%c\n", **p[1]);
	printf("%c\n", **(p[1]+2));
	printf("%c\n", *(*(p+1)+1)[7]);
	printf("%c\n", (*(*(p+1)+1))[7]);
	printf("%c\n", *(p[1][2]+2));
	return 0;
}
