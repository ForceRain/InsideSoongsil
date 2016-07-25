#include <stdio.h>
int quotient, rem;
int divide(int,int);
int main(void)
{
	int a=10, b=3;
	extern int quotient,rem;
	if (divide(a,b))
		printf("0으로 나눌 수 없습니다.\n");
	else
		printf("%d/%d : 몫은 %d이고 나머지는 %d입니다.\n",a,b,quotient,rem);
	return 0;
}
