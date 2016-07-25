#include <stdio.h>
struct profile{
	int ID;
	int num1;
	int num2;
	char name[10];
	int pay;
};
int main(void)
{
	struct profile per;
	FILE *ifp,*ofp;
	int end;
	ifp=fopen("info.txt","r");
	ofp=fopen("info","wb");
	end=fscanf(ifp,"%d %d %d %s %d",&per.ID,&per.num1,&per.num2,per.name,&per.pay);
	while(1){
		if (end==EOF)
			break;
		fwrite(&per,sizeof(struct profile),1,ofp);
		end=fscanf(ifp,"%d %d %d %s %d",&per.ID,&per.num1,&per.num2,per.name,&per.pay);
	}
	fclose(ifp);
	fclose(ofp);
	return 0;	
}
