#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int main(void)
{
	char booklist[5][50]={"Cygwin과 함께 배우는 C 프로그래밍", "C 99", "A book on C", "C 프로그래밍", "자바 프로그래밍"};
	char input[100];
	int i;
	char *p;
	printf("검색 문장을 입력하세요 : ");
	gets(input);
	for (i=0; i<5; i++) {
		p=strstr(booklist[i],input);
		if (p!=NULL)
			printf("%s\n",booklist[i]); //여기
	}
	return 0;
}
