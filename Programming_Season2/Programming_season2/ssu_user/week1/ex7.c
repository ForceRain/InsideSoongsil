#include <stdio.h>
char str_return (char sp[]) {
	return sp[4];
}
int main(void) {
	char str[]="strawberry";
	printf("output : %c\n", str_return(str));
	return 0;
}
