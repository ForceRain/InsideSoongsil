#include <stdio.h>
#include <math.h>
void func_addr(void *f);
int main(void) {
	func_addr(cos);
	func_addr(main);
	return 0;
}
void func_addr(void *f) 
{
	if (f==(void *)cos)
		printf("cos() 함수 주소 : %p\n", f);
	else
		printf("함수의 주소 : %p\n",f);
}
