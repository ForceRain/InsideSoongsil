#include <stdio.h>
#include <string.h>

int main(void)
{
	char string[100];
	do
	{
		gets(string);

		if (strlen(string)==0)
			break;
		strv(string);
		puts(string);

	}
	while(1);
	return 0;
}
