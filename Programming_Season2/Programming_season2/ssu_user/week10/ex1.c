#include <stdio.h>
void mygets(char *);
int main(void) {
	char input[100];
	printf("문자열을 입력하세요 : ");
	//gets(input);
	mygets(input);
	printf("입력 문자열 : ");
	//puts(input);
	printf("%s",input);
	printf("입력 끝");
	return 0;
}
void mygets(char *p) {
	char c;
	while ((c=getchar())!='\n') {
		*p=c;
		p++;
	}
	*p='\0';
}

