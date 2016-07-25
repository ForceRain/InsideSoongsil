#include <stdio.h>
#include <string.h>
void my_memmove(void *s1, const void *s2,unsigned int count)
{
	while(count--)
	{
		*(char *)s2=*(char *)s1;
		(char *)s1++;
		(char *)s2++;
	}
	return;
}
int main(void)
{
	char str1[100]="This is Shy";
	char str2[100]="This is...";

	my_memmove(str2+10,str1+8,3);
	puts (str2);
	return 0;
}
