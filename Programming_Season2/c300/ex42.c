#include <stdio.h>
#include <string.h>
int main(void)
{
	char str[]="abcde";
	char point=0;
	while(str[point]!='\0')
	{
		point++;
	}
	printf("abcde길이 : %d",point);
	return 0;
}
