#include <stdio.h>
#include <string.h>
int main(void)
{
	char str[100];
	char sky[]="skyfuck";
	printf("마음껏 눌러바\n");
	while(1)
	{
		gets(str);
		if (strncmp(str,sky,3)==0)
			break;
	}
	return 0;
}
