#include <stdio.h>
#include <string.h>
char *reverse(char *s) {
	char *p=NULL, *q=NULL, tmp;
	int n;

	n=strlen(s);
	q=(n>0)?s+n-1:s;
	for (p=s; p<q; ++p, --q) {
		tmp=*p;
		*p=*q;
		*q=tmp;
	}
	return s;
}
int main(void) {
	char str[]="Hello C";
	printf("%s\n", reverse(str));
	return 0;
}
