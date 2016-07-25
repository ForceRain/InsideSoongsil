#include <stdio.h>
#include <stdlib.h>
#include <string.h>
struct profile {
	char name[40];
	char num[40];
};

int main(void) {
	struct profile p, *pp;
	FILE *fp;
	int key,i, n, count=0, press=0;
	char find_name[40]={0},change_name[40]={0},change_num[40]={0};
	fp=fopen("phonebook.bin", "rb+");
	if (fp==NULL) {
		printf("파일 개방 실패 !");
		return 0;
	}
	while (1) {
		n=fread(&p,sizeof(struct profile), 1,fp);
		if (n==0)
			break;
		count++;
	}
	pp=(struct profile *)malloc(sizeof(struct profile)*n);
	fseek(fp, sizeof(struct profile)*count, SEEK_SET);
	while (1) {
		printf("\n1. 연락처 추가       2. 연락처 수정\n");
		printf("3. 연락처 출력        4. 연락처 삭제\n");
		printf("5. 프로그램 종료\n입력 : ");
		scanf("%d", &press);
	switch (press) {
		case 1 : 
			{
				while (1) {
					printf("이름을 입력하세요 : ");
					i=scanf("%s", p.name);
					if (i==EOF) {
						printf("연락처 입력을 종료합니다.\n");
						break;
					}
					printf("전화번호를 입력하세요 : ");
					scanf("%s", p.num);
					fwrite(&p, sizeof(struct profile), 1, fp);
				}
			}
			break;
		case 2 :
			{
				fseek(fp,0,SEEK_SET);
				while(1) {
					printf("수정할 전화번호의 사용자를 입력하세요. 되돌아가려면 ctrl+D를 입력하세요.\n");
					i=scanf("%s",find_name);
					if (i==EOF) {
						printf("연락처 수정을 종료합니다.\n");
						break;
					}
					while(1) {
						n=fread(&p, sizeof(struct profile), 1, fp);
						if (strcmp(p.name,find_name)==0) {
							printf("수정할 전화번호를 입력하세요 : ");
							scanf("%s", change_num);
							strcpy(p.num,change_num);
							fseek(fp,-sizeof(struct profile), SEEK_CUR);
							fwrite(&p,sizeof(struct profile), 1, fp);
							break;
						}
					}
				}
			}
			break;
		case 3 :
			{
				fseek(fp,0,SEEK_SET);
				n=fread(&p, sizeof(struct profile), 1, fp);
				while (1) {
					printf("%20s %30s\n", p.name, p.num);
					n=fread(&p, sizeof(struct profile), 1, fp);
					if (n==0)
						break;
					}
			}
			break;
		case 4 : 
			{
				fseek(fp,0,SEEK_SET);
				while (1) {
					printf("삭제할 전화번호의 사용자를 입력하세요. 되돌아가려면 ctrl+D를 입력하세요.\n");
					i=scanf("%s", find_name);
					if (i==EOF) {
						printf("연락처 삭제를 종료합니다.\n");
						break;
					}
					while (1) {
						n=fread(&p, sizeof(struct profile), 1, fp);
						if (strcmp(p.name,find_name)==0) {
							strcpy(p.name,change_name);
							strcpy(p.num,change_num);
							fseek(fp,-sizeof(struct profile), SEEK_CUR);
							fwrite(&p, sizeof(struct profile), 1, fp);
							break;
						}
					}
				}
			}
			break;
		case 5 :
			{
			free(pp);
			fclose(fp);
			return 0;
			}
			break;
		default :
			printf("잘못된 입력입니다.\n");
			break;
	}
	}

	return 0;
}
