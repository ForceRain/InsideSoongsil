#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define N 100
struct profile 
{
	char name[N];
	char num[N];
};
void func1(struct profile *mp1,int *i) 
{
	int n=-1;
	while (n!=*i)
	{
		printf("%s %s\n",(*mp1).name,(*mp1).num);
		mp1++;
		n++;
	};
	return;
}
void func2(struct profile *mp2,int *i)
{
	int n=-1;
	char name[N];
	printf("이름을 입력하세요 : ");
	scanf("%s", name);
	while (n!=*i) {
		if (strcmp(name,(*mp2).name)==0)
		{
			printf("%s의 전화번호는 %s입니다.", (*mp2).name,(*mp2).num);
			break;
		};
		mp2++;
		n++;
	};
	return;
}
void func3(struct profile *mp3, int *i)
{
	char name[N];
	char change[N];
	int n=-1;
	printf("이름을 입력하세요 : ");
	scanf("%s", name);
	printf("바꿀 전화번호를 입력하세요 : ");
	scanf("%s", change);
	while (n!=*i) {
		if (strcmp((*mp3).name,name)==0)
		{
			strcpy((*mp3).num,change);
			break;
		}
		mp3++;
		n++;
	}
	return;
}
void func4(struct profile *mp4,int *i)
{
	int n=0;
	char pl_name[N];
	char pl_num[N];
	printf("추가할 친구의 이름과 전화번호를 입력하세요 : ");
	scanf("%s%s", pl_name, pl_num);
	mp4=(struct profile *)realloc(mp4,sizeof(struct profile)*(*i+1));
	for (n=0; n<*i; n++) {
		mp4++;
	};
	strcpy((*mp4).name,pl_name);
	strcpy((*mp4).num,pl_num);
	return;
}
void func5(struct profile *mp5,int *i)
{
	int n=-1;
	char name[N];
	char NULL_name[N]={0};
	char NULL_num[N]={0};
	printf("삭제할 친구의 이름을 입력하세요 : ");
	scanf("%s", name);
	while (n!=*i) {
		if (strcmp((*mp5).name,name)==0)
		{
			strcpy((*mp5).name,NULL_name);
			strcpy((*mp5).num,NULL_num);
			break;
		};
		n++;
		mp5++;
	}
	return;
}
int main(void) 
{
	FILE *fp;
	struct profile *mp;
	struct profile p;
	int n=0,input;
	int i;
	int check;
	fp=fopen("my.bin", "wb+");
	fseek(fp,0,SEEK_END);
	check=ftell(fp);
	i=check/sizeof(struct profile);
	mp=(struct profile *)malloc(sizeof(struct profile)*i);
	for (n=0; n<i; n++) {
		fread(mp+n,sizeof(struct profile),1,fp);
	};
	fseek(fp,0,SEEK_SET);
	printf("%d\n",i);
	printf("1) 전체 전화번호 출력                2) 이름으로 검색 후 전화번호 출력\n");
	printf("3) 이름으로 검색 후 전화번호 수정    4) 친구 추가\n");
	printf("5) 친구 삭제                         6) 종료\n");
	scanf("%d", &input);
	switch (input) {
		case 1: 
			func1(mp,&i);
			break;
		case 2:
			func2(mp,&i);
			break;
		case 3:
			{
			func3(mp,&i);
			fwrite(mp,sizeof(struct profile),i,fp);
			};
			break;
		case 4:
			{
			func4(mp,&i);
			fwrite(mp,sizeof(struct profile),i,fp);
			};
			break;
		case 5:
			{
			func5(mp,&i);
			fwrite(mp,sizeof(struct profile),i,fp);
			};
			break;
		case 6:
			return 0;
			break;
	};
	for (n=0;n<i;n++) {
		printf("%s %s\n", (*mp).name,(*mp).num);
		mp++;
	};
	printf("%d\n",i);
	fclose(fp);
	return 0;
}
			

		
