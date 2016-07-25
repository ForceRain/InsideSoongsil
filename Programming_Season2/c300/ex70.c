#include <stdio.h>
#include <string.h>
int main(void)
{
	char str[100];
	
	scanf("%c",&str[0]);

	if (strlen(str)==0)
		return 0;
	strset(str,str[0]);
	printf("%s",str);
	return 0;
}
