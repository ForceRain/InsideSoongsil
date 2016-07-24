#include <stdio.h>
struct profile{
	int num;
	char name[20];
	int age;
	double height;
	char addr[20];
};

int main(void) {
	struct profile *p;
	FILE *fp;
	fp=fopen("student.txt", "w+");
	int i,j,n=0;
	while (1) {
		printf("학번을 입력하세요 : ");
		i=scanf("%d", &(*p).num);
		if (i==EOF || (*p).num==-1)
			return 0;
		printf("이름을 입력하세요 : ");
		scanf("%s", (*p).name);
		printf("나이를 입력하세요 : ");
		scanf("%d", &(*p).age);
		printf("키를 입력하세요 : ");
		scanf("%lf", &(*p).height);
		printf("주소를 입력하세요 : ");
		scanf("%s", (*p).addr);
		fprintf(fp,"%d \n%s %d %.1lf\n%s\n",(*p).num,(*p).name,(*p).age,(*p).height,(*p).addr);
		p++;
	}
	fclose(fp);
	return 0;
}
