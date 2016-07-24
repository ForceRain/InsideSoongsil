#include <stdio.h>
#define N 30
int grade_sum (int gr[N]) {
	int sum, i;
	for(sum=0, i=0; i<N; i++) 
		sum += gr[i];
	return sum;
}

int main(void) {
	int gr[N] = {1,2,3,45,6,7,8,9};
	int gap;
	gap = grade_sum(gr[N]);
	printf("%d", gap);
	return 0;
}

