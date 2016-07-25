#include <stdio.h>
int main(void) {
	char s[]="abc";
	const char *p=s;
	*p='A';
	printf("%s\n", s);
	return 0;
}
