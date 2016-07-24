#include <stdio.h>
#include <stdlib.h>
#include <time.h>
int main(void){
	int lotto[5][6], i=0, j=0, n, k, l, tmp;
	srand(time(NULL));
	for(i=0; i<5 ;i++){
		for (j=0; j<6; j++){
			l = (rand()%45)+1;                        //l에 rand함수를 이용, 1부터 45까지의 임의의 값을 입력함.
			for (k=0; k<=j-1; k++)
				if (l==lotto[i][k])                   //만약 l값과 lotto배열중의 값이 같을 경우, 
					while (l==lotto[i][k])
					{l = (rand()%45)+1;};             //l값을 다시 지정한다.

					lotto[i][j] = l;};                //l값과 lotto배열의 값이 다를경우 그대로 lotto배열에 입력한다.

		for (j=0; j<6; j++)
			for (n=6; n>j ; n--){
				                                      //각 세트별로 bubble 정렬을 시행한다.
				if (lotto[i][n-1] > lotto[i][n]){ 
					tmp = lotto[i][n-1];
					lotto[i][n-1] = lotto[i][n];
					lotto[i][n] = tmp;};};};
	for (i=0; i<5; i++)
	{printf("%d세트 : ", i+1);                        //각 세트별로 정렬된 lotto배열을 출력함.
		for (j=0; j<6; j++)
			printf(" %2d,", lotto[i][j]);
		printf("\b \n");};                            //줄 바꿈.
	return 0;
}

