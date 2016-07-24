#include <stdio.h>
int main(void){
	int k, z=0, y, l=0, year, month, n, remain;
	int mon[12] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	int ymon[12] = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	printf("년도를 입력하세요 : ");                           //년도 입력받음
	scanf("%d", &year);
	printf("월을 입력하세요 : ");                             //월 입력받음
	scanf("%d", &month);
	for (k=1; k<year; k++)                                    //윤년선택후 추가되는 나머지 계산
	{if (k%4==0 && k%100 !=0 || k%100 ==0 && k%400==0)
			l+=2;                                             //윤년일 경우 7로 나누면 나머지가 2이므로
		else
			l+=1;};                                           //평년일 경우 7로 나누면 나머지가 1이므로
	l=l%7;
	for (y=1; y<month; y++)
	{if (year%4==0 && year%100!=0 || year%100 ==0 && year%400==0)
			z+=ymon[y-1];                                       //윤년일 경우 
		else
			z+=mon[y-1];};                                      //평년일 경우 
	z=z%7;
	remain = l+z;

	if (remain>=7)
		remain=remain%7;                                        //remain값을 이용해서 요일 출력
	if (remain==0)
		printf("%d년 %d월 1일은 월요일입니다.\n", year, month);
	if (remain==1)
		printf("%d년 %d월 1일은 화요일입니다.\n", year, month);
	if (remain==2)
		printf("%d년 %d월 1일은 수요일입니다.\n", year, month);
	if (remain==3)
		printf("%d년 %d월 1일은 목요일입니다.\n", year, month);
	if (remain==4)
		printf("%d년 %d월 1일은 금요일입니다.\n", year, month);
	if (remain==5)
		printf("%d년 %d월 1일은 토요일입니다.\n", year, month);
	if (remain==6)
		printf("%d년 %d월 1일은 일요일입니다.\n", year, month);

	return 0;
}
