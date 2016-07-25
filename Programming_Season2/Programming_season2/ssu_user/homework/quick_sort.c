#include <stdio.h>
#include <time.h>
#include <stdlib.h>										//qsort함수를 이용하기 위해 stdlib.h라는 헤더파일 추가.
#define SIP 100000										//숫자가 크기 때문에 기호상수 선언.
int compare_integer (const void *a, const void *b) 
{														//qsort함수의 네번째 인자로 두 정수를 비교할 함수가 필요하여 작성한 비교하는 함수.
	if ( *(int *)a > *(int *)b )
		return -20132431;								//참조한 값중 앞의 것이 클때 내림차순으로 정렬하기 위해 음수값을 리턴함.(양수 리턴시에 오름차순으로 정렬됨.)
	else if ( *(int *)a < *(int *)b)
		return 20132431;								//뒤의 값이 크면 양수값을 리턴함.
	return 0;											//두개의 값이 같으면 0을 리턴함.
}
int main(void) 
{
	int ary[SIP]={0};									//배열은 선언함과 동시에 0으로 전부 초기화.
	int num=0;
	clock_t start, end, diff, ex_time;					//실행시간의 클록과 초를 측정하기 위해 clock_t변수 선언.
	srand(time(NULL));									//난수 생성을 위한 srand(time(NULL)).
	while(num!=SIP) 
	{
		ary[num]=rand();
		num++;
	}													//10만개의 배열에 모두 난수 입력.
	printf("정렬 시작\n");
	start=clock();										//클록 측정시작.
	qsort(ary, SIP, sizeof(int), compare_integer);		//qsort함수에 배열의 주소, 갯수, 데이터 하나당 크기, 비교하는 함수의 주소를 입력.
	end=clock();										//클록 측정완료.
	printf("정렬 완료\n");
	diff=end-start;										//나중클록과 처음클록의 차이를 구함.
	ex_time=diff/CLOCKS_PER_SEC;						//그 차이를 초로 환산함.
	printf("%d번 정렬 실행시간 : %d 클록, %d 초", SIP, diff, ex_time);
	return 0;
}
