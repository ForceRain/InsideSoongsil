#include <stdio.h>
#include <ctype.h>

int main(void)
{
	char *string="This is Korea\t\n";
	int cnt=0;

	while(*string)
	{
		if (isspace(*string))
			cnt++;
		string++;
	}
	printf("%d",cnt);
	return 0;
}
