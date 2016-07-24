#include <stdio.h>
struct profile{
	int num;
	char name[20];
	char addr[20];
	char hobby[20];
	char food[20];
};

int main(void) {
	FILE *fp;
	int i;
	struct profile per;
	fp=fopen("my1.txt","w+");
	if (fp==NULL) {
		printf("파일 개방 실패");
		return 0;
	};
	while (1) {
		printf("학번을 입력하세요 : ");
		i=scanf("%d", &per.num);
		if (i==EOF || per.num==-1)
			break;
		printf("이름을 입력하세요 : ");
		scanf("%s", per.name);
		printf("주소를 입력하세요 : ");
		scanf("%s", per.addr);
		printf("취미를 입력하세요 : ");
		scanf("%s", per.hobby);
		printf("좋아하는 음식을 입력하세요 : ");
		scanf("%s", per.food);
		fprintf(fp,"%d %s %s %s %s\n", per.num, per.name, per.addr, per.hobby, per.food);
	}
	fclose(fp);
	return 0;
}
