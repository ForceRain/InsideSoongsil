#include <stdio.h>
#include <string.h>

int main(void)
{
	char s1[100]="123";
	char s2[100]="123";

	strcpy(&s1[4],"abc");
	strcpy(&s2[4],"efg");

	if (strcmp(s1,s2)==0)
	{
		puts("strcmp : 버퍼값 같음.");
	}
	if (memcmp(s1,s2,7)==0)
	{
		puts("memcmp : 버퍼값 일치.");
	}
	else
	{
		puts("memcmp : 버퍼값 일치 안함.");
	}
	return 0;
}
