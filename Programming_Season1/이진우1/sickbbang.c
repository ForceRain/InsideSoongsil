#include <stdio.h>
#include <time.h>
int main(void){
	unsigned cur_time;
	int pmonth[12] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	int ymonth[12] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	int year=0, month=0, day=0, hour=0, mi=0, se=0, i=0, k, count_yoonyear=0, count_year=0, count_month, exam_year;
	cur_time = time(NULL);
	printf("1970년 1월 1일부터 흐른 초 : %u초 \n", cur_time);
	for(k=1970; cur_time>86400*365; k++) {
		if((k%4==0 && k%100!=0 || k%100==0 && k%400==0) && cur_time>86400*366) {
			cur_time = cur_time-86400*366;
			count_yoonyear++;
		}
		else {
			cur_time = cur_time-86400*365;
			count_year++;
		};
	};
	year=count_yoonyear+count_year;
	exam_year=count_yoonyear+count_year+1970;
	if(exam_year%4==0 && exam_year%100!=0 || exam_year%100==0 && exam_year%400==0)
		for(k=0; cur_time>86400*27; k++) {
			cur_time = cur_time-86400*ymonth[k];
			month++;
		}	
	else
		for(k=0; cur_time>86400*27; k++) {
			cur_time = cur_time-86400*pmonth[k];
			month++;
		};
	for(k=0; cur_time>86400;k++) {
		cur_time = cur_time-86400;
		day++;
	};
	for(k=0; cur_time>3600; k++) {
		cur_time = cur_time-3600;
		hour++;
	};
	for(k=0; cur_time>60; k++) {
		cur_time = cur_time-60;
		mi++;
	};
	se=cur_time;

	printf("%d년 %d월 %d일 %d시 %d분 %d초", year+1970, month+1, day+1, hour, mi, se);
	return 0;
}

		
	

