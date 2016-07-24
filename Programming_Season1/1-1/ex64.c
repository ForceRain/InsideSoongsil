#include <stdio.h>
unsigned long long factorial(int n) {
	unsigned long long f;
	int i;
	for (f=i=1; i<=n ; i++)
		f *=i;
	return f;
}
int main(void) {
	int i;
	printf(" i | factorial(i) \n");
	printf("-----------------\n");
	for (i=1 ; i<=10; i++)
		printf("%2d | %9lld\n", i, factorial(i));
	return 0;
}


