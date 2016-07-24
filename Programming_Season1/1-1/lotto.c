#include <stdio.h>
#include <stdlib.h>
#include <time.h>
int main(void){
	int lotto[5][6], i=0, j=0, n, k, l, tmp;
	srand(time(NULL));
	for(i=0; i<5 ;i++){
		for (j=0; j<6; j++){
choose:
			l = (rand()%45)+1;   //우선 l에 rand함수를 이용, 1부터 45까지의 값을 입력함.
			for (k=0; k<=j-1; k++)
				if (l==lotto[i][k]) //만약 l값과 lotto배열중의 값이 같은것이 있다면 goto문을 이용해서 다시 선택과정을 거침.
					goto choose;
			lotto[i][k] = l;}; //같은 l값이 없다면 lotto배열에 입력함.
		for (j=0; j<6; j++)
			for (n=6; n>j ; n--){
				//각 세트별로 bubble 정렬을 시행.
				if (lotto[i][n-1] > lotto[i][n]){ 
					tmp = lotto[i][n-1];
					lotto[i][n-1] = lotto[i][n];
					lotto[i][n] = tmp;};};};
	for (i=0; i<5; i++)
	{printf("%d세트 : ", i+1); //각 세트별로 정렬된 lotto배열을 출력함.
		for (j=0; j<6; j++)
			printf(" %2d,", lotto[i][j]);
		printf("\b \n");}; //줄 바꿈.
	return 0;
}

