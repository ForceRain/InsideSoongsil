#include <stdio.h>
#define N 21
int main(void) {
	char name[N] = "";
	int age = 100, i, c;
	printf("이름을 입력하세요 : ");
	i=0;
	while (1) {
		c=getchar();
		if((c==' ')||(i==N)) {
			while ((c=getchar()) != '\n');
			printf("이름을 다시 입력하세요 : ");
			i=0;
			continue;
		}
		if ((c=='\n') || (c==EOF)) {
			name[i++]='\0';
			break;
		}
		name[i++]=c;
	}
	printf("나이를 입력하세요 : ");
	scanf("%d", &age);
	printf("%s는(은) %d 살입니다.\n", name, age);
	return 0;
}
