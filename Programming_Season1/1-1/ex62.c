#include <stdio.h>

long long power(int n, int m)
{
	int i;
	long long pow =1;
	for(i=0; i<m ; ++i)
		pow *=n;
	return pow;
}
int main(void) {
	power(int, int);
	return 0;
}


