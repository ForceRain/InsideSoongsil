#include <stdio.h>
#include <stdlib.h>
#include <string.h>
struct profile{
	char ID[10];
	int pass;
};
void check(struct profile *p, int re) {
	int i;
	char check_ID[10];
	printf("ID를 입력하세요 : ");
	scanf("%s", check_ID);
	for (i=0; i<re; i++) {
		if (strcmp(check_ID,(*p).ID)==0) {
			printf("%s의 비밀번호는 %d입니다.\n", (*p).ID, (*p).pass);
			break;
		}
	}
	return;
}

void rewrite(struct profile *p, int re) {
	int i;
	int new_num;
	char check_ID[10];
	printf("ID를 입력하세요 : ");
	scanf("%s", check_ID);
	printf("새로운 비밀번호를 입력하세요 : ");
	scanf("%d",&new_num);
	for (i=0; i<re;i++) {
		if (strcmp(check_ID,(*p).ID)==0)
			(*p).pass=new_num;
	}
	return;
}
int main(int argc, char *argv[]) {
	struct profile *p,*rp;
	int re;
	int i;
	re=atoi(argv[1]);
	p=(struct profile *)malloc(sizeof(struct profile)*re);
	rp=p;
	for (i=0; i<re; i++) {
		printf("ID를 입력하세요 : ");
		scanf("%s", (*p).ID);
		printf("비밀번호를 입력하세요 : ");
		scanf("%d", &(*p).pass);
		p++;
	}
	p=rp;
	check(p,re);
	rewrite(p,re);
	printf("ID        pass\n");
	printf("--------------\n");
	for (i=0; i<re; i++) {
		printf("%-10s %d\n", (*p).ID, (*p).pass);
		p++;
	}
	return 0;
}

