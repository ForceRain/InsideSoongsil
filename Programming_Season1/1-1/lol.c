#include <stdio.h>
#include <string.h>
#define N 100
int end_check(char word[], int l) {
	int i;
	char out[5]=".end";
	for (i=0; i<l; i++) {
		if (word[i]!=out[i])
			return 1;
	};
	return 0;
}
int check(char word[], int l) {
	int i, right;
	for (i=0;i<l/2; i++) {
		if(word[i]!=word[l-1-i])
			return 0;
	};
	return 1;
}

int main(void) {
	int i, n, l;
	char word[N];
	while (1) {
		printf("문자열 입력 : ");
		scanf("%s", word);
		l=strlen(word);
		n=end_check(word,l);
		if (n==1) {                     //n=1이면 .end입력 아님
			i=check(word,l);
			if(i==0)
				printf("%s는 회문이 아닙니다.\n", word);
			else if (i==1)
				printf("%s는 회문입니다.\n", word);
		}	
		else
			{printf("프로그램을 종료합니다.\n");
			return 0;
			}
	};
	return 0;
}
