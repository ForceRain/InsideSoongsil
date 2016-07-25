#include <stdio.h>			//파일 입출력으로는 링크드 리스트를 불러올때 첫 주소값만 참조하면 됨. 나머지는 이미 다 이어져 있음
struct hwa_too{
	int num;
	struct hwa_too *next;
};
int main(void)
{
	int i=0;
	struct hwa_too pae[2]={{1,NULL},{2,NULL}};
	struct hwa_too pae1[2]={{0,NULL}};
	FILE *ofp, *ifp;
	ofp=fopen("read.txt","wb+");
	pae[0].next=&pae[1];
	while(1)
	{
		fwrite(&pae[i],sizeof(struct hwa_too),1,ofp);
		i++;
		if (i==2)
			break;
	}
	fclose(ofp);
	i=0;
	ifp=fopen("read.txt","rb");
	while(1)
	{
		fread(&pae1[i],sizeof(struct hwa_too),1,ifp);
		//i++;
		//if (i==2)
			break;
	}
	printf("%d\n%d%u\n", pae1[0].num, (pae1[0].next)->num,(pae1[0].next)->next);
	fclose(ifp);
	return 0;
}
