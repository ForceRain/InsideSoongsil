#include <stdio.h>
int main(int argc, char *argv[]) {
	int i;
	int j;
	if (strcmp(argv[1],"-h")==0) {
		printf("프로그램 사용법 : ex5 문자열 출력횟수");
		return 0;
	}
	if (argv[2]==NULL) {
		printf("사용법이 올바르지 않습니다.\n프로그램 사용법 : ex5 문자열 출력횟수");
		return 0;
	}
	j=atoi(argv[2]);
	for (i=0; i<j;i++) {
		printf("%s\n", argv[1]);
	}
	return 0;
}
