#include <stdio.h>
int main(void)
{
	int a,b;
	double ans;
	scanf("%d %d", &a, &b);
	ans=(double)a/(double)b;
	printf("%.32lf", ans);
	return 0;
}
