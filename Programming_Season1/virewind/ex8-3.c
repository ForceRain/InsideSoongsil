#include <stdio.h>
struct person{
	int num;
	char name[40];
	char addr[40];
	char hobby[40];
	char favor[40];
};

int main(void) {
	struct person per;
	FILE *fp;
	int i;
	fp=fopen("my.bin","wb+");
	while (1) {
		printf("학번, 이름, 주소, 취미, 음식 : ");
		i=scanf("%d", &per.num);
		if(i==EOF)
			break;
		getchar();
		scanf("%s%s%s%s",per.name,per.addr,per.hobby,per.favor);
		fwrite(&per,sizeof(struct person),1,fp);
		fflush(fp);
	}
	fclose(fp);
	return 0;
}
