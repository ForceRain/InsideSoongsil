#include <stdio.h>
#include <stdlib.h>
#include <string.h>
struct hwa_too{
	struct hwa_too *head;
	int num;
	char name[7];
	int check_num;														//같은거 처먹을때 표현하는 1,2
	struct hwa_too *next;
};
struct hwa_too *make_hwa_too(struct hwa_too init[], int check[],int num)
{
	int random;
	struct hwa_too *p;
	while(1)
	{
		random=rand()%48;
		if(check[random]!=-1)
		{
			p=(struct hwa_too *)malloc(sizeof(struct hwa_too));
			*p=init[random];
			p->head=p;
			num--;
			if(num==0)
				break;
			else
			{
				p->next=make_hwa_too(init,check,num);
				break;
			}
		}
	}
	return p;
}
void print_hwa_too(struct hwa_too *p)
{
	printf("%2d%s ",p->num,p->name);
	if(p->next==NULL)
		return;
	else
		print_hwa_too(p->next);
}
void sort_hwa_too(struct hwa_too *p[])
{
	int i,j;
	struct hwa_too temp;
	for (i=0;i<7;i++)
	{
		for(j=7;j>i;j--)
		{
			if((p[j-1]->num>p[j]->num) && p[j]->next!=NULL)
			{
					temp=*p[j-1];
					*p[j-1]=*p[j];
					*p[j]=temp;
			}
		}
	}
	return;
}
int main(void) {
	struct hwa_too init[48]={{NULL,1,"광",0,NULL},{NULL,1,"오",0,NULL},{NULL,1,"피1",0,NULL},{NULL,1,"피2",0,NULL},{NULL,2,"십",0,NULL},{NULL,2,"오",0,NULL},{NULL,2,"피1",0,NULL},{NULL,2,"피2",0,NULL},{NULL,3,"광",0,NULL},{NULL,3,"오",0,NULL},{NULL,3,"피1",0,NULL},{NULL,3,"피2",0,NULL},{NULL,4,"십",0,NULL},{NULL,4,"오",0,NULL},{NULL,4,"피1",0,NULL},{NULL,4,"피2",0,NULL},{NULL,5,"십",0,NULL},{NULL,5,"오",0,NULL},{NULL,5,"피1",0,NULL},{NULL,5,"피2",0,NULL},{NULL,6,"십",0,NULL},{NULL,6,"오",0,NULL},{NULL,6,"피1",0,NULL},{NULL,6,"피2",0,NULL},{NULL,7,"십",0,NULL},{NULL,7,"오",0,NULL},{NULL,7,"피1",0,NULL},{NULL,7,"피2",0,NULL},{NULL,8,"광",0,NULL},{NULL,8,"십",0,NULL},{NULL,8,"피1",0,NULL},{NULL,8,"피2",0,NULL},{NULL,9,"쌍피",0,NULL},{NULL,9,"오",0,NULL},{NULL,9,"피1",0,NULL},{NULL,9,"피2",0,NULL},{NULL,10,"십",0,NULL},{NULL,10,"오",0,NULL},{NULL,10,"피1",0,NULL},{NULL,10,"피2",0,NULL},{NULL,11,"광",0,NULL},{NULL,11,"쌍피",0,NULL},{NULL,11,"피1",0,NULL},{NULL,11,"피2",0,NULL},{NULL,12,"광",0,NULL},{NULL,12,"십",0,NULL},{NULL,12,"오",0,NULL},{NULL,12,"쌍피",0,NULL}};
	int check[48]={0};
	int wow,i=0,j=0,command,turn=0,tot=0,sort=0;
	char status[20]=" ";
	char system_command[7]=" ";
	struct hwa_too *player[3]={NULL};
	struct hwa_too *player_add[3][7]={{NULL}};
	struct hwa_too *eat_add[48]={NULL};
	struct hwa_too *eat_player[3]={NULL};
	struct hwa_too *eat=NULL;
	struct hwa_too *dummy=NULL;
	struct hwa_too *dummy_add[21]={NULL};
	srand(time(NULL));
	for(i=0;i<3;i++)
	{
#if DEBUG
		printf("작동시작\n");
#endif
		player[i]=make_hwa_too(init,check,7);
#if DEBUG
		printf("작동완료\n");
#endif
	}
#if DEBUG
	printf("됨.\n");
#endif
	eat=make_hwa_too(init,check,6);
#if DEBUG
	printf("됨.\n");
#endif
	dummy=make_hwa_too(init,check,21);
#if DEBUG
	printf("됨.\n");
#endif
	i=0;
	//sort_hwa_too(player_add[i]);
#if DEBUG
	printf("됨.\n");
#endif
	while(1){
		for (i=0; i<3; i++) {
			//print_turn(turn,i);
			if (i==0)
				printf("A%10s : 패)",status);
			if (i==1)
				printf("B%10s : 패)",status);
			if (i==2)
				printf("C%10s : 패)",status);
			print_hwa_too(player[i]);
			printf("\n(점수 : /**/) 딴 화투) \n");
			printf("\n");
		}
		printf("깔린 패) ");
		print_hwa_too(eat);
		printf("\n\n");
		printf("명령 ? ");
		scanf("%s",system_command);
		if(strcmp(system_command,"g")==0);
		else if(strcmp(system_command,"s")==0);
		else if(strcmp(system_command,"e")==0)
		{
			printf("고스톱 게임을 종료합니다.");
			return 0;
		}
		else if(strcmp(system_command,"b")==0);
		else if(strcmp(system_command,"h")==0);
		else if(strcmp(system_command,"save")==0);
		else if(strcmp(system_command,"load")==0);
		else if(0<atoi(system_command) && atoi(system_command)<8)
		{
		}
		else
		{
			printf("잘못된 명령입니다.\n");
			sleep(2);
		}
		tot++;
		turn++;
		if(turn==3)
			turn=0;
	}
	return 0;
}
