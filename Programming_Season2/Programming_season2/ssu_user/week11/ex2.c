#include <stdio.h>
struct part{
	int num;
	char name[15];
};

int main(int argc, char *argv[])
{
	int end;
	struct part per;
	FILE *ifp;
	ifp=fopen(argv[1],"w");
	printf("부서번호와 부서명을 입력하세요.\n");
	while(1)
	{
		end=scanf("%d %s",&per.num,per.name);
		if (end==EOF)
			break;
		else
			fwrite(&per,sizeof(struct part),1,ifp);
	}
	fclose(ifp);
	return 0;
}
