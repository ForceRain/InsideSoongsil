#include <stdio.h>
#include <ctype.h>

int main(void)
{
	char *string="Cat 1 Car 2 Cow 3,...";
	char buffer[100]={0};
	int cnt=0;

	while(*string)
	{
		if (isalnum(*string))
		{
			buffer[cnt++]=*string;
		}
		string++;
	}

	puts(buffer);
	return 0;
}
