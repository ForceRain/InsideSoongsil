#include <stdio.h>
int main(void) {
	int year, month,k,l,z=0,remain, remain1=0, remain2=0;
	int mon[12]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	int ymon[12]={31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};   //윤년일때의 달과 평년일때의 달을 구분해서 씀
	printf("년도를 입력 하세요 : ");
	scanf("%d", &year);
	printf("월을 입력 하세요 : ");
	scanf("%d", &month);
	for(k=1;k<year;k++) {
		if(k%4==0 && k%100!=0 || k%100==0 && k%400==0) 				//윤년일 경우 1년이 366일 이므로 7로 나눈 나머지인 2를 remain1에 더한다.
			remain1 +=2;
		else
			remain1 +=1;											//평년일 경우 1년이 365일 이므로 7로 나눈 나머지인 1을 remain1에 더한다.
	};
	for(l=0; l+1<month; l++) {
		if(year%4==0 && year%100!=0 || year%100==0 && year%400==0)	//입력된 year가 윤년일 경우 ymon배열을, 평년일 경우 mon배열을 이용한다.
			z += ymon[l];
		else
			z += mon[l];
	};							
	remain2 = z%7;													//매개변수 z에 저장된 날들의 수를 7로 나눈 나머지를 remain2에 저장한다.
	remain=remain1 + remain2;										//remain에 remain1과 remain2의 값을 입력한다.
	if(remain>=7)
		remain = remain%7;											//remain의 값이 7보다 같거나 클 경우 7로 나눈 나머지를 remain에 다시 저장한다.
	if(remain==0)
		printf("%d년 %d월 1일은 월요일입니다.",year,month);			//출력.
	if(remain==1)
		printf("%d년 %d월 1일은 화요일입니다.",year,month);
	if(remain==2)
		printf("%d년 %d월 1일은 수요일입니다.",year,month);
	if(remain==3)
		printf("%d년 %d월 1일은 목요일입니다.",year,month);
	if(remain==4)
		printf("%d년 %d월 1일은 금요일입니다.",year,month);
	if(remain==5)
		printf("%d년 %d월 1일은 토요일입니다.",year,month);
	if(remain==6)
		printf("%d년 %d월 1일은 일요일입니다.",year,month);
	return 0;
}



