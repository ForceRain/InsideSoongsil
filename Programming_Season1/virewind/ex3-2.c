#include <stdio.h>
int my_strlen(char *cp) {
	int k=0;
	while(*cp!='\0') {
		k++;
		cp++;
	};
	return k;
}
int main(void) {
	char temp[80]={0};
	int i;
	printf("문자열 입력 : ");
	gets(temp);
	//scanf("%s", temp);
	i=my_strlen(temp);
	printf("문자열의 길이는 %d입니다.", i);
	return 0;
}
