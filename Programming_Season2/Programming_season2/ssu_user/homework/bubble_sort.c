#include <stdio.h>
#include <time.h>
#define SIP 100000											//10만이라는 큰숫자 이므로 기호상수 선언.
int main(void) 
{
	int ary[SIP]={0};										//배열 선언후 0으로 초기화.
	int num=0, i, j, tmp;
	clock_t start_clock, end_clock, diff_clock, ex_time;	//클록과 초를 이용하기 위한 변수들. 
	srand(time(NULL));										//무작위로 난수를 생성하기 위해서 srand(time(NULL))을 이용함.
	while(num!=SIP) 
	{
		ary[num]=rand();
		num++;												//ary배열에 10만개의 무작위의 수를 집어 넣는다.
	}
	printf("정렬시작 \n");									//버블정렬을 이용해서 정렬을 시작.
	start_clock=clock();									//정렬하는 부분의 클록을 알기위해 start_clock을 이용.
	for (i=0; i<SIP-1; i++) 
		for (j=SIP-1; j>i; j--) 
			if (ary[j-1]<ary[j]) 
			{												//프로그램 7.1의 버블정렬은 오름차순으로 정렬할 경우이므로, 내림차순을 위해서는 부등호 방향을 바꿔준다.
				tmp=ary[j-1];
				ary[j-1]=ary[j];
				ary[j]=tmp;
			}
	end_clock=clock();										//정렬이 끝났으므로 end_clock을 이용.
	printf("정렬완료 \n");
	diff_clock=end_clock-start_clock;						//클록의 차를 구함.
	ex_time=diff_clock/CLOCKS_PER_SEC;						//구한 클록의 차를 CLOCKS_PER_SEC이라는 매크로를 이용, 초단위로 환산함.
	printf("%d번 정렬하기 실행 시간 : %d 클록, %d 초\n", SIP, diff_clock, ex_time);
	return 0;
}
