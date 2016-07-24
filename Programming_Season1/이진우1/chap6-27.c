#include <stdio.h>
void print_star(int m, int n, int print_num) {
	printf("*");									//별 하나 출력
	if (print_num<m)	{							//임의의 print_num변수를 지정, 한 줄에서 출력할 별의 개수를 나타냄
		print_num++;
		print_star(m,n,print_num);
	}
	else
		printf("\n");								//한 줄이 끝날때 개행문자
	return ;
}
int main(void) {
	int m,n,num, max;
	int print_num=1;
	printf("두 수를 입력하세요 : ");
	scanf("%d%d", &m, &n);
	max=(n-m)*2+1;									//실행할 횟수
	for(num=0; num<max; num++) {					//실행되는 횟수의 for문
		print_star(m,n,print_num);					//print_star실행
		if (num<max/2)								//절반이 진행될때까지 별의 갯수를 하나씩 증가시킴, 절반 이후는 별을 하나씩 감소 시킴
			m++;
		else
			m--;
	};
	return 0;
}
