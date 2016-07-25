#include <stdio.h>
#include <math.h>
void func_addr(void (*f)(double));
int main(void) {
	func_addr((void *)cos);
	func_addr((void *)main);
	return 0;
}
void func_addr(void (*f)(double)) 
{
	if ((double *)f==(double *)cos)
		printf("cos() 함수 주소 : %p\n",(double *)f);
	else 
		printf("함수의 주소 : %p\n",(int *)f);
}
