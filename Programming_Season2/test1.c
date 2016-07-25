#include <stdio.h>
int a(int);
int f(int);
void tell_me(int f(int)) {
	int x;
	((f==a)?x=1 : x=2);
	printf("%d\n", f(x));
}

int main(void) {
	tell_me(a);
	return 0;
}
int a(int i) {
	return i*3;
}
int f(int i) {
	return i*4;
}

