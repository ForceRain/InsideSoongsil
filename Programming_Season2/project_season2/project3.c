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
struct status_pan_hwa_too{
	unsigned shake:1, poop:1, eat_all:1, go:3, chong_tong:1, na_ga_ri:1, pi_bak:1, go_bak:1, mung_tung:1;
};
// 흔들기, 설사, 싹쓰리, 고(포고까지 지원), 총통, 나가리, 피박, 고박, 멍텅구리(10점짜리로 3점남(10점이 7개) 점수 2배), 
struct hwa_too_pan{
	struct status_pan_hwa_too pan_status;
	struct hwa_too *eat;
	struct hwa_too *dummy;
};
void print_hwa_too(struct hwa_too *);
void print_turn(int*, int);
void sorting_hwa_too(struct hwa_too *);
void input_hwa_too(struct hwa_too **,struct hwa_too);
struct hwa_too *plus_link_hwa_too(struct hwa_too *, struct hwa_too);
struct hwa_too location_copy_hwa_too(struct hwa_too *, int);
int compare_hwa_too(int, struct hwa_too *, int);
int same_check_hwa_too(struct hwatoo *, int, int*);
struct hwa_too *play_hwa_too(int,struct hwa_too_gamer *, struct hwa_too_pan *);
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
void print_turn(int* j,int i)
{
	if (*j==0 && i==0)
	{
		printf("%1c",'*');
		return;
	}
	if (*j==1 && i==1)
	{
		printf("%1c",'*');
		return;
	}
	if (*j==2 && i==2)
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
	if(q->next==NULL)
		return 0;															//0리턴은 같은 값을 찾지 못했을 경우임
	if(num==q->num)
		return count;														//count리턴은 같은게 있다는거임 
	count++;
	compare_hwa_too(num,q->next,count);
}
int same_check_hwa_too(struct hwa_too *p,int num, int *index)				//check값은 같은것의 갯수 index는 최초로 같은 번호를 가지는 것이 등장했을때
{
	int check=0,location=0;
	for (;p;p=p->next)
	{
		if (p->num==num)
			if(p->next->num==num)
			{
				check++;
				if(p->next->next->num==num)
				{
					check++;
					if(p->next->next->next->num==num)
						check++;
				}
			}

		if (check==1)
			*index=location;
		location++;
	}
	return check;
}
struct hwa_too *play_hwa_too(int count, struct hwa_too_gamer *player, struct hwa_too_pan *pan, int *pturn)
{
	int i=0,index=0, assam=0, bssam=0, cssam=0;
	int *pindex= index;

	int a;
	int eat_count=0, samecheck=0;
	struct hwa_too pae1={0},pae2={0},pae3={0},plus_pae={0};
	int temp_count;
	pae1=location_copy_hwa_too(player->pae_in_hand,count);
	player->pae_in_hand=disconnect_hwa_too(player->pae_in_hand,count);
#if DEBUG
	printf("주소값 : %u\n",player->pae_in_hand);
#endif
	pae3=location_copy_hwa_too(pan->dummy,1);
	pan->dummy=disconnect_hwa_too(pan->dummy,1);
	if(eat_count=compare_hwa_too(pae1.num,pan->eat,1))
		//A를 냈을때 패에 A가 한개 이상 있을 경우
	{	
		pae2=location_copy_hwa_too(pan->eat,eat_count);
		pan->eat=disconnect_hwa_too(pan->eat,eat_count);
		samecheck=same_check_hwa_too(pan->eat,pae1.num, pindex);
		//A를 냈을 때 바닥에 A가 2장 깔려 있는 경우
		if(samecheck==1)
		{
			printf("어떤것을 드시겠습니까?");
			scanf("%d",&a);
			if(a==2)
			{
				pae2=location_copy_hwa_too(pan->eat,eat_count+1);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
				pan->eat=disconnect_hwa_too(pan->eat,eat_count+1);

				if(pae3.num==pae1.num)
				{
					printf("따닥");
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
					pae2=location_copy_hwa_too(pan->eat,eat_count);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
					pan->eat=disconnect_hwa_too(pan->eat,eat_count);
				}

				else if(pae1.num==pae2.num && (temp_count = compare_hwa_too(pae3.num, pan->eat,1)))
				{
					samecheck=same_check_hwa_too(pan_eat, pae3.num, pindex);
					//뒷패를 선택하는 경우
					if(samecheck==1)
					{
						printf("어떤것을 드시겠습니까?");
						scanf("%d",&a);
						if(a==2)
						{
							plus_pae=location_copy_hwa_too(pan->eat,temp_count+1);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
							pan->eat=disconnect_hwa_too(pan->eat,temp_count+1);
						}

						if(a==1)
						{
							plus_pae=location_copy_hwa_too(pan->eat,temp_count);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
							pan->eat=disconnect_hwa_too(pan->eat,temp_count);

						}

					}

					//뒷패가 3장인 경우
					if(samecheck==2)
					{
						printf("았싸 4장");
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);

						pae2=location_copy_hwa_too(pan->eat, temp_count);
						pan->eat=disconnect_hwa_too(pan->eat, temp_count);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

						pae2=location_copy_hwa_too(pan->eat, temp_count);
						pan->eat=disconnect_hwa_too(pan->eat, temp_count);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

						pae2=location_copy_hwa_too(pan->eat, temp_count);
						pan->eat=disconnect_hwa_too(pan->eat, temp_count);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

						if((*pturn)==0 && assam>0)
							//피두장 넘어가기
							;
						else if((*pturn)==1 && bssam>0)
							;
						else if((*pturn)==2 && cssam>0)
							;
						else
							//(*pturn)의 플레이어에게 나머지 사람들이 피 한장씩
					}

					else
					{
						plus_pae=location_copy_hwa_too(pan->eat,temp_count);
						pan->eat=disconnect_hwa_too(pan->eat,temp_count);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
					}

				}
				else
				{
					pan->eat=plus_link_hwa_too(pan->eat, pae3);
				}
			}

			if(a==1)
			{
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
				pan->eat=disconnect_hwa_too(pan->eat,eat_count);
				if(pae3.num==pae1.num)
				{
					printf("따닥");
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
					pae2=location_copy_hwa_too(pan->eat,eat_count+1);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
					pan->eat=disconnect_hwa_too(pan->eat,eat_count+1);
				}

				else if(pae1.num==pae2.num && (temp_count = compare_hwa_too(pae3.num, pan->eat,1)))
				{
					samecheck=same_check_hwa_too(pan_eat, pae3.num, pindex);
					//뒷패를 선택하는 경우
					if(samecheck==1)
					{
						printf("어떤것을 드시겠습니까?");
						scanf("%d",&a);
						if(a==2)
						{
							plus_pae=location_copy_hwa_too(pan->eat,temp_count+1);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
							pan->eat=disconnect_hwa_too(pan->eat,temp_count+1);
						}

						if(a==1)
						{
							plus_pae=location_copy_hwa_too(pan->eat,temp_count);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
							player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
							pan->eat=disconnect_hwa_too(pan->eat,temp_count);

						}

					}

					//뒷패가 3장인 경우
					if(samecheck==2)
					{
						printf("았싸 4장");
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);

						pae2=location_copy_hwa_too(pan->eat, temp_count);
						pan->eat=disconnect_hwa_too(pan->eat, temp_count);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

						pae2=location_copy_hwa_too(pan->eat, temp_count);
						pan->eat=disconnect_hwa_too(pan->eat, temp_count);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

						pae2=location_copy_hwa_too(pan->eat, temp_count);
						pan->eat=disconnect_hwa_too(pan->eat, temp_count);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

						if((*pturn)==0 && assam>0)
							//피두장 넘어가기
							;
						else if((*pturn)==1 && bssam>0)
							;
						else if((*pturn)==2 && cssam>0)
							;
						else
							//(*pturn)의 플레이어에게 나머지 사람들이 피 한장씩
					}

					else
					{
						plus_pae=location_copy_hwa_too(pan->eat,temp_count);
						pan->eat=disconnect_hwa_too(pan->eat,temp_count);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
					}

				}

				else
				{
					pan->eat=plus_link_hwa_too(pan->eat, pae3);
				}
			}

		}

		//A를 냈을때 바닥에 A가 3장 있을경우
		else if(samecheck==2)
		{
			printf("았싸4장");
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);

			pae2=location_copy_hwa_too(pan->eat, eat_count);
			pan->eat=disconnect_hwa_too(pan->eat, eat_count);
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

			pae2=location_copy_hwa_too(pan->eat, eat_count);
			pan->eat=disconnect_hwa_too(pan->eat, eat_count);
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

			pae2=location_copy_hwa_too(pan->eat, eat_count);
			pan->eat=disconnect_hwa_too(pan->eat, eat_count);
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

			if((*pturn)==0 && assam>0)
				//피두장 넘어가기
				;
			else if((*pturn)==1 && bssam>0)
				;
			else if((*pturn)==2 && cssam>0)
				;
			else
				;//(*pturn)의 플레이어에게 나머지 사람들이 피 한장씩
			
			//뒷패가 붙는 경우

			if(pae1.num==pae2.num && (temp_count = compare_hwa_too(pae3.num, pan->eat,1)))
			{
				samecheck=same_check_hwa_too(pan_eat, pae3.num, pindex);
				//뒷패를 선택하는 경우
				if(samecheck==1)
				{
					printf("어떤것을 드시겠습니까?");
					scanf("%d",&a);
					if(a==2)
					{
						plus_pae=location_copy_hwa_too(pan->eat,temp_count+1);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
						pan->eat=disconnect_hwa_too(pan->eat,temp_count+1);
					}

					if(a==1)
					{
						plus_pae=location_copy_hwa_too(pan->eat,temp_count);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
						player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
						pan->eat=disconnect_hwa_too(pan->eat,temp_count);

					}

				}

				//뒷패가 3장인 경우
				if(samecheck==2)
				{
					printf("았싸 4장");
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);

					pae2=location_copy_hwa_too(pan->eat, temp_count);
					pan->eat=disconnect_hwa_too(pan->eat, temp_count);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

					pae2=location_copy_hwa_too(pan->eat, temp_count);
					pan->eat=disconnect_hwa_too(pan->eat, temp_count);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

					pae2=location_copy_hwa_too(pan->eat, temp_count);
					pan->eat=disconnect_hwa_too(pan->eat, temp_count);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

					if((*pturn)==0 && assam>0)
						//피두장 넘어가기
						;
					else if((*pturn)==1 && bssam>0)
						;
					else if((*pturn)==2 && cssam>0)
						;
					else
						//(*pturn)의 플레이어에게 나머지 사람들이 피 한장씩
				}

				else
				{
					plus_pae=location_copy_hwa_too(pan->eat,temp_count);
					pan->eat=disconnect_hwa_too(pan->eat,temp_count);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
				}

			}

			else 
			{
				pan->eat=plus_link_hwa_too(pan->eat, pae3);
			}
		}

		//A를 내고 A가 2장 혹은 3장인 경우는 모두 위에서 제함

		//A를 내고 A가 또 나오는 경우
		else if(pae1.num==pae3.num)
			//쌋다 ㅋㅋ
		{
			printf("뻑");
			if((*pturn)==0)
				assam++;
			if((*pturn)==1)
				bssam++;
			if((*pturn)==2)
				cssam++;
			pan->eat=plus_link_hwa_too(pan->eat,pae1);
			pan->eat=plus_link_hwa_too(pan->eat,pae2);
			pan->eat=plus_link_hwa_too(pan->eat,pae3);
		}

		//A를 내고 뒷패가 B인데 바닥에 B가 있을 경우
		else if (pae1.num==pae2.num && (temp_count=compare_hwa_too(pae3.num,pan->eat,1)))

		{
			samecheck=same_check_hwa_too(pan_eat, pae3.num, pindex);
			//뒷패를 선택하는 경우
			if(samecheck==1)
			{
				printf("어떤것을 드시겠습니까?");
				scanf("%d",&a);
				if(a==2)
				{
					plus_pae=location_copy_hwa_too(pan->eat,temp_count+1);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
					pan->eat=disconnect_hwa_too(pan->eat,temp_count+1);
				}

				if(a==1)
				{
					plus_pae=location_copy_hwa_too(pan->eat,temp_count);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
					pan->eat=disconnect_hwa_too(pan->eat,temp_count);
				}

			}

			//뒷패가 3장인 경우
			if(samecheck==2)
			{
				printf("았싸 4장");
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);

				pae2=location_copy_hwa_too(pan->eat, temp_count);
				pan->eat=disconnect_hwa_too(pan->eat, temp_count);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

				pae2=location_copy_hwa_too(pan->eat, temp_count);
				pan->eat=disconnect_hwa_too(pan->eat, temp_count);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

				pae2=location_copy_hwa_too(pan->eat, temp_count);
				pan->eat=disconnect_hwa_too(pan->eat, temp_count);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

				if((*pturn)==0 && assam>0)
					//피두장 넘어가기
					;
				else if((*pturn)==1 && bssam>0)
					;
				else if((*pturn)==2 && cssam>0)
					;
				else
					//(*pturn)의 플레이어에게 나머지 사람들이 피 한장씩
			}

			else
			{
				plus_pae=location_copy_hwa_too(pan->eat,temp_count);
				pan->eat=disconnect_hwa_too(pan->eat,temp_count);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
			}
		}

		else
			//A를 내고 바닥에 아무것도 없을 경우
		{
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
			pan->eat=plus_link_hwa_too(pan->eat,pae3);
		}
	}
	else
		//꼬라박, 내운을 믿는다
	{
		//뒷패가 붙을 경우
		if (temp_count=compare_hwa_too(pae3.num,pan->eat,1))

		{
			samecheck=same_check_hwa_too(pan_eat, pae3.num, pindex);
			//뒷패를 선택하는 경우
			if(samecheck==1)
			{
				printf("어떤것을 드시겠습니까?");
				scanf("%d",&a);
				if(a==2)
				{
					pan->eat=plus_link_hwa_too(pan->eat, pae1);
					plus_pae=location_copy_hwa_too(pan->eat,temp_count+1);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
					pan->eat=disconnect_hwa_too(pan->eat,temp_count+1);
				}

				if(a==1)
				{
					pan->eat=plus_link_hwa_too(pan->eat, pae1);
					plus_pae=location_copy_hwa_too(pan->eat,temp_count);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
					player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
					pan->eat=disconnect_hwa_too(pan->eat,temp_count);
				}

			}

			//뒷패가 3장인 경우
			if(samecheck==2)
			{
				printf("았싸 4장");
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);

				pae2=location_copy_hwa_too(pan->eat, temp_count);
				pan->eat=disconnect_hwa_too(pan->eat, temp_count);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

				pae2=location_copy_hwa_too(pan->eat, temp_count);
				pan->eat=disconnect_hwa_too(pan->eat, temp_count);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);

				pae2=location_copy_hwa_too(pan->eat, temp_count);
				pan->eat=disconnect_hwa_too(pan->eat, temp_count);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae2);
			
				pan->eat=plus_link_hwa_too(pan->eat, pae1);

				if((*pturn)==0 && assam>0)
					//피두장 넘어가기
					;
				else if((*pturn)==1 && bssam>0)
					;
				else if((*pturn)==2 && cssam>0)
					;
				else
					//(*pturn)의 플레이어에게 나머지 사람들이 피 한장씩
			}

			else
			{
				pan->eat=plus_link_hwa_too(pan->eat, pae1);
				plus_pae=location_copy_hwa_too(pan->eat,temp_count);
				pan->eat=disconnect_hwa_too(pan->eat,temp_count);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
				player->pae_eat=plus_link_hwa_too(player->pae_eat,plus_pae);
			}
		}

		else if(pae1==pae3)
		{
			printf("쪽");
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae1);
			player->pae_eat=plus_link_hwa_too(player->pae_eat,pae3);
		}

		else
		{
			pan->eat=plus_link_hwa_too(pan->eat, pae1);
			pan->eat=plus_link_hwa_too(pan->eat, pae3);
		}

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
void save_player_hwa_too(struct hwa_too_gamer *p,int num,int *turn)
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
	printf("1\n");
	//플레이어 든거 저장
	temp=p->pae_in_hand;
	for (;temp;temp=temp->next)
	{
		fwrite(temp,sizeof(struct hwa_too),1,ifp);
		fflush(ifp);
	}
	printf("3\n");
	fwrite(&check,sizeof(struct hwa_too),1,ifp);
	fflush(ifp);
	//플레이어 먹은거 저장
	temp=p->pae_eat;
	for (;temp;temp=temp->next)
	{
		fwrite(temp,sizeof(struct hwa_too),1,ifp);
		fflush(ifp);
	}
	printf("4\n");
	fwrite(&check,sizeof(struct hwa_too),1,ifp);
	fflush(ifp);
	//플레이어 점수 저장
	integer_temp=p->point;
	fwrite(&integer_temp,sizeof(int),1,ifp);
	fflush(ifp);
	printf("5\n");
	//플레이어 상태 저장
	string_temp=p->status;
	fwrite(string_temp,sizeof(char)*20,1,ifp);
	fflush(ifp);
	printf("6\n");
	//플레이어 돈 저장
	integer_temp=p->money;
	fwrite(&integer_temp,sizeof(int),1,ifp);
	fflush(ifp);
	printf("7\n");
	//플레이어 순서 저장
	integer_temp=*turn;
	fwrite(&integer_temp,sizeof(int),1,ifp);
	fflush(ifp);
	printf("8\n");
	fclose(ifp);
	return;
}
void save_hwa_too_pan(struct hwa_too_pan *p)
{
	FILE *ifp;
	struct hwa_too end={.num=-1,.name={'\0'},.check_num=0,.next=NULL};
	struct status_pan_hwa_too ttemp;
	struct hwa_too *temp;
	ifp=fopen("pan","wb");
	//깔린 화투 저장
	printf("실행1\n");
	temp=p->eat;
	for (;temp;temp=temp->next)
	{
		fwrite(temp,sizeof(struct hwa_too),1,ifp);
		fflush(ifp);
	}
	printf("실행2\n");
	fwrite(&end,sizeof(struct hwa_too),1,ifp);
	fflush(ifp);
	//화투 더미 저장
	temp=p->dummy;
	for (;temp;temp=temp->next)
	{
		fwrite(temp,sizeof(struct hwa_too),1,ifp);
		fflush(ifp);
	}
	printf("실행3\n");
	fwrite(&end,sizeof(struct hwa_too),1,ifp);
	fflush(ifp);
	//전체 화투판의 상태 저장
	ttemp=p->pan_status;
	fwrite(&ttemp,sizeof(struct status_pan_hwa_too),1,ifp);
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
struct hwa_too_gamer *load_player_hwa_too(struct hwa_too_gamer *p,int *turn)
{
	struct hwa_too tem[48]={{0}};
	FILE *ofp;
	int count=0;
	char string_temp[20]={'\0'};
	struct hwa_too temp;
	int i,integer_temp;
	ofp=fopen("gostop","rb");
	//손에 들고 있는거 전부 해제후, 불러 온 것으로 채움.
	p->pae_in_hand=killing_hwa_too(p->pae_in_hand);
	while(1)
	{
		printf("%d\n",temp.num);
		fread(&temp,sizeof(struct hwa_too),1,ofp);
		if (temp.num==-1)
			break;
		else
		{
			p->pae_in_hand=plus_link_hwa_too(p->pae_in_hand,temp);
		}
	}
	//들고 있는거 하나하나씩 불러 와
	//먹은거 전부 해제후, 불러 온 것으로 채움.
	p->pae_eat=killing_hwa_too(p->pae_eat);
	while(1)
	{
		printf("%d\n",temp.num);
		fread(&temp,sizeof(struct hwa_too),1,ofp);
		if (temp.num==-1)
			break;
		else
		{
			p->pae_eat=plus_link_hwa_too(p->pae_eat,temp);
		}
	}
	//먹었던거 하나하나씩 불러와
	//점수 불러와
	fread(&integer_temp,sizeof(int),1,ofp);
	p->point=integer_temp;
	//점수 넣어
	fread(string_temp,sizeof(char)*20,1,ofp);
	strcpy(p->status,string_temp);
	//상태 넣어
	fread(&integer_temp,sizeof(int),1,ofp);
	p->money=integer_temp;
	//돈 넣어
	fread(&integer_temp,sizeof(int),1,ofp);
	*turn=integer_temp;
	//순서 넣어
	fclose(ofp);
	return;
} 
struct hwa_too_pan *load_hwa_too_pan(struct hwa_too_pan *p)
{
	FILE *ofp;
	struct hwa_too temp;
	struct status_pan_hwa_too end;
	ofp=fopen("pan","rb");
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
	fread(&end,sizeof(struct status_pan_hwa_too),1,ofp);
	p->pan_status=end;
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
int result_hwa_too(struct hwa_too_gamer *player[])
{
	int i;
	char again;
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
	while (1)
	{
		printf("한번더 하시겠습니까? (y/n)\n");
		getchar();
		scanf("%c",&again);
		if (again=='Y' || again=='y')
			return 1;
		else if (again=='N' || again=='n')
			return 0;
		else
		{
			printf("잘못된 접근입니다.\n");
			sleep(2);
		}
	}
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
	int* pturn=turn;
	char system_command[7]=" ";
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
	}
	while (1){											//전체적인 while문
		for (i=0;i<3;i++)
		{
			player[i]->pae_eat=killing_hwa_too(player[i]->pae_eat);
			player[i]->point=0;
		}
		for (i=0;i<48;i++)								//다시 시작할때 초기화
		{
			check[i]=0;
		}
		tot=0;											//총 횟수 초기화자
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
		while (1) {											//한판당의 while문
#if DEBUG
			print_hwa_too(player[0]->pae_in_hand);
			print_hwa_too(player[0]->pae_eat);
#endif
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
			while(1){
				sorting_hwa_too(pan->eat);
				sort++;
				if(sort==100)
					break;
			}
			system("clear");
			for (i=0; i<3; i++) {
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
					if (sort==200)
						break;
				}
				if (player[i]->pae_eat==NULL)
					printf("\n");
				else
					print_hwa_too(player[i]->pae_eat);
				printf("\n");
				/*				if (rule_chong_tong(player[i]->pae_in_hand))
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
					*pturn--;
			}
			else if(strcmp(system_command,"h")==0)
			{
				print_help();
				printf("r을 누르게 되면 화면으로 돌아가게 됩니다.\n");
				scanf("%s",system_command);
				if(strcmp(system_command,"r")==0)	
					*pturn--;
			}
			else if(strcmp(system_command,"save")==0)
			{
				temp_turn=0;
				printf("ㄱㄳ\n");
				while(1)
				{
					printf("ㄱㄱ\n");
					save_player_hwa_too(player[temp_turn],temp_turn,&turn);
					temp_turn++;
					if (temp_turn==3)
						break;
				}
				save_hwa_too_pan(pan);
				printf("저장완료!\n");
				sleep(2);
				*pturn--;
			}
			else if(strcmp(system_command,"load")==0)
			{
				temp_turn=0;
				printf("저장한다\n");
				while(1)
				{
					printf("불러\n");
					player[temp_turn]=load_player_hwa_too(player[temp_turn],&turn);
					temp_turn++;
					if (temp_turn==3)
						break;
				}
				pan=load_hwa_too_pan(pan);
				printf("불러오기 완료!\n");
				sleep(2);
				*pturn--;
			}
			else if(0<atoi(system_command) && atoi(system_command)<8)
			{
				player[turn]->pae_in_hand=play_hwa_too(atoi(system_command),player[turn],pan); //dummy부분 주의
			}
			else
			{
				printf("잘못된 명령입니다.\n");
				*pturn--;
				tot--;
				sleep(2);
			}
			tot++;
			*pturn++;
			if(*pturn==3)
				*pturn=0;
			printf("%d\n",tot);
			if (tot==21)
			{
				printf("게임종료!\n");
				player[0]->point=count_checking_hwa_too(player[0]->pae_eat);
				player[1]->point=count_checking_hwa_too(player[1]->pae_eat);
				player[2]->point=count_checking_hwa_too(player[2]->pae_eat);
				if((result_hwa_too(player))==1)
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
