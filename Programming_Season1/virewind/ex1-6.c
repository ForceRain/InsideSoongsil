#include <stdio.h>
double aver(double *ap) {
	int i;
	double sum=0;
	for (i=0; i<5; i++) {
		sum=sum+*(ap+i);
	}
	return (sum/5);
}
int main(void) {
	double ary[]={1.5,20.1,16.4,2.3,3.5};
	double i;
	i=aver(ary);
	printf("%.2lf", i);
	return 0;
}
