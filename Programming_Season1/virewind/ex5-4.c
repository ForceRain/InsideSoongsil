#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define N 3
struct profile{
	char name[20];
	int st_num;
	char grade[3];
};

int main(void) {
	struct profile *p;
	int i,j;
	int rank[3]={0};
	char table[9][3]={"A+", "A0", "A-", "B+", "B0", "B-", "C+", "C0", "C-"};
	p=(struct profile *)malloc(sizeof(struct profile)*N);
	for (i=0; i<N; i++) {
		printf("이름, 학번, 학점입력 : ");
		scanf("%s %d %s", p[i].name, &p[i].st_num, p[i].grade);
	
	}
	for (i=0; i<N; i++) {
		for (j=0; j<9; j++) {
		if (strcmp(p[i].grade,table[j])==0)
			rank[i]=9-j;
		}
	}
	for (i=0; i<9; i++) {
		for (j=0; j<N; j++) {
			if (strcmp(p[j].grade,table[i])==0) {
				printf("%s %d %s\n", p[j].name, p[j].st_num, p[j].grade);
				break;
			};
		}
	}
	
	return 0;
}
