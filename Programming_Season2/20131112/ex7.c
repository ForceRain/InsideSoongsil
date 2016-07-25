#include <stdio.h>
#define MAXSTRING 100
int main(void)
{
	char fname[MAXSTRING];
	int c;
	FILE *ifp=NULL;
	fprintf(stderr, "\n입력 파일 : ");
	scanf("%s", fname);
	ifp=fopen(fname,"r");
	fseek(ifp,0,SEEK_END);
	if (ftell(ifp)==0)
		return 0;
	fseek(ifp,-1,SEEK_CUR);
	while(1) {
		c=getc(ifp);
		putchar(c);
		if (ftell(ifp)==1)
			break;
		fseek(ifp,-2,SEEK_CUR);
	}
	fclose(ifp);
	return 0;
}
