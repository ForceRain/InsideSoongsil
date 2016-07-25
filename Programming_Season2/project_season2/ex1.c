#include <stdio.h>
#include <stdlib.h>
struct hwa_too{
	int num;
	char name[7];
	struct hwa_too *next;
};
void hwa_too_link(struct hwa_too *p)
{
	while((p+1)->num!=0) {
		p->next=p+1;
		p++;
	}
	p->next=NULL;
	return;
}
void print_hwa_too(struct hwa_too *pr)
{
	int i=0;
	while (1){
		printf("%2d%s ", pr->num, pr->name);
		pr++;
		i++;
		if (i==7)
			break;
	}
	return;
}
int main(void) {
	struct hwa_too init[48]={{1,"광",NULL},{1,"오",NULL},{1,"피1",NULL},{1,"피2", NULL},{2,"십",NULL},{2,"오",NULL},{2,"피1",NULL},{2,"피2",NULL},{3,"광",NULL},{3,"오",NULL},{3,"피1",NULL},{3,"피2",NULL},{4,"십",NULL},{4,"오",NULL},{4,"피1",NULL},{4,"피2",NULL},{5,"십",NULL},{5,"오",NULL},{5,"피1",NULL},{5,"피2",NULL},{6,"십",NULL},{6,"오",NULL},{6,"피1",NULL},{6,"피2",NULL},{7,"십",NULL},{7,"오",NULL},{7,"피1",NULL},{7,"피2",NULL},{8,"광",NULL},{8,"십",NULL},{8,"피1",NULL},{8,"피2",NULL},{9,"쌍피",NULL},{9,"오",NULL},{9,"피1",NULL},{9,"피2",NULL},{10,"십",NULL},{10,"오",NULL},{10,"피1",NULL},{10,"피2",NULL},{11,"광",NULL},{11,"쌍피",NULL},{11,"피1",NULL},{11,"피2",NULL},{12,"광",NULL},{12,"십",NULL},{12,"오",NULL},{12,"쌍피",NULL}};
	int check[48];
	int wow,i=0,j=0,k;
	char status[20]=" ";
	struct hwa_too player[3][7]={{0},{0},{0}};
	struct hwa_too eat[8]={0};
	srand(time(NULL));
	while (1) {
		wow=(rand()%48);
		if(check[wow]!=-1){
			player[i][j]=init[wow];
			check[wow]=-1;
			j++;
		}
		if(j==7)
		{
			i++;
			j=0;
		}
		if(i==3 && j==0)
			break;
	}
	i=0;
	while (1)
	{
		wow=(rand()%48);
		if(check[wow]!=-1)
		{
			eat[i]=init[wow];
			check[wow]=-1;
			i++;
		}
		if (i==8)
			break;
	}
#if DEBUG
	printf("이어짐 확인\n");
#endif
	for (i=0;i<3;i++) {
		hwa_too_link(player[i]);
	}
#if DEBUG
	printf("%d%s\n", player[2][0].next->num,player[2][0].next->name);
	printf("주소이용 출력\n");
#endif
	for (i=0; i<3; i++) {
		if (i==0)
			printf("A%10s : 패)",status);
		if (i==1)
			printf("B%10s : 패)",status);
		if (i==2)
			printf("C%10s : 패)",status);
		print_hwa_too(player[i]);
		printf("\n");
	}
	hwa_too_link(eat);
	printf("깔린 패 ) ");
	print_hwa_too(eat);
	printf("\n");
#if DEBUG
	printf("값 이용 출력\n");
	for (i=0;i<3;i++) {
		for (j=0; j<7; j++) {
			printf("%2d%s ",player[i][j].num,player[i][j].name);
		}
		printf("\n");
	} 
#endif
	return 0;
}
