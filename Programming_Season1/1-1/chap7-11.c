#include <stdio.h>
#include <string.h>
#define N 100
int main(void) {
	char input[N]=" ";
	int n, length, sum=0;
	printf("문자열 입력 : ");
	scanf("%s", input);
	length=strlen(input);
	for (n=0; n<length; n++) {
		sum=sum+(input[n]-96);
	};
	printf("%s의 단어의 값은 %d입니다.", input, sum);
	return 0;
}
