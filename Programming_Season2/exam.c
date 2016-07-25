#include <stdio.h>
int main(void)
{
	char linebuf[10240];
	while(!feof(stdin))
	{
		if (fgets(linebuf,4096,stdin)==NULL)
		{
			continue;
		}
		printf("%s",linebuf);
	}
	return 1;
}
