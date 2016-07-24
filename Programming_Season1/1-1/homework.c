#include <stdio.h>
#include <time.h>
int main(void){
	unsigned cur_time;
	cur_time = time(NULL);
	printf("1970년 1월 1일부터 흐른 초 : %u초\n", cur_time);
	return 0;
}


