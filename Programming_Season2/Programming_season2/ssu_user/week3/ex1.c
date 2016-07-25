#include <stdio.h>
int main(void) {
	char c, *cp;
	int i, *ip;
	long long ll, *llp;
	float f, *fp;
	double d, *dp;

	d=i;
	dp=&d;
	i=cp;
	ip=&i;
	dp=ip;
	ip=(int *)cp;
	return 0;
}
