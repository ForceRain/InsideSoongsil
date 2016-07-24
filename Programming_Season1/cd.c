#include <stdio.h>
#include <string.h>
#include <stdlib.h>
typedef struct profile{
	char name[30];
	char tel[30];
}profile;

int main(void) {
	profile p;
	profile *mp,*mp1;
	FILE *fp;
	int input;
	int n=0,i,k;
	char name[30]={0};
	char NULL_name[30]={0};
	char NULL_num[30]={0};
	char num[30]={0};
	mp1=mp;
	fp=fopen("friend1.bin","rb+");
	fseek(fp,0,SEEK_END);
	n=ftell(fp);
	i=n/sizeof(profile);
	mp=(profile *)malloc(sizeof(profile)*i);
	for (k=0; k<i;k++) {
		fread(mp+k,sizeof(profile),1,fp);
	}
	mp=mp1;
	printf("%d %d\n", i,n);
	printf("1) 전체 전화번호 출력                  2) 이름으로 검색 후 전화번호 출력\n");
	printf("3) 이름으로 검색 후 전화번호 수정      4) 친구 추가\n");
	printf("5) 친구 삭제                           6) 종료\n");
	scanf("%d", &input);
	switch (input) {
		case 1:
			{
				fseek(fp,0,SEEK_SET);
				for (k=0; k<i;k++) {
					fread(mp,sizeof(profile),1,fp);
					printf("%s %s\n", (*mp).name, (*mp).tel);
					mp++;
				};
			}
			break;
		case 2:
			{
				printf("이름을 입력하세요 : ");
				scanf("%s", name);
				fseek(fp,0,SEEK_SET);
				for (k=0; k<i;k++) {
					fread(mp,sizeof(profile),1,fp);
					if (strcmp((*mp).name,name)==0)
					{
						printf("%s의 전화번호는 %s입니다.",name,(*mp).tel);
					}
					mp++;
				}
			}
			break;
		case 3:
			{
				printf("이름을 입력하세요 : ");
				scanf("%s", name);
				printf("바꿀 전화번호를 입력하세요 : ");
				scanf("%s", num);
				fseek(fp,0,SEEK_SET);
				for (k=0; k<i;k++) {
					fread(mp,sizeof(profile),1,fp);
					if (strcmp((*mp).name,name)==0)
					{
						strcpy((*mp).tel,num);
						fseek(fp,-sizeof(profile),SEEK_CUR);
						fwrite(mp,sizeof(profile),1,fp);
						fflush(fp);
					}
					mp++;
				}
			}
			break;
		case 4:
			{
				printf("추가할 친구의 이름을 입력하세요 : ");
				scanf("%s", name);
				printf("추가할 친구의 전화번호를 입력하세요 : ");
				scanf("%s", num);
				mp=(profile *)realloc(mp,sizeof(profile)*(i+1));
				//mp=mp+sizeof(profile)*i;
				for (k=0;k<i+1;k++) {
					mp++;
				};
				strcpy((*mp).name,name);
				strcpy((*mp).tel,num);
				fwrite(mp,sizeof(profile),1,fp);
				fflush(fp);
			}
			break;
		case 5:
			{
				printf("제거할 친구의 이름을 입력하세요 : ");
				scanf("%s", name);
				fseek(fp,0,SEEK_SET);
				for (k=0; k<i; k++) {
					//mp=mp+sizeof(profile);
					fread(mp,sizeof(profile),1,fp);
					if (strcmp((*mp).name,name)==0) {
						strcpy((*mp).name,NULL_name);
						strcpy((*mp).tel,NULL_num);
						fseek(fp,-sizeof(profile),SEEK_CUR);
						fwrite(mp,sizeof(profile),1,fp);
						fflush(fp);
					}
					mp++;
				}
			}
			break;
		case 6:
			return 0;
			break;
	};
	fclose(fp);
	return 0;
}
