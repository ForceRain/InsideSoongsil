#include <stdio.h>
int fib(int N)
{
	if (N==0)
		return 0;
	if (N==1)
		return 1;
	return (fib(N-1)+fib(N-2));
}
int main(void)
{
	int N;
	scanf("%d", &N);
	printf("%d",fib(N));
	return 0;
}
