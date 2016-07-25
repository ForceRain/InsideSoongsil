#include <stdio.h>
#include <time.h>
int main(void) {
	struct tm *t;
	time_t	now;
	now=time(NULL);
	t=localtime(&now);
	printf("오늘은 %d년 %d월 %d일 ",(t->tm_year)+1900, (t->tm_mon) +1, t->tm_mday);
	switch (t->tm_wday) {
		case 0 :
			printf("일요일입니다.\n");
			break;
		case 1 :
			printf("월요일입니다.\n");
			break;
		case 2 :
			printf("화요일입니다.\n");
			break;
		case 3 :
			printf("수요일입니다.\n");
			break;
		case 4 :
			printf("목요일입니다.\n");
			break;
		case 5 :
			printf("금요일입니다.\n");
			break;
		case 6 :
			printf("토요일입니다.\n");
			break;
		default :
			printf("뭐하노\n");
			break;
	}
	printf("현재 시간은 ");
	if ((t->tm_hour)>=13)
		printf("오후 %d시 %d분 %d초입니다.\n", (t->tm_hour)-12, (t->tm_min), (t->tm_sec));
	else
		printf("오전 %d시 %d분 %d초입니다.\n", (t->tm_hour), (t->tm_min), (t->tm_sec));
	return 0;
}
