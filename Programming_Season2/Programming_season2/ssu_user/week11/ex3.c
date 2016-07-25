#include <stdio.h>
struct profile{
	int ID;
	int part_num;
	int lev_num;
	char name[10];
	int pay;
};
struct part{
	int num;
	char part_name[15];
};
struct position{
	int i;
	char posi_name[15];
};
int main(int argc, char *argv[])
{
	FILE *ifp1, *ifp2, *ifp3;
	struct profile per1;
	struct part per2;
	struct position per3;
	int end,end1,end2;
	ifp1=fopen(argv[1],"r");
	ifp2=fopen(argv[2],"r");
	ifp3=fopen(argv[3],"r");
	while(1)
	{
		end=fread(&per1,sizeof(struct profile),1,ifp1);
		if(end==0)
			break;
		printf("%d ",per1.ID);
		while (1) {
			end1=fread(&per2,sizeof(struct part),1,ifp2);
			if (end1==0)
				rewind(ifp2);
			if(per1.part_num==per2.num){
				printf("%-15s ", per2.part_name);
				break;
			}
		}
		while (1) {
			end2=fread(&per3,sizeof(struct position),1,ifp3);
			if (end2==0)
				rewind(ifp3);
			if(per1.lev_num==per3.i){
				printf("%-15s ",per3.posi_name);
				break;
			}
		}
		printf("%-10s %d\n",per1.name,per1.pay);
	}
	fclose(ifp1);
	fclose(ifp2);
	fclose(ifp3);
	return 0;
}
