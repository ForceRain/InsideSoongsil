#include <stdio.h>
#include <stdlib.h>
#include <string.h>
struct profile {
	int num;
	char name[40];
	char addr[40];
	char hobby[40];
	char favor[40];
};

int main(void) {
	struct profile per;
	FILE *ifp;
	int num,i;
	char addr[40]={0};
	ifp=fopen("my.bin","rb+");
	printf("학번과 바꿀 주소를 입력하세요 : ");
	scanf("%d %s", &num,addr);
	while (1) {
		i=fread(&per,sizeof(struct profile),1,ifp);
		if (num==per.num) {
			strcpy(per.addr,addr);
			fseek(ifp,-sizeof(struct profile),SEEK_CUR);
			fwrite(&per,sizeof(struct profile),1,ifp);
			fflush(ifp);
		}
		if (i==0)
			break;
	}
	fclose(ifp);
	return 0;
}

