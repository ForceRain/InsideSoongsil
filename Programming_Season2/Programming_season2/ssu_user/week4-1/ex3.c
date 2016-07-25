#include <stdio.h>
void change_it(int []);
int main(void) {
	int a[5]={0};

	change_it(a);
	printf("main() : a[0] = %d\n", a[0]);
	return 0;
}
void change_it(int *a){
	int i=777, *q=&i;
	a=q;
	printf("change_it() : a[0] = %d\n", a[0]);
}
