#include <stdio.h>
struct profile{
	int num;
	char name[20];
	char addr[20];
	char hobby[20];
	char food[20];
};
int main(void) {
	struct profile per;
	FILE *fp;
	int i=1;
	fp=fopen("my1.txt", "r");
	if (fp==NULL) {
		printf("파일 개방 실패!");
		return 0;
	}
	i=fscanf(fp,"%d %s %s %s %s\n",&per.num, per.name,per.addr, per.hobby, per.food);
	while (i!=-1) {
		printf("%d %s %s %s %s\n", per.num, per.name, per.addr, per.hobby, per.food);
		i=fscanf(fp,"%d %s %s %s %s\n",&per.num, per.name,per.addr, per.hobby, per.food);
		};
	fclose(fp);
	return 0;
}
