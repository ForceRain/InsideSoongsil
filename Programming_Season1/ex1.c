#include <stdio.h>
#include <string.h>
#include <stdlib.h>
struct profile {
	char name[80];
	char num[80];
};

int main(void) {
	FILE *fp;
	struct profile per;
	int i,n=0;
	struct profile *mp,*mp1;
	fp=fopen("phone.bin","wb+");
	fseek(fp,0,SEEK_SET);
	fseek(fp,0,SEEK_END);
	n=ftell(fp)/sizeof(struct profile);
	mp=(struct profile *)malloc(sizeof(struct profile)*(n+1));
	mp1=mp;
	printf("%d\n",n);
	fseek(fp,sizeof(struct profile)*n,SEEK_SET);
	for (i=0;i<n;i++) {
		mp++;
		i++;
	}
	printf("이름과 전화번호를 입력하셈 : ");
	scanf("%s %s",per.name,per.num);
	strcpy((*mp).name,per.name);
	strcpy((*mp).num,per.num);
	fwrite(mp,sizeof(struct profile),1,fp);
	fflush(fp);

	mp=mp1;
	printf("입력끝\n");
	for (i=0;i<n+1;i++) {
		fread(mp,sizeof(struct profile),1,fp);
		printf("%s %s\n", (*mp).name,(*mp).num);
		mp++;
	}
	fclose(fp);
	return 0;
}
