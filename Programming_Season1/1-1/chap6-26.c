#include <stdio.h>
static int count=1;															//재귀함수를 이용해야 하기때문에 정적변수 적용
void halistone(int n) {
	if(n!=1) {																//n의 값이 1이 될 때 까지 if문의 조건을 이용한 재귀함수
		if ((n%2)==1) {														//n이 홀수 일 때
			n=3*n+1;
			printf("%7d ", n);
			count++;
			if ((count%5)==0)												//한 줄에 5개씩 출력해야 하므로 count의 값이 5의 배수일때 줄바꿈
				printf("\n");
			halistone(n);													//재귀함수 호출
			}
		else {																//n이 짝수 일 때
			n=n/2;	
			printf("%7d ", n);
			count++;
			if ((count%5)==0)
				printf("\n");
			halistone(n);
			};
		}
	else
		printf("\n수열 개수 : %d", count);									//마지막으로 수열의 갯수 출력
	return;
}
int main(void) {
	int input;
	printf("자연수를 하나 입력하세요 : ");
	scanf("%d", &input);
	printf("%d halistone 수열 : \n", input);
	printf("%7d ", input);													//출력을 같게 해주기 위해 임의로 지정
	halistone(input);														//함수 호출
	return 0;
}
