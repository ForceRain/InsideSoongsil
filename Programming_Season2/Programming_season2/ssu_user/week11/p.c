#include <stdio.h>
int main(void)
{	
	int a;
	FILE *ifp;
	ifp=fopen("oh.txt","r");
	fseek(ifp,0,SEEK_END);
	a=ftell(ifp);
	printf("%d",a);
	return 0;
}
