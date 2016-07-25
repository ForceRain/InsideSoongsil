#include <stdio.h>
#include <stdlib.h>
#include <string.h>
struct hwa_too_gamer{
	int ID;
	struct hwa_too *pae_in_hand;
	struct hwa_too *pae_eat;
	int point;
	char status[20];
	int money;
};
struct hwa_too{
	int num;
	char name[7];
	int check_num;
	struct hwa_too *next;
};
struct hwa_too_pan{
	struct hwa_too *eat;
	struct hwa_too *dummy;
};
void print_hwa_too(struct hwa_too *);
void print_turn(int, int);
void sorting_hwa_too(struct hwa_too *);
void input_hwa_too(struct hwa_too **,struct hwa_too);
struct hwa_too *plus_link_hwa_too(struct hwa_too *, struct hwa_too);
struct hwa_too location_copy_hwa_too(struct hwa_too *, int);
int compare_hwa_too(int, struct hwa_too *, int);
void play_hwa_too(int,struct hwa_too_gamer *, struct hwa_too_pan *);
void print_have_hwa_too(struct hwa_too *);
struct hwa_too *make_hwa_too(struct hwa_too [], int[], int);
struct hwa_too *disconnect_hwa_too(struct hwa_too *, int);
void print_help(void);
void print_hwa_too(struct hwa_too *p)
{
	if (p==NULL)
	{
		printf("\n");
		return;	
	}
	else
	{
		printf("%2d%s ", p->num,p->name);
		print_hwa_too(p->next);
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
void sorting_hwa_too(struct hwa_too *p) {
	int i=0,temp,j,check=0;
	char temp_string[7];
	if (p->next==NULL)
		return;
	if(p->num>(p->next)->num)
	{
		temp=p->num;
		p->num=(p->next)->num;
		(p->next)->num=temp;
		strcpy(temp_string,p->name);
		strcpy(p->name,p->next->name);
		strcpy(p->next->name,temp_string);
	}
	if ((p->num)==(p->next->num) && (strcmp(p->name,(p->next)->name))>0)
	{
	temp=p->num;
	p->num=(p->next)->num;
	(p->next)->num=temp;
	strcpy(temp_string,p->name);
	strcpy(p->name,p->next->name);
	strcpy(p->next->name,temp_string);
	}
		sorting_hwa_too(p->next);
	return;
}
void input_hwa_too(struct hwa_too *plus[],struct hwa_too pae)
{
	int i=0;
#if DEBUG
	printf("%2d%s\n", pae.num,pae.name);
#endif
	while(1)
	{
		if (plus[i]==NULL)
		{
			*plus[i]=pae;
			break;
		}
		else
			i++;
	}
	return;
}
struct hwa_too *plus_link_hwa_too(struct hwa_too *p,struct hwa_too plus)		//추기시킬 배열, 추가할 패의 내용
{
	if(p!=NULL)
		p->next=plus_link_hwa_too(p->next,plus);
	else
	{
		p=(struct hwa_too *)malloc(sizeof(struct hwa_too));
		p->num=plus.num;
		strcpy(p->name,plus.name);
		p->next=NULL;
	}
	return p;
}
struct hwa_too location_copy_hwa_too(struct hwa_too *p,int count)			//내용을 찾을 배열, 몇번위치의
{
	struct hwa_too temp;
	count--;
	if(count==0)
		return *p;
	else
		temp=location_copy_hwa_too(p->next,count);
	return temp;
}
int compare_hwa_too(int num,struct hwa_too *q,int count)
{
	if(q->next==NULL)
		return 0;															//0리턴은 같은 값을 찾지 못했을 경우임
	if(num==q->num)
		return count;														//count리턴은 같은게 있다는거임 
	count++;
	compare_hwa_too(num,q->next,count);
}
int same_check_hwa_too(struct hwa_too *p,int num,int *index)				//check값은 같은것의 갯수 index는 최초로 같은 번호를 가지는 것이 등장했을때
{
	int check=0,location=0;
	for (;p;p=p->next)
	{
		if (p->num==num)
			check++;
		if (check==1)
			*index=location;
		location++;
	}
	return check;
}
void play_hwa_too(int count, struct hwa_too_gamer *player, struct hwa_too_pan *pan)
{
	int i=0,index=0;
	int eat_count=0;
	struct hwa_too pae1={0},pae2={0},pae3={0},plus_pae={0};
	int temp_count;
	pae1=location_copy_hwa_too(player->pae_in_hand,count);
	player->pae_in_hand=disconnect_hwa_too(player->pae_in_hand,count);
	pae3=location_copy_hwa_too(pan->dummy,1);
	pan->dummy=disconnect_hwa_too(pan->dummy,1);
	if(eat_count=compare_hwa_too(pae1.num,pan->eat,1))			//깔린것과 낸 것이 같은숫자
	{															//아직 번호 같고 종류구분해서 먹기 구현 안함.
#if DEBUG
		printf("깔린 것과 낸 것이 같은숫자\n");
#endif
		pae2=location_copy_hwa_too(pan->eat,eat_count);
		pan->eat=disconnect_hwa_too(pan->eat,eat_count);
		if(pae1.num==pae3.num)								//뒤집은 것이 같은숫자.
		{
#if DEBUG
		printf("뒤집은 것이 같은 숫자.\n");
#endif
		strcpy(player->status,"설사");
		pan->eat=plus_link_hwa_too(pan->eat,pae1);
		pan->eat=plus_link_hwa_too(pan->eat,pae2);
		pan->eat=plus_link_hwa_too(pan->eat,pae3);
		}
		else if (pae1.num==pae2.num && (temp_count=compare_hwa_too(pae3.num,pan->eat,1)))											//뒤집은거랑 먹을거중에 하나 존재, 먹는거는 그대로 먹고	아직 번호 같고 종류 구분해서 먹기 구분 안함
		{
			plus_pae=location_copy_hwa_too(pan->eat,temp_count);
			pan->eat=disconnect_hwa_too(pan->eat,temp_count);
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
			player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);	
		}
		else if ()
		{

		}
		else												//그냥 처묵
		{
#if DEBUG
		printf("그냥 처묵\n");
#endif
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
			pan->eat=plus_link_hwa_too(pan->eat,pae3);
		}
	}
	else													//그냥 내는것
	{
		pan->eat=plus_link_hwa_too(pan->eat,pae1);
		pan->eat=plus_link_hwa_too(pan->eat,pae3);
	}
	return;	
}
struct hwa_too *make_hwa_too(struct hwa_too init[],int check[],int num)
{
	int wow;
	struct hwa_too *pae=NULL;
	while (1){
		if (num==7)
			break;
		wow=(rand()%48);
		if (check[wow]!=-1)
		{
			pae=(struct hwa_too *)malloc(sizeof(struct hwa_too));
			*pae=init[wow];
			check[wow]=-1;
			num++;
			pae->next=make_hwa_too(init,check,num);
			break;
		}
	}
	return pae;
}
struct hwa_too *disconnect_hwa_too(struct hwa_too *p,int num)	//끊을 링크, 횟수
{
	struct hwa_too *temp;
	int check;
	num--;
	if (num==0)
	{
		if (p->next==NULL && ((check=compare_hwa_too(p->num,p,1))==1))
		{
			p->num='\0';
			strcpy(p->name," ");			//게임 끝나는 조건
		}
		else if (p->next==NULL)
		{
			free(p);
			p=NULL;

		}
		else
		{
			temp=p->next;
			p->next=NULL;
			free(p);
			p=temp;
		}
	}
	else
	{
		p->next=disconnect_hwa_too(p->next,num);
	}
	return p;
}
void print_help(void)
{
		system("clear");
		printf("****고스톱 게임 키 도움말****\n\
g : 고 (자신의 차례일 때 점수가 났고 3점 이상일 때 할 수 있음.\n\
s : 스톱(자기 turn에 점수가 났고 3점 이상일 때 할 수 있음.\n\
e : 프로그램 끝내기\n\
b : 게이머의 잔고 보기\n\
r : 잔고보기를 이용후, 화면을 게임상태로 되돌림.\n\
h : 각 키에 대한 설명 보기\n\
1~7 : 낼 화투를 선택\n\
1~2 : 화투를 냈는데, 깔린 화투중 숫자는 같으나 값이 다를 경우 선택\n\
9 : 9십을 피로 또는 십으로 이동시킴. 최초의 상태는 '피'임. 각 판에서 한번만 할 수 있음.\n\
save : 현재 상태를 파일에 저장\n\
load : 파일에 저장되었던 상태를 읽어서 계속 게임을 진행함.\n\n");
		return;
}
int rule_chong_tong(struct hwa_too *p)
{
	int count=0;
	for (;p;p=p->next)
	{
		if(p->num==p->next->num && p->next->num==p->next->next->num && p->next->next->num==p->next->next->next->num)
			return 1;
	}
	return 0;
}
void save_player_hwa_too(struct hwa_too_gamer *p, int num)
{
	FILE *ifp;
	ifp=fopen("player","wb");
	fseek(ifp,sizeof(struct hwa_too_gamer)*num,SEEK_SET);
	fwrite(p,sizeof(struct hwa_too_gamer),1,ifp);
	fflush(ifp);
	fclose(ifp);
	return;
}
void save_hwa_too_pan(struct hwa_too_pan *p)
{
	FILE *ifp;
	ifp=fopen("pan","wb");
	fwrite(p,sizeof(struct hwa_too_pan),1,ifp);
	fflush(ifp);
	fclose(ifp);
	return;
}
int main(void) {
	struct hwa_too init[48]={{1,"광",0,NULL},{1,"오",0,NULL},{1,"피",0,NULL},{1,"피",0,NULL},\
		{2,"십",0,NULL},{2,"오",0,NULL},{2,"피",0,NULL},{2,"피",0,NULL},\
		{3,"광",0,NULL},{3,"오",0,NULL},{3,"피",0,NULL},{3,"피",0,NULL},\
		{4,"십",0,NULL},{4,"오",0,NULL},{4,"피",0,NULL},{4,"피",0,NULL},\
		{5,"십",0,NULL},{5,"오",0,NULL},{5,"피",0,NULL},{5,"피",0,NULL},\
		{6,"십",0,NULL},{6,"오",0,NULL},{6,"피",0,NULL},{6,"피",0,NULL},\
		{7,"십",0,NULL},{7,"오",0,NULL},{7,"피",0,NULL},{7,"피",0,NULL},\
		{8,"광",0,NULL},{8,"십",0,NULL},{8,"피",0,NULL},{8,"피",0,NULL},\
		{9,"쌍피",0,NULL},{9,"오",0,NULL},{9,"피",0,NULL},{9,"피",0,NULL},\
		{10,"십",0,NULL},{10,"오",0,NULL},{10,"피",0,NULL},{10,"피",0,NULL},\
		{11,"광",0,NULL},{11,"쌍피",0,NULL},{11,"피",0,NULL},{11,"피",0,NULL},\
		{12,"광",0,NULL},{12,"십",0,NULL},{12,"오",0,NULL},{12,"쌍피",0,NULL}};
	int check[48]={0};
	int wow,i=0,j=0,command,turn=0,tot=0,sort=0,temp_turn=0;
	char system_command[7]=" ";
	struct hwa_too_gamer *player[3];
	struct hwa_too *eat_player[3]={NULL};
	struct hwa_too_pan *pan;
	pan=(struct hwa_too_pan *)malloc(sizeof(struct hwa_too_pan));
	for(i=0;i<3;i++)
	{
		player[i]=(struct hwa_too_gamer *)malloc(sizeof(struct hwa_too_gamer));
		player[i]->money=100000;
		player[i]->pae_eat=NULL;
		player[i]->point=0;
	}
	srand(time(NULL));
#if DEBUG
	printf("작동\n");
#endif
	for (i=0; i<3; i++)
	{
		player[i]->pae_in_hand=make_hwa_too(init,check,0);
	}
#if DEBUG
	printf("플레이어 마다 화투 배정 작동\n");
#endif
	pan->eat=make_hwa_too(init,check,1);				//1부터 시작시 6장 뽑힘.
#if DEBUG
	printf("먹을거 만드는거 작동\n");
#endif
	pan->dummy=make_hwa_too(init,check,-14);
#if DEBUG
	printf("화투더미 작동\n");
#endif
	while (1) {
		sort=0;
		for (i=0;i<3;i++)
		{
			print_hwa_too(player[i]->pae_in_hand);
			while(1)
			{
			sorting_hwa_too(player[i]->pae_in_hand);
			sort++;
			if(sort==21)
				break;
			}
			sort=0;
		}
		while(1){
			sorting_hwa_too(pan->eat);
			sort++;
			if(sort==100)
				break;
		}
		system("clear");
		for (i=0; i<3; i++) {
			print_turn(turn,i);
			if (i==0)
				printf("A%7s : 패)",player[i]->status);
			if (i==1)
				printf("B%7s : 패)",player[i]->status);
			if (i==2)
				printf("C%7s : 패)",player[i]->status);
			print_hwa_too(player[i]->pae_in_hand);
			printf("\n(점수 : %d) 딴 화투) ",player[i]->point);
			if (player[i]->pae_eat==NULL)
				printf("\n");
			else
				print_hwa_too(player[i]->pae_eat);
			printf("\n");
/*			if (rule_chong_tong(player[i]->pae_in_hand))
			{
				printf("player %c가 총통입니다!\n\
계속 진행하시려면 y를 입력하세요\n");
				scanf("%c",system_command);
				if (strcmp(system_command,"y")==0);
					//통째 while문을 돌릴 방법 찾기
			}			*/
		}
		printf("깔린 패) ");
		print_hwa_too(pan->eat);
		printf("\n\n");
#if DEBUG
		print_hwa_too(pan->dummy);
#endif
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
		else if(strcmp(system_command,"b")==0)
		{
			system("clear");
			printf("각 플레이어의 현재 잔액\n");
			for (i=0;i<3;i++)
			{
				if (i==0)
					printf("A의 잔고 : ");
				if (i==1)
					printf("B의 잔고 : ");
				if (i==2)
					printf("C의 잔고 : ");
				printf("%d\n",player[i]->money);
			}
			printf("r을 누르게 되면 화면으로 돌아가게 됩니다.\n");
			scanf("%s",system_command);
			if(strcmp(system_command,"r")==0)
				turn--;
		}
		else if(strcmp(system_command,"h")==0)
		{
			print_help();
			printf("r을 누르게 되면 화면으로 돌아가게 됩니다.\n");
			scanf("%s",system_command);
			if(strcmp(system_command,"r")==0)	
				turn--;
		}
		else if(strcmp(system_command,"save")==0)
		{
			temp_turn=0;
			while(1)
			{
				save_player_hwa_too(player[temp_turn],temp_turn);
				temp_turn++;
				if (temp_turn==3)
					break;
			}
			save_hwa_too_pan(pan);
			printf("저장완료!\n");
			sleep(2);
			turn--;
		}
		else if(strcmp(system_command,"load")==0)
#if DEBUG
	printf("작동10\n");
#endif
		else if(0<atoi(system_command) && atoi(system_command)<8)
		{
			play_hwa_too(atoi(system_command),player[turn],pan); //dummy부분 주의
		}
	else
		{
			printf("잘못된 명령입니다.\n");
			turn--;
			sleep(2);
		}
		tot++;
		turn++;
		if(turn==3)
			turn=0;
	}
	return 0;
}
