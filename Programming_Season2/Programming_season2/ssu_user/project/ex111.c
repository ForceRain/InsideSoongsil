#include <stdio.h>
#include <stdlib.h>
#include <string.h>
struct hwa_too{
	int num;
	char name[7];
	struct hwa_too *next;
};
void hwa_too_link(struct hwa_too *p[])
{
	int i=0;
	while(p[i+1]!=NULL) {
		p[i]->next=p[i+1];
		i++;
	}
	if (i==0)
		return;
	else
		p[i-1]->next=NULL;
	return;
}
void print_hwa_too(struct hwa_too *pr[],int N)
{
	int i,max=0,check=0;
	for (i=0;i<N;i++,max++) {
		if (pr[i]==NULL)
			break;
	}
	while (1){
		printf("%2d%s ", (*pr)->num, (*pr)->name);
		pr++;
		check++;
		if (check==max)
			break;
	}
	return;
}
void print_turn(int j,int i)
{
	if (j==0 && i==0)
	{
		printf("%1c",'*');
		return;
	}
	if (j==1 && i==1)
	{
		printf("%1c",'*');
		return;
	}
	if (j==2 && i==2)
	{
		printf("%1c",'*');
		return;
	}
	printf(" ");
}
void sorting_hwa_too(struct hwa_too *p[],int N) {
	int i,j,max=0;
	struct hwa_too temp;
	for (i=0; i<N; i++,max++){
		if (p[i]==NULL)
			break;
	}
	for (i=0; i<max; i++){
		for (j=max-1; j>i; j--) {
			if (p[j-1]->num>p[j]->num) {
				temp=*p[j-1];
				*p[j-1]=*p[j];
				*p[j]=temp;
			}
		}
	}
	return;
}
void input_hwa_too(struct hwa_too *plus[],struct hwa_too pae)
{
	if (plus==NULL)
	{
		**plus=pae;
#if DEBUG
		printf("출력 : %2d%s",(*plus)->num,(*plus)->name);
#endif
		return;
	}
	while(plus!=NULL)
	{
		plus++;
	}
	**plus=pae;
#if DEBUG
		printf("출력 : %2d%s",(*plus)->num,(*plus)->name);
#endif
	return;
}
/*void play_hwa_too(int num, struct hwa_too *player[], struct hwa_too *eat[], struct hwa_too *eat_player, struct hwa_too *dummy, char string[])
{
	int i=0;
	while(1) {
		if(player[num]->num==eat[i]->num)
		{
			if(dummy->num==player[num]->num)
			{
				strcpy(string,"설사!");
				input_hwa_too(eat,*dummy);
				return;
			}
			else
			{
				input_hwa_too(eat_player,*player[num]);
				input_hwa_too(eat_player,*eat[i]);
				return;
			}
		}																//일반적인 먹는 경우
		else if(dummy->num==eat[i]->num);
	}
} */
void print_have_hwa_too(struct hwa_too *have)
{
	if(have==NULL)
	{
		return;
	}
	else
	{
		printf("%2d%s", have->num,have->name);
		print_have_hwa_too(have->next);
	}
	return;
}
int main(void) {
	struct hwa_too init[48]={{1,"광",NULL},{1,"오",NULL},{1,"피1",NULL},{1,"피2", NULL},{2,"십",NULL},{2,"오",NULL},{2,"피1",NULL},{2,"피2",NULL},{3,"광",NULL},{3,"오",NULL},{3,"피1",NULL},{3,"피2",NULL},{4,"십",NULL},{4,"오",NULL},{4,"피1",NULL},{4,"피2",NULL},{5,"십",NULL},{5,"오",NULL},{5,"피1",NULL},{5,"피2",NULL},{6,"십",NULL},{6,"오",NULL},{6,"피1",NULL},{6,"피2",NULL},{7,"십",NULL},{7,"오",NULL},{7,"피1",NULL},{7,"피2",NULL},{8,"광",NULL},{8,"십",NULL},{8,"피1",NULL},{8,"피2",NULL},{9,"쌍피",NULL},{9,"오",NULL},{9,"피1",NULL},{9,"피2",NULL},{10,"십",NULL},{10,"오",NULL},{10,"피1",NULL},{10,"피2",NULL},{11,"광",NULL},{11,"쌍피",NULL},{11,"피1",NULL},{11,"피2",NULL},{12,"광",NULL},{12,"십",NULL},{12,"오",NULL},{12,"쌍피",NULL}};
	int check[48]={0};
	int wow,i=0,j=0,command,turn=0,tot=0,compare=0;
	char status[20]=" ";
	char system_command[7]=" ";
	struct hwa_too *player[3][7]={{NULL}};
	struct hwa_too *eat_player[3]={NULL};
	struct hwa_too *eat[10]={NULL};
	struct hwa_too *dummy[21]={NULL};
	srand(time(NULL));
#if DEBUG
	printf("작동\n");
#endif
	while (1) {
		wow=(rand()%48);
		if(check[wow]!=-1){
			player[i][j]=(struct hwa_too *)malloc(sizeof(struct hwa_too));			//player[0]=A, player[1]=B, player[2]=C
			*player[i][j]=init[wow];
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
	}																				//임의의 패를 줌
	i=0;
#if DEBUG
	printf("작동\n");
#endif
	while (1)
	{
		wow=(rand()%48);
		if(check[wow]!=-1)
		{
			eat[i]=(struct hwa_too *)malloc(sizeof(struct hwa_too));
			*eat[i]=init[wow];
			check[wow]=-1;
			i++;
		}
		if (i==6)
			break;
	}																				//깔리는 패들
	i=0;
#if DEBUG
	printf("작동\n");
#endif
	while (1)
	{
		wow=(rand()%48);
		if(check[wow]!=-1)
		{
			dummy[i]=(struct hwa_too *)malloc(sizeof(struct hwa_too));
			*dummy[i]=init[wow];
			check[wow]=-1;
			i++;
		}
		if(i==21)
			break;
	}																				//21개의 화투 더미
#if DEBUG
	printf("작동\n");
#endif
	while (1) {
		system("clear");
		for (i=0;i<3;i++) {
			sorting_hwa_too(player[i],sizeof(player[i])/sizeof(*player[i]));
#if DEBUG
	printf("작동1\n");
#endif
			sorting_hwa_too(eat,sizeof(eat)/sizeof(*eat));
#if DEBUG
	printf("작동2\n");
#endif
			sorting_hwa_too(eat_player,sizeof(eat_player)/sizeof(*eat_player));
#if DEBUG
	printf("작동3\n");
#endif
			hwa_too_link(player[i]);						//여기에는 다른 함수가 들어갈것
#if DEBUG
	printf("작동4\n");
#endif
			hwa_too_link(eat);
#if DEBUG
	printf("작동5\n");
#endif
			hwa_too_link(eat_player);
#if DEBUG
	printf("작동6\n");
#endif
		}
		for (i=0; i<3; i++) {
			print_turn(turn,i);
			if (i==0)
				printf("A%10s : 패)",status);
			if (i==1)
				printf("B%10s : 패)",status);
			if (i==2)
				printf("C%10s : 패)",status);
			print_hwa_too(player[i],sizeof(player[i])/sizeof(*player[i]));
			printf("\n(점수 : /**/) 딴 화투) \n");
			print_have_hwa_too(eat_player[i]);
			printf("\n");
		}
		hwa_too_link(eat);
		printf("깔린 패) ");
		print_hwa_too(eat,sizeof(eat)/sizeof(*eat));
		printf("\n\n");
		printf("명령 ? ");
		scanf("%s",system_command);
		if(strcmp(system_command,"g")==0);
			//hwa_too_go()
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
		else if(0<(compare=atoi(system_command)) && compare<8);
			//play_hwa_too(compare,player[turn],eat,eat_player[turn],dummy[tot],status);
		else
		{
			printf("잘못된 명령입니다.\n");
			sleep(3);
		}
		tot++;
		turn++;
		if(turn==3)
			turn=0;
	}
	return 0;
}
