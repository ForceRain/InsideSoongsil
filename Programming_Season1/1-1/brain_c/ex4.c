#include <stdio.h>
#include <string.h>
	struct student {
		char name[20];
		int num;
		char grade[3];
		};
int main(void) {
	struct student arr[3];
	int rank[3]={0};
	int rank_1[3]={0};
	char check[9][3]={"A+", "A0", "A-", "B+", "B0", "B-", "C+", "C0", "C-"};
	int i,j,temp,max;
		for (i=0; i<3; i++) {
			printf("이름, 학번, 학점 순으로 입력하세요 : ");
			scanf("%s %d %s",arr[i].name,&arr[i].num,arr[i].grade);
			};
		for (j=0,i=0; j<9; j++) {
			if(strcmp(check[j],arr[i].grade)==0) {
				rank[i]=9-j;
				rank_1[i]=9-j;
				i++;
				j=0;
				};
		};
		for (i=0; i<2; i++) {
			if (rank[i]<rank[i+1]) {
				temp=rank[i];
				rank[i]=rank[i+1];
				rank[i+1]=temp;
			};
		};
		for (j=0,i=0; j<3; j++) {
			if (rank_1[j]==rank[i]) {
				printf("%15s %10d %3s\n",arr[j].name, arr[j].num, arr[j].grade);
				i++;
				j=0;
				};
			if (i==3)
			break;	
			};
	return 0;
}

