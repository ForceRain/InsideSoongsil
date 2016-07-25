#include <stdio.h>
long long power(int n, int m) {
	int i;
	long long pow=1;
	for (i=0; i<m; ++i) {
		pow*=n;
	}
	return pow;
}
int main (void) {
	int num;
	for (num=1; num<21; num++) {
		printf("%lld\n" ,power(2,num));
	}
	return 0;
}
