#include <stdio.h>
#include <stdlib.h>

int main(int argc, char **argv) {
	FILE *pro=NULL, *sol=NULL;
	int c, i, opd1, opd2;
	char word[50];

	if (argc!=3) {
		fprintf(stderr, "실행 오류 : \n");
		fprintf(stderr, "사용법 : %s data_file out_file\n", argv[0]);
		exit(1);
	}
	if ((pro=fopen(argv[1],"w"))==NULL) {
		fprintf(stderr, "오류 : %s 파일을 열 수 없습니다.\n", argv[1]);
		exit(1);
	}
	for (i=0; i<50; i++)
		fprintf(pro,"%d + %d = \n", rand()%1000, rand()%1000);
	fclose(pro);
	if ((pro=fopen(argv[1],"r"))==NULL) {
		fprintf(stderr,"오류 : %s 파일을 열 수 없습니다.\n", argv[1]);
		exit(1);
	}
	if ((sol=fopen(argv[2],"w"))==NULL) {
		fprintf(stderr,"오류 : %s 파일을 열 수 없습니다.\n", argv[2]);
		exit(1);
	}
	while (fscanf(pro,"%d + %d = \n", &opd1, &opd2)!=EOF)
		fprintf(sol,"%d + %d = %d\n", opd1, opd2, opd1+opd2);
	fclose(pro);
	fclose(sol);
	return 0;
}
