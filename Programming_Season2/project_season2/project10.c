#include <stdio.h>
#include <stdlib.h>
#include <string.h>
struct status_hwa_too{
	unsigned go:3, na_ga_ri:1;
};
struct hwa_too_gamer{
	int ID;
	struct hwa_too *pae_in_hand;
	struct hwa_too *pae_eat;
	int point;
	char status[20];
	int money;
	int go_plus;
	int go_num;
	int pi_bak;
	int go_bak;
	int shake;
	int win;
	int nagari;
	struct status_hwa_too player_status;
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
void print_turn(int *, int);
void sorting_hwa_too(struct hwa_too *);
struct hwa_too *plus_link_hwa_too(struct hwa_too *, struct hwa_too);
struct hwa_too location_copy_hwa_too(struct hwa_too *, int);
int compare_hwa_too(int, struct hwa_too *, int);
int same_check_hwa_too(struct hwa_too *,int, int *);
struct hwa_too *play_hwa_too(int,struct hwa_too_gamer *, struct hwa_too_pan *,int *,struct hwa_too_gamer *[],int [],int);
struct hwa_too *make_hwa_too(struct hwa_too [], int[], int);
struct hwa_too *disconnect_hwa_too(struct hwa_too *, int);
void print_help(void);
void stealing_hwa_too(struct hwa_too_gamer *[],int,int);
void print_hwa_too(struct hwa_too *p)
{
	if (p==NULL)
	{
		printf("\n");
		return;	
	}
	else
	{
		printf("%2d%s", p->num,p->name);
		if (p->check_num==0)
			printf(" ");
		else
			printf("%d ",p->check_num);
		print_hwa_too(p->next);
	}
	return;
}
void print_turn(int *j,int i)
{
	if ((*j)==0 && i==0)
	{
		printf("%1c",'*');
		return;
	}
	if ((*j)==1 && i==1)
	{
		printf("%1c",'*');
		return;
	}
	if ((*j)==2 && i==2)
	{
		printf("%1c",'*');
		return;
	}
	printf(" ");
}
void sorting_hwa_too(struct hwa_too *p) {
	int i=0,temp,j,check=0;
	char temp_string[7];
	if (p==NULL)
		return;
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
struct hwa_too *plus_link_hwa_too(struct hwa_too *p,struct hwa_too plus)		//추기시킬 배열, 추가할 패의 내용
{
	if(p!=NULL)
		p->next=plus_link_hwa_too(p->next,plus);
	else
	{
		p=(struct hwa_too *)malloc(sizeof(struct hwa_too));
		p->num=plus.num;
		strcpy(p->name,plus.name);
		p->check_num=plus.check_num;
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
	if (q==NULL)
		return 0;
	/*	if(q->next==NULL)
		return 0;				*/											//0리턴은 같은 값을 찾지 못했을 경우임
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
		{
			if (p->next!=NULL)
			{
				if (p->next->num==num)
				{
					check++;
					if (p->next->next!=NULL)
					{
						if (p->next->next->num==num)
						{
							check++;
							if (p->next->next->next!=NULL)
							{
								if (p->next->next->next->num==num)
									check++;
							}
						}
					}
				}
			}
		}
		if (check==1)
			*index=location;
		location++;
	}
	return check;
}
int safety_hwa_too(struct hwa_too *p)
{
	int count=0;
	for(;p;p=p->next)
	{
		count++;
	}
	return count;
}
struct hwa_too *play_hwa_too(int count,struct hwa_too_gamer *player,struct hwa_too_pan *pan, int *pturn,struct hwa_too_gamer *p[],int noo_ga_ssam[],int total)
{
	int i,exit=0,j,input=0,limit=0;
	int wow=0;
	int eat_check=0;
	int eat_count=0;
	int shake_count=0;
	int shake_check=0;
	int num_check[12]={0};
	struct hwa_too shake_pae={0};
	struct hwa_too bomb={0,"폭탄",0,0};
	struct hwa_too *check,*init,*confirm;
	struct hwa_too pae[4]={{0}};
	struct hwa_too sam_all[3]={{0}};
	limit=safety_hwa_too(player->pae_in_hand);
	//입력 실수 방지용 함수 시작
	if (count>limit)
	{
		printf("잘못된 숫자를 입력하셨습니다.다시 입력해 주십시오.\n");
		sleep(2);
		(*pturn)--;
		return player->pae_in_hand;
	}
	//입력 실수 방지용 함수 끝
	check=pan->eat;
	init=pan->eat;
	pae[0]=location_copy_hwa_too(player->pae_in_hand,count);
	player->pae_in_hand=disconnect_hwa_too(player->pae_in_hand,count);
	confirm=player->pae_in_hand;
	//낸 패가 폭탄인지 확인, 폭탄이면 그에 따른 경우 따지기 시작
	if ((strcmp(pae[0].name,"폭탄"))==0)
	{
		pae[2]=location_copy_hwa_too(pan->dummy,1);
		pan->dummy=disconnect_hwa_too(pan->dummy,1);
		for (;check;check=check->next)
		{
			num_check[(check->num)-1]++;
		}										//숫자들 확인
		//뒤집은 것과 하나가 같을 경우
		if (num_check[(pae[2].num)-1]==1)
		{
			eat_count=compare_hwa_too(pae[2].num,pan->eat,1);
			pae[3]=location_copy_hwa_too(pan->eat,eat_count);
			pan->eat=disconnect_hwa_too(pan->eat,eat_count);
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[3]);
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[2]);
			//플레이어 먹은 패에 저장
		}
		//뒤집은 것과 둘이 같을 경우
		else if (num_check[(pae[2].num)-1]==2)
		{
			eat_count=compare_hwa_too(pae[2].num,pan->eat,1);
			printf("뒤집은 패는 %2d%s입니다.\n",pae[2].num,pae[2].name);
			while(1)
			{
				printf("앞의 것을 선택하려면 1, 뒤의 것을 선택하려면 2를 입력하세요.\n");
				scanf("%d",&input);
				if ((input==1) || (input==2))
					break;
				else
				{
					printf("잘못된 입력입니다.\n");
					sleep(2);
				}
			}
			if (input==1)															//앞에거 처묵
			{
				pae[3]=location_copy_hwa_too(pan->eat,eat_count);
				pan->eat=disconnect_hwa_too(pan->eat,eat_count);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[3]);
			}
			if (input==2)															//뒤에거 처묵
			{
				pae[3]=location_copy_hwa_too(pan->eat,eat_count+1);
				pan->eat=disconnect_hwa_too(pan->eat,eat_count+1);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[3]);
			}
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[2]);
		}
		//뒤집은 것과 셋이 같을 경우
		else if (num_check[(pae[2].num)-1]==3)
		{
			//누가 처쌌는지 확인하기
			exit=0;
			while(1)
			{
				if ((*noo_ga_ssam)==pae[2].num)
				{
					stealing_hwa_too(p,*pturn,2);								//자기가 싼거 자기가 처먹음 2개!!
					break;
				}
				noo_ga_ssam++;
				exit++;
				if (exit==4)
				{
					stealing_hwa_too(p,*pturn,1);								//한 플레이어가 최대 쌀수 있는 것을 4번이라고 했을 때, 비교해서 찾아내어 처묵
					break;
				}
			}
			printf("먹을거임!!!\n");
			sleep(2);
			eat_count=compare_hwa_too(pae[2].num,pan->eat,1);
			for (j=0;j<3;j++)
			{
				pae[3]=location_copy_hwa_too(pan->eat,eat_count);
				pan->eat=disconnect_hwa_too(pan->eat,eat_count);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[3]);			//세번 처먹음
			}
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[2]);
		}
		//뒤집은 것과 같은것이 없는 경우
		else if (num_check[(pae[2].num)-1]==0)
		{
			pan->eat=plus_link_hwa_too(pan->eat,pae[2]);
		}
		return player->pae_in_hand;
	}
	//낸 패가 폭탄인지 확인, 폭탄이면 그에 따른 경우 따지기 끝
	//흔들기 실행함수 시작
	for (;confirm;confirm=confirm->next)
	{
		if (pae[0].num==confirm->num)
		{
			shake_count++;
		}
	}
	if (shake_count==2)							//pae[0]포함 든것이 3개
	{
		player->shake=1;
		printf("흔들기!!!\n");
		strcpy(player->status,"흔듦!!!");
		sleep(1);
		if ((shake_check=compare_hwa_too(pae[0].num,pan->eat,1)))
		{
			//하나라도 깔려있는 경우
			pae[1]=location_copy_hwa_too(pan->eat,shake_check);
			pan->eat=disconnect_hwa_too(pan->eat,shake_check);
			shake_check=compare_hwa_too(pae[0].num,player->pae_in_hand,1);
			for (j=0;j<2;j++)
			{
				shake_pae=location_copy_hwa_too(player->pae_in_hand,shake_check);
				player->pae_in_hand=disconnect_hwa_too(player->pae_in_hand,shake_check);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,shake_pae);
			}			//반복문에서 들고있는 나머지 2개 먹음
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[0]);			//최초에 냈던것
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[1]);			//깔려있는 것
			bomb.num=pae[0].num;
			player->pae_in_hand=plus_link_hwa_too(player->pae_in_hand,bomb);
			player->pae_in_hand=plus_link_hwa_too(player->pae_in_hand,bomb);
			stealing_hwa_too(p,*pturn,1);
		}
		else	//안 깔린 경우
		{
			pan->eat=plus_link_hwa_too(pan->eat,pae[0]);
			//깔린게 없을 때는 흔듦 출력후 하나 내고 끝
		}
		return player->pae_in_hand;
	}
	//흔들기 실행함수 끝
	pae[2]=location_copy_hwa_too(pan->dummy,1);
	pan->dummy=disconnect_hwa_too(pan->dummy,1);
	for (;check;check=check->next)
	{
		num_check[(check->num)-1]++;
	}									//갯수 알아와
	if (pae[0].num!=pae[2].num)					//내는것과 뒤집은것이 다른 경우
	{
		for (i=0;i<4;i=i+2)				
			//0=손에서 냄, 1=깔린것에서 손에서 내는것과 같은것, 2=뒤집는것, 3=깔린것에서 뒤집은 것과 같은것
		{
			eat_check=0;
			if (num_check[(pae[i].num)-1]==1)
			{
				eat_check=compare_hwa_too(pae[i].num,pan->eat,1);
				pae[i+1]=location_copy_hwa_too(pan->eat,eat_check);
				pan->eat=disconnect_hwa_too(pan->eat,eat_check);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[i+1]);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[i]);
				//하나 처먹는 조건
			}
			else if (num_check[(pae[i].num)-1]==2)
			{
				eat_check=compare_hwa_too(pae[i].num,pan->eat,1);
				if (i==2)
					printf("뒤집은 패는 %2d%s입니다.\n",pae[i].num,pae[i].name);
				while(1)
				{
					printf("앞의 것을 선택하려면 1, 뒤의 것을 선택하려면 2를 입력하세요.\n");
					scanf("%d",&input);
					if ((input==1) || (input==2))
						break;
					else
					{
						printf("잘못된 입력입니다.\n");
						sleep(2);
					}
				}
				if (input==1)
				{
					pae[i+1]=location_copy_hwa_too(pan->eat,eat_check);
					pan->eat=disconnect_hwa_too(pan->eat,eat_check);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[i+1]);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[i]);
				}			//1을 입력할 때(앞의 것)
				else if (input==2)
				{
					pae[i+1]=location_copy_hwa_too(pan->eat,eat_check+1);
					pan->eat=disconnect_hwa_too(pan->eat,eat_check+1);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[i+1]);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[i]);
				}			//2를 입력할 때
				//두개 골라 처먹는 조건
			}
			else if (num_check[(pae[i].num)-1]==3)
			{
				//누가 처쌌는지 확인하기
				exit=0;
				while(1)
				{
					if ((*noo_ga_ssam)==pae[i].num)
					{
						stealing_hwa_too(p,*pturn,2);								//자기가 싼거 자기가 처먹음 2개!!
						break;
					}
					noo_ga_ssam++;
					exit++;
					if (exit==4)
					{
						stealing_hwa_too(p,*pturn,1);								//한 플레이어가 최대 쌀수 있는 것을 4번이라고 했을 때, 비교해서 찾아내어 처묵
						break;
					}
				}
				eat_check=compare_hwa_too(pae[i].num,pan->eat,1);
				for (j=0;j<3;j++)
				{
					sam_all[j]=location_copy_hwa_too(pan->eat,eat_check);
					pan->eat=disconnect_hwa_too(pan->eat,eat_check);
				}
				for (j=0;j<3;j++)
				{
					player->pae_eat=plus_link_hwa_too(player->pae_eat,sam_all[j]);
				}
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[i]);
				//세개 다 처먹는 조건
			}
			else if (num_check[(pae[i].num)-1]==0)
			{
				pan->eat=plus_link_hwa_too(pan->eat,pae[i]);
			}
		}		//같은것이 없어 그냥 내는 것.
	}									//내는것과 뒤집는것이 다른 경우 끝
	else if (pae[0].num==pae[2].num)
	{
		eat_count=0;
		eat_count=compare_hwa_too(pae[0].num,pan->eat,1);
		if (eat_count)
		{
			pae[1]=location_copy_hwa_too(pan->eat,eat_count);
			pan->eat=disconnect_hwa_too(pan->eat,eat_count);
		}													//없을때
		eat_count=compare_hwa_too(pae[0].num,pan->eat,1);
		if (pae[1].num==pae[0].num && (compare_hwa_too(pae[0].num,pan->eat,1)==0))
		{
			while(1)
			{
				if ((*noo_ga_ssam)!=0)
					noo_ga_ssam++;
				else if ((*noo_ga_ssam)==0)
				{
					(*noo_ga_ssam)=pae[1].num;
					break;
				}
			}
			printf("뻑!!!!!!\n");
			sleep(2);
			strcpy(player->status,"SSAM");
			pan->eat=plus_link_hwa_too(pan->eat,pae[0]);
			pan->eat=plus_link_hwa_too(pan->eat,pae[1]);
			pan->eat=plus_link_hwa_too(pan->eat,pae[2]);
		}
		//뻑
		else if (pae[1].num==pae[0].num && (eat_count=compare_hwa_too(pae[0].num,pan->eat,1))!=0)			//깔린것 중에 하나 더 있을때
		{
			printf("따닥!!!\n");
			sleep(2);
			pae[3]=location_copy_hwa_too(pan->eat,eat_count);
			pan->eat=disconnect_hwa_too(pan->eat,eat_count);
			for (j=0;j<4;j++)
			{
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[j]);
			}
			stealing_hwa_too(p,(*pturn),1);
		}
		//따닥
		if (pae[0].num!=pae[1].num && (compare_hwa_too(pae[0].num,pan->eat,1)==0))
		{
			printf("쪾쪾쪾\n");
			sleep(2);
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[0]);
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae[2]);
			stealing_hwa_too(p,(*pturn),1);
		}
		//쪽
	}									//내는것과 뒤집는 것이 같을 경우 끝

	if ((pan->eat)==NULL && total!=20)
	{
		printf("판쓸!!!!\n");
		stealing_hwa_too(p,(*pturn),1);
	}
	return player->pae_in_hand;
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
			p=NULL;
			free(p);
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
load : 파일에 저장되었던 상태를 읽어서 계속 게임을 진행함.\n\
bomb : 들고 있는 패에 '폭탄'이 있을 경우 사용가능함.\n\n");
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
void save_player_hwa_too(struct hwa_too_gamer *p,int num,int turn)
{
	FILE *ifp;
	char *string_temp;
	char check_string[20]={255};				//문자열은 최초의 값에 255
	int integer_temp;
	int check_int=-1;							//구분선 정수형은 -1
	struct hwa_too check={.num=-1,.name={'\0'},.check_num=0,.next=NULL};		//화투형은 num이 -1
	struct hwa_too *temp;
	if (num==0)
		ifp=fopen("gostop","wb");
	else 
		ifp=fopen("gostop","ab");
	//플레이어 든거 저장
	temp=p->pae_in_hand;
	for (;temp;temp=temp->next)
	{
		fwrite(temp,sizeof(struct hwa_too),1,ifp);
		fflush(ifp);
	}
	fwrite(&check,sizeof(struct hwa_too),1,ifp);
	fflush(ifp);
	//플레이어 먹은거 저장
	temp=p->pae_eat;
	for (;temp;temp=temp->next)
	{
		fwrite(temp,sizeof(struct hwa_too),1,ifp);
		fflush(ifp);
	}
	fwrite(&check,sizeof(struct hwa_too),1,ifp);
	fflush(ifp);
	//플레이어 점수 저장
	integer_temp=p->point;
	fwrite(&integer_temp,sizeof(int),1,ifp);
	fflush(ifp);
	//플레이어 상태 저장
	string_temp=p->status;
	fwrite(string_temp,sizeof(char)*20,1,ifp);
	fflush(ifp);
	//플레이어 돈 저장
	integer_temp=p->money;
	fwrite(&integer_temp,sizeof(int),1,ifp);
	fflush(ifp);
	//플레이어 순서 저장
	integer_temp=turn;
	fwrite(&integer_temp,sizeof(int),1,ifp);
	fflush(ifp);
	//플레이어 고_점수차 저장
	integer_temp=p->go_plus;
	fwrite(&integer_temp,sizeof(int),1,ifp);
	fflush(ifp);
	//플레이어 고 횟수 저장
	integer_temp=p->go_num;
	fwrite(&integer_temp,sizeof(int),1,ifp);
	fflush(ifp);
	//플레이어 피박 유무
	integer_temp=p->pi_bak;
	fwrite(&integer_temp,sizeof(int),1,ifp);
	fflush(ifp);
	//플레이어 고박 유무
	integer_temp=p->go_bak;
	fwrite(&integer_temp,sizeof(int),1,ifp);
	fflush(ifp);
	//플레이어 흔들기 유무
	integer_temp=p->shake;
	fwrite(&integer_temp,sizeof(int),1,ifp);
	fflush(ifp);
	//플레이어 이김
	integer_temp=p->win;
	fwrite(&integer_temp,sizeof(int),1,ifp);
	fflush(ifp);
	//플레이어 나가리
	integer_temp=p->nagari;
	fwrite(&integer_temp,sizeof(int),1,ifp);
	fflush(ifp);
	//플레이어의 전체적인 상태 저장-1
	fwrite(&(p->player_status),sizeof(struct status_hwa_too),1,ifp);
	fflush(ifp);
	fclose(ifp);
	return;
}
void save_hwa_too_pan(struct hwa_too_pan *p,int total)
{
	FILE *ifp;
	struct hwa_too end={.num=-1,.name={'\0'},.check_num=0,.next=NULL};
	struct status_hwa_too ttemp;
	struct hwa_too *temp;
	ifp=fopen("pan","wb");
	//깔린 화투 저장
	temp=p->eat;
	for (;temp;temp=temp->next)
	{
		fwrite(temp,sizeof(struct hwa_too),1,ifp);
		fflush(ifp);
	}
	fwrite(&end,sizeof(struct hwa_too),1,ifp);
	fflush(ifp);
	//화투 더미 저장
	temp=p->dummy;
	for (;temp;temp=temp->next)
	{
		fwrite(temp,sizeof(struct hwa_too),1,ifp);
		fflush(ifp);
	}
	fwrite(&end,sizeof(struct hwa_too),1,ifp);
	fflush(ifp);
	//현재 진행된 횟수 저장
	fwrite(&total,sizeof(int),1,ifp);
	fflush(ifp);
	fclose(ifp);
	return;
}
struct hwa_too *killing_hwa_too(struct hwa_too *p)
{
	if (p==NULL)				//초기 상황일때
		return NULL;
	if (p->next!=NULL)
		p->next=killing_hwa_too(p->next);
	if (p->next==NULL)
	{
		free(p);
		p=NULL;
		return p;
	}
}
void load_player_hwa_too(struct hwa_too_gamer *p[],int *turn)
{
	struct hwa_too tem[48]={{0}};
	FILE *ofp;
	int count=0;
	char string_temp[20]={'\0'};
	struct hwa_too temp;
	int i,integer_temp;
	struct status_hwa_too pla_status;
	ofp=fopen("gostop","rb");
	if (ofp==NULL)
	{
		printf("save한 파일이 존재하지 않습니다!!!\n");
		return;
	}
	//손에 들고 있는거 전부 해제후, 불러 온 것으로 채움.
	for (i=0;i<3;i++)
	{
		p[i]->pae_in_hand=killing_hwa_too(p[i]->pae_in_hand);
		while(1)
		{
			fread(&temp,sizeof(struct hwa_too),1,ofp);
			if (temp.num==-1)
			{
				break;
			}
			else
			{
				p[i]->pae_in_hand=plus_link_hwa_too(p[i]->pae_in_hand,temp);
			}
		}
		//들고 있는거 하나하나씩 불러 와
		//먹은거 전부 해제후, 불러 온 것으로 채움.
		p[i]->pae_eat=killing_hwa_too(p[i]->pae_eat);
		while(1)
		{
			fread(&temp,sizeof(struct hwa_too),1,ofp);
			if (temp.num==-1)
			{
				break;
			}
			else
			{
				p[i]->pae_eat=plus_link_hwa_too(p[i]->pae_eat,temp);
			}
		}
		//먹었던거 하나하나씩 불러와
		//점수 불러와
		fread(&integer_temp,sizeof(int),1,ofp);
		p[i]->point=integer_temp;
		//점수 넣어
		fread(string_temp,sizeof(char)*20,1,ofp);
		strcpy(p[i]->status,string_temp);
		//상태 넣어
		fread(&integer_temp,sizeof(int),1,ofp);
		p[i]->money=integer_temp;
		//돈 넣어
		fread(&integer_temp,sizeof(int),1,ofp);
		*turn=integer_temp;
		//순서 넣어
		fread(&integer_temp,sizeof(int),1,ofp);
		p[i]->go_plus=integer_temp;
		//점수차
		fread(&integer_temp,sizeof(int),1,ofp);
		p[i]->go_num=integer_temp;
		//고 횟수
		fread(&integer_temp,sizeof(int),1,ofp);
		p[i]->pi_bak=integer_temp;
		//피박
		fread(&integer_temp,sizeof(int),1,ofp);
		p[i]->go_bak=integer_temp;
		//고박
		fread(&integer_temp,sizeof(int),1,ofp);
		p[i]->shake=integer_temp;
		//흔들기
		fread(&integer_temp,sizeof(int),1,ofp);
		p[i]->win=integer_temp;
		//이김
		fread(&integer_temp,sizeof(int),1,ofp);
		p[i]->nagari=integer_temp;
		//나가리
		fread(&pla_status,sizeof(struct status_hwa_too),1,ofp);
		p[i]->player_status=pla_status;
		//플레이어 전체 상태
	}
	fclose(ofp);
	return;
} 
void load_hwa_too_pan(struct hwa_too_pan *p,int *tot)
{
	FILE *ofp;
	int total=0;
	struct hwa_too temp;
	struct status_hwa_too end;
	ofp=fopen("pan","rb");
	if (ofp==NULL)
	{
		printf("save한 파일이 존재하지 않습니다!!!!\n");
		return;
	}
	//깔린 화투 불러오기 위해 기존거 전부 죽임
	p->eat=killing_hwa_too(p->eat);
	while(1)
	{
		fread(&temp,sizeof(struct hwa_too),1,ofp);
		if (temp.num==-1)
			break;
		else
		{
			p->eat=plus_link_hwa_too(p->eat,temp);
		}
	}
	//깔린 화투 깔음
	p->dummy=killing_hwa_too(p->dummy);
	while(1)
	{
		fread(&temp,sizeof(struct hwa_too),1,ofp);
		if (temp.num==-1)
			break;
		else
		{
			p->dummy=plus_link_hwa_too(p->dummy,temp);
		}
	}
	//화투 더미 재 생성
	//몇번 동작했는지 확인
	fread(&total,sizeof(int),1,ofp);
	*tot=total;
	fclose(ofp);
	return;
}
int count_checking_hwa_too(struct hwa_too *p)
{
	int result=0;
	int Gwang_count=0, Bi_Gwang_Check=0;
	int ten_count=0, bird_count=0;
	int five_count=0, chung_count=0, cho_count=0, hong_count=0;
	int pi_count=0;
	for (;p;p=p->next)
	{
		if (strcmp("광",p->name)==0)
			Gwang_count++;
		if ((p->num==12) && strcmp("광",p->name)==0)
			Bi_Gwang_Check++;
		if (strcmp("오",p->name)==0)
			five_count++;
		if ((p->num==6 && (strcmp("오",p->name))==0) || (p->num==9 && (strcmp("오",p->name))==0) || (p->num==10 && (strcmp("오",p->name)))==0)
			chung_count++;					//청단
		if ((p->num==1 && (strcmp("오",p->name)==0)) || (p->num==2 && (strcmp("오",p->name))==0) || (p->num==3 && (strcmp("오",p->name)==0)))
			hong_count++;					//초단
		if ((p->num==4 && strcmp("오",p->name)) || (p->num==5 && strcmp("오",p->name)) || (p->num==7 && strcmp("오",p->name)))
			cho_count++;
		if (strcmp("십",p->name)==0)
			ten_count++;
		if (((p->num==2) && (strcmp("십",p->name)==0)) || ((p->num==4) && (strcmp("십",p->name)==0)) || ((p->num==8) && (strcmp("십",p->name)==0)))
			bird_count++;
		if (strcmp("피",p->name)==0)
			pi_count++;
		if (strcmp("쌍피",p->name)==0)
			pi_count+=2;
	}
	switch (Gwang_count)
	{
		case 5 :
			result+=15;
			break;
		case 4 :
			result+=4;
			break;
		case 3 :
			{
				if (Bi_Gwang_Check==1)
					result+=2;
				else if (Bi_Gwang_Check==0)
					result+=3;
			}
			break;
		default :
			break;
	}
	if (ten_count>=5)
	{
		if (bird_count==3)
			result+=5;
		else
			result+=(ten_count-4);
	}
	if (five_count>=5)
	{
		result+=(five_count-4);
		if (cho_count==3)
			result+=3;
		if (chung_count==3)
			result+=3;
		if (hong_count==3)
			result+=3;
	}
	if (pi_count>=10)
	{
		result+=(pi_count-9);
	}
	return result;
}
void moneycalcul(struct hwa_too_gamer *p[])
{
     int i;
     int money_[3];
     int a=0;
     int b=0;
     int result_point=0;
     for(i=0;i<3;i++)
     {
         money_[i]=0;
     }

     for(i=0;i<3;i++)
     {
         if(p[i]->win==1)
         {
             result_point=count_checking_hwa_too(p[i]->pae_eat    );

             if(p[i]->go_num==1)
                 result_point+=1;
             else if(p[i]->go_num==2)
                 result_point+=2;
             else if(p[i]->go_num==3)
                 result_point*=2;
             else if(p[i]->go_num==4)
                 result_point*=4;

             money_[i]=result_point;

             if(count_mungtong_hwa_too(p[i]->pae_eat))
                 money_[i]*=2;
             if(p[i]->shake==1)
                 money_[i]*=2;//고점수
			 if (p[i]->nagari==2)
				 money_[i]*=2;			//나가리는 디폴트로 A에 저장

             a=money_[i]*100;
             b=money_[i]*100;
             p[i]->shake=0;
         }
     }
     for(i=0;i<3;i++)
     {
         if(p[i]->win==0)
         {
             if(p[i]->go_num!=0)
                 p[i]->go_bak=1;
             if(count_pi_hwa_too(p[i]->pae_eat))
                 p[i]->pi_bak=1;
         }
     }

		for(i=0;i<3;i++)
		{
			if(p[i]->win==0)
			{
				if(p[i]->go_bak==1)
					a*=2;
				if(p[i]->pi_bak==1)
					a*=2;
					
				money_[i]=a;
				p[i]->money-=money_[i];  //고박 피박 계산
				a=b;
			}
		}
		for(i=0;i<3;i++)
		{
			if(p[i]->win==1)
			{
				if(i==0)
					p[i]->money+=money_[1]+money_[2];
				if(i==1)
					p[i]->money+=money_[0]+money_[2];
				if(i==2)
					p[i]->money+=money_[0]+money_[1];
			}
			p[i]->win=0;
		}
}
int count_pi_hwa_too(struct hwa_too *temp)
{
	int count=0;
	for (;temp;temp=temp->next)
	{

		if (strcmp("피",temp->name)==0)
			count++;
	}
	if(count<=5){
		return 1;
	}
	else
		return 0;
}
int count_mungtong_hwa_too(struct hwa_too *temp)
{
	int count=0;
	for (;temp;temp=temp->next)
	{
		if (strcmp("십",temp->name)==0)
			count++;
	}
	if(count>=7){
		return 1;
	}
	else
		return 0;
}

int result_hwa_too(struct hwa_too_gamer *player[],int *pturn)
{
	moneycalcul(player);
	int i,temp;
	char again;
	temp=*pturn;
	if (temp==0)
		temp=3;
	printf("   점수|  잔고\n");
	for (i=0;i<3;i++)
	{
		if (i==0)
			printf("A : %2d | %6d\n",player[i]->point,player[i]->money);
		if (i==1)
			printf("B : %2d | %6d\n",player[i]->point,player[i]->money);
		if (i==2)
			printf("C : %2d | %6d\n",player[i]->point,player[i]->money);
	}
	for (i=0;i<3;i++)
	{
		if ((i==temp-1) && (player[temp-1]->win==1))
		{
			printf("승자는 A입니다!!!!\n");
		}
		if ((i==temp-1) && (player[temp-1]->win==1))
		{
			printf("승자는 B입니다!!!!\n");
		}
		if ((i==temp-1) && (player[temp-1]->win==1))
		{
			printf("승자는 C입니다!!!!\n");
		}
	}
	while (1)
	{
		printf("한번더 하시겠습니까? (y/n)\n");
		getchar();
		scanf("%c",&again);
		if (again=='Y' || again=='y')
		{
			if((*pturn)==0)
				(*pturn)=2;
			else if((*pturn)==1)
					(*pturn)=0;
			else if((*pturn)==2)
					(*pturn)=1; 


			return 1;

		}
		else if (again=='N' || again=='n')
			return 0;
		else
		{
			printf("잘못된 접근입니다.\n");
			sleep(2);
		}
	}
}
void scan_num_hwa_too(struct hwa_too_pan *p)
{
	int num[12]={0};
	int i;
	struct hwa_too *init,*temp,*more;
	if (p==NULL)
		return;
	temp=p->eat;
	more=p->eat;
	init=p->eat;
	for (;temp;temp=temp->next)
	{
		num[(temp->num)-1]++;
		printf("%d\n",num[(temp->num)-1]);
	}
	for (i=0;i<12;i++)
	{
		if (num[i]==2)						//같은 숫자가 2개 깔렸을때
		{
			for (more=init;more;more=more->next)		//처음부터 찾기
			{
				if ((more->num)==(i+1))
				{
					more->check_num=1;
					more->next->check_num=2;//정렬이 되어있다는 전제하에 대입
					break;
				}
			}
		}
		if (num[i]==1)
		{
			for (more=init;more;more=more->next)
			{
				if ((more->num)==(i+1))
				{
					more->check_num=0;
					break;
				}
			}
		}
		if (num[i]==3)
		{
			for (more=init;more;more=more->next)
			{
				if ((more->num)==(i+1))
				{
					more->check_num=0;
					more->next->check_num=0;
					more->next->next->check_num=0;
					break;
				}
			}
		}
	}
}
int check_nine_hwa_too(struct hwa_too_gamer *p)
{
	struct hwa_too *temp;
	int input;
	temp=p->pae_eat;
	for (;temp;temp=temp->next)
	{
		if ((strcmp("쌍피",temp->name)==0) && (temp->num==9))
		{
			printf("9십을 십으로 사용하시려면 '9'를 입력하세요. 그렇지 않으면 '0'을 입력하세요.\n");
			scanf("%d",&input);
			if (input==9)
			{
				strcpy(temp->name,"십");
				return 1;
			}
			else if (input==0)
				return 1;
			else
				printf("잘못된 입력입니다.\n");
		}
	}
	return 0;
}
void stealing_hwa_too(struct hwa_too_gamer *p[],int turn,int point)
{
	int i=0;
	int check=0;
	struct hwa_too temp,temp1;
	int proce[3][3]={{0,1,2},{1,0,2},{2,0,1}};
	int use[3]={0};
	if (turn==0)
	{
		for (i=0;i<3;i++)
		{
			use[i]=proce[0][i];
		}
	}
	if (turn==1)
	{
		for (i=0;i<3;i++)
		{
			use[i]=proce[1][i];
		}
	}
	if (turn==2)
	{
		for (i=0;i<3;i++)
		{
			use[i]=proce[2][i];
		}
	}
	if (point==1)
	{
		if (p[use[1]]->pae_eat!=NULL)
		{
			check=find_pi_hwa_too(p[use[1]]->pae_eat);													//1번째 플레이어 꺼 뺏어옴
			if (check==0)
			{
				check=find_ssang_hwa_too(p[use[1]]->pae_eat);
				if (check==0)
				{
				}
				else
				{
					temp=location_copy_hwa_too(p[use[1]]->pae_eat,check);
					p[use[1]]->pae_eat=disconnect_hwa_too(p[use[1]]->pae_eat,check);
					p[use[0]]->pae_eat=plus_link_hwa_too(p[use[0]]->pae_eat,temp);
				}
			}
			else
			{
				temp=location_copy_hwa_too(p[use[1]]->pae_eat,check);
				p[use[1]]->pae_eat=disconnect_hwa_too(p[use[1]]->pae_eat,check);
				p[use[0]]->pae_eat=plus_link_hwa_too(p[use[0]]->pae_eat,temp);
			}
		}
		if (p[use[2]]->pae_eat!=NULL)
		{
			check=find_pi_hwa_too(p[use[2]]->pae_eat);													//2번째 플레이어 꺼 뺏어옴
			if (check==0)
			{
				check=find_ssang_hwa_too(p[use[2]]->pae_eat);
				if (check==0)
				{
				}
				else
				{
					temp=location_copy_hwa_too(p[use[2]]->pae_eat,check);
					p[use[2]]->pae_eat=disconnect_hwa_too(p[use[2]]->pae_eat,check);
					p[use[0]]->pae_eat=plus_link_hwa_too(p[use[0]]->pae_eat,temp);
				}
			}
			else
			{
				temp=location_copy_hwa_too(p[use[2]]->pae_eat,check);
				p[use[2]]->pae_eat=disconnect_hwa_too(p[use[2]]->pae_eat,check);
				p[use[0]]->pae_eat=plus_link_hwa_too(p[use[0]]->pae_eat,temp);
			}																							//피 먹는 함수
		}
	}
	if (point==2)
	{
		if (p[use[1]]->pae_eat!=NULL)
		{
			check=find_ssang_hwa_too(p[use[1]]->pae_eat);
			if (check==0)
			{
				check=find_pi_hwa_too(p[use[1]]->pae_eat);					//쌍피 찾는게 음슴
				if (check==0)												//피 없으면 종료
				{
				}
				else
				{
					temp=location_copy_hwa_too(p[use[1]]->pae_eat,check);	//하나 찾으면 바로 넣기
					p[use[1]]->pae_eat=disconnect_hwa_too(p[use[1]]->pae_eat,check);
					p[use[0]]->pae_eat=plus_link_hwa_too(p[use[0]]->pae_eat,temp);
					check=find_pi_hwa_too(p[use[1]]->pae_eat);				//두번 찾기
					if (check==0)
					{
					}
					else
					{
						temp1=location_copy_hwa_too(p[use[1]]->pae_eat,check);//두개 찾으면 두개까지
						p[use[1]]->pae_eat=disconnect_hwa_too(p[use[1]]->pae_eat,check);
						p[use[0]]->pae_eat=plus_link_hwa_too(p[use[0]]->pae_eat,temp1);
					}
				}
			}
			else
			{
				temp=location_copy_hwa_too(p[use[1]]->pae_eat,check);
				p[use[1]]->pae_eat=disconnect_hwa_too(p[use[1]]->pae_eat,check);
				p[use[0]]->pae_eat=plus_link_hwa_too(p[use[0]]->pae_eat,temp);
			}
		}									//2번째 플레이어 ㄱㄱ
		if(p[use[2]]->pae_eat!=NULL)
		{
			check=find_ssang_hwa_too(p[use[2]]->pae_eat);
			if (check==0)
			{
				check=find_pi_hwa_too(p[use[2]]->pae_eat);					//쌍피 찾는게 음슴
				if (check==0)												//피 없으면 종료
				{
				}
				else
				{
					temp=location_copy_hwa_too(p[use[2]]->pae_eat,check);	//하나 찾으면 바로 넣기
					p[use[2]]->pae_eat=disconnect_hwa_too(p[use[2]]->pae_eat,check);
					p[use[0]]->pae_eat=plus_link_hwa_too(p[use[0]]->pae_eat,temp);
					check=find_pi_hwa_too(p[use[2]]->pae_eat);				//두번 찾기
					if (check==0)
						return;
					else
					{
						temp1=location_copy_hwa_too(p[use[2]]->pae_eat,check);//두개 찾으면 두개까지
						p[use[2]]->pae_eat=disconnect_hwa_too(p[use[2]]->pae_eat,check);
						p[use[0]]->pae_eat=plus_link_hwa_too(p[use[0]]->pae_eat,temp1);
					}
				}
			}
			else
			{
				temp=location_copy_hwa_too(p[use[2]]->pae_eat,check);
				p[use[2]]->pae_eat=disconnect_hwa_too(p[use[2]]->pae_eat,check);
				p[use[0]]->pae_eat=plus_link_hwa_too(p[use[0]]->pae_eat,temp);
			}
		}
	}
}
int find_pi_hwa_too(struct hwa_too *temp)
{
	int count=0;
	for (;temp;temp=temp->next)
	{
		count++;
		if (strcmp("피",temp->name)==0)
			return count;
	}
	return 0;
}
int find_ssang_hwa_too(struct hwa_too *temp)
{
	int count=0;
	for (;temp;temp=temp->next)
	{
		count++;
		if (strcmp("쌍피",temp->name)==0)
			return count;
	}
	return 0;
}
int check_rule_n_get_point(struct hwa_too *firstHand)
{
	int i=0;
	int typeCountArray[12]; 				// 총 12개의 타입들 체크
	for(i=0; i<12; i++)
	{
		typeCountArray[i] = 0;
	}
	struct hwa_too *currentPae = firstHand;
	while(1)
	{
		typeCountArray[(currentPae->num)-1]++; // 타입들의 갯수 파악
		currentPae = currentPae->next;
		if(currentPae == NULL) 
		{
			break;
		}
	}
	for(i=0; i<12; i++)
	{
		if( typeCountArray[i] >= 4 )
		{
			return 1;					 	//총통 조건 충족.
		}
	}
	return 0; 								// 총통 X
}
void na_ga_ri(int ggo,int total, struct hwa_too_gamer *player)
{
	if (ggo<=0 && total==20)						//고한 횟수가 없고 진행횟수가 21번일 경우(main에서 tot(여기서 total)이 0번부터 동작하므로)
	{
		printf("나가리!! 다음판 두배!!\n");
		sleep(2);
		player->player_status.na_ga_ri=1;
	}
	else if (ggo>0 && total==20)					//고를 하였고 진행횟수가 21번일 경우
	{
		printf("나가리!! 다음판 두배!!\n");
		sleep(2);
		player->player_status.na_ga_ri=1;
	}
	return;
}
int check_na_ga_ri(struct hwa_too_gamer *player[])
{
	int i,sum=0;
	for (i=0;i<3;i++)
	{
		sum+=player[i]->player_status.na_ga_ri;		//나가리가 1이라도 올라가 있다면 
		player[i]->player_status.na_ga_ri=0;
	}
	if (sum>0)
		return 2;									//2를 리턴하여 곱하기 시전
	else
		return 1;									//없으면 1을 리턴, 곱하기
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
	int noo_ga_ssam[3][4]={{0}};
	int ggo=0,wow,i=0,j=0,command,turn=0,tot=0,sort=0,temp_turn=0,nine_check=0,hurry_check=0,before_turn=0,notice_na_ga_ri=1;
	int *pturn=&turn;
	char system_command[10]=" ";
	int before_go_check[3]={0};
	struct hwa_too_gamer *player[3];
	struct hwa_too_pan *pan;
	pan=(struct hwa_too_pan *)malloc(sizeof(struct hwa_too_pan));

	for(i=0;i<3;i++)
	{
		player[i]=(struct hwa_too_gamer *)malloc(sizeof(struct hwa_too_gamer));
		player[i]->ID=i;
		player[i]->money=100000;
		player[i]->pae_eat=NULL;
		player[i]->point=0;
		player[i]->shake=0;
		player[i]->win=0;
		player[i]->pi_bak=0;
		player[i]->go_bak=0;
		player[i]->go_num=0;
		player[i]->nagari=1;
	}

	while (1){											//전체적인 while문
		if (check_na_ga_ri(player)==2)
		{
				player[turn]->nagari*=notice_na_ga_ri*check_na_ga_ri(player);
		}
		else
		{
				player[turn]->nagari*=notice_na_ga_ri*check_na_ga_ri(player);
		}
		before_turn=turn;
		for (i=0;i<3;i++)
		{
			player[i]->pae_eat=killing_hwa_too(player[i]->pae_eat);
			player[i]->point=0;
			player[i]->go_plus=0;
			player[i]->player_status.go=0;
			player[i]->shake=0;
			player[i]->win=0;
			player[i]->pi_bak=0;
			player[i]->go_bak=0;
			player[i]->go_num=0;
			for(j=0;j<20;j++)
			{
				player[i]->status[j]=0;
			}
		}
		for (i=0;i<48;i++)								//다시 시작할때 초기화
		{
			check[i]=0;
		}
		for (i=0;i<3;i++)
		{
			for (j=0;j<4;j++)
			{
				noo_ga_ssam[i][j]=0;					//누가 쌌는지 확인
			}
			before_go_check[i]=0;
		}
		tot=0;											//총 횟수 초기화자
		hurry_check=0;
		srand(time(NULL));
		for (i=0; i<3; i++)
		{
			player[i]->pae_in_hand=make_hwa_too(init,check,0);
		}
		pan->eat=make_hwa_too(init,check,1);				//1부터 시작시 6장 뽑힘.
		pan->dummy=make_hwa_too(init,check,-14);
		while (1) 
		{											//한판당의 while문
			for (i=0;i<3;i++)
			{
				player[i]->point=count_checking_hwa_too(player[i]->pae_eat);
			}
			sort=0;
			for (i=0;i<3;i++)
			{
				while(1)
				{
					sorting_hwa_too(player[i]->pae_in_hand);
					sort++;
					if(sort==21)
						break;
				}
				sort=0;
			}
			while(1)
			{
				sorting_hwa_too(pan->eat);
				sort++;
				if(sort==300)
					break;
			}
			system("clear");
			for (i=0; i<3; i++) 
			{
				print_turn(pturn,i);
				if (i==0)
					printf("A%7s : 패)",player[i]->status);
				if (i==1)
					printf("B%7s : 패)",player[i]->status);
				if (i==2)
					printf("C%7s : 패)",player[i]->status);
				print_hwa_too(player[i]->pae_in_hand);
				printf("\n(점수 : %d) 딴 화투) ",player[i]->point);
				sort=0;
				while(1)
				{
					sort++;
					sorting_hwa_too(player[i]->pae_eat);
					if (sort==400)
						break;
				}
				if (player[i]->pae_eat==NULL)
					printf("\n");
				else
					print_hwa_too(player[i]->pae_eat);
				printf("\n");
			}
			scan_num_hwa_too(pan);
			printf("깔린 패) ");
			print_hwa_too(pan->eat);
			printf("\n\n");
#if DEBUG
			print_hwa_too(pan->dummy);
#endif
			if (tot==0)
			{
				for (i=0;i<3;i++)
				{
					if (check_rule_n_get_point(player[i]->pae_in_hand))
					{
						if (i==0)
							printf("Player A가 총통입니다.\n");
						if (i==1)
							printf("Player B가 총통입니다.\n");
						if (i==2)
							printf("Player C가 총통입니다.\n");
						player[i]->point=10;
						player[i]->win=1;

						if ((result_hwa_too(player,pturn))==1)
						{
							printf("한판 더!\n");
							sleep(2);
							break;
						}
						else
						{
							printf("고스톱 게임을 종료합니다.\n");
							return 0;
						}
					}
				}
			}
#if DEBUG
			for (i=0;i<3;i++)
			{
				for (j=0;j<4;j++)
				{
					printf("싼 것리스트 : %d의 %d번째 내용 = %d\n",i,j,noo_ga_ssam[i][j]);
				}
			}
			printf("before_turn : %d\n",before_turn);
#endif
			if ((player[before_turn]->point>=3) && before_turn>=0&&tot<20)
			{
				while(1)
				{
					for (i=0;i<3;i++)
					{
						player[i]->go_plus=player[i]->point-before_go_check[i];
					}
					if (player[before_turn]->go_plus>0)
					{
						if (before_turn==0)
							printf("Player A, Go(g) or Stop(s)?\n");
						if (before_turn==1)
							printf("Player B, Go(g) or Stop(s)?\n");
						if (before_turn==2)
							printf("Player C, Go(g) or Stop(s)?\n");
						scanf("%s",system_command);
						if ((strcmp(system_command,"g")==0) || (strcmp(system_command,"s")==0))
							break;
						else
						{
							printf("정확하게 입력하여 주십시오.\n");
							sleep(1);
						}
					}
					else
						break;
				}
				if (strcmp(system_command,"g")==0)
				{
					printf("Go!\n");
					sleep(2);
					(player[before_turn]->player_status.go)++;
					before_go_check[before_turn]=player[before_turn]->point;
				}
				if (strcmp(system_command,"s")==0)
				{
					printf("Stop!\n");
					sleep(2);
					player[before_turn]->win=1;
					if((result_hwa_too(player,pturn))==1)
					{
						printf("한 판더!\n");
						sleep(2);
						break;
					}
					else
					{
						printf("고스톱 게임을 종료합니다.\n");
						sleep(2);
						return 0;
					}
				}
			}
			printf("명령 ? ");
			//명령행 시작
			scanf("%s",system_command);
			if((strcmp(system_command,"e")==0) || (strcmp(system_command,"end")==0))
			{
				printf("고스톱 게임을 종료합니다.");
				return 0;
			}
			else if((strcmp(system_command,"b")==0) || (strcmp(system_command,"balance")==0))
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
				printf("r이나 refresh를 입력하게 되면 화면으로 돌아가게 됩니다.\n");
				scanf("%s",system_command);
				if((strcmp(system_command,"r")==0) || (strcmp(system_command,"refresh")==0))
				{
					turn--;
					tot--;
				}
			}
			else if((strcmp(system_command,"h")==0) || (strcmp(system_command,"help")==0))
			{
				print_help();
				printf("r이나 refresh를 입력하게 되면 화면으로 돌아가게 됩니다.\n");
				scanf("%s",system_command);
				if((strcmp(system_command,"r")==0) || (strcmp(system_command,"refresh")==0))
				{
					turn--;
					tot--;
				}
			}
			else if(strcmp(system_command,"save")==0)
			{
				temp_turn=0;
				while(1)
				{
					save_player_hwa_too(player[temp_turn],temp_turn,turn);
					temp_turn++;
					if (temp_turn==3)
						break;
				}
				save_hwa_too_pan(pan,tot);
				printf("저장완료!\n");
				sleep(2);
				turn--;
				if (turn==-1)
				{
					turn=0;
					continue;
				}
				tot--;
			}
			else if(strcmp(system_command,"load")==0)
			{
				temp_turn=0;
				load_player_hwa_too(player,&turn);
				load_hwa_too_pan(pan,&tot);
				printf("불러오기 완료!\n");
				sleep(2);
				turn--;
				if (turn==-1)
				{
					turn=0;
					continue;
				}
				tot--;
			}
			else if(0<atoi(system_command) && atoi(system_command)<8)
			{
				player[turn]->pae_in_hand=play_hwa_too(atoi(system_command),player[turn],pan,pturn,player,noo_ga_ssam[turn],tot); 
				if (hurry_check==0)
					nine_check=check_nine_hwa_too(player[turn]);
				if (nine_check==1)
					hurry_check=1;
			}
			else
			{
				printf("잘못된 명령입니다.\n");
				turn--;
				tot--;
				sleep(2);
				if (turn==-1)
				{
					turn=0;
					continue;
				}
			}
			//명령행 끝
			for (i=0;i<3;i++)
			{
				player[i]->point=count_checking_hwa_too(player[i]->pae_eat);
			}													//먹은것의 점수 계산
			for (i=0;i<3;i++)
			{
				ggo+=player[i]->player_status.go;							//플레이어들의 go한 횟수를 합침. 고하는 경우 구현
			}
			if (turn==-1)
			{
				turn=0;
				continue;
			}				//turn이 음수인 경우에는 help,입력을 잘못 했을 경우,저장,불러오기,잔고보기이다.
			before_turn=turn;
			tot++;
			turn++;
			
			if(turn==3)
				turn=0;
			

#if DEBUG
			for (i=0;i<3;i++)
			{
				printf("플레이어[%d] 점수 : %d\n",i,player[i]->point);
				printf("플레이어[%d] 고한뒤 점수 : %d\n",i,player[i]->go_plus);
				printf("플레이어[%d] 고한횟수 : %d\n",i,player[i]->player_status.go);
			}
#endif
			if(tot>=19)
			{
				if((player[before_turn]->point>=3) && before_turn>=0)
				{
						for(i=0;i<3;i++)
						{
							player[i]->point=count_checking_hwa_too(player[i]->pae_eat);
							player[i] -> go_plus=player[i]->point-before_go_check[i];
						}
						
						if(player[before_turn]->go_plus>0)
						{
							if(before_turn==0)
							{
								printf("Player A, Stop\n");
							}
							if(before_turn==1)
							{
								printf("Player B, Stop\n");
							}
							if(before_turn==2)
							{
								printf("Player C, Stop\n");
							}
						}
						else if (player[turn]->go_plus==0)
							na_ga_ri(ggo,tot,player[turn]);
						printf("Stop!\n");
						sleep(2);
						player[before_turn]->win=1;
						if((result_hwa_too(player,pturn))==1)
						{
							printf("한 판더!\n");
							sleep(2);
							break;
						}
						else
						{
							printf("고스톱 게임을 종료합니다.\n");
							sleep(2);
							return 0;
						}
				}
				//무조건 스톱이 되도록
			}
			
			if (tot==21)										//종료할 조건
			{
				printf("게임종료!\n");
				player[0]->point=count_checking_hwa_too(player[0]->pae_eat);
				player[1]->point=count_checking_hwa_too(player[1]->pae_eat);
				player[2]->point=count_checking_hwa_too(player[2]->pae_eat);
				if((result_hwa_too(player,pturn))==1)
				{
					printf("한 판더!\n");
					sleep(2);
					break;
				}
				else
				{
					printf("고스톱 게임을 종료합니다.\n");
					sleep(2);
				}
				return 0;
			}
		}
	}
	return 0;
}
