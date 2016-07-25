#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main(void)
{
	char string[100];
	char *p;

	do
	{
		gets(string);
		if (strlen(string)==0)
			break;
		p=strdup(string);

		strcpy(string,"Temporary String");
		puts(string);
		puts(p);
		free(p);
	}
	while(1);
	return 0;
}
