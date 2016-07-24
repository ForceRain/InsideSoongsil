#include <stdio.h>
void change(float kilo, float kph) {
	float second, total_second;
	int hour, min;
	total_second=kilo/(kph/3600);
	hour=total_second/3600;
	min=(total_second-hour*3600)/60;
	second=total_second-hour*3600-min*60;
	printf("%.2f km => %d시간 %d분 %.3f 초 소요됨", kilo, hour, min, second);
	return;
}

int main(void) {
	float kilo, kph;
	printf("*거리를 입력하시오(km단위) : ");
	scanf("%f", &kilo);
	printf("*시속을 입력하시오(km/h단위) : ");
	scanf("%f", &kph);
	change(kilo, kph);
	return 0;
}

