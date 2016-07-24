#include <stdio.h>
long long power(int n, int m) {
	int i;
	long long pow =1;
	for(i=0; i<m; ++i)
		pow *=n;
	return pow;
}

int main(void) {
	int n, m, k;
	long long pow;
	printf("  n          2^n          3^n          4^n          5^n\n");
	printf("  -----------------------------------------------------\n");
	for(m=0; m<11; m++) {
		printf("%10d", m);
		for (n=2; n<6; n++) {
			pow = power(n,m);
			printf("%10ld", pow);
		};
		printf("\n");
	};
	return 0;
}

	
