#include <stdio.h>
#include <stdlib.h>
#include <string.h>
struct hwa_too{
	int num;
	char name[7];
	struct hwa_too *next;
};												//화투패를 구조체화
void print_hwa_too(struct hwa_too *p)			//화투 패를 출력
{
	if (p==NULL)								//NULL일때는 함수 종료.
	{
		printf("\n");
		return;	
	}
	else
	{
		printf("%2d%s ", p->num,p->name);
		print_hwa_too(p->next);					//재귀함수 형식으로 출력
	}
	return;
}
void print_turn(int j,int i)					//플레이어의 순서를 출력함
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
void sorting_hwa_too(struct hwa_too *p) {		//화투를 정렬함
	int temp;									//숫자를 저장할 임시변수
	char temp_string[7];						//문자열을 저장할 임시변수
	if (p->next==NULL)							//p->next가 가리키는 곳이 NULL이면 정렬X
		return;
	if(p->num>(p->next)->num)					//숫자크기로 정렬
	{
		temp=p->num;
		p->num=(p->next)->num;
		(p->next)->num=temp;
		strcpy(temp_string,p->name);
		strcpy(p->name,p->next->name);
		strcpy(p->next->name,temp_string);
	}
	if ((p->num)==(p->next->num) && (strcmp(p->name,(p->next)->name))>0)		//숫자가 같을 때, 광, 십, 오, 피, 쌍피를 가나다순으로 정렬하기위해 strcmp사용.
	{
	temp=p->num;
	p->num=(p->next)->num;
	(p->next)->num=temp;
	strcpy(temp_string,p->name);
	strcpy(p->name,p->next->name);
	strcpy(p->next->name,temp_string);
	}
		sorting_hwa_too(p->next);				//정렬이 끝나면 다음함수로 넘어감
	return;
}												//횟수는 최대로 발생할 횟수를 main함수에서 while문으로 돌려 준다.
void play_hwa_too(int count, struct hwa_too *p, struct hwa_too *q, struct hwa_too *r,struct hwa_too *s)
{
	int i=0;
	count--;
	if(count!=0)
		play_hwa_too(count,p->next,q,r,s);
	else
	{
		if(p->num==q->num)			//같은거 먹을 경우
		{
			
		}
		else						//그냥 내는 경우
		{
		}
	}
	return;	
}									//미완 
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
}									//미완
struct hwa_too *make_hwa_too(struct hwa_too init[],int check[],int num)		//화투패 생성
{
	int wow;
	struct hwa_too *pae=NULL;
	while (1){
		if (num==7)															//main함수에서 지정해준 값(화투패, 바닥에 까는 패, 화투 더미등의 갯수).
			break;
		wow=(rand()%48);													//랜덤하게 추출
		if (check[wow]!=-1)													//check배열은 이미 main에서 모두 0으로 초기화 되어있고, 48개의 화투를 초기화한 init배열도 같이 보내어 wow에서 뽑힌 숫자가 init과 check배열의 인덱스가 된다. 따라서, init[wow]에서 한번 뽑은 값에는 -1을 check[wow]에 대입한다.
		{
			pae=(struct hwa_too *)malloc(sizeof(struct hwa_too));			//동적 메모리 할당
			*pae=init[wow];													//pae가 가리키는 곳에 초기화된 값을 넣음.
			check[wow]=-1;
			num++;
			pae->next=make_hwa_too(init,check,num);							//pae->next에 재귀함수가 리턴하는 포인터 값을 저장, 링크드 리스트를 만듦.
			break;
		}
	}
	return pae;																//주소를 리턴
}
struct hwa_too *disconnect_hwa_too(struct hwa_too *p,int num)					//링크를 끊어주는 함수
{
	struct hwa_too *temp;													//입력받은 num에 따라 그 수치만큼 링크를 옮겨 감.
	num--;
	if (num==0)																//다 옮겨 갔을 때,
	{
		if (p->next==NULL)													//옮겨 간 곳이 마지막부분일 경우
		{
			free(p);														//p가 가리키는 곳의 메모리를 반환.
			p=NULL;															//p가 가리키는 곳을 NULL로 바꿈.
		}
		else
		{																	//옮겨 간 곳이 화투와 화투사이 일 경우
			temp=p->next;													//p->next의 값을 임시 포인터 변수인 temp에 저장.
			p->next=NULL;													//p->next가 가리키는 곳을 NULL화 시킴.
			free(p);														//p가 가리키는 곳의 메모리 반환.
			p=temp;															//p에 임시 포인터 변수 인 temp의 값을 대입.
		}
	}
	else																	//아직 그 위치에 도달하지 못했을 경우
	{
		p->next=disconnect_hwa_too(p->next,num);							//p->next를 매개변수로 보냄.
	}
	return p;																//포인터를 리턴.
}
int main(void) {
	struct hwa_too init[48]={{1,"광",NULL},{1,"오",NULL},{1,"피1",NULL},{1,"피2", NULL},{2,"십",NULL},{2,"오",NULL},{2,"피1",NULL},{2,"피2",NULL},{3,"광",NULL},{3,"오",NULL},{3,"피1",NULL},{3,"피2",NULL},{4,"십",NULL},{4,"오",NULL},{4,"피1",NULL},{4,"피2",NULL},{5,"십",NULL},{5,"오",NULL},{5,"피1",NULL},{5,"피2",NULL},{6,"십",NULL},{6,"오",NULL},{6,"피1",NULL},{6,"피2",NULL},{7,"십",NULL},{7,"오",NULL},{7,"피1",NULL},{7,"피2",NULL},{8,"광",NULL},{8,"십",NULL},{8,"피1",NULL},{8,"피2",NULL},{9,"쌍피",NULL},{9,"오",NULL},{9,"피1",NULL},{9,"피2",NULL},{10,"십",NULL},{10,"오",NULL},{10,"피1",NULL},{10,"피2",NULL},{11,"광",NULL},{11,"쌍피",NULL},{11,"피1",NULL},{11,"피2",NULL},{12,"광",NULL},{12,"십",NULL},{12,"오",NULL},{12,"쌍피",NULL}};
	int check[48]={0};
	int wow,i=0,j=0,command,turn=0,tot=0,sort=0;
	char status[20]=" ";
	char system_command[7]=" ";
	struct hwa_too *player[3]={NULL};
	struct hwa_too *eat_player[3]={NULL};
	struct hwa_too *eat=NULL;
	struct hwa_too *dummy=NULL;
	srand(time(NULL));
#if DEBUG
	printf("작동\n");
#endif
	for (i=0; i<3; i++)
	{
		player[i]=make_hwa_too(init,check,0);
	}
#if DEBUG
	printf("플레이어 마다 화투 배정 작동\n");
#endif
	eat=make_hwa_too(init,check,1);				//1부터 시작시 6장 뽑힘.
#if DEBUG
	printf("먹을거 만드는거 작동\n");
#endif
	dummy=make_hwa_too(init,check,-14);
#if DEBUG
	printf("화투더미 작동\n");
	printf("%2d%s\n",player[0]->num,player[0]->name);
#endif
	while (1) {
		for (i=0;i<3;i++)
		{
			print_hwa_too(player[i]);
			while(1)
			{
			sorting_hwa_too(player[i]);
			sort++;
			if(sort==21)
				break;
			}
			sort=0;
		}
		while(1){
			sorting_hwa_too(eat);
			sort++;
			if(sort==100)
				break;
		}
		system("clear");
		for (i=0; i<3; i++) {
			print_turn(turn,i);
			if (i==0)
				printf("A%10s : 패)",status);
			if (i==1)
				printf("B%10s : 패)",status);
			if (i==2)
				printf("C%10s : 패)",status);
			print_hwa_too(player[i]);
			printf("\n(점수 : /**/) 딴 화투) \n");
			//print_have_hwa_too(*eat_player[i]);
			printf("\n");
		}
		printf("깔린 패) ");
		print_hwa_too(eat);
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
		else if(strcmp(system_command,"b")==0)
#if DEBUG
	printf("작동7\n");
#endif
		else if(strcmp(system_command,"h")==0)
#if DEBUG
	printf("작동8\n");
#endif
		else if(strcmp(system_command,"save")==0)
#if DEBUG
	printf("작동9\n");
#endif
		else if(strcmp(system_command,"load")==0)
#if DEBUG
	printf("작동10\n");
#endif
		else if(0<atoi(system_command) && atoi(system_command)<8)
		{
			play_hwa_too(atoi(system_command),player[turn],eat,eat_player[turn],dummy); //dummy부분 주의
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
