#include <stdio.h>
int main(void)
{
	char *str;
	char *p;
	int i=0;
	gets(str);
	p=str;
	while((*p)!='\0')
	{
		if ((*p)=='a')
			i++;
		p++;
	}
	printf("문자 'a'의 개수 : %d", i);
	return 0;
}
