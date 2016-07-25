#include <stdio.h>
#include <stdlib.h>
int main(int argc, char **argv) {
	FILE *ifp=NULL, *ofp=NULL;
	int c;
	if (argc!=3) {
		printf("실행 오류 : \n사용법 : %s from_file to_file\n",argv[0]);
		exit(1);
	}
	if ((ifp=fopen(argv[1],"r"))==NULL) {
		printf("오류 : %s 파일을 열 수 없습니다.\n", argv[1]);
		exit(1);
	}
	if ((ofp=fopen(argv[2],"w"))==NULL) {
		printf("오류 : %s 파일을 열 수 없습니다.\n", argv[2]);
		exit(1);
	}
	while ((c=getc(ifp))!=EOF)
		putc(c,ofp);
	fclose(ifp);
	fclose(ofp);
	return 0;
}

