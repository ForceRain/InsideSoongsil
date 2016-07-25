#include <stdio.h>
#include <stdlib.h>
int main(void)
{
	char string1[]="1a2b3c4d5e";
	char string2[]="..........";
	
	swab(string1,string2,sizeof(string1)-1);

	puts(string1);
	puts(string2);

	return 0;
}
