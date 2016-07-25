#include <stdio.h>
#include <math.h>
#define PI 3.14159
typedef double dbl;
typedef dbl FDD (dbl);
typedef FDD *PFDD;

int main(void) {
	PFDD f=sin, g=cos;
	printf("f(%lf) = %lf\n", PI, f(PI));
	printf("g(%lf) = %lf\n", PI, g(PI));
	return 0;
}
