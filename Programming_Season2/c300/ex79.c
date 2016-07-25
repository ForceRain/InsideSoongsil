#include <stdio.h>
#include <stdlib.h>
int main(void)
{
	char *string="0x11";
	char *stop;
	int rad;
	long value;

	rad=16;
	value=strtol(string,&stop,rad);

	printf("%d개 변환\n",stop-string);
	printf("16진수 %s를 10진 : %ld\n",string,value);
	return 0;
}
