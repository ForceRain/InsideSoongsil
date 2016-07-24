#include <stdio.h>
void exchange(double *, double *);
void func(void (*)(double *, double *), double, double);

int main(void) {
	double a=10, b=20;
	func(exchange,a,b);
	return 0;
}
void exchange(double *ap, double *bp) {
	double tp;
	tp=*ap;
	*ap=*bp;
	*bp=tp;
}
void func(void exchange(double *,double *),double a,double b) {
	printf("%lf %lf\n", a,b);
	exchange(&a,&b);
	printf("%lf %lf\n", a,b);
	return;
}
