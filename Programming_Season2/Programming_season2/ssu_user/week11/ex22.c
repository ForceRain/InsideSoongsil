#include <stdio.h>
struct posi{
	int i;
	char name[15];
};
int main(int argc,char *argv[])
{
	FILE *ifp;
	int end;
	struct posi per;
	ifp=fopen(argv[1],"w");
	printf("직급번호와 직급명을 입력하세요.\n");
	while (1)
	{
		end=scanf("%d %s",&per.i,per.name);
		if (end==EOF)
			break;
		else
			fwrite(&per,sizeof(struct posi),1,ifp);
	}
	fclose(ifp);
	return 0;
}
