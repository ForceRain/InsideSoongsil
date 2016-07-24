#include <stdio.h>
#include <string.h>
#include <stdlib.h>
int main(void) {
	char input[99]={0};
	char *p[10];
	int i=0,j=0,check;
	while (1) {
		printf("문자열을 입력하세요 : ");
		check=scanf("%s", input);
		p[i]=(char *)malloc(sizeof(input)+1);
		strcpy(p[i],input);
		if (check==-1)
			break;
		if (strcmp(input,"end")==0)
			break;
		if (i==10)
			break;
		i++;
	}
	for (j=0;j<i;j++) {
		printf("\n%s", p[j]);
	}
	return 0;
}
