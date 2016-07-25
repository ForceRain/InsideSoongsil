#include <stdio.h>
#include <string.h>
struct profile{
	char name[20];
	char mail[40];
	char num[20];
};
void one_print(struct profile per[]){
	char tmp_name[20];
	int i;
	printf("찾으려는 학생의 이름을 입력하세요 : ");
	scanf("%s", tmp_name);
	for (i=0; i<10; i++) {
		if (strcmp(per[i].name,tmp_name)==0) {
			printf("%s학생의 이메일은 %s, 전화번호는 %s입니다.\n", per[i].name, per[i].mail, per[i].num);
			break;
		}
	}
}
void all_print(struct profile per[]) {
	int i;
	printf("전체 출력을 실시하겠습니다.\n");
	for (i=0; i<10; i++) {
		printf("%s 학생의 이메일은 %s, 전화번호는 %s입니다.\n", per[i].name, per[i].mail, per[i].num);
	}
}
int main(void) {
	struct profile per[10];
	int i, choose;
	for (i=0; i<10; i++) {
		printf("%d번째 학생의 이름을 입력하세요 : ", i+1);
		scanf("%s", per[i].name);
		printf("%d번째 학생의 이메일 주소를 입력하세요 : ", i+1);
		scanf("%s", per[i].mail);
		printf("%d번째 학생의 전화번호를 입력하세요 : ", i+1);
		scanf("%s", per[i].num);
	}
	while (1) {
		printf("\n\n1. 이름검색, 2. 전체검색, 3.종료\n");
		scanf("%d", &choose);
		if (choose==1)
			one_print(per);
		else if (choose==2)
			all_print(per);
		else if (choose==3)
			return 0;
	}
	return 0;
}
